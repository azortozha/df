package core;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import core.objects.BlankCanvas;
import core.objects.Cube;
import core.objects.Renderable;
import core.types.FrameBuffer;
import core.types.Shader;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DFMain {
    
    public static long window;

    Matrix4f projection, lookAt;

    public void begin() {
        
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        try (MemoryStack stack = stackPush()) {

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            window = glfwCreateWindow(vidmode.width(), vidmode.height(), "Physically Based Bloom", NULL, NULL);

            glfwSetWindowSizeLimits(window, 750, 400, GLFW_DONT_CARE, GLFW_DONT_CARE);
            glfwSetWindowAspectRatio(window, 750, 400);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        loop();
    }

    private void loop() {
        GL.createCapabilities();
        GL33.glEnable(GL33.GL_DEPTH_TEST);

        projection = new Matrix4f().perspective(5f * 3.14159265358f / 9f, 750f/450f, 0.1f, 1000f);
        lookAt = new Matrix4f().lookAt(new Vector3f(3), new Vector3f(0), new Vector3f(0, 1, 0));

        Renderable canvas = new BlankCanvas().initialize(),
                   cube = new Cube().initialize();
        Shader canvasShader = new Shader("src/shaders/sampler"),
               basicShader = new Shader("src/shaders/basic");

        FrameBuffer buffer = FrameBuffer.createFramebuffer();

        while (!glfwWindowShouldClose(window)) {
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            buffer.bind();
            cube.render(basicShader, projection, lookAt);
            buffer.unbind();
            
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, buffer.normalTexture);
            canvas.render(canvasShader, projection, lookAt);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
