package granite.engine.model;

import com.mokiat.data.front.parser.*;
import granite.engine.core.IDestroyable;
import granite.engine.util.Buffer;
import granite.engine.util.Resource;
import granite.engine.util.ResourceType;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model implements IDestroyable {

    public Model(String name) {
        this.name = name;
    }

    private String name;
    private List<Mesh> meshes = new ArrayList<>();
    private MaterialManager materials = new MaterialManager();

    public String getName() {
        return name;
    }

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    private class IndexManager {

        private List<VertexAttributes> attributes = new LinkedList<>();
        private List<Integer> indices = new LinkedList<>();

        private class VertexAttributes {
            OBJVertex vertex;
            OBJNormal normal;
            OBJTexCoord textureCoordinate;

            public VertexAttributes(OBJVertex vertex, OBJNormal normal, OBJTexCoord textureCoordinate) {
                this.vertex = vertex;
                this.normal = normal;
                this.textureCoordinate = textureCoordinate;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof VertexAttributes) {
                    VertexAttributes o = (VertexAttributes) obj;
                    return vertex == o.vertex && normal == o.normal && textureCoordinate == o.textureCoordinate;
                }
                return false;
            }
        }

        public void add(VertexAttributes a) {
            if (!attributes.contains(a))
                attributes.add(a);
            indices.add(attributes.indexOf(a));
        }

        public void add(OBJVertex vertex, OBJNormal normal, OBJTexCoord texCoord) {
            add(new VertexAttributes(vertex, normal, texCoord));
        }

        public List<OBJVertex> getVertices() {
            List<OBJVertex> items = new ArrayList<>(attributes.size());
            for (VertexAttributes a : attributes) {
                items.add(a.vertex);
            }
            return items;
        }

        public List<OBJNormal> getNormals() {
            List<OBJNormal> items = new ArrayList<>(attributes.size());
            for (VertexAttributes a : attributes) {
                items.add(a.normal);
            }
            return items;
        }

        public List<OBJTexCoord> getTextureCoordinates() {
            List<OBJTexCoord> items = new ArrayList<>(attributes.size());
            for (VertexAttributes a : attributes) {
                items.add(a.textureCoordinate);
            }
            return items;
        }

        public List<Integer> getIndices() {
            return indices;
        }

    }

    public void load(String file) throws IOException {
        final IOBJParser objParser = new OBJParser();
        final IMTLParser mtlParser = new MTLParser();

        final OBJModel objModel = objParser.parse(Resource.loadResource(ResourceType.MODEL, file));

        for (String libraryReference : objModel.getMaterialLibraries()) {
            final MTLLibrary library = mtlParser.parse(Resource.loadResource(ResourceType.MODEL, libraryReference));
            for (MTLMaterial material : library.getMaterials()) {
                materials.add(new Material(material));
            }
        }

        for (OBJObject object : objModel.getObjects()) {
            for (OBJMesh mesh : object.getMeshes()) {
                IndexManager indexManager = new IndexManager();
                for (OBJFace face : mesh.getFaces()) {
                    for (OBJDataReference ref : face.getReferences()) {
                        indexManager.add(objModel.getVertex(ref), objModel.getNormal(ref), ref.texCoordIndex >= 0 ? objModel.getTexCoord(ref) : null);
                    }
                }
                List<Integer> indices = indexManager.getIndices();
                List<OBJVertex> objVertices = indexManager.getVertices();
                List<OBJNormal> objNormals = indexManager.getNormals();
                List<Float> vertices = new ArrayList<>(objVertices.size() * 3);
                objVertices.forEach(v -> {
                    vertices.add(v.x);
                    vertices.add(v.y);
                    vertices.add(v.z);
                });
                List<Float> normals = new ArrayList<>(objNormals.size() * 3);
                objNormals.forEach(n -> {
                    normals.add(n.x);
                    normals.add(n.y);
                    normals.add(n.z);
                });
                IntBuffer indexBuf = Buffer.createIntBuffer(indices);
                FloatBuffer vertexBuf = Buffer.createFloatBuffer(vertices);
                FloatBuffer normalBuff = Buffer.createFloatBuffer(normals);
                // TODO: get texture coordinates
                addMesh(new RawMesh(vertexBuf, normalBuff, indexBuf, indices.size(), materials.get(mesh.getMaterialName())));
            }
        }
    }

    @Override
    public void destroy() {
        for (Mesh mesh : meshes) {
            mesh.destroy();
        }
        materials.destroy();
    }
}