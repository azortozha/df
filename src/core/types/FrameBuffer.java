package core.types;

import java.nio.IntBuffer;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import core.DFMain;

public class FrameBuffer {
    
    public int framebufferObject, renderbufferObject;
    public int positionTexture,
               normalTexture,
               colorTexture;

    public static FrameBuffer createFramebuffer() {

        FrameBuffer fb = new FrameBuffer();
        
        IntBuffer pWidth  = MemoryUtil.memAllocInt(1), 
                  pHeight = MemoryUtil.memAllocInt(1);
        GLFW.glfwGetFramebufferSize(DFMain.window, pWidth, pHeight);

        int width  = pWidth.get(0), 
            height = pHeight.get(0);

        fb.framebufferObject = GL33.glGenFramebuffers();
        GL33.glBindFramebuffer(GL33.GL_FRAMEBUFFER, fb.framebufferObject);

        fb.renderbufferObject = GL33.glGenRenderbuffers();

        fb.positionTexture = GL33.glGenTextures();
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, fb.positionTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA16F, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);
        GL33.glFramebufferTexture2D(GL33.GL_FRAMEBUFFER, GL33.GL_COLOR_ATTACHMENT0, GL33.GL_TEXTURE_2D, fb.positionTexture, 0);

        fb.normalTexture = GL33.glGenTextures();
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, fb.normalTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA16F, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);
        GL33.glFramebufferTexture2D(GL33.GL_FRAMEBUFFER, GL33.GL_COLOR_ATTACHMENT1, GL33.GL_TEXTURE_2D, fb.normalTexture, 0);

        fb.colorTexture = GL33.glGenTextures();
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, fb.colorTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);
        GL33.glFramebufferTexture2D(GL33.GL_FRAMEBUFFER, GL33.GL_COLOR_ATTACHMENT2, GL33.GL_TEXTURE_2D, fb.colorTexture, 0);

        int[] attachments = {
            GL33.GL_COLOR_ATTACHMENT0,
            GL33.GL_COLOR_ATTACHMENT1,
            GL33.GL_COLOR_ATTACHMENT2
        };
        GL33.glDrawBuffers(attachments);


        GL33.glBindRenderbuffer(GL33.GL_RENDERBUFFER, fb.renderbufferObject);
        GL33.glRenderbufferStorage(GL33.GL_RENDERBUFFER, GL33.GL_DEPTH24_STENCIL8, width, height);

        GL33.glFramebufferRenderbuffer(GL33.GL_FRAMEBUFFER, GL33.GL_DEPTH_STENCIL_ATTACHMENT, GL33.GL_RENDERBUFFER, fb.renderbufferObject);

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
        GL33.glBindFramebuffer(GL33.GL_FRAMEBUFFER, 0);

        return fb;
    }

    public void update() {

        GL33.glBindFramebuffer(GL33.GL_FRAMEBUFFER, framebufferObject);
        GL33.glBindRenderbuffer(GL33.GL_RENDERBUFFER, renderbufferObject);

        IntBuffer pWidth  = MemoryUtil.memAllocInt(1), 
                  pHeight = MemoryUtil.memAllocInt(1);
        GLFW.glfwGetFramebufferSize(DFMain.window, pWidth, pHeight);

        int width  = pWidth.get(0), 
            height = pHeight.get(0);

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, positionTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA16F, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, normalTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA16F, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, colorTexture);
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, width, height, 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, 0);

        GL33.glRenderbufferStorage(GL33.GL_RENDERBUFFER, GL33.GL_DEPTH24_STENCIL8, width, height);
    }

    public void bind() {
        update();
        GL33.glBindFramebuffer(GL33.GL_FRAMEBUFFER, framebufferObject);
    }

    public void unbind() {
        GL33.glBindFramebuffer(GL33.GL_FRAMEBUFFER, 0);
    }
}
