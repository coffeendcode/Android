package com.filip.androidbasics;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MultiTouchTest extends Activity implements View.OnTouchListener
{
    StringBuilder builder = new StringBuilder();
    TextView textView;
    float[] x = new float[10];
    float[] y = new float[10];
    boolean[] touched = new boolean[10];

    private void updateTextView() {
        builder.setLength(0);
        for(int i = 0; i < 10; i++) {
            builder.append(touched[i]);
            builder.append(", ");
            builder.append(x[i]);
            builder.append(", ");
            builder.append(y[i]);
            builder.append("\n");
        }
        textView.setText(builder.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("Touch and drag (multiple fingers supported)!");
        textView.setOnTouchListener(this);
        setContentView(textView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = MotionEventCompat.getActionMasked(event);
            int pointerIndex = MotionEventCompat.getActionIndex(event);
            int pointerId = event.getPointerId(pointerIndex);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touched[pointerId] = true;
                    x[pointerId] = (int)event.getX(pointerIndex);
                    y[pointerId] = (int)event.getY(pointerIndex);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touched[pointerId] = false;
                    x[pointerId] = (int)event.getX(pointerIndex);
                    y[pointerId] = (int)event.getY(pointerIndex);
                    break;

                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);
                        x[pointerId] = (int)event.getX(pointerIndex);
                        y[pointerId] = (int)event.getY(pointerIndex);
                    }
                    break;
            }
            updateTextView();
            return true;
        }
    }


}
