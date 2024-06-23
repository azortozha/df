#version 410 core

out vec4 fragc;
in vec2 iuv;
uniform sampler2D capture;
uniform vec3 color;

void main() {
    fragc = texture(capture, iuv);
    //fragc.rgb = 1 - fragc.rgb;
    //fragp = vec4(color, 1.0);
}