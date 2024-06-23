#version 410 core

layout (location = 0) in vec2 vertex;
layout (location = 1) in vec2 uv;

out vec2 iuv;

void main() {
    iuv = uv;
    gl_Position = vec4(vertex, 0.0, 1.0);
}