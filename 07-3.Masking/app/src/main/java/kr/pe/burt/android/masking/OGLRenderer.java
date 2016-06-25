package kr.pe.burt.android.masking;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.renderscript.Float3;
import android.renderscript.Matrix4f;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kr.pe.burt.android.masking.glkit.ShaderProgram;
import kr.pe.burt.android.masking.glkit.ShaderUtils;
import kr.pe.burt.android.masking.glkit.TextureUtils;


/**
 * Created by burt on 2016. 6. 15..
 */
public class OGLRenderer implements GLSurfaceView.Renderer {

    private static final float ONE_SEC = 1000.0f; // 1 second

    private Context context;
    private MaskedSquare zombie;
    private MaskedSquare frame;


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

        int zombieTexture = TextureUtils.loadTexture(context, R.drawable.picture);
        int frameTexture = TextureUtils.loadTexture(context, R.drawable.picture_frame);
        int maskTexture = TextureUtils.loadTexture(context, R.drawable.picture_frame_mask);

        zombie = new MaskedSquare(shader);
        frame  = new MaskedSquare(shader);

        frame.setPosition(new Float3(0.0f, 0.0f, 0.1f));
        zombie.setPosition(new Float3(0.0f, 0.0f, 0.0f));
        zombie.setTexture(zombieTexture);
        zombie.setMask(maskTexture);
        frame.setTexture(frameTexture);

        lastTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);

        Matrix4f perspective = new Matrix4f();
        perspective.loadPerspective(85.0f, (float)w / (float)h, 1.0f, -150.0f);

        if(zombie != null) {
            zombie.setProjection(perspective);
        }

        if(frame != null) {
            frame.setProjection(perspective);
        }
    }

    /**
     * GLSurfaceView has default 16bit depth buffer
     */
    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        long currentTimeMillis = System.currentTimeMillis();
        updateWithDelta(currentTimeMillis - lastTimeMillis);
        lastTimeMillis = currentTimeMillis;
    }

    public void updateWithDelta(long dt) {

        Matrix4f camera = new Matrix4f();
        camera.translate(0.0f, 0.0f, -5.0f);

        zombie.setCamera(camera);
        frame.setCamera(camera);

        zombie.draw(dt);

        final float secsPerMove = 2.0f * ONE_SEC;
        float movement = (float) (Math.sin(System.currentTimeMillis() * 2 * Math.PI / secsPerMove));
        frame.setPosition(new Float3(movement, frame.position.y, frame.position.z));
        frame.draw(dt);
    }

}
