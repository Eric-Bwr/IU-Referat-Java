package app.scene;

import app.scene.scenes.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public class SceneManager {

    private final List<Scene> scenes = new ArrayList<>();
    private Scene currentScene;
    private int currentSceneIndex;
    private long window;

    public SceneManager() {
        addScene(new A_TriangleScene());
        addScene(new B_HalfQuadScene());
        addScene(new C_QuadScene());
        addScene(new D_ThreeDQuadScene());
        addScene(new E_CubeScene());
    }

    public void init(long window, int width, int height) {
        this.window = window;
        selectCurrentScene(scenes.size() - 1);
        for (Scene scene : scenes) {
            scene.init(width, height);
        }
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void update() {
        if (currentScene == null) {
            return;
        }
        currentScene.update();
    }

    public void render() {
        if (currentScene == null) {
            return;
        }
        currentScene.render();
    }

    public void end() {
        for (Scene scene : scenes) {
            scene.end();
        }
    }

    public void nextScene() {
        if (currentSceneIndex < scenes.size() - 1) {
            selectCurrentScene(currentSceneIndex + 1);
        }
    }

    public void prevScene() {
        if (currentSceneIndex > 0) {
            selectCurrentScene(currentSceneIndex - 1);
        }
    }

    public void selectCurrentScene(int index) {
        if (index < scenes.size()) {
            currentSceneIndex = index;
            currentScene = scenes.get(currentSceneIndex);
            glfwSetWindowTitle(window, "Folie / Szene: " + (currentSceneIndex + 1));
        }
    }

    public void keyEvent(int key, int action, int mods) {
        try {
            for (Scene scene : scenes) {
                scene.keys[key] = action != GLFW.GLFW_RELEASE;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        if (currentScene == null) {
            return;
        }
        currentScene.keyEvent(key, action, mods);
    }

    public void mousePositionEvent(double x, double y) {
        if (currentScene == null) {
            return;
        }
        currentScene.mousePositionEvent(x, y);
    }

    public void mouseButtonEvent(int button, int action) {
        if (currentScene == null) {
            return;
        }
        currentScene.mouseButtonEvent(button, action);
    }
}
