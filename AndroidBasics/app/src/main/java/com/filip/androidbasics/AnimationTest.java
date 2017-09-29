package com.filip.androidbasics;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(new RenderView(this));
    }

    class RenderView extends View{
        Bitmap sprites;
        int Column = 1;
        int Row = 1;

        int columns = 6;
        int rows = 4;
        int width = 10;
        int height = 150;

        public RenderView(Context context) {
            super(context);
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("Spritesheet/dude_animation_sheet.png");
                sprites = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask(){
                    public void run() {
                        postInvalidate();
                    }
                }, 0, 200);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected void onDraw(Canvas c) {
            //Using the current row and column, determine the frames next start location.
            int startx = width * (Column - 1);
            int starty = height * (Row - 1);

            //Create a new bitmap from the sprite sheet and scale it up to 500px, 500px.
            c.drawBitmap(Bitmap.createScaledBitmap(Bitmap.createBitmap(sprites, startx, starty, width, height), 1000, 1000, true), 0, 0, null);

            //Prepare for next frame by updating the next column and row.
            Column++;
            if(Column > columns) {
                Row++;
                Column = 1;
                if(Row > rows){
                    Row = 1;
                }
            }
        }
    }
}
