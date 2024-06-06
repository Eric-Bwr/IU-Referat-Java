package app.scene.scenes;

import app.camera.CameraFPS;
import app.math.Mat4f;
import app.math.Vec3f;
import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene3D;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class E_CubeScene extends Scene3D {

    public E_CubeScene() {
        vertexCount = 6 * 6;
        data = new float[]{
                // X Y Z I
                // Front
                0, 0, 1, 1,
                1, 0, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1,
                0, 1, 1, 1,
                0, 0, 1, 1,

                // Back
                0, 0, 0, 2,
                0, 1, 0, 2,
                1, 1, 0, 2,
                1, 1, 0, 2,
                1, 0, 0, 2,
                0, 0, 0, 2,

                // Left
                0, 1, 1, 3,
                0, 1, 0, 3,
                0, 0, 0, 3,
                0, 0, 0, 3,
                0, 0, 1, 3,
                0, 1, 1, 3,

                // Right
                1, 1, 1, 4,
                1, 0, 0, 4,
                1, 1, 0, 4,
                1, 0, 0, 4,
                1, 1, 1, 4,
                1, 0, 1, 4,

                // Top
                0, 1, 0, 5,
                0, 1, 1, 5,
                1, 1, 1, 5,
                1, 1, 1, 5,
                1, 1, 0, 5,
                0, 1, 0, 5,

                // Bottom
                0, 0, 0, 6,
                1, 0, 0, 6,
                1, 0, 1, 6,
                1, 0, 1, 6,
                0, 0, 1, 6,
                0, 0, 0, 6
        };
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(3);
        layout.pushFloat(1);
    }

    @Override
    public void init(int width, int height) {
        camera = new CameraFPS(new Vec3f(0, 0, 12), 0f, 0f);
        shader = new Shader("E.glsl");
        shader.bind();
        shader.setUniformMat4f("projection", Mat4f.projection(FOV, width, height, NEAR, FAR, null));

        vao = new VertexArrayObject();
        VertexBufferObject vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void update() {
        handleCameraMovementControl();
    }

    @Override
    public void render() {
        shader.bind();
        shader.setUniformMat4f("view", camera.getViewMatrix());
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    @Override
    public void end() {
        shader.unbind();
        shader.cleanUpMemory();
        vao.delete();
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
