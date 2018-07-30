package granite.engine.model;

import granite.engine.core.IBindable;
import granite.engine.core.IDestroyable;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class VAO implements IBindable, IDestroyable {

    private int id, vertexCount;
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
        throw new RuntimeException();
    }
}
