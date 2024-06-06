package app.scene.scenes;

import app.camera.CameraFPS;
import app.math.Mat4f;
import app.math.Vec3f;
import app.rendering.Shader;
import app.rendering.Texture;
import app.rendering.buffer.VertexArrayObject;
import app.rendering.buffer.VertexBufferObject;
import app.rendering.buffer.VertexBufferObjectLayout;
import app.scene.Scene3D;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class F_TexturedCubeScene extends Scene3D {

    public Texture textureBottom, textureSide, textureTop;
    public Mat4f transformationMatrix;
    private Vec3f rotationAxis, scale, position;
    private float angle;

    public F_TexturedCubeScene(){
        vertexCount = 6 * 6;
        data = new float[] {
                // V-XYZ T-XY  F
                // Front
                0, 0, 1, 0, 0, 0,
                1, 0, 1, 1, 0, 0,
                0, 1, 1, 0, 1, 0,
                1, 0, 1, 1, 0, 0,
                1, 1, 1, 1, 1, 0,
                0, 1, 1, 0, 1, 0,
                // Back
                1, 0, 0, 0, 0, 1,
                0, 0, 0, 1, 0, 1,
                1, 1, 0, 0, 1, 1,
                0, 0, 0, 1, 0, 1,
                0, 1, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1,
                // Left
                0, 0, 0, 0, 0, 2,
                0, 0, 1, 1, 0, 2,
                0, 1, 0, 0, 1, 2,
                0, 0, 1, 1, 0, 2,
                0, 1, 1, 1, 1, 2,
                0, 1, 0, 0, 1, 2,
                // Right
                1, 0, 1, 0, 0, 3,
                1, 0, 0, 1, 0, 3,
                1, 1, 1, 0, 1, 3,
                1, 0, 0, 1, 0, 3,
                1, 1, 0, 1, 1, 3,
                1, 1, 1, 0, 1, 3,
                // Top
                0, 1, 1, 0, 0, 4,
                1, 1, 1, 1, 0, 4,
                0, 1, 0, 0, 1, 4,
                1, 1, 1, 1, 0, 4,
                1, 1, 0, 1, 1, 4,
                0, 1, 0, 0, 1, 4,
                // Bottom
                0, 0, 0, 0, 0, 5,
                1, 0, 0, 1, 0, 5,
                0, 0, 1, 0, 1, 5,
                1, 0, 0, 1, 0, 5,
                1, 0, 1, 1, 1, 5,
                0, 0, 1, 0, 1, 5,
        };
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        layout.pushFloat(1);
    }

    @Override
    public void init(int width, int height) {
        position = new Vec3f(0.0f);
        scale = new Vec3f(1.0f);
        angle = 0.0f;
        transformationMatrix = Mat4f.identity();
        camera = new CameraFPS(new Vec3f(0, 0, 12.0f), 0f, 0f);

        textureBottom = new Texture("Dirt.png", true);
        textureSide = new Texture("GrassSide.png", false);
        textureTop = new Texture("Grass.png", true);
        rotationAxis = new Vec3f(0.0f, 1.0f, 0.0f);

        shader = new Shader("F-H.glsl");
        shader.bind();
        shader.setUniform1i("textureBottom", 0);
        shader.setUniform1i("textureSide", 1);
        shader.setUniform1i("textureTop", 2);
        shader.setUniformMat4f("projection", Mat4f.projection(FOV, width, height, NEAR, FAR, null));

        vao = new VertexArrayObject();
        VertexBufferObject vbo = new VertexBufferObject(data, GL_STATIC_DRAW);
        vao.addBuffer(vbo, layout);
    }

    @Override
    public void update() {
        handleCameraMovementControl();
        angle += 1.0f;
        if (angle >= 360.0f) {
            angle = 0.0f;
        }
        if (isKeyPressed(GLFW.GLFW_KEY_UP)) {
            scale.x(scale.x() + 0.1f);
            scale.y(scale.y() + 0.1f);
            scale.z(scale.z() + 0.1f);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            scale.x(scale.x() - 0.1f);
            scale.y(scale.y() - 0.1f);
            scale.z(scale.z() - 0.1f);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_L)) {
            position.y(position.y() + 0.1f);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_K)) {
            position.y(position.y() - 0.1f);
        }
        transformationMatrix = Mat4f.translate(position, Mat4f.identity(), null);
        transformationMatrix = Mat4f.rotation(angle, rotationAxis, transformationMatrix, transformationMatrix);
        transformationMatrix = Mat4f.scale(scale, transformationMatrix, transformationMatrix);
    }

    public void setupShader() {
        shader.bind();
        glActiveTexture(GL_TEXTURE0);
        textureBottom.bind();
        glActiveTexture(GL_TEXTURE1);
        textureSide.bind();
        glActiveTexture(GL_TEXTURE2);
        textureTop.bind();
        vao.bind();
        shader.setUniformMat4f("view", camera.getViewMatrix());
    }

    @Override
    public void render() {
        setupShader();
        shader.setUniformMat4f("transformation", transformationMatrix);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glActiveTexture(GL_TEXTURE0);
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
