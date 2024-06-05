package app.rendering.buffer;

import org.lwjgl.opengl.GL30;

import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class VertexArrayObject {

    private int vao;

    public VertexArrayObject() {
        vao = GL30.glGenVertexArrays();
        bind();
    }

    public void addBuffer(VertexBufferObject vbo, VertexBufferObjectLayout layout) {
        bind();
        vbo.bind();
        List<VertexElement> elements = layout.getElements();
        int offset = 0;

        for (int i = 0; i < elements.size(); i++) {
            VertexElement element = elements.get(i);
            glEnableVertexAttribArray(i);
            glVertexAttribPointer(i, element.count, element.type, element.normalized, layout.getStride(), offset);
            offset += element.count * VertexElement.getSizeOfType(element.type);
            if (element.divided) {
                glVertexAttribDivisor(i, 1);
            }
        }
    }

    public void bind() {
        GL30.glBindVertexArray(vao);
    }

    public static void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(vao);
    }
}
