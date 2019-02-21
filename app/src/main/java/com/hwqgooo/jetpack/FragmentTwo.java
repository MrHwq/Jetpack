package com.hwqgooo.jetpack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

public class FragmentTwo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        view.findViewById(R.id.backFragmentOne).setOnClickListener(v -> {
//            Navigation.findNavController(getActivity(), R.id.action_fragmentTwo_to_fragmentOne);
//            Navigation.findNavController(view).navigate(R.id.action_fragmentTwo_to_fragmentOne);
            Navigation.findNavController(view).navigateUp();
        });
        view.findViewById(R.id.goFragmentThree).setOnClickListener(v -> {
//            Navigation.findNavController(getActivity(), R.id.action_fragmentTwo_to_fragmentThree);
            Navigation.findNavController(view).navigate(R.id.action_fragmentTwo_to_fragmentThree);
        });
        return view;
    }
}
