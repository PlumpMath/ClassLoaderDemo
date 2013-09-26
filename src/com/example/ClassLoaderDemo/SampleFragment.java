package com.example.ClassLoaderDemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with IntelliJ IDEA.
 * User: enplug
 * Date: 9/25/13
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.samplefragment_layout, container, false);
    }
}
