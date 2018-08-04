package granite.engine.model;

import org.joml.Vector3f;

public class Color {

    private Vector3f vector;

    public Color(Vector3f vector) {
        this.vector = vector;
    }

    public Color(float r, float g, float b) {
        this(new Vector3f(r, g, b));
    }

    public Vector3f getVector() {
        return vector;
    }
}
