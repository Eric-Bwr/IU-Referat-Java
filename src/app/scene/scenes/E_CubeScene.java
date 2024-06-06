package app.scene.scenes;

import app.camera.CameraFPS;
import app.math.Mat4f;
import app.math.Vec2f;
import app.math.Vec3f;
import app.rendering.Shader;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene3D;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class E_CubeScene extends Scene3D {

    @Override
    public void init(int width, int height) {
        camera = new CameraFPS(new Vec3f(0, 0, 0), 0f, 0f);
        shader = new Shader("/res/shaders/Cube.glsl");
        shader.bind();
        shader.setUniform2f("windowSize", new Vec2f(width, height));
        shader.setUniformMat4f("projection", Mat4f.projection(FOV, width, height, NEAR, FAR, null));
        vao = new VertexArrayObject();
        vertexCount = 6 * 6;
        float[] data = {
                // X    Y      Z     I
                // Front
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,

                // Back
                0.0f, 0.0f, 0.0f, 2.0f,
                0.0f, 1.0f, 0.0f, 2.0f,
                1.0f, 1.0f, 0.0f, 2.0f,
                1.0f, 1.0f, 0.0f, 2.0f,
                1.0f, 0.0f, 0.0f, 2.0f,
                0.0f, 0.0f, 0.0f, 2.0f,

                // Left
                0.0f, 1.0f, 1.0f, 3.0f,
                0.0f, 1.0f, 0.0f, 3.0f,
                0.0f, 0.0f, 0.0f, 3.0f,
                0.0f, 0.0f, 0.0f, 3.0f,
                0.0f, 0.0f, 1.0f, 3.0f,
                0.0f, 1.0f, 1.0f, 3.0f,

                // Right
                1.0f, 1.0f, 1.0f, 4.0f,
                1.0f, 0.0f, 0.0f, 4.0f,
                1.0f, 1.0f, 0.0f, 4.0f,
                1.0f, 0.0f, 0.0f, 4.0f,
                1.0f, 1.0f, 1.0f, 4.0f,
                1.0f, 0.0f, 1.0f, 4.0f,

                // Top
                0.0f, 1.0f, 0.0f, 5.0f,
                0.0f, 1.0f, 1.0f, 5.0f,
                1.0f, 1.0f, 1.0f, 5.0f,
                1.0f, 1.0f, 1.0f, 5.0f,
                1.0f, 1.0f, 0.0f, 5.0f,
                0.0f, 1.0f, 0.0f, 5.0f,

                // Bottom
                0.0f, 0.0f, 0.0f, 6.0f,
                1.0f, 0.0f, 0.0f, 6.0f,
                1.0f, 0.0f, 1.0f, 6.0f,
                1.0f, 0.0f, 1.0f, 6.0f,
                0.0f, 0.0f, 1.0f, 6.0f,
                0.0f, 0.0f, 0.0f, 6.0f
        };

        VertexBufferObject vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        VertexBufferObjectLayout layout = new VertexBufferObjectLayout();
        layout.pushFloat(3);
        layout.pushFloat(1);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void update() {
        if (isKeyPressed(GLFW.GLFW_KEY_W)) {
            camera.moveForward(MOVE_SPEED);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.moveBackwards(MOVE_SPEED);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_A)) {
            camera.moveLeft(MOVE_SPEED);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            camera.moveRight(MOVE_SPEED);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            camera.moveUp(MOVE_SPEED);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(MOVE_SPEED);
        }
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
        camera.rotate((float) (oldMouseX - x), (float) (oldMouseY - y), SENSITIVITY);
        oldMouseX = x;
        oldMouseY = y;
    }

    @Override
    public void mouseButtonEvent(int button, int action) {

    }
}
