package com.example.ClassLoaderDemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Method;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try
        {
            Log.d("MainActivity", "***************Initializing Class Loader");
            final Context childAppCtx = getApplicationContext().createPackageContext("com.example.TestApplication", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

            //final DexClassLoader pathClassLoader = new DexClassLoader("/mnt/sdcard/Download/com.example.TestApplication",getFilesDir().getAbsolutePath(),null, getClass().getClassLoader());

            Log.d("MainActivity", "++++++++++++++++Finding child app main fragment class");

            Class<?> childAppClass = Class.forName("com.example.TestApplication.TestAnimationFragment", false, childAppCtx.getClassLoader());

            //Class<?> childAppClass = pathClassLoader.loadClass("com.example.TestApplication.TestAnimationFragment");

            Log.d("MainActivity", "----------------Instantiating Fragment");

            Fragment frag = (Fragment) childAppClass.getMethod("newInstance").invoke(null);
            //Fragment frag = (Fragment) childAppClass.getConstructor().newInstance();

            Log.d("MainActivity", "////////////////////Launching Fragment");

            getFragmentManager().beginTransaction().replace(R.id.child_app_fragment, frag).commit();

            Log.d("MainActivity", "Operations Complete!******************");
        }
        catch(Exception ex)
        {
            Log.e("MainActivity", ex.toString());
        }
    }
}
