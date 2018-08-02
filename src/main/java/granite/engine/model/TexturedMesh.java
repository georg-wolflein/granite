package granite.engine.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class TexturedMesh extends RawMesh {

    public TexturedMesh(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textureCoordinates, IntBuffer indices, int vertexCount, Material material) {
        super(vertices, normals, indices, vertexCount, material);
        storeDataInAttributeList(2, 2, textureCoordinates);
    }
}
