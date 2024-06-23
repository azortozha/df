package core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL33;

import core.types.Shader;

public class BlankCanvas extends Renderable {
    
    @Override public Renderable initialize() {
        
        this.position = new Vector3f();
        this.scale = new Vector3f(1);
        this.color = new Vector3f(1, 0, 0);

        this.vertexArrayObject = GL33.glGenVertexArrays();
        this.vertexBufferObject = GL33.glGenBuffers();

        float minimzer = 1.0f;

        this.vertices = new float[] {
            -1.0f, -1.0f,   0.0f, 0.0f,
            -1.0f,  1.0f,   0.0f, 1.0f,
             1.0f,  1.0f,   1.0f, 1.0f,

            -1.0f, -1.0f,   0.0f, 0.0f,
             1.0f, -1.0f,   1.0f, 0.0f,
             1.0f,  1.0f,   1.0f, 1.0f
        };

        GL33.glBindVertexArray(this.vertexArrayObject);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, this.vertexBufferObject);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, this.vertices, GL33.GL_STATIC_DRAW);

        GL33.glEnableVertexAttribArray(0);
        GL33.glEnableVertexAttribArray(1);

        GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 4 * Float.BYTES, 0);
        GL33.glVertexAttribPointer(1, 2, GL33.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);

        GL33.glBindVertexArray(0);

        return this;
    }

    @Override public void render(Shader shader, Matrix4f projection, Matrix4f lookat) {

        GL33.glBindVertexArray(vertexArrayObject);

        shader.use();
        shader.setVector3("color", color);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vertexBufferObject);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, vertices, GL33.GL_STATIC_DRAW);
        
        GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 4 * Float.BYTES, 0);
        GL33.glVertexAttribPointer(1, 2, GL33.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, vertices.length/4);
    }
}
