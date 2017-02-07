package br.ufscar.auxiliares;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

// Contains auxiliary methods to convert stuff
public class Convert {
    public static byte[] bitmapToBlob(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap blobToBitmap(byte[] stream) {
        return BitmapFactory.decodeByteArray(stream, 0, stream.length);
    }
}
