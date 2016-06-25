package kr.pe.burt.android.masking;

import kr.pe.burt.android.masking.glkit.ShaderProgram;

/**
 * Created by burt on 2016. 6. 25..
 */
public class MaskedSquare extends Model {

    static final float vertices[] = {
             1.0f, -1.0f, 0f,   1f, 0f,
             1.0f,  1.0f, 0f,   1f, 1f,
            -1.0f,  1.0f, 0f,   0f, 1f,
            -1.0f, -1.0f, 0f,   0f, 0f

    };

    static final short indices[] = {
            0, 1, 2,
            2, 3, 0
    };



    public MaskedSquare(ShaderProgram shader) {
        super("MaskedSquare", shader, vertices, indices);
    }
}
