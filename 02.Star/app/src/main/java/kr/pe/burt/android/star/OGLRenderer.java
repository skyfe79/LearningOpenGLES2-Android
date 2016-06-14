package kr.pe.burt.android.star;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

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

    ShaderProgram shader;
    Context context;
    int vertexBuffer = -1;

    //we should convert vertices to FloatBuffer!
    Vertex [] vertices = new Vertex[] {
        new Vertex( 0.37f, -0.12f, 0.0f),
        new Vertex( 0.95f,  0.30f, 0.0f),
        new Vertex( 0.23f,  0.30f, 0.0f),

        new Vertex( 0.23f,  0.30f, 0.0f),
        new Vertex( 0.00f,  0.90f, 0.0f),
        new Vertex(-0.23f,  0.30f, 0.0f),

        new Vertex(-0.23f,  0.30f, 0.0f),
        new Vertex(-0.95f,  0.30f, 0.0f),
        new Vertex(-0.37f, -0.12f, 0.0f),

        new Vertex(-0.37f, -0.12f, 0.0f),
        new Vertex(-0.57f, -0.81f, 0.0f),
        new Vertex( 0.00f, -0.40f, 0.0f),

        new Vertex( 0.00f, -0.40f, 0.0f),
        new Vertex( 0.57f, -0.81f, 0.0f),
        new Vertex( 0.37f, -0.12f, 0.0f),
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
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    private void setupShader() {
        shader = new ShaderProgram(
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_vertex_shader),
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_fragment_shader)
        );
    }

    private void setupVertexBuffer() {
        IntBuffer vb = BufferUtils.newIntBuffer(1);
        GLES20.glGenBuffers(1, vb);
        vertexBuffer = vb.get(0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer);
        //GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, );
    }
}
