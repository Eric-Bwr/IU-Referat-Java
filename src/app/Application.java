package app;

import app.util.Log;
import app.window.Window;
import app.window.WindowConfig;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class Application {

    private Window window;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    public Application() {
        WindowConfig windowConfig = new WindowConfig();
        window = new Window(windowConfig);

        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        };
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                Log.log(key);
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

    public void init(){
        window.initWindow();
        window.initCallbacks(windowSizeCallback, keyCallback, mouseButtonCallback, cursorPosCallback);
    }

    public void run() {
        while(!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwPollEvents();
            window.swapBuffers();
        }
    }

    public void end() {
        window.terminate();
    }
}
