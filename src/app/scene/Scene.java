package app.scene;

import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;

public abstract class Scene {

    protected Shader shader;
    protected int vertexCount;
    protected VertexArrayObject vao;

    public abstract void init();

    public abstract void render();

    public abstract void end();

}