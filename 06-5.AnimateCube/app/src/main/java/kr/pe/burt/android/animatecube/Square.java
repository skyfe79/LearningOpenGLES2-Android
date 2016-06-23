package kr.pe.burt.android.animatecube;


import kr.pe.burt.android.animatecube.glkit.ShaderProgram;


/**
 * code from the url at { @link https://developer.android.com/training/graphics/opengl/shapes.html }
 * Created by burt on 2016. 6. 16..
 */
public class Square extends Model {

    static final float squareCoords[] = {
             1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,// top left
             1.0f,  1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,// bottom left
            -1.0f,  1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,// bottom right
            -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,// top right
    };

    static final short indices[] = {
            0, 1, 2,
            2, 3, 0
    };

    public Square(ShaderProgram shader) {
        super("square", shader, squareCoords, indices);
    }

}
