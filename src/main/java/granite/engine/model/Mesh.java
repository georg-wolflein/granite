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

public abstract class Mesh implements IBindable, IDestroyable {

    private int id, vertexCount;
    private List<Integer> vbos = new ArrayList<>();
    private Material material;

    public Mesh(FloatBuffer vertices, FloatBuffer normals, IntBuffer indices, int vertexCount, Material material) {
        this.id = glGenVertexArrays();
        this.vertexCount = vertexCount;
        this.material = material;
        storeDataInAttributeList(0, 3, vertices);
        storeDataInAttributeList(1, 3, normals);
        bindIndicesBuffer(indices);
    }

    public int getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    protected void storeDataInAttributeList(int attributeNumber, int coordinateSize, FloatBuffer data) {
        bind();
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vbos.add(vboId);
        unbind();
    }

    protected void bindIndicesBuffer(IntBuffer indices) {
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
}
