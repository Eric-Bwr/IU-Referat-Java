package app.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int id;
    private String path;
    private float lodBias = -0.4f;
    //TODO: Implement Texture Atlases

    public Texture(String path) {
        try {
            this.path = path;
            String texturePath = System.getProperty("user.dir") + path;
            System.out.println("Loading texture: " + texturePath);
            BufferedImage image = ImageIO.read(new File(texturePath));
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);
            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            buffer.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	        glGenerateMipmap(GL_TEXTURE_2D);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, lodBias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getID(){
        return id;
    }

    public String getPath() {
        return path;
    }

	public float getLodBias() {
		return lodBias;
	}

	public void setLodBias(float lodBias) {
		this.lodBias = lodBias;
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