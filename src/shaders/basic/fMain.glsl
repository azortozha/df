#version 410 core

layout (location = 0) out vec4 fragp;
layout (location = 1) out vec4 normal;
layout (location = 2) out vec4 fragc;

uniform vec3 color;

in vec3 ivertex;
in vec3 inormal;
in vec2 iuv;

void main() {

    fragp  = vec4(ivertex, 1.0);
    normal = vec4(inormal, 1.0);
    fragc  = vec4(color, 1.0);
}