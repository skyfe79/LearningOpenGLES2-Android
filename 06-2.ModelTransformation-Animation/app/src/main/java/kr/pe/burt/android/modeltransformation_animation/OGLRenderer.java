package kr.pe.burt.android.modeltransformation_animation;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.renderscript.Float3;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kr.pe.burt.android.modeltransformation_animation.glkit.ShaderProgram;
import kr.pe.burt.android.modeltransformation_animation.glkit.ShaderUtils;


/**
 * Created by burt on 2016. 6. 15..
 */
public class OGLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private Square square;
    private long lastTimeMillis = 0L;

    public OGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        ShaderProgram shader = new ShaderProgram(
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_vertex_shader),
                ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_fragment_shader)
        );
        square = new Square(shader);
        square.setPosition(new Float3(0.0f, 0.0f, 0.0f));
        lastTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        long currentTimeMillis = System.currentTimeMillis();
        square.draw(currentTimeMillis - lastTimeMillis);
        lastTimeMillis = currentTimeMillis;
    }

}
