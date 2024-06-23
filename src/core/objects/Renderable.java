package core.objects;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.types.Shader;

public abstract class Renderable {

    public Vector3f position, scale, color;
    public float[] vertices;

    public int vertexBufferObject,
               vertexArrayObject;

    public Renderable initialize() { return null; }
    
    public void render(Shader shader, Matrix4f projection, Matrix4f lookat) {}
}
