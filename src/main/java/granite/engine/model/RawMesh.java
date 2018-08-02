package granite.engine.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RawMesh extends Mesh {

    public RawMesh(FloatBuffer vertices, FloatBuffer normals, IntBuffer indices, int vertexCount, Material material) {
        super(vertices, normals, indices, vertexCount, material);
    }
}
