package granite.engine.model;

import com.mokiat.data.front.parser.*;
import granite.engine.core.IDestroyable;
import granite.engine.entities.SpatialNode;
import granite.engine.rendering.IRenderer;
import granite.engine.util.Buffer;
import granite.engine.util.resources.Resource;
import granite.engine.util.resources.ResourceType;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Model extends SpatialNode implements IDestroyable {

    private List<Mesh> meshes = new ArrayList<>();
    private MaterialManager materials = new MaterialManager();

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public MaterialManager getMaterials() {
        return materials;
    }

    private static class IndexManager {

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

    public static Model load(String file, IRenderer renderer) throws IOException {
        Model model = new Model();
        final IOBJParser objParser = new OBJParser();
        final IMTLParser mtlParser = new MTLParser();

        final OBJModel objModel = objParser.parse(Resource.loadResource(ResourceType.MODEL, file));

        for (String libraryReference : objModel.getMaterialLibraries()) {
            final MTLLibrary library = mtlParser.parse(Resource.loadResource(ResourceType.MODEL, libraryReference));
            for (MTLMaterial material : library.getMaterials()) {
                MTLColor diffuse = material.getDiffuseColor();
                MTLColor specular = material.getSpecularColor();
                model.getMaterials().add(new Material(material.getName(), new Color(diffuse.r, diffuse.g, diffuse.b), new Color(specular.r, specular.g, specular.b), material.getSpecularExponent()));
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
                model.addMesh(new RawMesh(vertexBuf, normalBuff, indexBuf, indices.size(), model.getMaterials().get(mesh.getMaterialName()), renderer));
            }
        }
        return model;
    }

    @Override
    public void destroy() {
        for (Mesh mesh : meshes) {
            mesh.destroy();
        }
        getMaterials().destroy();
    }

    @Override
    public void addChild(SpatialNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChild(SpatialNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDescendants(Collection<SpatialNode> descendants) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeDescendants(Collection<SpatialNode> descendants) {
        throw new UnsupportedOperationException();
    }
}