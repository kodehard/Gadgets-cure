package com.gadgetscure.gadgetscure.tutorialfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gadgetscure.gadgetscure.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fragment2, container, false);
        return rootView;
    }

}
