package granite.engine.model;

import granite.engine.core.IDestroyable;

public class Material implements IDestroyable {

    private String name;
    private Color diffuseColor, specularColor;
    private float specularExponent;

    public Material(String name, Color diffuseColor, Color specularColor, float specularExponent) {
        this.name = name;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularExponent = specularExponent;
    }

    public String getName() {
        return name;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    @Override
    public void destroy() {

    }
}
