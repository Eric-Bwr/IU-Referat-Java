package app.rendering;

import app.math.*;
import app.util.Log;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.TreeMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final TreeMap<String, Integer> locations = new TreeMap<>();

    public int id;
    private int vertID;
    private int fragID;
    private String fragmentSource;
    private String vertexSource;

    public Shader(String path) {
        String mode = "none";
        StringBuilder vertexBuffer = new StringBuilder();
        StringBuilder fragmentBuffer = new StringBuilder();
        try {
            String shaderPath = System.getProperty("user.dir") + "/res/shaders/" + path;
            BufferedReader reader = new BufferedReader(new FileReader(shaderPath));
            String buff;
            while ((buff = reader.readLine()) != null) {
                if (buff.toLowerCase().contains("vertex")) {
                    mode = "vertex";
                } else if (buff.toLowerCase().contains("fragment")) {
                    mode = "fragment";
                } else {
                    if (mode.equals("vertex")) {
                        vertexBuffer.append(buff).append("\n");
                    } else if (mode.equals("fragment")) {
                        fragmentBuffer.append(buff).append("\n");
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.logError("Failed to read Shader");
            e.printStackTrace();
        }

        id = glCreateProgram();
        vertexSource = vertexBuffer.toString();
        fragmentSource = fragmentBuffer.toString();
        vertID = compileShader(vertexSource, GL_VERTEX_SHADER);
        fragID = compileShader(fragmentSource, GL_FRAGMENT_SHADER);
        glAttachShader(id, vertID);
        glAttachShader(id, fragID);
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL11.GL_FALSE) {
            Log.logError("Failed to link Shader\n" + glGetProgramInfoLog(id));
        }
        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            Log.logError("Failed to validate Shader");
        }
    }

    private int compileShader(String source, int shaderType) {
        int shader = glCreateShader(shaderType);
        if (shader == 0) {
            Log.logError("Failed to create Shader of type " + shaderType);
        }
        glShaderSource(shader, source);
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE) {
            int infoLogLength = glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
            String infoLog = glGetShaderInfoLog(shader, infoLogLength);
            Log.logError("Failed to compile Shader of type " + shaderType + ": " + infoLog);
        }
        return shader;
    }

    public void bind() {
        glUseProgram(id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public int getUniform(String name) {
        if (locations.containsKey(name)) {
            return locations.get(name);
        }
        int location = glGetUniformLocation(id, name);
        locations.put(name, location);
        return location;
    }

    public void setUniform1i(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    public void setUniform2i(String name, Vec2i vector) {
        glUniform2i(getUniform(name), vector.x(), vector.y());
    }

    public void setUniform3i(String name, Vec3i vector) {
        glUniform3i(getUniform(name), vector.x(), vector.y(), vector.z());
    }

    public void setUniform1f(String name, float value) {
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, Vec2f vector) {
        glUniform2f(getUniform(name), vector.x(), vector.y());
    }

    public void setUniformMat4f(String name, Mat4f mat) {
        glUniformMatrix4fv(getUniform(name), false, mat.toFloatBuffer());
    }

    public void setUniform3f(String name, Vec3f vector) {
        glUniform3f(getUniform(name), vector.x(), vector.y(), vector.z());
    }

    public void setUniform4f(String name, Vec4f vec) {
        glUniform4f(getUniform(name), vec.x(), vec.y(), vec.z(), vec.w());
    }

    public void cleanUpMemory() {
        glDetachShader(id, vertID);
        glDetachShader(id, fragID);
        glDeleteProgram(id);
    }
}