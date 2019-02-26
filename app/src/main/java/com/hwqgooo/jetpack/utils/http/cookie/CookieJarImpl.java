package com.hwqgooo.jetpack.utils.http.cookie;

import com.hwqgooo.jetpack.utils.http.cookie.store.CookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieJarImpl implements CookieJar {
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) throw new IllegalStateException("cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
        cookieStore.addCookies(httpUrl, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        return cookieStore.get(httpUrl);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
