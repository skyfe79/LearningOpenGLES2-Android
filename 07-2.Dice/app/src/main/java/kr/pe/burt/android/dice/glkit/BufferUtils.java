package kr.pe.burt.android.dice.glkit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * Created by burt on 2016. 5. 11..
 */
public class BufferUtils {

    public static FloatBuffer newFloatBuffer (int numFloats) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numFloats * 4);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asFloatBuffer();
    }

    public static DoubleBuffer newDoubleBuffer (int numDoubles) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numDoubles * 8);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asDoubleBuffer();
    }

    public static ByteBuffer newByteBuffer (int numBytes) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numBytes);
        buffer.order(ByteOrder.nativeOrder());
        return buffer;
    }

    public static ShortBuffer newShortBuffer (int numShorts) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numShorts * 2);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asShortBuffer();
    }

    public static CharBuffer newCharBuffer (int numChars) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numChars * 2);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asCharBuffer();
    }

    public static IntBuffer newIntBuffer(int numInts) {
        ByteBuffer buffer = ByteBuffer.allocate(numInts * 4);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asIntBuffer();
    }

    public static LongBuffer newLongBuffer(int numLongs) {
        ByteBuffer buffer = ByteBuffer.allocate(numLongs * 8);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asLongBuffer();
    }
}
