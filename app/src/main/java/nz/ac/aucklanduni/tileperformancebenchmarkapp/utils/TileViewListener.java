package nz.ac.aucklanduni.tileperformancebenchmarkapp.utils;

import android.util.Log;

import com.qozix.tileview.tiles.TileRenderListener;


public class TileViewListener implements TileRenderListener {
    private long start;
    private long end;

    @Override
    public void onRenderStart() {
        start = System.nanoTime();
    }

    @Override
    public void onRenderCancelled() {

    }

    @Override
    public void onRenderComplete() {
        end = System.nanoTime();
        Log.i("TileViewBenchmark", "Tile render in nanoSeconds: " + (end - start));
    }
}
