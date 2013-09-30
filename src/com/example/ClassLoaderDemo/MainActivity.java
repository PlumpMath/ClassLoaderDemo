package com.example.ClassLoaderDemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Method;

public class MainActivity extends Activity
{
    GLSurfaceView glView;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        glView = (GLSurfaceView) findViewById(R.id.surfaceView);//Attach this openGL view to id defined in our main xml layout

        try
        {
            Log.d("MainActivity", "Initializing Class Loader");
            final Context childAppCtx = getApplicationContext().createPackageContext("com.example.TestApplication", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

            Log.d("MainActivity", "Using class loader to find renderer in child app.");

            final Class<?> loadedClass = Class.forName("com.example.TestApplication.MyGLRenderer", true, childAppCtx.getClassLoader());

            Log.d("MainActivity", "Instantiating Renderer");

            final GLSurfaceView.Renderer renderer = (GLSurfaceView.Renderer) loadedClass.getDeclaredConstructor(Context.class).newInstance(getApplicationContext());

            Log.d("MainActivity", "Instantiated renderer");
            glView.setRenderer(renderer);//Set the external renderer object that will be rendering the content for our internal openGL view

            Log.d("MainActivity", "Simple reflection test");
            Log.d("MainActivity", "External fragment created is: "+renderer.getClass().toString());
        }
        catch(Exception ex)
        {
            Log.e("MainActivity", ex.toString());
        }

        //Finish the glSurfaceView intialization now that we have the renderer setup
        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
