package app.rendering.buffer;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class VertexBufferObjectLayout {
    private final List<VertexElement> elements = new ArrayList<>();
    private int stride = 0;

    public void pushFloat(int count) {
        elements.add(new VertexElement(GL11.GL_FLOAT, count, false, false));
        stride += count * VertexElement.getSizeOfType(GL11.GL_FLOAT);
    }

    public void pushFloatDivided(int count) {
        elements.add(new VertexElement(GL11.GL_FLOAT, count, false, true));
        stride += count * VertexElement.getSizeOfType(GL11.GL_FLOAT);
    }

    public void pushDouble(int count) {
        elements.add(new VertexElement(GL11.GL_DOUBLE, count, false, false));
        stride += count * VertexElement.getSizeOfType(GL11.GL_DOUBLE);
    }

    public void pushDoubleDivided(int count) {
        elements.add(new VertexElement(GL11.GL_DOUBLE, count, false, true));
        stride += count * VertexElement.getSizeOfType(GL11.GL_DOUBLE);
    }

    public void pushInt(int count) {
        elements.add(new VertexElement(GL11.GL_UNSIGNED_INT, count, false, false));
        stride += count * VertexElement.getSizeOfType(GL11.GL_UNSIGNED_INT);
    }

    public void pushIntDivided(int count) {
        elements.add(new VertexElement(GL11.GL_UNSIGNED_INT, count, false, true));
        stride += count * VertexElement.getSizeOfType(GL11.GL_UNSIGNED_INT);
    }

    public void pushByte(int count) {
        elements.add(new VertexElement(GL11.GL_UNSIGNED_BYTE, count, true, false));
        stride += count * VertexElement.getSizeOfType(GL11.GL_UNSIGNED_BYTE);
    }

    public void pushByteDivided(int count) {
        elements.add(new VertexElement(GL11.GL_UNSIGNED_BYTE, count, true, true));
        stride += count * VertexElement.getSizeOfType(GL11.GL_UNSIGNED_BYTE);
    }

    public List<VertexElement> getElements() {
        return elements;
    }

    public int getStride() {
        return stride;
    }
}