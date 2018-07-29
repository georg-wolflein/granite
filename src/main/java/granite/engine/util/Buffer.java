package granite.engine.util;

import org.lwjgl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer {

    public static FloatBuffer createFloatBuffer(float[] floats) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(floats.length);
        buffer.put(floats);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] ints) {
        IntBuffer buffer = BufferUtils.createIntBuffer(ints.length);
        buffer.put(ints);
        buffer.flip();
        return buffer;
    }
}
