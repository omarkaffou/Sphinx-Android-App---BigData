package com.example.sphinxpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Image extends AppCompatActivity {

    ShapeableImageView btnCapture, btn_Gallery, btnClear ,btn_Cam;
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
        setContentView(R.layout.activity_image);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFC300\">" + getString(R.string.app_name) + "</font>"));

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