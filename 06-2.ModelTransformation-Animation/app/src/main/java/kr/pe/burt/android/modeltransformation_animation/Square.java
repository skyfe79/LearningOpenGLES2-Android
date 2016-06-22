package kr.pe.burt.android.modeltransformation_animation;


import android.renderscript.Float3;

import kr.pe.burt.android.modeltransformation_animation.glkit.ShaderProgram;

/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Square extends Model {

    private static final float ONE_SEC = 1000.0f; // 1 second


    static final float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,// top left
            -0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,// bottom left
             0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1.0f,// bottom right
             0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f,// top right
    };

    static final short indices[] = {
            0, 1, 2,
            0, 2, 3,
    };

    public Square(ShaderProgram shader) {
        super("square", shader, squareCoords, indices);
    }

    @Override
    public void updateWithDelta(long dt) {
        final float secsPerMove = 2.0f * ONE_SEC;
        setPosition(new Float3(
                (float)(Math.sin(System.currentTimeMillis() * 2 * Math.PI / secsPerMove)),
                position.y,
                position.z)
        );
    }
}
