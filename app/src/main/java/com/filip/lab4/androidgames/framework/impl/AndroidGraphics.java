package com.filip.lab4.androidgames.framework.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.res.TypedArrayUtils;

import com.filip.lab4.androidgames.framework.Graphics;
import com.filip.lab4.androidgames.framework.Pixmap;

import static android.R.attr.bitmap;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer; // represents our artificial framebuffer
    Canvas canvas;		// use to draw to the artificial framebuffer
    Paint paint;		// needed for drawing
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(ArrayList<String> fileNames, PixmapFormat format) {
        InputStream in = null;
        ArrayList<Bitmap> bitmap = null;
        try {
            for(String s : fileNames) {
                in = assets.open(s);
                bitmap.add(BitmapFactory.decodeStream(in));
                if (bitmap == null)
                    throw new RuntimeException("Couldn't load bitmap from asset '" + s + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(pixmap.getBitmaps(), srcRect, dstRect, null);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(pixmap.getBitmaps(), x, y, null);
    }

    @Override
    public void drawText(String assetLocation, String text, int x, int y) {
        Typeface tf = Typeface.createFromAsset(assets, assetLocation);
        Paint p = new Paint();
        p.setTypeface(tf);
        p.setColor(Color.BLACK);
        canvas.drawText(text, x, y, p);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}