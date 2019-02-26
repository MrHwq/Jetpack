package com.hwqgooo.jetpack.utils.http.cookie.store;

import java.io.Serializable;

import okhttp3.Cookie;

public class SerializableCookie implements Serializable {
    public String host;
    public String name;
    public String domain;
    public String value;
    public long expiresAt;
    public String path;
    public boolean secure;
    public boolean httpOnly;
    public boolean persistent;
    public boolean hostOnly;
    private transient Cookie cookie;

    public SerializableCookie() {

    }

    public SerializableCookie(String host, Cookie cookie) {
        this.cookie = cookie;
        this.host = host;
        set(cookie);
    }

    private void set(Cookie cookie) {
        if (name == null && cookie != null) {
            this.name = cookie.name();
            this.domain = cookie.domain();
            this.value = cookie.value();
            this.expiresAt = cookie.expiresAt();
            this.path = cookie.path();
            this.secure = cookie.secure();
            this.httpOnly = cookie.httpOnly();
            this.persistent = cookie.persistent();
            this.hostOnly = cookie.hostOnly();
        }
    }

    private Cookie setCookie() {
        if (cookie == null && name != null) {
            Cookie.Builder builder = new Cookie.Builder();
            builder = builder.name(name);
            builder = builder.value(value);
            builder = builder.expiresAt(expiresAt);
            builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
            builder = builder.path(path);
            builder = secure ? builder.secure() : builder;
            builder = httpOnly ? builder.httpOnly() : builder;
            cookie = builder.build();
        }
        return cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = setCookie();
        set(bestCookie);
        return bestCookie;
    }
}
