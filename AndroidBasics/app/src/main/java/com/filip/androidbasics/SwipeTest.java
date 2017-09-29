package com.filip.androidbasics;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SwipeTest extends Activity {

    //Declarations
    private TextView txtSwipe;
    private int startX = 0, startY = 0;
    private final int MINOFFSET = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_test);

        //Getting textview for later use.
        txtSwipe = findViewById(R.id.txtSwipe);
    }

    public boolean onTouchEvent(MotionEvent e) {
        int t = e.getAction();

        //Get starting X/Y coordinates to later compare with end.
        if (t == MotionEvent.ACTION_DOWN) {
            startX = (int)e.getX();
            startY = (int)e.getY();
            txtSwipe.setText("");
            return true;
        }

        //Get ending X/Y coordinates and determine the gesture.
        if (t == MotionEvent.ACTION_UP) {
            determineGesture(startX, startY, (int)e.getX(), (int)e.getY());
            return true;
        }
        return false;
    }

    public void determineGesture(int startX, int startY, int endX, int endY) {
        int left = startX - endX;
        int right = endX - startX;
        int up = startY - endY;
        int down = endY - startY;

        System.out.println(left + " " + right + " " + up + " " + down);

        int[] a = {left, right, up, down};

        int direction = 0;
        int counter = 0;
        int previousValue = 0;
        for(int i : a) {
            counter++;
            if(i > MINOFFSET) {
                if (previousValue < i) {
                    previousValue = i;
                    direction = counter;
                }
            }
        }
        if(direction == 1){
            txtSwipe.setText("Left Swipe");
        }
        if(direction == 2){
            txtSwipe.setText("Right Swipe");
        }
        if(direction == 3){
            txtSwipe.setText("Up Swipe");
        }
        if(direction == 4){
            txtSwipe.setText("Down Swipe");
        }
    }
}