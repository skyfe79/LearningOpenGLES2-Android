package kr.pe.burt.android.star;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kr.pe.burt.android.star.R;
import kr.pe.burt.android.star.glkit.BufferUtils;
import kr.pe.burt.android.star.glkit.ShaderProgram;
import kr.pe.burt.android.star.glkit.ShaderUtils;

/**
 * Created by burt on 2016. 6. 15..
 */
public class OGLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private ShaderProgram shader;
    private FloatBuffer vertexBuffer;
    private int vertexBufferId;

    //we should convert vertices to FloatBuffer!
    float [] vertices = new float[] {
             0.37f, -0.12f, 0.0f,
             0.95f,  0.30f, 0.0f,
             0.23f,  0.30f, 0.0f,

             0.23f,  0.30f, 0.0f,
             0.00f,  0.90f, 0.0f,
            -0.23f,  0.30f, 0.0f,

            -0.23f,  0.30f, 0.0f,
            -0.95f,  0.30f, 0.0f,
            -0.37f, -0.12f, 0.0f,

            -0.37f, -0.12f, 0.0f,
            -0.57f, -0.81f, 0.0f,
             0.00f, -0.40f, 0.0f,

             0.00f, -0.40f, 0.0f,
             0.57f, -0.81f, 0.0f,
             0.37f, -0.12f, 0.0f,
    };

    public OGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        setupShader();
        setupVertexBuffer();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        shader.begin();

        shader.enableVertexAttribute("a_Position");
        shader.setVertexAttribute("a_Position", 3, GLES20.GL_FLOAT, false, 3*4, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length);

        shader.disableVertexAttribute("a_Position");

        shader.end();
    }

    private void setupShader() {
        // compile & link shader
        shader = new ShaderProgram(
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_vertex_shader),
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_fragment_shader)
        );
    }

    private void setupVertexBuffer() {

        //store vertices vertexBuffer
        vertexBuffer = BufferUtils.newFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        //copy vertices from cpu to the gpu
        IntBuffer buffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, buffer);
        vertexBufferId = buffer.get(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);

    }
}
