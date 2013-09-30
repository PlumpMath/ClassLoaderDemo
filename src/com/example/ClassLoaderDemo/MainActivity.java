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
    Fragment fragment;
    GLSurfaceView glView;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        glView = (GLSurfaceView) findViewById(R.id.surfaceView);//Attach this openGL view to id defined in our main xml layout

        try
        {
            Log.d("MainActivity", "***************Initializing Class Loader");
            final Context childAppCtx = getApplicationContext().createPackageContext("com.example.TestApplication", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

            Log.d("MainActivity", "++++++++++++++++Finding child app main renderer class");

            //Class<?> loadedClass = Class.forName("com.example.TestApplication.ExternalFragment", true, childAppCtx.getClassLoader());
            final Class<?> loadedClass = Class.forName("com.example.TestApplication.MyGLRenderer", true, childAppCtx.getClassLoader());

            Log.d("MainActivity", "----------------Instantiating Renderer");
            //return Class.forName(className).getConstructor(String.class).newInstance(arg);

            //fragment = (Fragment) loadedClass.newInstance();
            final GLSurfaceView.Renderer renderer = (GLSurfaceView.Renderer) loadedClass.getDeclaredConstructor(Context.class).newInstance(getApplicationContext());

            Log.d("MainActivity", "Instantiated renderer");
            glView.setRenderer(renderer);//Set the external renderer object that will be rendering the content for our internal openGL view

            Log.d("MainActivity", "Simple reflection test");
            Log.d("MainActivity", "External fragment created is: "+renderer.getClass().toString());
            //calling a simple test method which does nothing but return a string from the external class object
            //getMethod(..).invoke(..) seems to have trouble when using just loadedClass as the invoke param. Only worked when I used the object instance of type fragment.
            //Log.d("MainActivity", "Calling test method"+loadedClass.getMethod("testMethod").invoke(fragment, null));

            Log.d("MainActivity", "////////////////////Creating sample Fragment");

            SampleFragment sampleFragment = new SampleFragment();
            sampleFragment.setArguments(getIntent().getExtras());

//            Log.d("MainActivit", "------------------------Creating transaction");
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            fragmentTransaction.add(R.id.mainFrameLayout, sampleFragment);
//            fragmentTransaction.add(R.id.mainFrameLayout, fragment);
//
//            Log.d("MainActivity", "Committing transaction!");
//
//            fragmentTransaction.commit();
//            fragmentManager.executePendingTransactions();
//            Log.d("MainActivity", "####################Fragment launch complete!");
        }
        catch(Exception ex)
        {
            Log.e("MainActivity", ex.toString());
        }


        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
