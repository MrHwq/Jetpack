package com.hwqgooo.jetpack.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.jetpack.R;

import androidx.navigation.Navigation;

public class FragmentFour extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        view.findViewById(R.id.back_fragment).setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });

        return view;
    }
}
