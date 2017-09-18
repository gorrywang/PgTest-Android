package com.example.iswgr.pgtest.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * 自定义字体
 * Created by iswgr on 2017/9/18.
 */

public class CustomApp extends Application {
    public static Typeface TypeFace;
    private static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        TypeFace = Typeface.createFromAsset(getAssets(), "fonts/a.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Context getCtx() {
        return ctx;
    }

}
