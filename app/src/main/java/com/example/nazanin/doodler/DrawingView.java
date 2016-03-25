package com.example.nazanin.doodler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.LinkedList;

public class DrawingView extends View {
    private float mStrokeWidth = 48;
    private int mStrokeColor = Color.RED;
    private List<Line> mLines;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    class Line extends Path {
        int color;
        float width;

        public Line(int color, float width) {
            super();
            this.color = color;
            this.width = width;
        }

        public float getWidth() {
            return width;
        }

        public int getColor() {
            return color;
        }
    }

    class TouchAdapter implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                Line line = new Line(mStrokeColor, mStrokeWidth);
                line.moveTo(event.getX(), event.getY());
                mLines.add(line);
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                mLines.get(mLines.size() - 1).lineTo(event.getX(), event.getY());
            }
            invalidate();
            return false;
        }
    }

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLines = new LinkedList<Line>();

        TouchAdapter touchAdapter = new TouchAdapter();
        setOnTouchListener(touchAdapter);

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            for (Line line : mLines) {
                drawLine(line);
            }
        } else if (mLines.size() > 0) {
            drawLine(mLines.get(mLines.size() - 1));
        }

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
        }

    }

    private void drawLine(Line line) {
        mPaint.setStrokeWidth(line.getWidth());
        mPaint.setColor(line.getColor());
        mCanvas.drawPath(line, mPaint);
    }

    public void saveImage() {
//        Picture save = new Picture();
//        Canvas canvas = save.beginRecording(mBitmap.getWidth(), mBitmap.getHeight());
//        canvas.drawBitmap(mBitmap, 0, 0, new Paint());
    }

    public void undo() {
        if (mLines.size() > 0) {
            mLines.remove(mLines.size() - 1);
            mBitmap = null;
            invalidate();
        }
    }

    public void setStrokeWidth(float width) {
        mStrokeWidth = width;
    }

    public void setStrokeColor(int color) {
        mStrokeColor = color;
    }
}
