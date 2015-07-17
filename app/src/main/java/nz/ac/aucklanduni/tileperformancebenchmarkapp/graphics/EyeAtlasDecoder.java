package nz.ac.aucklanduni.tileperformancebenchmarkapp.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qozix.tileview.graphics.BitmapDecoder;

import nz.ac.aucklanduni.tileperformancebenchmarkapp.model.Properties;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.utils.S3ImageAdapter;

public class EyeAtlasDecoder implements BitmapDecoder {

    private static final BitmapFactory.Options OPTIONS = new BitmapFactory.Options();
    static {
        OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    @Override
    public Bitmap decode(String fileName, Context context) {
        return  S3ImageAdapter.getTile (Properties.getInstance(context));
    }
}
