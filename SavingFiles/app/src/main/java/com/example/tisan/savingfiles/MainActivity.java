package com.example.tisan.savingfiles;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Writing File to internal memory
        String fileName = "myFile";
        String string = "Hwllo World!";
        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    // Writing File to internal cache memory
    public File getTempFile(Context context,String url){
        File file = null;
        try{
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName,null,context.getCacheDir());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }


    // Writing to external storage

    /* Checks if external strorage is available for read and write */
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /* Checks if external storage is available at least to read */
    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    /* Creates File passed as albumName in external storage public picture directory */
    public File getAlbumStorageDir(String albumName){
        // Get the directory for user's public picture directory
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),albumName);
        if(!file.mkdirs()){
            Log.e("File","Directory Not Created");
        }
        return file;
    }

    /* Creates File passed as albumName in external storage private picture directory */
    public File getAlbumStorageDir(Context context,String albumname){
        // Get the directory for app's private picture directory
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),albumname);
        if(!file.mkdirs()){
            Log.e("File","Directory Not Created!");
        }
        return file;
    }



    // Delete File
    public void deleteFile(File fileName){
        try{
            fileName.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /*
    If you know ahead of time how much data you're saving, you can find out whether sufficient space is available
    without causing an IOException by calling getFreeSpace() or getTotalSpace(). These methods provide the current
    available space and the total space in the storage volume, respectively. This information is also useful to
    avoid filling the storage volume above a certain threshold.

        However, the system does not guarantee that you can write as many bytes as are indicated by getFreeSpace().
        If the number returned is a few MB more than the size of the data you want to save, or if the file system
        is less than 90% full, then it's probably safe to proceed. Otherwise, you probably shouldn't write to storage.

            Note: You aren't required to check the amount of available space before you save your file. You can
            instead try writing the file right away, then catch an IOException if one occurs. You may need to do
            this if you don't know exactly how much space you need. For example, if you change the file's encoding
            before you save it by converting a PNG image to JPEG, you won't know the file's size beforehand.
     */


}
