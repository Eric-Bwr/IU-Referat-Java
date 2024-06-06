package app.scene;

import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObjectLayout;

public abstract class Scene {

    protected Shader shader;
    protected int vertexCount;
    protected float[] data;
    protected VertexBufferObjectLayout layout;
    protected VertexArrayObject vao;
    protected final boolean[] keys = new boolean[512];

    public abstract void init(int width, int height);

    public abstract void update();

    public abstract void render();

    public abstract void end();


    public abstract void keyEvent(int key, int action, int mods);

    public abstract void mousePositionEvent(double x, double y);

    public abstract void mouseButtonEvent(int button, int action);

    protected boolean isKeyPressed(int key) {
        return keys[key];
    }
}