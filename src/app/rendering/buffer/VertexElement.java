package app.rendering.buffer;

import org.lwjgl.opengl.GL11;

public class VertexElement {
    
    public final int type;
    public final int count;
    public final boolean normalized;
    public final boolean divided;

    public VertexElement(int type, int count, boolean normalized, boolean divided) {
        this.type = type;
        this.count = count;
        this.normalized = normalized;
        this.divided = divided;
    }

    public static int getSizeOfType(int type) {
        switch (type) {
            case GL11.GL_FLOAT:
            case GL11.GL_UNSIGNED_INT:
                return 4;
            case GL11.GL_DOUBLE:
                return 8;
            case GL11.GL_UNSIGNED_BYTE:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}