package app.scene;

import app.scene.scenes.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SceneManager {

    private final List<Scene> scenes = new ArrayList<>();
    private Scene currentScene;
    private int currentSceneIndex;
    private long window;

    public SceneManager() {
        for (int i = 0; i < 11; i++){
            addScene(new Slide(i + ".jpg"));
        }
        addScene(new A_TriangleScene());
        for (int i = 12; i < 15; i++){
            addScene(new Slide(i + ".jpg"));
        }
        addScene(new B_HalfQuadScene());
        addScene(new Slide("16.jpg"));
        addScene(new C_QuadScene());
        for (int i = 18; i < 25; i++){
            addScene(new Slide(i + ".jpg"));
        }
        addScene(new D_ThreeDQuadScene());
        addScene(new Slide("26.jpg"));
        addScene(new E_CubeScene());
        addScene(new Slide("28.jpg"));
        addScene(new F_TexturedCubeScene());
        addScene(new Slide("30.jpg"));
        addScene(new G_MultiDirtScene());
        addScene(new Slide("32.jpg"));
        addScene(new H_IndividualFacesScene());
        addScene(new Slide("34.jpg"));
        addScene(new Slide("35.jpg"));
        addScene(new I_Terrain());
        addScene(new Slide("37.jpg"));
        addScene(new Slide("38.jpg"));
        addScene(new Slide("39.jpg"));
    }

    public void init(long window, int width, int height) {
        this.window = window;
        selectCurrentScene(0);
        for (Scene scene : scenes) {
            scene.init(width, height);
        }
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void update() {
        glfwPollEvents();
        if (currentScene == null) {
            return;
        }
        currentScene.update();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if (currentScene == null) {
            return;
        }
        currentScene.render();
        glfwSwapBuffers(window);
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

    public int getCurrentSceneIndex() {
        return currentSceneIndex;
    }
}
