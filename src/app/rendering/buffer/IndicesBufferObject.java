package app.rendering.buffer;

import org.lwjgl.opengl.GL15;

public class IndicesBufferObject {

    private int ibo;

    public IndicesBufferObject(int[] data, int drawType) {
        ibo = GL15.glGenBuffers();
        bind();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, drawType);
    }

    public void subData(int[] data, int offset, int drawMode) {
        bind();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, (long) data.length * Integer.BYTES, drawMode);
        GL15.glBufferSubData(GL15.GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public void bind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
    }

    public static void unbind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete() {
        GL15.glDeleteBuffers(ibo);
    }
}
