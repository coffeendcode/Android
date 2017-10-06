package com.filip.lab4.androidgames.framework;

import com.filip.lab4.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap 
{
    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}

