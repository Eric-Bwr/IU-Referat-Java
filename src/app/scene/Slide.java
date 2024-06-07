package app.scene;

import app.rendering.Shader;
import app.rendering.Texture;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Slide extends Scene {

    private String slide;
    private Texture texture;

    public Slide(String slide){
        this.slide = slide;
    }

    @Override
    public void init(int width, int height) {
        shader = new Shader("/0_DisplayTexture.glsl");
        shader.bind();
        texture = new Texture(slide, true, true);
        texture.bind();
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
    public void update() {
    }

    @Override
    public void render() {
        shader.bind();
        texture.bind();
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
