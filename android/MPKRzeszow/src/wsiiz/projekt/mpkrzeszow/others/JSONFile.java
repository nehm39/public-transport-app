package wsiiz.projekt.mpkrzeszow.others;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;

public class JSONFile {

public static String readJSON(String fileName, Context c) {
    try {
        InputStream is = c.getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String text = new String(buffer);

        return text;

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

}
