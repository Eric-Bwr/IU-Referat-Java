package app.window;

import app.math.Vec4f;

public class WindowConfig {
    public String title = "Referat";
    public int width = 800;
    public int height = 600;
    public String windowIconPath = "";
    public boolean vsync = false;
    public Vec4f clearColor = new Vec4f(0.5f, 0.5f, 0.5f, 1f);
}
