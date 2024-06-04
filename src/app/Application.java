package app;

import app.window.Window;
import app.window.WindowConfig;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class Application {

    private Window window;

    public Application() {
        WindowConfig windowConfig = new WindowConfig();
        window = new Window(windowConfig);
    }

    public void init(){
        window.initWindow();
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
