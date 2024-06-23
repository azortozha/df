package core.types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

public class Shader {
    
    public int program;

    public Shader(String shaderPath) {

        program = GL33.glCreateProgram();

        try {
            int vertexShaderID = createShader(loadShaderSource(shaderPath+"/vMain.glsl"), GL33.GL_VERTEX_SHADER);
            int fragmentShaderID = createShader(loadShaderSource(shaderPath+"/fMain.glsl"), GL33.GL_FRAGMENT_SHADER);

            GL33.glAttachShader(program, vertexShaderID);
            GL33.glAttachShader(program, fragmentShaderID);
            GL33.glLinkProgram(program);
            if (GL33.glGetProgrami(program, GL33.GL_LINK_STATUS) == GL33.GL_FALSE) {
                System.err.println("Failed to link program: " + GL33.glGetProgramInfoLog(program));
            }

            GL33.glDeleteProgram(vertexShaderID);
            GL33.glDeleteProgram(fragmentShaderID);
        }
        catch (Exception e) {}
    }


    public void setInt(String variableName, int value) {
        int location = fetchShaderLocationName(variableName);
        GL33.glUniform1i(location, value);
    }

    public void setFloat(String variableName, float value) {
        int location = fetchShaderLocationName(variableName);
        GL33.glUniform1f(location, value);
    }

    public void setVector3(String variableName, Vector3f value) {
        int location = fetchShaderLocationName(variableName);
        GL33.glUniform3f(location, value.x, value.y, value.z);
    }

    public void setMatrix4(String variableName, Matrix4f value) {
        int location = fetchShaderLocationName(variableName);
        GL33.glUniformMatrix4fv(location, false, matrixToFloatBuffer(value, 0));
    }

    public void use() {
        GL33.glUseProgram(program);
    }
    
    public void unbind() {
        GL33.glUseProgram(0);
    }

    private int fetchShaderLocationName(String name) {
        return GL33.glGetUniformLocation(program, name);
    }







    private static int createShader(String source, int type) {
        int shaderID = GL33.glCreateShader(type);
        GL33.glShaderSource(shaderID, source);
        GL33.glCompileShader(shaderID);

        if (GL33.glGetShaderi(shaderID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
            System.err.println("Failed to compile shader: " + GL33.glGetShaderInfoLog(shaderID));
            return -1;
        }
        return shaderID;
    }

    private static String loadShaderSource(String shaderPath) throws FileNotFoundException {

        File file = new File(shaderPath);
        InputStream stream = new FileInputStream(file);

        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        catch (IOException e) {}
        return result.toString();
    }

    private static FloatBuffer matrixToFloatBuffer(Matrix4f m, int offset) {

        FloatBuffer dest = MemoryUtil.memAllocFloat(16);
        dest.put(offset, m.m00());
        dest.put(offset + 1, m.m01());
        dest.put(offset + 2, m.m02());
        dest.put(offset + 3, m.m03());
        dest.put(offset + 4, m.m10());
        dest.put(offset + 5, m.m11());
        dest.put(offset + 6, m.m12());
        dest.put(offset + 7, m.m13());
        dest.put(offset + 8, m.m20());
        dest.put(offset + 9, m.m21());
        dest.put(offset + 10, m.m22());
        dest.put(offset + 11, m.m23());
        dest.put(offset + 12, m.m30());
        dest.put(offset + 13, m.m31());
        dest.put(offset + 14, m.m32());
        dest.put(offset + 15, m.m33());

        return dest;
    }
}
