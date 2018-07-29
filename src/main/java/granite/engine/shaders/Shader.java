package granite.engine.shaders;

import granite.engine.core.IBindable;
import granite.engine.core.IDestroyable;
import granite.engine.util.Resource;
import granite.engine.util.ResourceType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader implements IDestroyable, IBindable {

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader(String vertexFile, String fragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        programId = glCreateProgram();
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        glLinkProgram(programId);
        glValidateProgram(programId);
        getAllUniformLocations();
    }

    @Override
    public void bind() {
        glUseProgram(programId);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void destroy() {
        unbind();
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programId, attribute, variableName);
    }

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String name) {
        return glGetUniformLocation(programId, name);
    }

    protected void loadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f value) {
        glUniform3f(location, value.x, value.y, value.z);
    }

    protected void loadBoolean(int location, boolean value) {
        loadFloat(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        //buffer.flip();
        glUniformMatrix4fv(location, false, buffer);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(Resource.loadResource(ResourceType.SHADER, file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderId, 500));
            System.exit(-1);
        }

        return shaderId;
    }
}
