package app.scene;

import app.camera.CameraFPS;
import org.lwjgl.glfw.GLFW;

public abstract class Scene3D extends Scene {

    protected CameraFPS camera;
    protected float FOV = 70;
    protected float NEAR = 0.1f;
    protected float FAR = 10000;
    protected float SENSITIVITY = 0.05F;
    protected float MOVE_SPEED = 0.2F;
    protected double oldMouseX = 0.0f;
    protected double oldMouseY = 0.0f;

    public void handleCameraMovementControl(){
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

    public void handleCameraMouseControl(double x, double y) {
        camera.rotate((float) (oldMouseX - x), (float) (oldMouseY - y), SENSITIVITY);
        oldMouseX = x;
        oldMouseY = y;
    }
}
