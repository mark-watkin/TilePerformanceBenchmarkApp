package nz.ac.aucklanduni.tileperformancebenchmarkapp.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

import nz.ac.aucklanduni.tileperformancebenchmarkapp.R;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.model.Properties;
import nz.ac.aucklanduni.tileperformancebenchmarkapp.utils.S3ImageAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Properties properties = Properties.getInstance(this);

        try {
            final ImageView iv = (ImageView) this.findViewById(R.id.image);
            long start = System.nanoTime();
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    return S3ImageAdapter.getImage(properties);
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    iv.setImageBitmap(result);
                }

            }.execute().get();
            long end = System.nanoTime();
            Log.i("TileViewBenchmark", "Image render in nanoSeconds: " + (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
