package nz.ac.aucklanduni.tileperformancebenchmarkapp.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;

import com.qozix.tileview.TileView;

import nz.ac.aucklanduni.tileperformancebenchmarkapp.R;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.graphics.EyeAtlasDecoder;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.utils.TileViewListener;

public class ImageViewerActivity extends Activity {

    private final int img2W = 2144;
    private final int img2H = 1430;
    private final int img4W = 3124;
    private final int img4H = 2082;
    private final int img6W = 3900;
    private final int img6H = 2600;
    private final int img8W = 4528;
    private final int img8H = 3019;

    private TileView tileView;
    private static final float IMAGE_SCALE = 0;

    private int imageSizeX;
    private int imageSizeY;

    private String filePrefix = "test/2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageSizeX = img2W;
        imageSizeY = img2H;

        tileView = new TileView( this );
        tileView.setCacheEnabled(true);

        tileView.setBackgroundColor(Color.BLACK);
        //tileView.disableSuppress();
        tileView.setDecoder(new EyeAtlasDecoder());
        tileView.getTileManager().setTileRenderListener(new TileViewListener());

        // size of original image at 100% scale
        tileView.setSize(imageSizeX, imageSizeY);

        String previewFile = filePrefix + "/preview/preview.jpg";

        // detail levels
        tileView.addDetailLevel( 1.000f, filePrefix + "/1000/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel(0.750f, filePrefix + "/750/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel( 0.500f, filePrefix + "/500/img_%col%_%row%.jpg", previewFile, 512, 512);
        tileView.addDetailLevel(0.250f, filePrefix + "/250/img_%col%_%row%.jpg", previewFile, 512, 512);

        // allow scaling past original size
        tileView.setScaleLimits(0, 2);



//        frameTo(imageSizeX / 2, imageSizeY / 2);

        // scale down a little
        tileView.setScale(1);

        setContentView(tileView);


        pollNetworkUsage();
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

    public void pollNetworkUsage() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                while(true) {
                    try {
                        ApplicationInfo info = getPackageManager().getApplicationInfo("nz.ac.aucklanduni.tileperformancebenchmarkapp", 0);
                        Log.w("POLL", "TOTAL BYTE: " + TrafficStats.getUidRxBytes(info.uid));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }


    public void pollMemoryUsage() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                while(true) {
                    Log.w("POLL", "TOTAL MEM: " + Runtime.getRuntime().totalMemory());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}
