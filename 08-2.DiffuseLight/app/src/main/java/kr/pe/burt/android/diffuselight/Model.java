package kr.pe.burt.android.diffuselight;

import android.opengl.GLES20;
import android.renderscript.Float3;
import android.renderscript.Matrix4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import kr.pe.burt.android.diffuselight.glkit.BufferUtils;
import kr.pe.burt.android.diffuselight.glkit.ShaderProgram;


/**
 * Created by burt on 2016. 6. 22..
 */
public abstract class Model {
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COLORS_PER_VERTEX = 4;
    private static final int TEXCOORDS_PER_VERTEX = 2;
    private static final int NORMALS_PER_VERTEX = 3;
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

    private int textureName = 0;

    // ModelView Transformation
    protected Float3 position = new Float3(0f, 0f, 0f);
    protected float rotationX  = 0.0f;
    protected float rotationY  = 0.0f;
    protected float rotationZ  = 0.0f;
    protected float scale      = 1.0f;
    protected Matrix4f camera  = new Matrix4f();
    protected Matrix4f projection = new Matrix4f();

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
        vertexStride = (COORDS_PER_VERTEX + COLORS_PER_VERTEX + TEXCOORDS_PER_VERTEX + NORMALS_PER_VERTEX) * SIZE_OF_FLOAT; // 4 bytes per vertex
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

    public void setCamera(Matrix4f mat) {
        camera.load(mat);
    }

    public void setProjection(Matrix4f mat) {
        projection.load(mat);
    }

    public void setTexture(int textureName) {
        this.textureName = textureName;
    }

    public void draw(long dt) {

        shader.begin();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);                 // 1번 텍스처 슬롯을 activate 한다.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName);    // 1번 텍스처 슬롯에 생성한 텍스처를 바인딩한다.
        shader.setUniformi("u_Texture", 1);                         // 유니폼 u_Texture에는 activate한 텍스처슬롯번호를 알려준다.

        camera.multiply(modelMatrix());
        shader.setUniformMatrix("u_ProjectionMatrix", projection);
        shader.setUniformMatrix("u_ModelViewMatrix",  camera);

        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

//        shader.enableVertexAttribute("a_Color");
//        shader.setVertexAttribute("a_Color", COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, COORDS_PER_VERTEX * SIZE_OF_FLOAT);

        shader.enableVertexAttribute("a_TexCoord");
        shader.setVertexAttribute("a_TexCoord", TEXCOORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * SIZE_OF_FLOAT);

        shader.enableVertexAttribute("a_Normal");
        shader.setVertexAttribute("a_Normal", NORMALS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, (COORDS_PER_VERTEX + COLORS_PER_VERTEX + TEXCOORDS_PER_VERTEX) * SIZE_OF_FLOAT);

        injectData(shader);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES,        // mode
                indices.length,             // count
                GLES20.GL_UNSIGNED_SHORT,   // type
                0);                         // offset

        shader.disableVertexAttribute("a_Position");
//        shader.disableVertexAttribute("a_Color");
        shader.disableVertexAttribute("a_TexCoord");

        shader.end();
    }

    abstract void injectData(ShaderProgram shader);
}
