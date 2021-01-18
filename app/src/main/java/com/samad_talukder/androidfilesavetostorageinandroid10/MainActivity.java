package com.samad_talukder.androidfilesavetostorageinandroid10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

import static com.samad_talukder.androidfilesavetostorageinandroid10.ExternalStorageUtil.getPrivateExternalStorageBaseDir;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG_EXTERNAL_STORAGE = "FileSaveToStorage";

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText emailEditor = findViewById(R.id.et_write_something);

        /*int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/

        /* Save Text To A Public External Storage Folder. */
        Button savePublicExternalStorageButton = findViewById(R.id.btn_save_public_external_storage);
        savePublicExternalStorageButton.setOnClickListener(view -> {

            try {
                if (ExternalStorageUtil.isExternalStorageMounted()) {

                    // Check whether this app has write external storage permission or not.
                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    // If do not grant write external storage permission.
                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Request user to grant write external storage permission.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                    } else {

                        // Save sample.txt file to /storage/emulated/0/DCIM folder
                        String publicDcimDirPath = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);

                        File newFile = new File(publicDcimDirPath, "sample.txt");

                        FileWriter fw = new FileWriter(newFile);

                        fw.write(emailEditor.getText().toString());

                        fw.flush();

                        fw.close();

                        Toast.makeText(getApplicationContext(), "Save to public external storage success. File Path " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);

                Toast.makeText(getApplicationContext(), "Save to public external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /* Save Text To A Private External Storage Folder. */
        Button savePrivateExternalStorageButton = findViewById(R.id.btn_external_storage_save_private);
        savePrivateExternalStorageButton.setOnClickListener(view -> {

            try {
                if (ExternalStorageUtil.isExternalStorageMounted()) {

                    // Check whether this app has write external storage permission or not.
                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    // If do not grant write external storage permission.
                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Request user to grant write external storage permission.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                    } else {

                        // Save sample.txt file to /storage/emulated/0/Android/data/com.samad_talukder.androidfilesavetostorageinandroid10/files folder
                        String privateDirPath = getPrivateExternalStorageBaseDir(getApplicationContext(), null);

                        File newFile = new File(privateDirPath, "sample.txt");

                        FileWriter fw = new FileWriter(newFile);

                        fw.write(emailEditor.getText().toString());

                        fw.flush();

                        fw.close();

                        Toast.makeText(getApplicationContext(), "Save to private external storage success. File Path " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);

                Toast.makeText(getApplicationContext(), "Save to private external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /* Save Text To A App Private Cached External Storage Folder.*/
        Button savePrivateCachedExternalStorageButton = findViewById(R.id.btn_external_storage_save_private_cache);
        savePrivateCachedExternalStorageButton.setOnClickListener(view -> {

            try {
                if (ExternalStorageUtil.isExternalStorageMounted()) {

                    // Check whether this app has write external storage permission or not.
                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    // If do not grant write external storage permission.
                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Request user to grant write external storage permission.
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                    } else {

                        // Save sample_cache.txt file to /storage/emulated/0/Android/data/com.samad_talukder.androidfilesavetostorageinandroid10/cache folder
                        String privateDirPath = ExternalStorageUtil.getPrivateCacheExternalStorageBaseDir(getApplicationContext());

                        File newFile = new File(privateDirPath, "sample_cache.txt");

                        FileWriter fw = new FileWriter(newFile);

                        fw.write(emailEditor.getText().toString());

                        fw.flush();

                        fw.close();

                        Toast.makeText(getApplicationContext(), "Save to private external storage success. File Path " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);

                Toast.makeText(getApplicationContext(), "Save to private external storage failed. Error message is " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /* Display Private And Public External Storage Directory In Android Monitor Logcat Console. */
        Button displayExternalStoragePathButton = findViewById(R.id.btn_external_storage_display_path);
        displayExternalStoragePathButton.setOnClickListener(view -> {

            // Because create custom directory in public external storage folder need write permission
            // So we should grant the permission to the app first.

            // Check whether this app has write external storage permission or not.
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // If do not grant write external storage permission.
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Below are public external storage folder path.");

                // Use Environment class to get public external storage directory.
                File publicDir = Environment.getExternalStorageDirectory();
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStorageDirectory() : " + publicDir.getAbsolutePath());

                // Because at the beginning of this method, we had grant write external storage permission
                // So we can create this directory here.
                File customPublicDir = new File(publicDir, "ST");
                customPublicDir.mkdirs();
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Create custom dir : " + customPublicDir.getAbsolutePath());

                try {

                    /*URL url = new URL("https://firebasestorage.googleapis.com/v0/b/watch-faces-2dada.appspot.com/o/faces%2F34fca39c-6b6e-4d42-ae53-7090c9673936?alt=media&token=10ea65ab-ba14-49ca-9491-eb14a3dcad4a");
                    url.openConnection();

                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();
                    FileOutputStream fos = new FileOutputStream(customPublicDir + "/bok3.vxp");
                    fos.write(response);
                    fos.close();*/

                    File newFile = new File(customPublicDir, "sample.txt");

                    FileWriter fw = new FileWriter(newFile);

                    fw.write(emailEditor.getText().toString());

                    fw.flush();

                    fw.close();
                    Toast.makeText(getApplicationContext(), "Save to  external storage success. File Path " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Log.e(LOG_TAG_EXTERNAL_STORAGE, ex.getMessage(), ex);
                }


                File musicPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) : " + musicPublicDir.getAbsolutePath());

                File dcimPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) : " + dcimPublicDir.getAbsolutePath());

                Log.e(LOG_TAG_EXTERNAL_STORAGE, "**********************************************************************");

                Log.e(LOG_TAG_EXTERNAL_STORAGE, "Below are android app private external storage folder path.");

                // Use Context class to get app private external storage directory

                Context context = getApplicationContext();

                File privateDir = context.getExternalFilesDir(null);
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(null) : " + privateDir.getAbsolutePath());

                File musicPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) : " + musicPrivateDir.getAbsolutePath());

                File dcimPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_DCIM) : " + dcimPrivateDir.getAbsolutePath());

                File customPrivateDir = context.getExternalFilesDir("ST");
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(\"ST\") : " + customPrivateDir.getAbsolutePath());

                File cachedPrivateDir = context.getExternalCacheDir();
                Log.e(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalCacheDir() : " + cachedPrivateDir.getAbsolutePath());

                Toast.makeText(context, "Please see the output in android monitor logcat console.", Toast.LENGTH_LONG).show();
            }

        });
    }

    // This method is invoked after user click buttons in permission grant popup dialog.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "You grant write external storage permission. Please click original button again to continue.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }
}