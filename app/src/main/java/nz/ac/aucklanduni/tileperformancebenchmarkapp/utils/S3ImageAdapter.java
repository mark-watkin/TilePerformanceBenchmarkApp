package nz.ac.aucklanduni.tileperformancebenchmarkapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.BufferedInputStream;

import nz.ac.aucklanduni.tileperformancebenchmarkapp.model.Properties;

public class S3ImageAdapter {

    public static String getImageUrl() {
        return "test/img8.jpg";
    }

    public static Bitmap getImage(Properties properties) {

        String key = getImageUrl();
        AmazonS3 s3Client = new AmazonS3Client(properties);
        s3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));

        GetObjectRequest request = new GetObjectRequest(properties.getBucketName(), key);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream in = object.getObjectContent();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

        return bmp;
    }

    public static Bitmap getTile(Properties properties, String fileName) {
        AmazonS3 s3Client = new AmazonS3Client(properties);
        s3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));

        GetObjectRequest request = new GetObjectRequest(properties.getBucketName(), fileName);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream in = object.getObjectContent();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

        return bmp;
    }
}
