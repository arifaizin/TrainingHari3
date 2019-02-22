package com.imastudio.traininghari3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {


    public RadioFragment() {
        // Required empty public constructor
    }


    ProgressBar progress;
    Button btnPlay, btnStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview =  inflater.inflate(R.layout.fragment_radio, container, false);
        progress = fragmentview.findViewById(R.id.progressBar);
        btnPlay = fragmentview.findViewById(R.id.btn_play);
        btnStop = fragmentview.findViewById(R.id.btn_stop);

        progress.setVisibility(View.INVISIBLE);
        progress.setIndeterminate(true);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                Intent service = new Intent(getActivity(), StreamingService.class);
                service.setAction("play");
                getActivity().startService(service);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.INVISIBLE);
                Intent service = new Intent(getActivity(), StreamingService.class);
                service.setAction("stop");
                getActivity().startService(service);
            }
        });
        return fragmentview;
    }

}
