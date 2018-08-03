#version 400

in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_Color;

uniform vec3 color;
uniform vec3 lightColor;

void main(void) {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float normalDotLight = dot(unitNormal, unitLightVector);
    float brightness = max(normalDotLight, 0.);

    vec3 diffuse = brightness * lightColor;

    out_Color = vec4(diffuse, 1.) * vec4(color, 1.);

}
