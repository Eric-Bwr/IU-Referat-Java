package app.rendering;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.BufferUtils;

public class Texture {

    private int id, width, height;
    private String path;

    public Texture(String path, boolean flip) {
        try {
            this.path = path;
            String texturePath = System.getProperty("user.dir") + "/res/textures/" + path;

            IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer compBuffer = BufferUtils.createIntBuffer(1);

            stbi_set_flip_vertically_on_load(flip);
            ByteBuffer data = stbi_load(texturePath, widthBuffer, heightBuffer, compBuffer, 4);

            if (data == null) {
                throw new RuntimeException("Failed to load texture: " + stbi_failure_reason());
            }

            width = widthBuffer.get();
            height = heightBuffer.get();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getTextureId() {
        return id;
    }

    public void cleanUpMemory() {
        glDeleteTextures(id);
    }
}