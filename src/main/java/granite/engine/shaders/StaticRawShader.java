package granite.engine.shaders;

import granite.engine.entities.Camera;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StaticRawShader extends Shader {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationColorVector;

    public StaticRawShader() {
        super("staticRaw.vert", "staticRaw.frag");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "color");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationColorVector = super.getUniformLocation("color");
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

    public void loadColorVector(Vector3f vector) {
        super.loadVector(locationColorVector, vector);
    }
}
