package kr.pe.burt.android.square;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import kr.pe.burt.android.square.glkit.BufferUtils;
import kr.pe.burt.android.square.glkit.ShaderProgram;
import kr.pe.burt.android.square.glkit.ShaderUtils;

/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Square {

    private FloatBuffer vertexBuffer;
    private ShaderProgram shader;
    private int vertexBufferId;
    private int vertexCount;
    private int vertexStride;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
             0.5f, -0.5f, 0.0f,   // bottom right

            -0.5f,  0.5f, 0.0f,   // top left
             0.5f, -0.5f, 0.0f,   // bottom right
             0.5f,  0.5f, 0.0f    // top right
    };

    public Square(Context context) {
        setupShader(context);
        setupVertexBuffer();
    }

    private void setupShader(Context context) {
        // compile & link shader
        shader = new ShaderProgram(
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_vertex_shader),
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_fragment_shader)
        );
    }

    private void setupVertexBuffer() {
        // initialize vertex float buffer for shape coordinates
        vertexBuffer = BufferUtils.newFloatBuffer(squareCoords.length);

        // add the coordinates to the FloatBuffer
        vertexBuffer.put(squareCoords);

        // set the buffer to read the first coordinate
        vertexBuffer.position(0);


        //copy vertices from cpu to the gpu
        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, squareCoords.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);

        vertexCount = squareCoords.length / COORDS_PER_VERTEX;
        vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    }

    public void draw() {

        shader.begin();

        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        shader.disableVertexAttribute("a_Position");

        shader.end();
    }
}
