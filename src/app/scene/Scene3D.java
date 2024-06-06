package app.scene;

import app.camera.CameraFPS;

public abstract class Scene3D extends Scene {

    protected CameraFPS camera;
    protected float FOV = 70;
    protected float NEAR = 0.1f;
    protected float FAR = 10000;
    protected float SENSITIVITY = 0.05F;
    protected float MOVE_SPEED = 0.2F;
    protected double oldMouseX = 0.0f;
    protected double oldMouseY = 0.0f;
}
