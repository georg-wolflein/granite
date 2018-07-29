package granite.engine.model;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import granite.engine.core.IBindable;
import granite.engine.core.IDestroyable;
import granite.engine.util.Resource;
import granite.engine.util.ResourceType;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VAO implements IBindable, IDestroyable {

    private int id;
    private int vertexCount = 0;
    private List<Integer> vbos = new ArrayList<>();

    public VAO(FloatBuffer vertices, FloatBuffer textureCoordinates, FloatBuffer normals, IntBuffer indices, int vertexCount) {
        id = glGenVertexArrays();
        storeDataInAttributeList(0, 3, vertices);
        storeDataInAttributeList(1, 2, textureCoordinates);
        storeDataInAttributeList(2, 3, normals);
        bindIndicesBuffer(indices);
        this.vertexCount = vertexCount;
    }

    public int getId() {
        return id;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, FloatBuffer data) {
        bind();
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vbos.add(vboId);
        unbind();
    }

    private void bindIndicesBuffer(IntBuffer indices) {
        bind();
        int vboId = glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        unbind();
    }

    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public void bind() {
        glBindVertexArray(getId());
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public void destroy() {
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        glDeleteVertexArrays(getId());
    }

    public static VAO loadFromFile(String file) {
        InputStream input = Resource.loadResource(ResourceType.MODEL, file);
        try {
            Obj obj = ObjReader.read(input);
            return new VAO(ObjData.getVertices(obj), ObjData.getTexCoords(obj, 2), ObjData.getNormals(obj), ObjData.getFaceVertexIndices(obj), obj.getNumVertices());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("unable to read OBJ file");
            System.exit(-1);
            return null;
        }
    }
}
