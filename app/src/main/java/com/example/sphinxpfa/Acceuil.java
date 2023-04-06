package com.example.sphinxpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Acceuil extends AppCompatActivity {
    private ImageView addnoma;

    ShapeableImageView btnsave, btn_Gallery, btnclear ,btn_Cam;
    ShapeableImageView btnCrop;
    SomeView someView;
    private static final String RESULT_BUNDLE_EXTAS = "RESULT_BUNDLE_EXTAS";
    private static final String RESULT_BUNDLE_DATA = "RESULT_BUNDLE_DATA";
    private static final String RESULT_BUNDLE_REQUEST_CODE = "RESULT_BUNDLE_REQUEST_CODE";
    private static final String RESULT_BUNDLE_RESULT_CODE = "RESULT_BUNDLE_RESULT_CODE";
    private static final int REQUEST_PICK_IMAGE = 0;
    private static final int REQUEST_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CROP = 2;
    private Uri captureImageUri;
    private Bundle resultBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFC300\">" + getString(R.string.app_name) + "</font>"));

        addnoma = (ImageView) findViewById(R.id.addnoma);
        addnoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_main2();
              //  someView.Crop();

            }
        });

        ///Code Pour Moi

        btnsave=(ShapeableImageView)findViewById(R.id.btn_save);
        btnclear=(ShapeableImageView)findViewById(R.id.btn_clear);
        //Principe
        btn_Gallery=(ShapeableImageView)findViewById(R.id.btn_gellery);
        someView=(SomeView)findViewById(R.id.some_View);
        btn_Cam=(ShapeableImageView)findViewById(R.id.btn_camera);
        btnclear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                someView.clear();

            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                captureImageUri = null;
                try {
                    String storageState = Environment.getExternalStorageState();
                    if (TextUtils.equals(storageState, Environment.MEDIA_MOUNTED)) {
                        String path = Environment.getExternalStorageDirectory()
                                .getAbsolutePath();
//                                + File.separatorChar
//                                + "Android/data/"
//                                + getApplicationContext().getPackageName()
//                                + "/files/";
                        File directory = new File(path);
                        directory.mkdirs();
                        File file = new File(directory,
                                Integer.toHexString((int) System
                                        .currentTimeMillis()) + ".jpg");
                        file.createNewFile();
                        captureImageUri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
                    }

                } catch (IOException e) {
                }
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);


            }
        });
        btn_Gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMAGE);

            }
        });
        btn_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(camera_intent, "Select Gallery " +
                        "Image"), REQUEST_PICK_IMAGE);
            }
        });

    }
    public void openactivity_main2() {
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        resultBundle = new Bundle();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            resultBundle.putBundle(RESULT_BUNDLE_EXTAS, extras);
            Uri uriData = intent.getData();
            if (uriData != null) {
                resultBundle.putParcelable(RESULT_BUNDLE_DATA, uriData);
            }
        }

        resultBundle.putInt(RESULT_BUNDLE_REQUEST_CODE, requestCode);
        resultBundle.putInt(RESULT_BUNDLE_RESULT_CODE, resultCode);

        super.onActivityResult(requestCode, resultCode, intent);

        handleActivityResult();
    }

    //Pour charger l'image dans some view
    private void handleActivityResult() {
        if (resultBundle == null) {
            return;
        }
        int requestCode = resultBundle.getInt(RESULT_BUNDLE_REQUEST_CODE);
        int resultCode = resultBundle.getInt(RESULT_BUNDLE_RESULT_CODE);
        Uri dataUri = resultBundle.getParcelable(RESULT_BUNDLE_DATA);
        switch (requestCode) {
            case REQUEST_PICK_IMAGE: {
                if (resultCode == RESULT_OK) {
                    if (dataUri != null) {

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(dataUri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();


                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, options);
                        Uri uri = Uri.fromFile(new File(filePath));
                        someView.setBitmap(bitmap1, uri);
                        // Intend for EditorActivity
                    }
                }
                break;
            }
            case REQUEST_CAPTURE_IMAGE: {
                if (resultCode == RESULT_OK) {
                    if (captureImageUri != null) {
                        // Intend for EditorActivity

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), captureImageUri);
                            someView.setBitmap(bitmap, captureImageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
                break;

            }
            case REQUEST_CROP: {
            }
        }
    }



}


