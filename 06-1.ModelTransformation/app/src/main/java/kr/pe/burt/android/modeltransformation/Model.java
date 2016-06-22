package kr.pe.burt.android.modeltransformation;

import android.opengl.GLES20;
import android.renderscript.Float3;
import android.renderscript.Matrix4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import kr.pe.burt.android.modeltransformation.glkit.BufferUtils;
import kr.pe.burt.android.modeltransformation.glkit.ShaderProgram;


/**
 * Created by burt on 2016. 6. 22..
 */
public class Model {
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COLORS_PER_VERTEX = 4;
    private static final int SIZE_OF_FLOAT = 4;
    private static final int SIZE_OF_SHORT = 2;

    private ShaderProgram shader;
    private String name;
    private float vertices[];
    private short indices[];


    private FloatBuffer vertexBuffer;
    private int vertexBufferId;
    private int vertexStride;

    private ShortBuffer indexBuffer;
    private int indexBufferId;

    // ModelView Transformation
    private Float3 position = new Float3(0f, 0f, 0f);
    private float rotationX  = 0.0f;
    private float rotationY  = 0.0f;
    private float rotationZ  = 0.0f;
    private float scale      = 1.0f;

    public Model(String name, ShaderProgram shader, float[] vertices, short[] indices) {
        this.name = name;
        this.shader = shader;
        this.vertices = Arrays.copyOfRange(vertices, 0, vertices.length);
        this.indices = Arrays.copyOfRange(indices, 0, indices.length);

        setupVertexBuffer();
        setupIndexBuffer();
    }

    public void setPosition(Float3 position) {
        this.position = position;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    private void setupVertexBuffer() {
        vertexBuffer = BufferUtils.newFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * SIZE_OF_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);
        vertexStride = (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * SIZE_OF_FLOAT; // 4 bytes per vertex
    }

    private void setupIndexBuffer() {
        // initialize index short buffer for index
        indexBuffer = BufferUtils.newShortBuffer(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);

        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        indexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices.length * SIZE_OF_SHORT, indexBuffer, GLES20.GL_STATIC_DRAW);
    }

    public Matrix4f modelMatrix() {
        Matrix4f mat = new Matrix4f(); // make a new identitiy 4x4 matrix
        mat.translate(position.x, position.y, position.z);
        mat.rotate(rotationX, 1.0f, 0.0f, 0.0f);
        mat.rotate(rotationY, 0.0f, 1.0f, 0.0f);
        mat.rotate(rotationZ, 0.0f, 0.0f, 1.0f);
        mat.scale(scale, scale, scale);
        return mat;
    }

    public void draw() {


        shader.begin();

        shader.setUniformMatrix("u_ModelViewMatrix", modelMatrix());
        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        shader.enableVertexAttribute("a_Color");
        shader.setVertexAttribute("a_Color", COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, COORDS_PER_VERTEX * SIZE_OF_FLOAT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES,        // mode
                indices.length,             // count
                GLES20.GL_UNSIGNED_SHORT,   // type
                0);                         // offset

        shader.disableVertexAttribute("a_Position");
        shader.disableVertexAttribute("a_Color");

        shader.end();
    }
}
