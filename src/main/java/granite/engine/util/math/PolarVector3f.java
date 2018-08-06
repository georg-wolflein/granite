package granite.engine.util.math;

import org.joml.Vector3f;

public class PolarVector3f {

    private float r, theta, phi;

    public Vector3f toCartesian() {
        double x = r * Math.sin(theta) * Math.cos(phi);
        double y = r * Math.sin(theta) * Math.sin(phi);
        double z = r * Math.cos(theta);
        return new Vector3f((float) x, (float) y, (float) z);
    }
}
