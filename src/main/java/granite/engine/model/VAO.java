package granite.engine.model;

import granite.engine.core.IBindable;
import granite.engine.core.IDestroyable;
import granite.engine.util.Buffer;
import org.lwjgl.opengl.*;

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

    public VAO(float[] positions, float[] textureCoordinates, int[] indices) {
        id = glGenVertexArrays();
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoordinates);
        bindIndicesBuffer(indices);
    }

    public int getId() {
        return id;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        bind();
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, Buffer.createFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vbos.add(vboId);
        unbind();
        vertexCount = data.length;
    }

    private void bindIndicesBuffer(int[] indices) {
        bind();
        int vboId = glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = Buffer.createIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
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
