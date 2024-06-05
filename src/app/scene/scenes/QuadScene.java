package app.scene.scenes;

import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class QuadScene extends Scene {

    @Override
    public void init() {
        shader = new Shader("/res/shaders/Test.glsl");
        vao = new VertexArrayObject();
        vertexCount = 6;
        float[] data = {
                -1.0f, -1.0f,
                -1.0f, 1.0f,
                1.0f, -1.0f,
                -1.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, -1.0f,
        };
        VertexBufferObject vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        VertexBufferObjectLayout layout = new VertexBufferObjectLayout();
        layout.pushFloat(2);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void render(){
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
}
