package app.scene.scenes;

import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene;
import app.math.Vec2f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class A_TriangleScene extends Scene {

    public A_TriangleScene() {
        vertexCount = 3;
        data = new float[]{
                -1, -1,
                +0, +1,
                +1, -1
        };
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(2);
    }

    @Override
    public void init(int width, int height) {
        shader = new Shader("A-C.glsl");
        shader.bind();
        shader.setUniform2f("windowSize", new Vec2f(width, height));

        vao = new VertexArrayObject();
        VertexBufferObject vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        shader.bind();
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    @Override
    public void end() {
        shader.unbind();
        shader.cleanUpMemory();
        vao.delete();
    }

    @Override
    public void keyEvent(int key, int action, int mods) {
    }

    @Override
    public void mousePositionEvent(double x, double y) {
    }

    @Override
    public void mouseButtonEvent(int button, int action) {
    }
}
