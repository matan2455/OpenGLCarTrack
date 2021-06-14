package edu.cg.models.Locomotive;

import edu.cg.models.Box;
import edu.cg.models.IRenderable;

import static org.lwjgl.opengl.GL21.*;

/***
 * A 3D locomotive front-body model renderer.
 */
public class FrontBody implements IRenderable {
    // The front body is composed of one box that represents the locomotive front body.
    private Box chassis = new Box(Specification.FRONT_BODY_WIDTH,
            Specification.FRONT_BODY_HEIGHT,
            Specification.FRONT_BODY_DEPTH);
    // The front body is composed of two front wheels.
    // Use a single wheel renderer along with affine transformations to render the two wheels.
    private Wheel wheel = new Wheel();
    // The front body is composed of a chimney model.
    private Chimney chimney = new Chimney();
    // The front body is composed of two front lights.
    // Use a single car light renderer along with affine transformations to render the two car lights.
    private CarLight carLight = new CarLight();

    @Override
    public void render() {

        Materials.setMaterialChassis();
        glPushMatrix();

        chassis.render();
        //In this part we draw the two front wheels
        glPushMatrix();
        glTranslated(0,0,-Specification.FRONT_BODY_DEPTH/2 + Specification.FRONT_BODY_DEPTH*0.6);

        glPushMatrix();
            glTranslated(Specification.FRONT_BODY_WIDTH/2,0,0);
            glTranslated(0,-Specification.FRONT_BODY_HEIGHT/2,0);
            wheel.render();
        glPopMatrix();

        glPushMatrix();
            glTranslated(-Specification.FRONT_BODY_WIDTH/2,0,0);
            glTranslated(0,-Specification.FRONT_BODY_HEIGHT/2,0);
            wheel.render();
        glPopMatrix();

        glPopMatrix();

        //In this part we draw the two front lights
        Materials.setMaterialLightCase();
        glPushMatrix();
        glTranslated(0,0,-Specification.FRONT_BODY_DEPTH/2 + Specification.FRONT_BODY_DEPTH);

        glPushMatrix();
        glTranslated(Specification.FRONT_BODY_WIDTH/4,0,0);
        carLight.render();
        glPopMatrix();

        glPushMatrix();
        glTranslated(-Specification.FRONT_BODY_WIDTH/4,0,0);
        carLight.render();
        glPopMatrix();

        glPopMatrix();

        //In thi part we draw the chimney
        glPushMatrix();
        glTranslated(0,1.25*Specification.FRONT_BODY_HEIGHT,0);
        chimney.render();
        glPopMatrix();
        glPopMatrix();
        glPopMatrix();
    }

    @Override
    public void init() {
        chassis.init();
        wheel.init();
    }
}
