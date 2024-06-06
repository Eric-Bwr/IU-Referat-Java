package app;

import app.scene.SceneManager;
import app.window.Window;
import app.window.WindowConfig;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Application {

    private static final int TICKS_PER_SECOND = 60;

    private WindowConfig windowConfig;
    private Window window;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    private SceneManager sceneManager;

    private int polygonMode = GL_FILL;
    private int catchMouse = GLFW_CURSOR_NORMAL;
    private int swapInterval = 1;

    public Application() {
        windowConfig = new WindowConfig();
        window = new Window(windowConfig);
        sceneManager = new SceneManager();

        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        };
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    if (key == GLFW_KEY_ESCAPE) {
//                        glfwSetWindowShouldClose(window, true);
                    } else if (key == GLFW_KEY_LEFT) {
                        sceneManager.prevScene();
                    } else if (key == GLFW_KEY_RIGHT) {
                        sceneManager.nextScene();
                    } else if (key == GLFW_KEY_RIGHT_SHIFT) {
                        polygonMode = polygonMode == GL_FILL ? GL_LINE : GL_FILL;
                        glPolygonMode(GL_FRONT_AND_BACK, polygonMode);
                    } else if (key == GLFW_KEY_TAB) {
                        catchMouse = catchMouse == GLFW_CURSOR_NORMAL ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL;
                        glfwSetInputMode(window, GLFW_CURSOR, catchMouse);
                    } else if (key == GLFW_KEY_ENTER) {
                        swapInterval = swapInterval == 0 ? 1 : 0;
                        glfwSwapInterval(swapInterval);
                    }
                }
                sceneManager.keyEvent(key, action, mods);
            }
        };
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                sceneManager.mouseButtonEvent(button, action);
            }
        };
        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                sceneManager.mousePositionEvent(xpos, ypos);
            }
        };
    }

    public void init() {
        window.initWindow();
        window.initCallbacks(windowSizeCallback, keyCallback, mouseButtonCallback, cursorPosCallback);
        sceneManager.init(window.handle, windowConfig.width, windowConfig.height);
        glDisable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
    }

    public void run() {

        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / TICKS_PER_SECOND;
        int frames = 0;
        int fps = 0;
        long lastTimer = System.currentTimeMillis();

        while (!window.shouldClose()){
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            boolean shouldRender = true;
            lastTime = now;
            while (unprocessed >= 1) {
                sceneManager.update();
                unprocessed -= 1;
                shouldRender = true;
                glfwSetWindowTitle(window.handle, "Folie / Szene: " + (sceneManager.getCurrentSceneIndex() + 1) + " FPS: " + fps);
            }

            if (shouldRender) {
                frames++;
                sceneManager.render();
            }

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                fps = frames;
                frames = 0;
            }
        }
    }

    public void end() {
        sceneManager.end();
        window.terminate();
    }
}
