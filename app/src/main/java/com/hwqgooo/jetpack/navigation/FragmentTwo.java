package com.hwqgooo.jetpack.navigation;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwqgooo.jetpack.R;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import androidx.navigation.Navigation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FragmentTwo extends Fragment {
    ObservableField<Integer> bbb;
    Observable.OnPropertyChangedCallback callback;

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
        TextView mutable = view.findViewById(R.id.mutable);
        TextView mutableforever = view.findViewById(R.id.mutableforever);
        TextView observe = view.findViewById(R.id.observe);
        MutableLiveData<Integer> aaa = new MutableLiveData<>();
        bbb = new ObservableField<>();
        aaa.observe(this, v -> {
            Logger.i("===========AAA mutable:::%d", v);
            mutable.setText(mutable.getText() + "..." + v.intValue());
        });
        aaa.observeForever(v -> {
            mutableforever.setText(mutableforever.getText() + "..." + v.intValue());
        });
        callback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Logger.i("===========AAA observable:::%d %d", propertyId, bbb.get());
                observe.setText(observe.getText() + "..." + bbb.get());
            }
        };
        bbb.addOnPropertyChangedCallback(callback);
        Disposable disposable = io.reactivex.Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    aaa.setValue(aLong.intValue());
                    bbb.set(aLong.intValue());
                });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bbb.removeOnPropertyChangedCallback(callback);
    }
}
