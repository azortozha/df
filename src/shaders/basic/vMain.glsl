#version 410 core

uniform mat4 projection;
uniform mat4 lookat;

layout(location = 0) in vec3 vertex;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;

out vec3 ivertex;
out vec3 inormal;
out vec2 iuv;

void main() {

    ivertex = vertex;
    inormal = normal;
    iuv = uv;

    gl_Position = projection * lookat * vec4(vertex, 1.0);
}