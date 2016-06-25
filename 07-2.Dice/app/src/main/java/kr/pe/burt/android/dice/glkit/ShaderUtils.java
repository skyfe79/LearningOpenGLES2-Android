package kr.pe.burt.android.dice.glkit;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by burt on 2016. 6. 15..
 */
public class ShaderUtils {

    public static String readShaderFileFromRawResource(final Context context, final int resourceId)
    {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();

        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append("\n");
            }
        }
        catch(IOException e)
        {
            return null;
        }

        return body.toString();
    }

    public static String readShaderFileFromFilePath(final Context context, final String filePath)
    {
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream ims = assetManager.open(filePath);

            String re = convertStreamToString(ims);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertStreamToString(InputStream is)
    {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
