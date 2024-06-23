package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import core.types.Shader;

public class Cube extends Renderable {

    @Override public Renderable initialize() {
        
        this.position = new Vector3f();
        this.scale = new Vector3f(1);
        this.color = new Vector3f(1);

        this.vertexArrayObject = GL33.glGenVertexArrays();
        this.vertexBufferObject = GL33.glGenBuffers();

        float minimzer = 1.0f;

        this.vertices = new float[] {
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 0.0f, 0.0f, // bottom-left
             1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 1.0f, 1.0f, // top-right
             1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 1.0f, 0.0f, // bottom-right         
             1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 1.0f, 1.0f, // top-right
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 0.0f, 0.0f, // bottom-left
            -1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  0.0f, -1.0f, 0.0f, 1.0f, // top-left
            // front face
            -1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 0.0f, 0.0f, // bottom-left
             1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 1.0f, 0.0f, // bottom-right
             1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 1.0f, 1.0f, // top-right
             1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 1.0f, 1.0f, // top-right
            -1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 0.0f, 1.0f, // top-left
            -1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f,  0.0f,  1.0f, 0.0f, 0.0f, // bottom-left
            // left face
            -1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer, -1.0f,  0.0f,  0.0f, 1.0f, 0.0f, // top-right
            -1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer, -1.0f,  0.0f,  0.0f, 1.0f, 1.0f, // top-left
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer, -1.0f,  0.0f,  0.0f, 0.0f, 1.0f, // bottom-left
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer, -1.0f,  0.0f,  0.0f, 0.0f, 1.0f, // bottom-left
            -1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer, -1.0f,  0.0f,  0.0f, 0.0f, 0.0f, // bottom-right
            -1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer, -1.0f,  0.0f,  0.0f, 1.0f, 0.0f, // top-right
            // right face
             1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  1.0f,  0.0f,  0.0f, 1.0f, 0.0f, // top-left
             1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  1.0f,  0.0f,  0.0f, 0.0f, 1.0f, // bottom-right
             1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  1.0f,  0.0f,  0.0f, 1.0f, 1.0f, // top-right         
             1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  1.0f,  0.0f,  0.0f, 0.0f, 1.0f, // bottom-right
             1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  1.0f,  0.0f,  0.0f, 1.0f, 0.0f, // top-left
             1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  1.0f,  0.0f,  0.0f, 0.0f, 0.0f, // bottom-left     
            // bottom face
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f, -1.0f,  0.0f, 0.0f, 1.0f, // top-right
             1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f, -1.0f,  0.0f, 1.0f, 1.0f, // top-left
             1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f, -1.0f,  0.0f, 1.0f, 0.0f, // bottom-left
             1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f, -1.0f,  0.0f, 1.0f, 0.0f, // bottom-left
            -1.0f / minimzer, -1.0f / minimzer,  1.0f / minimzer,  0.0f, -1.0f,  0.0f, 0.0f, 0.0f, // bottom-right
            -1.0f / minimzer, -1.0f / minimzer, -1.0f / minimzer,  0.0f, -1.0f,  0.0f, 0.0f, 1.0f, // top-right
            // top face
            -1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  1.0f,  0.0f, 0.0f, 1.0f, // top-left
             1.0f / minimzer,  1.0f / minimzer , 1.0f / minimzer,  0.0f,  1.0f,  0.0f, 1.0f, 0.0f, // bottom-right
             1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  1.0f,  0.0f, 1.0f, 1.0f, // top-right     
             1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  0.0f,  1.0f,  0.0f, 1.0f, 0.0f, // bottom-right
            -1.0f / minimzer,  1.0f / minimzer, -1.0f / minimzer,  0.0f,  1.0f,  0.0f, 0.0f, 1.0f, // top-left
            -1.0f / minimzer,  1.0f / minimzer,  1.0f / minimzer,  0.0f,  1.0f,  0.0f, 0.0f, 0.0f  // bottom-left  
        };

        GL33.glBindVertexArray(this.vertexArrayObject);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, this.vertexBufferObject);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, this.vertices, GL33.GL_STATIC_DRAW);

        GL33.glEnableVertexAttribArray(0);
        GL33.glEnableVertexAttribArray(1);
        GL33.glEnableVertexAttribArray(2);

        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 8 * Float.BYTES, 0);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);

        GL33.glBindVertexArray(0);

        return this;
    }

    @Override public void render(Shader shader, Matrix4f projection, Matrix4f lookat) {

        GL33.glBindVertexArray(vertexArrayObject);

        shader.use();
        shader.setMatrix4("projection", projection);
        shader.setMatrix4("lookat", lookat);
        shader.setVector3("color", color);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vertexBufferObject);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, vertices, GL33.GL_STATIC_DRAW);
        
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 8 * Float.BYTES, 0);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, vertices.length/8);
    }
}