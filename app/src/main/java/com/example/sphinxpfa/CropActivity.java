package com.example.sphinxpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CropActivity extends Activity {
    ImageView compositeImageView;
    boolean crop;
    TextView wi;
    Bitmap resultingImage;
    Uri imageUri;
    Button btnDone,btnCancel;
    float heightratio=0.0f,widthratio=0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        wi=findViewById(R.id.w);
        btnDone=(Button)findViewById(R.id.btnDone);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropActivity.this, Acceuil.class);
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int widthOfscreen = 0;
                FileOutputStream out = null;
                try {
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    File Filename=new File(getFilesDir(),ts+".png");
                    //File Filename1=new File(getFilesDir(),"EPICBATTLE"+((Long)(System.currentTimeMillis()/1000)).toString()+"_ico.png");
                    out = new FileOutputStream(Filename);
                    resultingImage.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(CropActivity.this, Acceuil.class);
                setResult(RESULT_OK);
                finish();

            }
        });
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            crop = extras.getBoolean("crop");
            heightratio=extras.getFloat("heightratio");
            widthratio=extras.getFloat("widthratio");
            imageUri=Uri.parse(extras.getString("URI"));
        }
        int widthOfscreen = 0;
        int heightOfScreen = 0;
        DisplayMetrics dm = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        } catch (Exception ex) {
        }
        widthOfscreen = dm.widthPixels;
        heightOfScreen = dm.heightPixels;
        compositeImageView = (ImageView) findViewById(R.id.imageView1);
        Bitmap bitmap2=null;
        try {
            bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap2=Bitmap.createScaledBitmap(bitmap2, (int)(bitmap2.getWidth()*widthratio),(int)(bitmap2.getHeight()*heightratio), true);
        resultingImage = Bitmap.createBitmap(widthOfscreen,
                heightOfScreen, bitmap2.getConfig());
        // resultingImage=CropBitmapTransparency(resultingImage);
        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        for (int i = 0; i < SomeView.points.size(); i++) {
            path.lineTo(SomeView.points.get(i).x, SomeView.points.get(i).y);
        }
        canvas.drawPath(path, paint);
        if (crop) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        }
        canvas.drawBitmap(bitmap2, 0, 0, paint);
        resultingImage=CropBitmapTransparency(resultingImage);
        compositeImageView.setImageBitmap(resultingImage);
    }

    Bitmap CropBitmapTransparency(Bitmap sourceBitmap){
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for(int y = 0; y < sourceBitmap.getHeight(); y++)
        {
            for(int x = 0; x < sourceBitmap.getWidth(); x++)
            {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if(alpha > 0)   // pixel is not 100% transparent
                {
                    if(x < minX)
                        minX = x;
                    if(x > maxX)
                        maxX = x;
                    if(y < minY)
                        minY = y;
                    if(y > maxY)
                        maxY = y;
                }
            }
        }
        if((maxX < minX) || (maxY < minY))
            return null;
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }
}