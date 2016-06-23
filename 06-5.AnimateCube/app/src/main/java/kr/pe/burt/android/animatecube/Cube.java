package kr.pe.burt.android.animatecube;

import kr.pe.burt.android.animatecube.glkit.ShaderProgram;

/**
 * Created by burt on 2016. 6. 23..
 */
public class Cube extends Model {

    static final float vertices[] = {
        // Front
         1.0f, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,       // 0
         1.0f,  1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,       // 1
        -1.0f,  1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,       // 2
        -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,       // 3


        // Back
        -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,     // 4
        -1.0f,  1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,     // 5
         1.0f,  1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,     // 6
         1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f      // 7
    };

    static final short indices[] = {
        // Front
        0, 1, 2,
        2, 3, 0,

        // Back
        4, 5, 6,
        6, 7, 4,

        // Left
        3, 2, 5,
        5, 4, 3,

        // Right
        7, 6, 1,
        1, 0, 7,

        // Top
        1, 6, 5,
        5, 2, 1,

        // Bottom
        3, 4, 7,
        7, 0, 3
    };

    public Cube(ShaderProgram shader) {
        super("cube", shader, vertices, indices);
    }
}
