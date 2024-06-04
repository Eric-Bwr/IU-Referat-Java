package app.window;

import app.util.Log;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    public long window;

    private WindowConfig config;

    public Window(WindowConfig config){
        this.config = config;
    }

    public void initWindow() {
        if (!glfwInit()) {
            Log.logError("Failed to init GLFW!");
            glfwTerminate();
        }
        initHints();
        window = glfwCreateWindow(config.width, config.height, config.title, NULL, NULL);
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - config.width) / 2, (vidMode.height() - config.height) / 2);
        if (window == NULL) {
            Log.logError("Failed to create app.Window!");
            glfwTerminate();
        }
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        glfwFocusWindow(window);
        GL.createCapabilities();
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_DEPTH_TEST);
        glClearColor(config.clearColor.x(), config.clearColor.y(), config.clearColor.z(), config.clearColor.w());
        if (config.vsync)
            glfwSwapInterval(1);
        else
            glfwSwapInterval(0);
        Log.logInfo(glGetString(GL_VERSION));
    }

    private void initHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
        glfwMakeContextCurrent(temp);
        GL.createCapabilities();
        GLCapabilities caps = GL.getCapabilities();
        glfwDestroyWindow(temp);
        glfwDefaultWindowHints();
        if (caps.OpenGL46) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
            Log.logInfo("Loading OpenGL 3.2");
        } else if (caps.OpenGL21) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
            Log.logInfo("Loading OpenGL 2.1");
        } else {
            Log.logInfo("Neither OpenGL 3.2 nor OpenGL 2.1 is supported");
        }
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    }

    public void initCallbacks(GLFWWindowSizeCallback windowSizeCallback,
                              GLFWKeyCallback keyCallback,
                              GLFWMouseButtonCallback mouseButtonCallback,
                              GLFWCursorPosCallback cursorPosCallback) {
        glfwSetWindowSizeCallback(window, windowSizeCallback);
        glfwSetKeyCallback(window, keyCallback);
        glfwSetMouseButtonCallback(window, mouseButtonCallback);
        glfwSetCursorPosCallback(window, cursorPosCallback);
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void terminate() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}