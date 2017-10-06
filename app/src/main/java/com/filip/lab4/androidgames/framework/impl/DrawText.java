package com.filip.lab4.androidgames.framework.impl;

/**
 * Created by Richard Rifj on 10/5/2017.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class DrawText {

    public String m_fontLoc;
    public String m_text;
    public int m_xLoc;
    public int m_yLoc;
    public Canvas m_canvas;


    public DrawText(String fontLoc, String text, int xPos, int yPos, Canvas c)
    {
        m_fontLoc = fontLoc;
        m_text = text;
        m_xLoc = xPos;
        m_yLoc = yPos;
        m_canvas = c;

        Typeface face = Typeface.createFromAsset(contextFondler.getContext().getAssets(), fontLoc);

        Paint txt = new Paint();
        txt.setTypeface(face);
    }
}
