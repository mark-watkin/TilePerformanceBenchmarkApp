package nz.ac.aucklanduni.tileperformancebenchmarkapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import com.qozix.tileview.TileView;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.graphics.EyeAtlasDecoder;

public class ImageViewerActivity extends Activity {

    private TileView tileView;
    private static final float IMAGE_SCALE = 0;

    private int imageSizeX;
    private int imageSizeY;

    private String filePrefix = "test/8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageSizeY = 3019;
        imageSizeX = 4528;

        tileView = new TileView( this );
        tileView.setCacheEnabled( false );
        setContentView( tileView );
        tileView.setBackgroundColor(Color.BLACK);
        //tileView.disableSuppress();
        tileView.setDecoder(new EyeAtlasDecoder());

        // size of original image at 100% scale
        tileView.setSize(imageSizeX, imageSizeY );

        String previewFile = filePrefix + "/preview/preview.jpg";

        // detail levels
        tileView.addDetailLevel( 1.000f, filePrefix + "/1000/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel( 0.750f, filePrefix + "/750/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel( 0.500f, filePrefix + "/500/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel( 0.250f, filePrefix + "/250/img_%col%_%row%.jpg", previewFile, 512, 512);

        // allow scaling past original size
        tileView.setScaleLimits( 0, 2 );

        frameTo(imageSizeX / 2, imageSizeY / 2);

        // scale down a little
        tileView.setScale( IMAGE_SCALE );
    }

    @Override
    public void onPause() {
        super.onPause();
        tileView.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        tileView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tileView.destroy();
        tileView = null;
    }

    public TileView getTileView() {
        return tileView;
    }

    /**
     * This is a convenience method to moveToAndCenter after layout (which won't happen if called directly in onCreate
     * see https://github.com/moagrius/TileView/wiki/FAQ
     */
    private void frameTo( final double x, final double y ) {
        getTileView().post( new Runnable() {
            @Override
            public void run() {
                getTileView().moveToAndCenter( x, y );
            }
        });
    }
}
