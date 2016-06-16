package kr.pe.burt.android.triangle;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import kr.pe.burt.android.triangle.glkit.BufferUtils;
import kr.pe.burt.android.triangle.glkit.ShaderProgram;
import kr.pe.burt.android.triangle.glkit.ShaderUtils;

/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Triangle {

    private FloatBuffer vertexBuffer;
    private ShaderProgram shader;
    private int vertexBufferId;
    private int vertexCount;
    private int vertexStride;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final float triangleCoords[] = {
             0.0f,  0.25f, 0.0f,    // TOP
            -0.5f, -0.25f, 0.0f,    // LEFT
             0.5f, -0.25f, 0.0f,    // RIGHT
    };

    public Triangle(Context context) {
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
        vertexBuffer = BufferUtils.newFloatBuffer(triangleCoords.length);

        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);

        // set the buffer to read the first coordinate
        vertexBuffer.position(0);


        //copy vertices from cpu to the gpu
        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, triangleCoords.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);

        vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
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
