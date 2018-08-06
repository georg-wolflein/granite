package granite.engine.util.math;

import granite.engine.entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathUtil {

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rotation.x), 1.0f, 0.0f, 0.0f);
        matrix.rotate((float) Math.toRadians(rotation.y), 0.0f, 1.0f, 0.0f);
        matrix.rotate((float) Math.toRadians(rotation.z), 0.0f, 0.0f, 1.0f);
        matrix.scale(scale);
        return matrix;
    }

    public static Matrix4f createProjectionMatrix(int width, int height, float fieldOfView, float nearPlane, float farPlane) {
        float aspectRatio = (float) width / (float) height;
        float yScale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = farPlane - nearPlane;
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.m00(xScale);
        projectionMatrix.m11(yScale);
        projectionMatrix.m22(-((farPlane + nearPlane) / frustumLength));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * nearPlane * farPlane)) / frustumLength);
        projectionMatrix.m33(0);
        return projectionMatrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        // Invert position
        Matrix4f pos = new Matrix4f(camera.getAbsoluteTransformation());
        pos.m30(-pos.m30());
        pos.m31(-pos.m31());
        pos.m32(-pos.m32());
        return pos;
    }

    public static Vector3f getPositionFromTransformationMatrix(Matrix4f matrix) {
        return new Vector3f(matrix.m30(), matrix.m31(), matrix.m32());
    }
}
