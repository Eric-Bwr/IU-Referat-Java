package app.scene.scenes;

import app.math.Mat4f;
import app.rendering.buffer.VertexBufferObjectLayout;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class H_IndividualFacesScene extends G_MultiDirtScene {

    public static final int FACE_FRONT = 0;
    public static final int FACE_BACK = 1;
    public static final int FACE_LEFT = 2;
    public static final int FACE_RIGHT = 3;
    public static final int FACE_TOP = 4;
    public static final int FACE_BOTTOM = 5;

    private float[][] faceData = {
            {
                    // Front
                    0, 0, 1, 0, 0, 0,
                    1, 0, 1, 1, 0, 0,
                    0, 1, 1, 0, 1, 0,
                    1, 0, 1, 1, 0, 0,
                    1, 1, 1, 1, 1, 0,
                    0, 1, 1, 0, 1, 0,
            },
            {
                    // Back
                    1, 0, 0, 0, 0, 1,
                    0, 0, 0, 1, 0, 1,
                    1, 1, 0, 0, 1, 1,
                    0, 0, 0, 1, 0, 1,
                    0, 1, 0, 1, 1, 1,
                    1, 1, 0, 0, 1, 1,
            },
            {
                    // Left
                    0, 0, 0, 0, 0, 2,
                    0, 0, 1, 1, 0, 2,
                    0, 1, 0, 0, 1, 2,
                    0, 0, 1, 1, 0, 2,
                    0, 1, 1, 1, 1, 2,
                    0, 1, 0, 0, 1, 2,
            },
            {
                    // Right
                    1, 0, 1, 0, 0, 3,
                    1, 0, 0, 1, 0, 3,
                    1, 1, 1, 0, 1, 3,
                    1, 0, 0, 1, 0, 3,
                    1, 1, 0, 1, 1, 3,
                    1, 1, 1, 0, 1, 3,
            },
            {
                    // Top
                    0, 1, 1, 0, 0, 4,
                    1, 1, 1, 1, 0, 4,
                    0, 1, 0, 0, 1, 4,
                    1, 1, 1, 1, 0, 4,
                    1, 1, 0, 1, 1, 4,
                    0, 1, 0, 0, 1, 4,
            },
            {
                    // Bottom
                    0, 0, 0, 0, 0, 5,
                    1, 0, 0, 1, 0, 5,
                    0, 0, 1, 0, 1, 5,
                    1, 0, 0, 1, 0, 5,
                    1, 0, 1, 1, 1, 5,
                    0, 0, 1, 0, 1, 5,
            }
    };

    private void addFace(int x, int y, int z, int face) {
        float[] faceVertices = faceData[face];
        int startIndex = data.length;
        data = Arrays.copyOf(data, startIndex + faceVertices.length);
        for (int i = 0; i < faceVertices.length; i += 6) {
            data[startIndex + i] = faceVertices[i] + x;
            data[startIndex + i + 1] = faceVertices[i + 1] + y;
            data[startIndex + i + 2] = faceVertices[i + 2] + z;
            data[startIndex + i + 3] = faceVertices[i + 3];
            data[startIndex + i + 4] = faceVertices[i + 4];
            data[startIndex + i + 5] = faceVertices[i + 5];
        }
    }

    private void constructTerrainMesh() {
        int FLOOR_SIZE = 40;
        for(int x = 0; x < FLOOR_SIZE; x++){
            for(int z = 0; z < FLOOR_SIZE; z++){
                int y = (int)(x / 10.0f);
                addFace(x, y, z, FACE_TOP);
            }
        }
    }

    public H_IndividualFacesScene(){
        data = new float[0];
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        layout.pushFloat(1);
        constructTerrainMesh();
        vertexCount = data.length / 6;
    }

    @Override
    public void render() {
        setupShader();
        shader.setUniformMat4f("view", camera.getViewMatrix());
        shader.setUniformMat4f("transformation", Mat4f.identity());
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glActiveTexture(GL_TEXTURE0);
    }

    @Override
    public void end() {
        shader.unbind();
        shader.cleanUpMemory();
    }

    @Override
    public void keyEvent(int key, int action, int mods) {
    }

    @Override
    public void mousePositionEvent(double x, double y) {
        handleCameraMouseControl(x, y);
    }

    @Override
    public void mouseButtonEvent(int button, int action) {

    }
}
