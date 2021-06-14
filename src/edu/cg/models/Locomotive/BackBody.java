package edu.cg.models.Locomotive;

import edu.cg.models.Box;
import edu.cg.models.IRenderable;

import static org.lwjgl.opengl.GL21.*;


/***
 * A 3D locomotive back body renderer. The back-body of the locomotive model is composed of a chassis, two back wheels,
 * , a roof, windows and a door.
 */
public class BackBody implements IRenderable {
    // The back body is composed of one box that represents the locomotive front body.
    private Box chassis = new Box(Specification.BACK_BODY_WIDTH, Specification.BACK_BODY_HEIGHT, Specification.BACK_BODY_DEPTH);
    // The back body is composed of two back wheels.
    private Wheel wheel = new Wheel();
    // The back body is composed of a roof that lies on-top of the locomotive chassis.
    private Roof roof = new Roof();
    private Box windowOne = new Box(Specification.BACK_BODY_WIDTH/2 + 2 *Specification.EPS ,  Specification.BACK_BODY_HEIGHT/2, Specification.BACK_BODY_DEPTH/5);
    private Box door = new Box(Specification.BACK_BODY_WIDTH/2 +4 * Specification.EPS , 4 * Specification.BACK_BODY_HEIGHT/5, Specification.BACK_BODY_DEPTH/5);
    private Box windowTwo = new Box(Specification.BACK_BODY_WIDTH+4 * Specification.EPS, Specification.BACK_BODY_HEIGHT/2, Specification.BACK_BODY_DEPTH/5);
    private Box windowThree = new Box(Specification.BACK_BODY_WIDTH+ 4 * Specification.EPS, Specification.BACK_BODY_HEIGHT/2, Specification.BACK_BODY_DEPTH/5);
    private Box backWindow = new Box(3 * Specification.BACK_BODY_WIDTH/4, Specification.BACK_BODY_HEIGHT/2, Specification.BACK_BODY_DEPTH/5);

    // TODO (9): Define your window/door objects here. You are free to implement these models as you wish as long as you
    //           stick to the locomotive sketch.
    @Override
    public void render() {
        glPushMatrix();
        // TODO(8): Copy your code from HW5.
        Materials.setMaterialChassis();
        glPushMatrix();
        chassis.render();

        glPushMatrix();
        glTranslated(0,0,Specification.BACK_BODY_DEPTH/2 - Specification.BACK_BODY_DEPTH*0.625);

        glPushMatrix();
        glTranslated(Specification.BACK_BODY_WIDTH/2,0,0);
        glTranslated(0,-Specification.BACK_BODY_HEIGHT/2,0);
        wheel.render();
        glPopMatrix();

        glPushMatrix();
        glTranslated(-Specification.BACK_BODY_WIDTH/2,0,0);
        glTranslated(0,-Specification.BACK_BODY_HEIGHT/2,0);
        wheel.render();
        glPopMatrix();
        glPopMatrix();

        //In this part we draw roof
        glPushMatrix();
        glTranslated(0,0,-Specification.BACK_BODY_DEPTH/2 + 2*Specification.EPS);
        glTranslated(0,Specification.BACK_BODY_HEIGHT/2,0);
        roof.render();
        glPushMatrix();
        glRotated(-90,0,1,0);
        glTranslated(Specification.BACK_BODY_DEPTH,0,0);
        glPopMatrix();
        glPopMatrix();

        //In this part we front window
        Materials.setMaterialWheelRim();
        glPushMatrix();
        glTranslated(0,0,Specification.BACK_BODY_DEPTH/2 + Specification.EPS);

        glBegin(GL_QUADS);
        glNormal3d(0, 0, 1);
        glVertex3d(0.75*Specification.BACK_BODY_WIDTH*0.5, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0);
        glVertex3d(-0.75*Specification.BACK_BODY_WIDTH*0.5, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0);
        glVertex3d(-0.75*Specification.BACK_BODY_WIDTH*0.5, 0, 0);
        glVertex3d(0.75*Specification.BACK_BODY_WIDTH*0.5, 0, 0);
        glEnd();
        glPopMatrix();

        //In this part we draw back window
        glPushMatrix();
        glTranslated(0,0,-Specification.BACK_BODY_DEPTH/2 - Specification.EPS);
        glBegin(GL_QUADS);
        glNormal3d(0, 0, -1);
        glVertex3d(0.75*Specification.BACK_BODY_WIDTH*0.5, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0);
        glVertex3d(0.75*Specification.BACK_BODY_WIDTH*0.5, -Specification.BACK_BODY_HEIGHT/8, 0);
        glVertex3d(-0.75*Specification.BACK_BODY_WIDTH*0.5, -Specification.BACK_BODY_HEIGHT/8, 0);
        glVertex3d(-0.75*Specification.BACK_BODY_WIDTH*0.5, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0);
        glEnd();
        glPopMatrix();

        //In this part first window
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3d(1, 0, 0);
        glVertex3d( Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();

        //In this part door
        glPushMatrix();
        glBegin(GL_QUADS);
        glNormal3d(-1, 0, 0);
        glVertex3d( -Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5- Specification.EPS, -Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5- Specification.EPS, -Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5- Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();

        //In this part second window (both sides)
        glPushMatrix();
        glTranslated(0,0,- 0.3125*Specification.BACK_BODY_DEPTH);
        glBegin(GL_QUADS);
        glNormal3d(1, 0, 0);
        glVertex3d( Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glTranslated(0,0,- 0.3125*Specification.BACK_BODY_DEPTH);
        glBegin(GL_QUADS);
        glNormal3d(-1, 0, 0);
        glVertex3d( -Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();


        //In this part third window (both sides)
        glPushMatrix();
        glTranslated(0,0,- 0.625*Specification.BACK_BODY_DEPTH);
        glBegin(GL_QUADS);
        glNormal3d(1, 0, 0);
        glVertex3d( Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(Specification.BACK_BODY_WIDTH*0.5+ Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glTranslated(0,0,- 0.625*Specification.BACK_BODY_DEPTH);
        glBegin(GL_QUADS);
        glNormal3d(-1, 0, 0);
        glVertex3d( -Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5,0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.375*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, -0.25*Specification.BACK_BODY_HEIGHT*0.50, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glVertex3d(-Specification.BACK_BODY_WIDTH*0.5-Specification.EPS, 0.75*Specification.BACK_BODY_HEIGHT*0.5, 0.75*Specification.BACK_BODY_DEPTH*0.5);
        glEnd();
        glPopMatrix();
        glPopMatrix();
    }


    @Override
    public void init() {

    }
}
