package kr.pe.burt.android.triangle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kr.pe.burt.android.triangle.glkit.BufferUtils;
import kr.pe.burt.android.triangle.glkit.ShaderProgram;
import kr.pe.burt.android.triangle.glkit.ShaderUtils;

/**
 * Created by burt on 2016. 6. 15..
 */
public class OGLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private Triangle triangle;

    public OGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        triangle = new Triangle(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        triangle.draw();
    }

}
