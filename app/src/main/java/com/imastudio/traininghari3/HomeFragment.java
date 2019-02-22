package com.imastudio.traininghari3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    Button btnFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview = inflater.inflate(R.layout.fragment_home, container, false);
        btnFragment = fragmentview.findViewById(R.id.button_fragment);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "tes fragment", Toast.LENGTH_SHORT).show();
            }
        });

        return fragmentview;
    }

}

//perbedaan activity dan fragment
//1 perlu bikin viewnya dulu
//2 context NamaActivity.this jadi getActivity()