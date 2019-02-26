package com.hwqgooo.jetpack.utils.http.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class MemoryCookieStore implements CookieStore {
    private final Map<String, List<Cookie>> allCookies = new HashMap<>();

    @Override
    public void addCookies(HttpUrl url, List<Cookie> cookies) {
        addCookies(url.host(), cookies);
    }

    @Override
    public void addCookies(String host, List<Cookie> cookies) {
        List<Cookie> oldCookies = allCookies.get(host);
        if (oldCookies != null) {
            Iterator<Cookie> itNew = cookies.iterator();
            Iterator<Cookie> itOld = oldCookies.iterator();
            while (itNew.hasNext()) {
                String va = itNew.next().name();
                while (va != null && itOld.hasNext()) {
                    String v = itOld.next().name();
                    if (v != null && va.equals(v)) {
                        itOld.remove();
                    }
                }
            }
            oldCookies.addAll(cookies);
        } else {
            allCookies.put(host, cookies);
        }
    }

    @Override
    public void addCookie(HttpUrl url, Cookie cookie) {
        addCookie(url.host(), cookie);
    }

    @Override
    public void addCookie(String host, Cookie cookie) {
        List<Cookie> oldCookies = allCookies.get(host);
        if (oldCookies != null) {
            Iterator<Cookie> itOld = oldCookies.iterator();
            while (itOld.hasNext()) {
                String name = itOld.next().name();
                if (name != null && name.equals(cookie.name())) {
                    itOld.remove();
                }
            }
            oldCookies.add(cookie);
        } else {
            oldCookies = new ArrayList<>();
            oldCookies.add(cookie);
            allCookies.put(host, oldCookies);
        }
    }

    @Override
    public List<Cookie> get(HttpUrl url) {
        return get(url.host());
    }

    @Override
    public List<Cookie> get(String host) {
        List<Cookie> cookies = allCookies.get(host);
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCookies.put(host, cookies);
        }
        return cookies;
    }

    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(allCookies.get(url));
        }
        return cookies;
    }

    @Override
    public boolean remove(HttpUrl url, Cookie cookie) {
        List<Cookie> cookies = allCookies.get(url.host());
        return cookie != null && cookies.remove(cookie);
    }

    @Override
    public boolean remove(HttpUrl url) {
        return allCookies.remove(url.host()) != null;
    }

    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }

    @Override
    public Map<String, List<Cookie>> getCookieMap() {
        return allCookies;
    }

    @Override
    public Map<String, List<SerializableCookie>> getSerialCookieMap() {
        Map<String, List<SerializableCookie>> map = new HashMap<>();
        for (Map.Entry<String, List<Cookie>> entity : allCookies.entrySet()) {
            String host = entity.getKey();
            List<SerializableCookie> serializableCookies = new ArrayList<>();
            for (Cookie cookie : entity.getValue()) {
                serializableCookies.add(new SerializableCookie(host, cookie));
            }
            map.put(host, serializableCookies);
        }
        return map;
    }

    @Override
    public List<SerializableCookie> getSerializableCookie(String host) {
        List<Cookie> oldCookie = allCookies.get(host);
        List<SerializableCookie> serializableCookies = new ArrayList<>();
        if (oldCookie.size() > 0) {
            for (Cookie cookie : oldCookie) {
                serializableCookies.add(new SerializableCookie(host, cookie));
            }
        }
        return serializableCookies;
    }

    @Override
    public void addSerialCookieMap(Map<String, List<SerializableCookie>> cookies) {
        for (Map.Entry<String, List<SerializableCookie>> entry : cookies.entrySet()) {
            addSerialCookies(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void addSerialCookies(String host, List<SerializableCookie> cookies) {
        List<Cookie> newList = new ArrayList<>();
        for (SerializableCookie cookie : cookies) {
            newList.add(cookie.getCookie());
        }
        addCookies(host, newList);
    }
}
