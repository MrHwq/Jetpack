package com.hwqgooo.jetpack.utils.http.cookie.store;

import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public interface CookieStore {
    /**
     * 保存url对应所有cookie
     */
    void addCookies(HttpUrl url, List<Cookie> cookies);

    void addCookies(String host, List<Cookie> cookies);

    /**
     * 保存url对应所有cookie
     */
    void addCookie(HttpUrl url, Cookie cookie);

    void addCookie(String host, Cookie cookie);


    /**
     * 获取当前url对应的所有的cookie
     */
    List<Cookie> get(HttpUrl url);

    /**
     * 获取当前host对应的所有的cookie
     */
    List<Cookie> get(String host);

    /**
     * 获取当前所有保存的cookie
     */
    List<Cookie> getCookies();

    /**
     * 根据url和cookie移除对应的cookie
     */
    boolean remove(HttpUrl url, Cookie cookie);

    /**
     * 根据url移除所有的cookie
     */
    boolean remove(HttpUrl url);

    /**
     * 移除所有的cookie
     */
    boolean removeAll();

    /**
     * 获取存储的Cookie Map
     */
    Map getCookieMap();

    /**
     * 得到序列化后的Cookie Map
     */
    Map<String, List<SerializableCookie>> getSerialCookieMap();

    List<SerializableCookie> getSerializableCookie(String host);

    /**
     * 序列化后的Cookie Map转成原始的Cookie
     */
    void addSerialCookieMap(Map<String, List<SerializableCookie>> cookies);

    void addSerialCookies(String host, List<SerializableCookie> cookies);
}
