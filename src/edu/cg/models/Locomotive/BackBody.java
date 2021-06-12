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
        glTranslated(0,0, -Specification.BACK_BODY_DEPTH/2);
        Materials.setMaterialChassis();
        chassis.render();
        // X=-rx/2:
        Materials.setMaterialWindow();
        glTranslated(0,0, - Specification.BACK_BODY_DEPTH/4 - Specification.EPS);
        windowTwo.render();
        glTranslated(0,0, Specification.BACK_BODY_DEPTH/4 + 2 * Specification.EPS);
        windowThree.render();
        glTranslated(Specification.BACK_BODY_WIDTH/4 ,0,Specification.BACK_BODY_DEPTH/4);
        windowOne.render();
        glTranslated( -Specification.BACK_BODY_WIDTH/2 - 4 * Specification.EPS,-Specification.BACK_BODY_HEIGHT/12,Specification.BACK_BODY_DEPTH/12);
        door.render();
        glPopMatrix();

        glPushMatrix();
        glTranslated( 0,Specification.BACK_BODY_HEIGHT/6,-0.9*Specification.BACK_BODY_DEPTH - 2 * Specification.EPS);
        backWindow.render();

        glPopMatrix();


        glPushMatrix();
        glTranslated(-1.25 * Specification.BACK_BODY_WIDTH/2 - Specification.EPS,-Specification.BACK_BODY_HEIGHT/2,- Specification.BACK_BODY_DEPTH/2);
        glRotated(90,0,1,0);
        wheel.render();
        glPopMatrix();
        glPushMatrix();
        glTranslated(1.25 * Specification.BACK_BODY_WIDTH/2 + Specification.EPS,-Specification.BACK_BODY_HEIGHT/2,-Specification.BACK_BODY_DEPTH/2);
        glRotated(-90,0,1,0);
        wheel.render();
        glPopMatrix();


        glPushMatrix();
        glTranslated(0,Specification.BACK_BODY_HEIGHT/2,-Specification.BACK_BODY_DEPTH);
        roof.render();
        glPopMatrix();
    }


    @Override
    public void init() {

    }
}
