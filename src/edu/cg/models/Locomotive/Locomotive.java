package edu.cg.models.Locomotive;

import edu.cg.models.IRenderable;

import static org.lwjgl.opengl.GL21.*;

/**
 * A 3D Renderable Locomotive model.
 */
public class Locomotive implements IRenderable {

    FrontBody frontBody = new FrontBody();
    BackBody backBody = new BackBody();

    public void render() {
        glPushMatrix();
        glPushMatrix();
        frontBody.render();
        glPopMatrix();
        backBody.render();
        glPopMatrix();
    }


    @Override
    public String toString() {
        return new String("Locomotive 3D model");
    }


    @Override
    public void init() {
    }

}
