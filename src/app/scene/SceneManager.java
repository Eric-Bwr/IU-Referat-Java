package app.scene;

import app.scene.scenes.HalfQuadScene;
import app.scene.scenes.QuadScene;
import app.scene.scenes.TriangleScene;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private List<Scene> scenes = new ArrayList<>();;

    private int currentScene = 0;

    public SceneManager() {
        addScene(new TriangleScene());
        addScene(new HalfQuadScene());
        addScene(new QuadScene());
    }

    public void init() {
        for (Scene scene : scenes) {
            scene.init();
        }
    }

    public void addScene(Scene scene){
        scenes.add(scene);
    }

    public void render(){
        if(currentScene < scenes.size()){
            scenes.get(currentScene).render();
        }
    }

    public void end() {
        for (Scene scene : scenes) {
            scene.end();
        }
    }

    public void nextScene(){
        if(currentScene < scenes.size() - 1){
            currentScene++;
        }
    }

    public void prevScene(){
        if(currentScene > 0){
            currentScene--;
        }
    }
}
