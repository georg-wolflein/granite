package granite.engine.model;

import granite.engine.rendering.IRenderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RawMesh extends Mesh {

    public RawMesh(FloatBuffer vertices, FloatBuffer normals, IntBuffer indices, int vertexCount, Material material, IRenderer renderer) {
        super(vertices, normals, indices, vertexCount, material, renderer);
    }
}
