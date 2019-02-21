package com.hwqgooo.jetpack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

public class FragmentOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        view.findViewById(R.id.gofragmenttwo).setOnClickListener(v -> {
//            Navigation.findNavController(getActivity(), R.id.action_fragmentOne_to_fragmentTwo);
            Navigation.findNavController(view).navigate(R.id.action_fragmentOne_to_fragmentTwo);
        });
        return view;
    }
}
