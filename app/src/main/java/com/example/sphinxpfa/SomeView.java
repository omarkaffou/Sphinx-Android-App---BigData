package com.example.sphinxpfa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SomeView extends View implements View.OnTouchListener {
    private Paint paint;
    public static List<Point> points;;
    boolean falg = true;
    public Uri orignalUri;
    float originheight=0.0f;
    float originwidth=0.0f;
    float ratioheight=0.0f;
    float ratiowidth=0.0f;
    public static int REQUEST_CODE=2;
    Point mfirstpoint = null;
    boolean bfirstpoint = false;
    Point mlastpoint = null;
    boolean cropflag=false;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img);
    Context mContext;
    public void setBitmap(Bitmap bmp,Uri uri){
        orignalUri=uri;
        points = new ArrayList<Point>();
        setFocusable(true);
        setFocusableInTouchMode(true);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        originheight=bmp.getHeight();
        originwidth=bmp.getWidth();
        ratioheight=height/originheight;
        ratiowidth=width/originwidth;
        bitmap=bmp;
        bitmap=Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth()*ratiowidth),(int)(bmp.getHeight()*ratioheight), true);
        // bitmap=bmp;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        falg = true;
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
        cropflag=false;

    }
    public void clear(){
        setFocusable(true);
        setFocusableInTouchMode(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
        falg = true;
        cropflag=false;
        invalidate();
    }

    public SomeView(Context c) {
        super(c);
        mContext = c;
        setFocusable(true);
        setFocusableInTouchMode(true);
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        originheight=bitmap.getHeight();
        originwidth=bitmap.getWidth();
        ratioheight=height/originheight;
        ratiowidth=width/originwidth;
        bitmap=Bitmap.createScaledBitmap(bitmap, (int)(originwidth*ratiowidth),(int)(originheight*ratioheight), true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
        falg = true;
        cropflag=false;
        Log.d("Height",  ratioheight+" ");
        Log.d("Width",  ratiowidth+" ");
        Log.e("d3","-------H-W detecter----------");
    }
    public SomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        originheight=bitmap.getHeight();
        originwidth=bitmap.getWidth();
        ratioheight=height/originheight;
        ratiowidth=width/originwidth;
        bitmap=Bitmap.createScaledBitmap(bitmap, (int)(originwidth*ratiowidth),(int)(originheight*ratioheight), true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
        falg = true;
        cropflag=false;
        Log.d("Height",  ratioheight+" ");
        Log.d("Width",  ratiowidth+" ");
        Log.e("d4","-------H-W detecter----------");
    }
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0,paint);
        Path path = new Path();
        boolean first = true;
        for (int i = 0; i < points.size(); i += 2) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.x, point.y, next.x, next.y);
            } else {
                mlastpoint = points.get(i);
                path.lineTo(point.x, point.y);
            }
        }
        canvas.drawPath(path, paint);
    }
    @SuppressLint("LongLogTag")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        Toast.makeText(mContext, "[   x ="+point.x +" ;  y =" +point.y + "  ]", Toast.LENGTH_SHORT).show();
        if (falg) {
            if (bfirstpoint) {
                if (Compare_point(mfirstpoint, point)) {
                    points.add(mfirstpoint);
                    falg = false;
                    cropflag=true;
                } else {
                    points.add(point);
                }
            } else {
                points.add(point);
            }
            if (!(bfirstpoint)) {

                mfirstpoint = point;
                bfirstpoint = true;
            }
        }
        invalidate();
        Log.d("d1",  point.x + ";" + point.y+" ");
        Log.e("d2","-------Position detecter----------");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("bien detecter", "bien detecter");
            Toast.makeText(mContext, "Bien detecter", Toast.LENGTH_SHORT).show();
            mlastpoint = point;
            if (falg) {
                if (points.size() > 12) {
                    if (!Compare_point(mfirstpoint, mlastpoint)) {
                        falg = false;
                        points.add(mfirstpoint);
                        cropflag=true;
                    }
                }
            }
        }
        return true;
    }
    private boolean Compare_point(Point first, Point current) {
        int left_range_x = (int) (current.x - 3);
        int left_range_y = (int) (current.y - 3);
        int right_range_x = (int) (current.x + 3);
        int right_range_y = (int) (current.y + 3);
        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {
            if (points.size() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }
    public void remplir_une_partie_du_chemin() {
        Point point = new Point();
        point.x = points.get(0).x;
        point.y = points.get(0).y;

        points.add(point);
        invalidate();
    }
    public void reset() {
        points.clear();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        falg = true;
        invalidate();
    }
    public void Crop(){
        if(cropflag){
            Intent intent;
            intent = new Intent(mContext, CropActivity.class);
            intent.putExtra("crop", true);
            intent.putExtra("heightratio", ratioheight);
            intent.putExtra("widthratio", ratiowidth);
            intent.putExtra("URI", orignalUri.toString());
            ((Activity)mContext).startActivityForResult(intent,REQUEST_CODE);
        }
    }
    }
