package edu.cg.models.Locomotive;

import edu.cg.models.IRenderable;
import edu.cg.util.glu.Cylinder;
import edu.cg.util.glu.Disk;

import static org.lwjgl.opengl.GL11.*;

/***
 * A 3D roof model renderer.
 * The roof is modeled using a cylinder bounded by disks that undergo a non-uniform scaling.
 */
public class Roof implements IRenderable {

    @Override
    public void render() {
        glPushMatrix();

        Materials.setMaterialRoof();
        glScaled((float)(Specification.ROOF_WIDTH/Specification.ROOF_HEIGHT)/2, 0.75,1);

        new Cylinder().draw((float) Specification.ROOF_HEIGHT,(float) Specification.ROOF_HEIGHT,(float) (Specification.ROOF_DEPTH),20,1);
        glTranslated(0,0,Specification.ROOF_DEPTH);

        new Disk().draw(0f,(float) Specification.ROOF_HEIGHT,20,1);
        glTranslated(0,0,-Specification.ROOF_DEPTH);
        glRotated(-180,1,0,0);


        glPopMatrix();
    }

    @Override
    public void init() {

    }
}
