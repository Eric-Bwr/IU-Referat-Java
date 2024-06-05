package app.scene.scenes;

import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class TriangleScene extends Scene {

    private VertexArrayObject vao;
    private VertexBufferObject vbo;
    private VertexBufferObjectLayout layout;
    private Shader shader;

    @Override
    public void init() {
        shader = new Shader("/res/shaders/Test.glsl");
        vao = new VertexArrayObject();
        float[] data = {
                -1.0f, -1.0f,
                0.0f, 1.0f,
                1.0f, -1.0f
        };
        vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(2);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void render(){
        shader.bind();
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public void end() {
        shader.unbind();
        shader.cleanUpMemory();
        vao.delete();
    }
}
