package edu.cg;

import edu.cg.algebra.Vec;
import edu.cg.models.Locomotive.Locomotive;
import edu.cg.models.Track.Track;
import edu.cg.models.Track.TrackSegment;
import edu.cg.util.glu.Project;
import org.lwjgl.opengl.GL21;

import static org.lwjgl.opengl.GL21.*;

/**
 * An OpenGL model viewer
 */
public class Viewer {
    int canvasWidth, canvasHeight;
    private final GameState gameState; // Tracks the vehicle movement and orientation
    private final Locomotive car; // The locomotive we wish to render.
    private final Track gameTrack; // The track we wish to render.
    // driving direction, or looking down on the scene from above.
    private Vec carCameraTranslation; // The accumulated translation that should be applied on the car and camera.
    private boolean isModelInitialized = false; // Indicates whether initModel was called.
    private boolean isDayMode = true; // Indicates whether the lighting mode is day/night.
    private boolean isBirdseyeView = false; // Indicates whether the camera's perspective corresponds to the vehicle's
    private final double carInitialPositionX = 0;
    private final double carInitialPositionY = 1;
    private final double carInitialPositionZ = -20;
    private final double[] carInitialPosition = {carInitialPositionX,carInitialPositionY,carInitialPositionZ};
    private final double[] standardCameraPosition = {0,carInitialPositionY + 3,carInitialPositionZ+10};
//    private final double[] standardCameraPosition = {0.0,0.0,0.0};
    private final double[] birdEyeCameraPostion = {carInitialPositionX,carInitialPositionY+40,carInitialPositionZ};
    private final float[] dayLight = {1.0f,1.0f,1.0f,1.0f};
    private final float[] moonLight = {0.2f,0.2f,0.2f,1.0f};
    private final float[] glAmbient =  new float[] {1f,1f,1f,1f};
    private final float[] glSpecular = new float[] {1f,1f,1f,1f};
    private final float[] glPosition = new float[] {1f,1f,1f,0f};

    // TODO: set the car scale as you wish - we uniformly scale the car by 3.0.

    // TODO: You can add additional fields to assist your implementation, for example:
    // - Camera initial position for standard 3rd person mode(should be fixed)
    // - Camera initial position for birdseye view)
    // - Light colors
    // Or in short anything reusable - this make it easier for your to keep track of your implementation.



    public Viewer(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
        this.gameState = new GameState();
        this.gameTrack = new Track();
        this.carCameraTranslation = new Vec(0.0D);
        this.car = new Locomotive();
    }

    public void render() {
        if (!this.isModelInitialized)
            initModel();
        if (this.isDayMode) {

            glClearColor(0.35F, 0.6F, 0.85F, 1.0F);

        } else {
            glClearColor(0.0F, 0.0F, 0.25F, 1.0F);
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        // Step (1) Update the accumulated translation that needs to be
        // applied on the car, camera and light sources.
        updateCarCameraTranslation();
        // Step (2) Position the camera and setup its orientation.
        setupCamera();
        // Step (3) setup the lights.
        setupLights();
        // Step (4) render the car.
        renderVehicle();
        // Step (5) render the track.
        renderTrack();
    }

    public void init() {
        // TODO(*) In your final submission you need to make sure that BACK FACE culling is enabled.
        //      You may disable face culling while building your model, and only later return it.
        //      Note that doing so may require you to modify the way you present the vertices to OPENGL in order for the
        //      normal of all surface be facing outside. See recitation 8 for more information about face culling.
        glCullFace(GL_BACK);    // Set Culling Face To Back Face
        glEnable(GL_CULL_FACE); // Enable back face culling

        // Enable other flags for OPENGL.
        glEnable(GL_NORMALIZE);
        glEnable(GL_DEPTH_TEST);


        reshape(0, 0, canvasWidth, canvasHeight);
    }

    private void updateCarCameraTranslation() {
        // Here we update the car and camera translation values (not the ModelView-Matrix).
        // - We always keep track of the car offset relative to the starting
        // point.
        // - We change the track segments here if necessary.
        // getNextTranslation returns the delta - the change to be accounted for in the translation.
        // getNextTranslation returns the delta - the change to be accounted for in the translation.
        Vec ret = this.gameState.getNextTranslation();
        this.carCameraTranslation = this.carCameraTranslation.add(ret);
        // Min and Max calls to make sure we do not exceed the lateral boundaries of the track.
        double dx = Math.max(this.carCameraTranslation.x, -TrackSegment.ASPHALT_LENGTH / 2 - 2);
        this.carCameraTranslation.x = (float) Math.min(dx, TrackSegment.ASPHALT_LENGTH / 2 + 2);
        // If the car reaches the end of the track segment, we generate a new segment.
        if (Math.abs(this.carCameraTranslation.z) >= TrackSegment.TRACK_SEGMENT_LENGTH - this.carInitialPosition[2]) {
            this.carCameraTranslation.z = -((float) (Math.abs(this.carCameraTranslation.z) % TrackSegment.TRACK_SEGMENT_LENGTH));
            this.gameTrack.changeTrackSegment();
        }
    }

    private void setupCamera() {

        double eyeX = this.carCameraTranslation.x;
        double eyeY = this.carCameraTranslation.y;
        double eyeZ = this.carCameraTranslation.z;


        if (this.isBirdseyeView) {
            eyeX += birdEyeCameraPostion[0];
            eyeY += birdEyeCameraPostion[1];
            eyeZ += birdEyeCameraPostion[2];
            Project.gluLookAt((float)eyeX,(float)eyeY,(float)eyeZ, (float)eyeX,(float)eyeY-1,(float)eyeZ,0f,0f,-1f );

        } else {
            eyeX += standardCameraPosition[0];
            eyeY += standardCameraPosition[1];
            eyeZ += standardCameraPosition[2];
            Project.gluLookAt((float)eyeX,(float)eyeY,(float)eyeZ, (float)eyeX,(float)eyeY,(float)eyeZ-10,0f,1f,0f );
        }

    }

    private void setupLights() {


        if (this.isDayMode) {
            // TODO Setup day lighting.
            // * Remember: switch-off any light sources that were used in night mode and are not use in day mode.
            glDisable(GL_LIGHT1);
            Vec lightDirection = (new Vec(0.0D, 1.0D, 1.0D)).normalize();
            glLightfv(GL_LIGHT0, GL_DIFFUSE, dayLight);
            glLightfv(GL_LIGHT0, GL_SPECULAR, dayLight);
            glLightfv(GL_LIGHT0, GL_POSITION, new float[] {lightDirection.x,lightDirection.y,lightDirection.z,0f});
            glLightfv(GL_LIGHT0, GL_AMBIENT, new float[]{0.1F, 0.1F, 0.1F, 1.0F});
            glEnable(GL_LIGHT0);
        } else {
            // TODO Setup night lighting - here you should only set the ambient light source.
            //      The locomotive's spotlights should be defined in the car local coordinate system.
            //      so it is better to define the car light properties right before your render the locomotive rather
            //      than at this point.
            glDisable(GL_LIGHT0);
            glLightModelfv(GL_LIGHT1, new float[]{0.25F, 0.25F, 0.3F, 1.0F});
        }
    }

    private void renderTrack() {
        glPushMatrix();


//        glLoadIdentity();
        this.gameTrack.init();
//        // TODO : Note that if you wish to support textures, the render method of gameTrack must be changed.
        this.gameTrack.render();
        glPopMatrix();
    }

    private void renderVehicle() {
        // TODO: Render the vehicle.
        // * Remember: the vehicle's position should be the initial position + the accumulated translation.
        //             This will simulate the car movement.
        // * Remember: the car was modeled locally, you may need to rotate/scale and translate the car appropriately.
        // * Recommendation: it is recommended to define fields (such as car initial position) that can be used during rendering.
        // * You should set up the car lights right before you render the locomotive after the appropriate transformations
        // * have been applied. This ensures that the light sources are fixed to the locomotive (ofcourse all of this
        // * is only relevant to rendering the vehicle in night mode).
        glPushMatrix();

        glTranslated(this.carInitialPosition[0] + (double)this.carCameraTranslation.x, this.carInitialPosition[1] + (double)this.carCameraTranslation.y, this.carInitialPosition[2] + (double)this.carCameraTranslation.z);
        glRotated(180.0D - this.gameState.getCarRotation(), 0.0D, 1.0D, 0.0D);
        glScaled(5,5,5);
        glTranslated(0,0.3,0);
        if (!this.isDayMode) {
            this.setupCarLights();
        }
        this.car.render();
        glPopMatrix();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void initModel() {
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_NORMALIZE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_SMOOTH);
        this.gameTrack.init();
        this.car.init();
        this.isModelInitialized = true;
    }


    private void setupCarLights() {

        float[] rightLight = new float[]{0.1f, 0.15f, 0.5f, 1.0F};
        float[] leftLight = new float[]{-0.1f, 0.15f, 0.5f, 1.0F};
        this.setupCarLight(GL_LIGHT2, rightLight);
        this.setupCarLight(GL_LIGHT3, leftLight);
    }

    private void setupCarLight(int light, float[] pos) {
        glLightfv(light,GL_POSITION, pos);
        glLightfv(light, GL_SPOT_DIRECTION, new float[] {0.0f, 0.0f, 1.0f, 0.0f});
        glLightf(light, GL_SPOT_CUTOFF, 60.0F);
        glLightfv(light, GL_DIFFUSE, dayLight);
        glEnable(light);
    }

    private void initDayLight(int light) {


    }

    private void initMoonLight(int moonLight) {
        glLightModelfv(moonLight, new float[]{0.25F, 0.25F, 0.3F, 1.0F});
    }

    public void reshape(int x, int y, int width, int height) {
        // We recommend using gluPerspective, which receives the field of view in the y-direction. You can use this
        // method by importing it via:
        // >> import static edu.cg.util.glu.Project.gluPerspective;
        // Further information about this method can be found in the recitation materials.
        glViewport(x, y, width, height);
        canvasWidth = width;
        canvasHeight = height;
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        if (this.isBirdseyeView) {
            // TODO : Set a projection matrix for birdseye view mode.
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            Project.gluPerspective(60.0f,(float)width/(float)height,0.5f,100.0f);
        } else {
            // TODO : Set a projection matrix for third person mode.
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            Project.gluPerspective(120.0f,(float)width/(float)height,0.1f,100.0f);
        }
    }

    public void toggleNightMode() {
        this.isDayMode = !this.isDayMode;
    }

    public void changeViewMode() {
        this.isBirdseyeView = !this.isBirdseyeView;
    }
}
