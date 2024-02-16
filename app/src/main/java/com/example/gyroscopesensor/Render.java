package com.example.gyroscopesensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.sql.Time;

public class Render extends View {

    float x = getWidth() / 2f;
    float y = getHeight() / 2f;
    double vx = 0;
    double vy = 0;
    double ax = 0;
    double ay = 0;
    int radius = 170;
    final int aTyag = 100;
    final int koeff = 3000;

    public Render (Context context) {
        super(context);
    }

    private void updateCoords() {
        double a1 = Math.sin(MainActivity.vx) * aTyag;
        double a2 = Math.sin(MainActivity.vy) * aTyag;
        if (Math.round(a1) == 0) {
            ax = 0;
        } else {
            ax += a1;
        }
        if (Math.round(a2) == 0) {
            ay = 0;
        } else {
            ay += a2;
        }
        vx += ax * 0.1 / 2 / koeff;
        vy += ay * 0.1 / 2 / koeff;
        x += (float) vx;
        y += (float) vy;
        if (x < radius) {
            x = radius;
            vx = -vx / 3;
            ax = 0;
        }
        if (y < radius) {
            y = radius;
            vy = -vy / 3;
            ay = 0;
        }
        if (x > getWidth() - radius) {
            x = getWidth() - radius;
            vx = -vx / 3;
            ax = 0;
        }
        if (y > getHeight() - radius) {
            y = getHeight() - radius;
            vy = -vy / 3;
            ay = 0;
        }
        Log.d("SPEED", vx + " " + vy + " " + ax + " " + ay);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 0, 186, 255));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x, y, radius, paint);
        updateCoords();

        invalidate();
    }
}
