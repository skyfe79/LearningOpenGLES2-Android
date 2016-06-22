package kr.pe.burt.android.model;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import kr.pe.burt.android.model.glkit.BufferUtils;
import kr.pe.burt.android.model.glkit.ShaderProgram;
import kr.pe.burt.android.model.glkit.ShaderUtils;


/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Square extends Model {



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
}
