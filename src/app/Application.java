package app;

import app.scene.SceneManager;
import app.util.Log;
import app.window.Window;
import app.window.WindowConfig;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Application {

    private Window window;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    private SceneManager sceneManager;

    private int polygonMode = GL_FILL;

    public Application() {
        WindowConfig windowConfig = new WindowConfig();
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
                        glfwSetWindowShouldClose(window, true);
                    } else if (key == GLFW_KEY_LEFT) {
                        sceneManager.prevScene();
                    } else if (key == GLFW_KEY_RIGHT) {
                        sceneManager.nextScene();
                    } else if (key == GLFW_KEY_RIGHT_SHIFT) {
                        polygonMode = polygonMode == GL_FILL ? GL_LINE : GL_FILL;
                        glPolygonMode(GL_FRONT_AND_BACK, polygonMode);
                    }
                }
            }
        };
        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {

            }
        };
        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                Log.log(xpos + " " + ypos);
            }
        };
    }

    public void init() {
        window.initWindow();
        window.initCallbacks(windowSizeCallback, keyCallback, mouseButtonCallback, cursorPosCallback);
        sceneManager.init();
    }

    public void run() {
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);
            sceneManager.render();
            window.swapBuffers();
            glfwPollEvents();
        }
    }

    public void end() {
        sceneManager.end();
        window.terminate();
    }
}
