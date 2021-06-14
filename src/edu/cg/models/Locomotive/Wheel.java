package edu.cg.models.Locomotive;

import edu.cg.models.IRenderable;
import edu.cg.util.glu.Cylinder;
import edu.cg.util.glu.Disk;

import static org.lwjgl.opengl.GL21.*;

/***
 * A simple 3D wheel renderer. The 3D wheel is centered at the origin, and its oriented along the x-axis.
 * This means that the wheel is parallel to the YZ-axis.
 */
public class Wheel implements IRenderable {
    @Override
    public void render() {
        glPushMatrix();
        // TODO(3) : Render the wheel using a Cylinder, and disks that about the cylinder.
        //First we arrange the Tire position, material and draw it.
        glRotated(90,0,1,0);
        glTranslated(0,0, - Specification.WHEEL_WIDTH / 2);
        Materials.setMaterialWheelTire();
        new Cylinder().draw((float) Specification.WHEEL_RADIUS, (float) Specification.WHEEL_RADIUS, (float) Specification.WHEEL_WIDTH,20,1);

        //Now we draw on side of the tire with rim.
        glTranslated(0,0, Specification.WHEEL_WIDTH);
        new Disk().draw( 0f,(float) Specification.WHEEL_RADIUS, 20,1);
        Materials.setMaterialWheelRim();
        glTranslated(0,0, Specification.EPS);
        new Disk().draw(0f,(float) Specification.WHEEL_RIM_RADIUS, 20,1);

        //Finally rotate and draw the second side of the tire with rim.
        glTranslated(0,0, -Specification.WHEEL_WIDTH);
        glRotated(180,1,0,0);
        Materials.setMaterialWheelTire();
        new Disk().draw(0f, (float) Specification.WHEEL_RADIUS, 20,1);
        Materials.setMaterialWheelRim();
        glTranslated(0,0, Specification.EPS);
        new Disk().draw(0f ,(float) Specification.WHEEL_RIM_RADIUS, 20,1);
        glPopMatrix();

    }

    public void drawRims() {
        glPushMatrix();
        glRotated(180, 0, 1, 0);
        Materials.setMaterialWheelRim();
        new Disk().draw(0f, (float) Specification.WHEEL_RIM_RADIUS, 20, 1);
        Materials.setMaterialWheelTire();
        new Disk().draw((float) Specification.WHEEL_RIM_RADIUS, (float) Specification.WHEEL_RADIUS, 20, 1);
        glPopMatrix();
    }

    @Override
    public void init() {
    }
}
