package com.hwqgooo.jetpack.paging;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public List<DataBean> data = new ArrayList<>();

    public DataRepository() {
        super();
        for (int i = 0; i < 100; ++i) {
            data.add(new DataBean(i, "ID:" + i + "Name:" + Math.random()));
        }
    }

    public List<DataBean> loadData(int size) {
        try {
            return data.subList(0, size);
        } catch (Exception e) {
            return null;
        }
    }

    public List<DataBean> loadData(int start, int size) {
        try {
            if (start + size >= data.size()) {
                return data.subList(start, data.size());
            }
            return data.subList(start, start + size);
        } catch (Exception e) {
            return null;
        }
    }

    public List<DataBean> loadPageData(int page, int size) {
        Logger.i("loadPageData page:%d size:%d", page, size);
        int totalPage = data.size() / size;
        if (data.size() % size != 0) {
            totalPage += 1;
        }
        if (page >= totalPage || page < 0) {
//            int old = (page + 1) * size;
//            List<DataBean> sub = new ArrayList<>(size);
//            for (int i = old; i < old + 100; ++i) {
//                sub.add(new DataBean(i, "ID:" + i + ".....Name:" + Math.random()));
//            }
//            return sub;
            return null;
        }
        return (page == totalPage - 1) ? data.subList(page * size, data.size())
                : data.subList(page * size, page * size + size);
    }
}
