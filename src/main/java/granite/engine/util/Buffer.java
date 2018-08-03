package granite.engine.util;

import org.lwjgl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class Buffer {

    public static FloatBuffer createFloatBuffer(float[] floats) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(floats.length);
        buffer.put(floats);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFloatBuffer(List<Float> floats) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(floats.size());
        floats.forEach(buffer::put);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] ints) {
        IntBuffer buffer = BufferUtils.createIntBuffer(ints.length);
        buffer.put(ints);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(List<Integer> ints) {
        IntBuffer buffer = BufferUtils.createIntBuffer(ints.size());
        ints.forEach(buffer::put);
        buffer.flip();
        return buffer;
    }

}
