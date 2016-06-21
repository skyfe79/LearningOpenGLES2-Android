package kr.pe.burt.android.coloredindexsquare;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import kr.pe.burt.android.coloredindexsquare.glkit.BufferUtils;
import kr.pe.burt.android.coloredindexsquare.glkit.ShaderProgram;
import kr.pe.burt.android.coloredindexsquare.glkit.ShaderUtils;

/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Square {


    private ShaderProgram shader;

    private FloatBuffer vertexBuffer;
    private int vertexBufferId;
    private int vertexCount;
    private int vertexStride;

    private ShortBuffer indexBuffer;
    private int indexBufferId;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COLORS_PER_VERTEX = 4;
    static final int SIZE_OF_FLOAT = 4;
    static final int SIZE_OF_SHORT = 2;

    static final float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,// top left
            -0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,// bottom left
             0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1.0f,// bottom right
             0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f,// top right
    };

    static final short indexes[] = {
            0, 1, 2,
            0, 2, 3,
    };


    public Square(Context context) {
        setupShader(context);
        setupVertexBuffer();
        setupIndexBuffer();
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
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, squareCoords.length * SIZE_OF_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);

        vertexCount = squareCoords.length / (COORDS_PER_VERTEX + COLORS_PER_VERTEX);
        vertexStride = (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * SIZE_OF_FLOAT; // 4 bytes per vertex
    }

    private void setupIndexBuffer() {
        // initialize index short buffer for index
        indexBuffer = BufferUtils.newShortBuffer(indexes.length);
        indexBuffer.put(indexes);
        indexBuffer.position(0);

        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        indexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexes.length * SIZE_OF_SHORT, indexBuffer, GLES20.GL_STATIC_DRAW);
    }

    public void draw() {

        shader.begin();

        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        shader.enableVertexAttribute("a_Color");
        shader.setVertexAttribute("a_Color", COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, COORDS_PER_VERTEX * SIZE_OF_FLOAT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES,        // mode
                indexes.length,             // count
                GLES20.GL_UNSIGNED_SHORT,   // type
                0);                         // offset

        shader.disableVertexAttribute("a_Position");
        shader.disableVertexAttribute("a_Color");

        shader.end();
    }
}
