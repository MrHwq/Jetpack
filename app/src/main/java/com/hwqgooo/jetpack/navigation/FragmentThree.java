package com.hwqgooo.jetpack.navigation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.jetpack.R;
import com.hwqgooo.jetpack.databinding.FragmentThreeBinding;
import com.hwqgooo.jetpack.paging.CustomPageDataSourceFactory;
import com.hwqgooo.jetpack.paging.DataBean;
import com.hwqgooo.jetpack.paging.DataRepository;
import com.hwqgooo.jetpack.viewmodel.PageViewModel;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import androidx.navigation.Navigation;

public class FragmentThree extends Fragment {
    FragmentThreeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_three, container, false);
        FragmentThreeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three,
                container, false);
        binding.back.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        });
        PageViewModel pvm = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        binding.recycleview.setAdapter(pvm.adapter);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recycleviewone.setAdapter(pvm.adapter);
        binding.recycleviewone.setLayoutManager(new LinearLayoutManager(getContext()));

        CustomPageDataSourceFactory factory = new CustomPageDataSourceFactory(new DataRepository());
        LiveData<PagedList<DataBean>> pl = new LivePagedListBuilder<>(
                factory,
                new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setPrefetchDistance(5)
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(20).build()).build();
        pl.observe(this, dataBeans -> pvm.adapter.submitList(dataBeans));

        //设置 Header 为 贝塞尔雷达 样式
        binding.refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext())
                .setEnableHorizontalDrag(true));
//        //设置 Footer 为 球脉冲 样式
        binding.refreshLayout.setRefreshFooter(new BallPulseFooter(getContext())
                .setSpinnerStyle(SpinnerStyle.Scale));
//        AtomicInteger i = new AtomicInteger();
//        refreshLayout.setOnRefreshListener(relayout -> {
//            Logger.i("setOnRefreshListener--------");
//            Observable.timer(3000, TimeUnit.MILLISECONDS)
//                    .subscribe(aLong -> {
//                        Logger.i("i is %d", i.get());
//                        refreshLayout.finishRefresh(false);
//                    });
////                            relayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//        });
//        refreshLayout.setOnLoadMoreListener(relayout -> {
//            Logger.i("setOnLoadMoreListener--------");
//            Observable.timer(3000, TimeUnit.MILLISECONDS)
//                    .subscribe(aLong -> refreshLayout.finishLoadMore((i.getAndIncrement() % 2) == 0));
////            relayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//        });
        binding.getRoot().findViewById(R.id.goFragmentFour)
                .setOnClickListener(v -> {
                    Navigation.findNavController(binding.getRoot())
                            .navigate(R.id.action_fragmentThree_to_fragmentFour);
                });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
