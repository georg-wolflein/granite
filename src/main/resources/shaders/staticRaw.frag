#version 400

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform vec3 diffuseColor;
uniform vec3 specularColor;
uniform float specularExponent;
uniform vec3 lightColor;

void main(void) {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float normalDotLight = dot(unitNormal, unitLightVector);
    float brightness = max(normalDotLight, 0.2); // ambient light 0.2

    vec3 diffuse = brightness * lightColor;

    vec3 unitCameraVector = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    specularFactor = max(specularFactor, 0.0);
    specularFactor = pow(specularFactor, specularExponent);
    vec3 specular = specularFactor * lightColor * specularColor;

    out_Color = vec4(diffuse, 1.) * vec4(diffuseColor, 1.) + vec4(specular, 1.);


}
