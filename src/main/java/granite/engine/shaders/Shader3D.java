package granite.engine.shaders;

import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.model.Color;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;

public abstract class Shader3D extends Shader {

    public Shader3D(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix("projectionMatrix", matrix);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix("transformationMatrix", matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix("viewMatrix", MathUtil.createViewMatrix(camera));
    }

    public void loadDiffuseColor(Color color) {
        super.loadVector("diffuseColor", color.getVector());
    }

    public void loadSpecularVariables(Color specularColor, float specularExponent) {
        super.loadVector("specularColor", specularColor.getVector());
        super.loadFloat("specularExponent", specularExponent);
    }

    public void loadLight(Light light) {
        super.loadVector("lightColor", light.getColor().getVector());
        super.loadVector("lightPosition", light.getAbsolutePosition());
    }

}
