package com.filip.androidbasics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewTest extends Activity {
    FastRenderView renderView;

    @Override public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        renderView = new FastRenderView(this);
        setContentView(renderView);
    }

    @Override protected void onResume() {
        super.onResume();
        renderView.resume();
    }

    @Override protected void onPause() {
        super.onPause();
        renderView.pause();
    }

    // implements the Runnable interface so that we can pass it to the
    // rendering thread in order for it to run the render thread logic
    class FastRenderView extends SurfaceView implements Runnable {
        // a reference to the Thread instance that will be responsible
        // for executing our rendering thread logic
        Thread renderThread = null;

        // a reference to the SurfaceHolder instance that we get from
        // the SurfaceView superclass from which we derive
        SurfaceHolder holder;

        // flag we will use to signal the rendering thread that it
        // should stop execution.
        // volatile - variable's value will be modified by different threads.
        volatile boolean running = false;

        public FastRenderView(Context context) {
            super(context);
            holder = getHolder();
        }

        // responsible for starting up the rendering thread
        public void resume() {
            running = true;
            // we set the FastRenderView instance itself as the Runnable
            // of the thread (will execute the run method)
            renderThread = new Thread(this);
            renderThread.start();
        }

        public void pause() {
            running = false;

            // wait for the thread to die completely
            while(true) {
                try {
                    renderThread.join();
                    break;
                } catch (InterruptedException e) {
                    // retry
                }
            }

            renderThread = null;
        }

        // the workhorse of our custom View class
        // body is executed in the rendering thread
        public void run() {
            while(running) {
                if(!holder.getSurface().isValid())
                    continue;

                Canvas canvas = holder.lockCanvas();
                drawSurface(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        private void drawSurface(Canvas canvas) {
            canvas.drawRGB(255, 0, 0);
        }
    }
}