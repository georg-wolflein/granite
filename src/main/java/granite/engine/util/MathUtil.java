package granite.engine.util;

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

        projectionMatrix = new Matrix4f();
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
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        Vector3f position = camera.getPosition();
        Vector3f negativePosition = new Vector3f(-position.x, -position.y, -position.z);
        viewMatrix.translate(negativePosition);
        return viewMatrix;
    }
}
