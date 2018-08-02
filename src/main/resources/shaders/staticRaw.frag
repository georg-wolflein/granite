#version 400

out vec4 out_Color;

uniform vec3 color;

void main(void) {

    out_Color = vec4(color.xyz, 1.);

}
