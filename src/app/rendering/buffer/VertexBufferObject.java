package app.rendering.buffer;

import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL15.glBufferData;

public class VertexBufferObject {

    private int vbo;
    private long oldSize = 0;

    public VertexBufferObject(float[] data, int drawMode) {
        init();
        glBufferData(GL15.GL_ARRAY_BUFFER, data, drawMode);
    }

    private void init() {
        vbo = GL15.glGenBuffers();
        bind();
    }

    public void subData(float[] data, int drawMode) {
        bind();
        if (oldSize < (long) data.length * Float.BYTES) {
            glBufferData(GL15.GL_ARRAY_BUFFER, data, drawMode);
            oldSize = (long) data.length * Float.BYTES;
        } else {
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);
        }
    }

    public void bind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    }

    public static void unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        GL15.glDeleteBuffers(vbo);
    }
}
