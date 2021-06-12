package edu.cg.models.Locomotive;

import edu.cg.algebra.Vec;
import static org.lwjgl.opengl.GL21.*;

/**
 * TODO(0): Read instructions below.
 * This class contains different material properties that can be used to color different surfaces of the locomotive
 * model. You need to use the static methods of the class inorder to define the color of each surface. Note, defining
 * the color using these static methods will be useful in the next assignment.
 *
 * For example:
 * If you want to render the locomotive back chassis, then the right way to do this would be:
 *     TrackMaterials.setMaterialChassis();
 *     chassis.render();
 * Instead of:
 *     glColor3d(r,g,b,1.0);
 *     chassis.render();
 *
 *
 *  Note: you would still want to call glColor3d(r,g,b,1.0);  in the definition of TrackMaterials.setMaterialChassis().
 */
public final class Materials {
    // TODO: We defined some colors that you can use in-order to reproduce our result.
    //       you can use them or define your own color. Note that the colors are stored in Vec objects.
    //       We added a new method called toGLColor() that can be used to return the color in a 4-element float array.
    //       This array can be passed to glColor3fv.
    private static final Vec BLACK = new Vec(0f);
    private static final Vec WHITE = new Vec(1f);
    private static final Vec DARK_RED = new Vec(0.5f, 0.f, 0f);
    private static final Vec DARK_GREY = new Vec(25f / 255f, 25f / 255f, 25f / 255f);
    private static final Vec GREY = new Vec(125f / 255f, 125f / 255f, 125f / 255f);
    private static final Vec LIGHT_GREY = new Vec(225f / 255f, 225f / 255f, 225f / 255f);
    private static final Vec DARK_BLUE = new Vec(0f, 0f, 25f / 255f);

    private Materials() {
    }

    public static void setMaterialRoof() {
        glColor4fv(BLACK.toGLColor());
    }

    public static void setMaterialChassis() {
        glColor4fv(DARK_RED.toGLColor());
    }

    public static void setMaterialWheelTire() {
        float col[] = { .01f, .01f, .01f };
        glColor3fv(col);
        glMaterialf(GL_FRONT, GL_SHININESS, 100);
        glMaterialf(GL_FRONT, GL_DIFFUSE,  0);
        glMaterialf(GL_FRONT, GL_SPECULAR,  0);
        glMaterialf(GL_FRONT, GL_EMISSION,  0);
    }

    public static void setMaterialWheelRim() {
        // TODO: Set the rims' material properties.
        glColor4fv(GREY.toGLColor());
    }

    public static void setMaterialWindow() {
        // TODO(4): Use this method to define the color of the locomotive chassis.
        glColor4fv(GREY.toGLColor());
    }

    public static void setMaterialFrontLight() {
        // TODO: Consult our implementation for how we set the material properties for the car's lights
        //  and plan your implementation for the remaining methods.
        float[] col = LIGHT_GREY.toGLColor();
        glColor4fv(col);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, col);
        glMaterialfv(GL_FRONT, GL_SPECULAR, BLACK.toGLColor());
        glMaterialfv(GL_FRONT, GL_EMISSION, col);
    }

    public static void setMaterialLightCase() {
        // TODO: Consult our implementation for how we set the material properties for the cases enveloping the car's lights
        //  and plan your implementation for the remaining methods.
        glColor3fv(DARK_GREY.toGLColor());
        glMaterialfv(GL_FRONT, GL_AMBIENT, DARK_GREY.mult(0.2).toGLColor());
        glMaterialfv(GL_FRONT, GL_DIFFUSE, DARK_GREY.toGLColor());
        glMaterialfv(GL_FRONT, GL_SPECULAR, BLACK.toGLColor());
        glMaterialfv(GL_FRONT, GL_EMISSION, BLACK.toGLColor());
    }
}
