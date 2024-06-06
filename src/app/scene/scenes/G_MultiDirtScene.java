package app.scene.scenes;

import app.math.Mat4f;
import app.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.*;

public class G_MultiDirtScene extends F_TexturedCubeScene {

    @Override
    public void update() {
        handleCameraMovementControl();
    }

    @Override
    public void render() {
        setupShader();
        shader.setUniformMat4f("transformation", transformationMatrix);
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glActiveTexture(GL_TEXTURE0);
        vao.bind();
        int FLOOR_SIZE = 40;
        for (int x = 0; x < FLOOR_SIZE; x++) {
            for (int z = 0; z < FLOOR_SIZE; z++) {
                float y = (float) (int) (x / 10.0f);
                Vec3f blockPosition = new Vec3f(x, y, z);
                transformationMatrix = Mat4f.translate(blockPosition, Mat4f.identity(), null);
                shader.setUniformMat4f("transformation", transformationMatrix);
                glDrawArrays(GL_TRIANGLES, 0, vertexCount);
            }
        }

        glActiveTexture(GL_TEXTURE0);
    }
}
