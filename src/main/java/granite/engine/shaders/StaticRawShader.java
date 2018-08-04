package granite.engine.shaders;

import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.model.Color;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class StaticRawShader extends Shader {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationDiffuseColor;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationSpecularColor;
    private int locationSpecularExponent;

    public StaticRawShader() {
        super("staticRaw.vert", "staticRaw.frag");
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationDiffuseColor = super.getUniformLocation("diffuseColor");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationLightColor = super.getUniformLocation("lightColor");
        locationSpecularColor = super.getUniformLocation("specularColor");
        locationSpecularExponent = super.getUniformLocation("specularExponent");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(locationViewMatrix, MathUtil.createViewMatrix(camera));
    }

    public void loadDiffuseColor(Color color) {
        super.loadVector(locationDiffuseColor, color.getVector());
    }

    public void loadSpecularVariables(Color specularColor, float specularExponent) {
        super.loadVector(locationSpecularColor, specularColor.getVector());
        super.loadFloat(locationSpecularExponent, specularExponent);
    }

    public void loadLight(Light light) {
        super.loadVector(locationLightColor, light.getColor());
        super.loadVector(locationLightPosition, light.getPosition());
    }
}
