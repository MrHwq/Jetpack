
package com.hwqgooo.jetpack.utils.http;

import com.hwqgooo.jetpack.utils.http.cookie.CookieJarImpl;
import com.hwqgooo.jetpack.utils.http.cookie.store.CookieStore;
import com.hwqgooo.jetpack.utils.http.cookie.store.MemoryCookieStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpManager {
    private OkHttpClient httpClient;
    private CookieStore cookieStore;

    private OkHttpManager() {

    }

    public OkHttpManager(Builder builder) {
        /** OkHttpClient.Builder 其他参数:
         * DEFAULT_PROTOCOLS 协议
         * DEFAULT_CONNECTION_SPECS TLS那些事
         * SSLSocketFactory defaultSslSocketFactor 懒加载(HTTPS那些事)
         * RouteDatabase routeDatabase 多个IP时候的选择策略
         * Dispatcher dispatcher 异步请求执行时候的策略，用到了ExecutorService作线程池，默认创建的是core 0，max Integer.MAX_VALUE，keep alive 60秒的线程池
         * Proxy proxy 代理嘛，大家都懂得，分为DIRECT(直连无代理)、HTTP和SOCKS
         * interceptors 拦截器 - 在Response正式返回前处理数据
         * networkInterceptors 这个网络拦截器可就牛逼了。。。相较前面那个发生地更早，在HttpEngine的readResponse中被调用
         * ProxySelector proxySelector 代理服务器选择器
         * CookieHandler cookieHandler Cookie处理器，可以接受get和put cookie的事件
         * CertificatePinner certificatePinner 证书验证
         * Authenticator authenticator 回复服务器认证要求
         * ConnectionPool connectionPool  连接池，同一个host的请求可能共享同一个connection，该类还实现了connection为以后的使用保持打开的策略
         * Network network 接口，实际使用了InetAddress.getAllByName
         * 其实10_000就是10000，这里是10000毫秒即10秒
         */
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        for (Interceptor interceptor : builder.interceptors) {
//            okBuilder.addInterceptor(interceptor);
//        }
        okBuilder.interceptors().addAll(builder.interceptors);

//        for (Interceptor interceptor : builder.networkInterceptors) {
//            okBuilder.addNetworkInterceptor(interceptor);
//        }
        okBuilder.networkInterceptors().addAll(builder.networkInterceptors);
        //设定30秒超时
        okBuilder.readTimeout(builder.readTimeout, TimeUnit.SECONDS);
        okBuilder.connectTimeout(builder.connectTimeout, TimeUnit.SECONDS);
        okBuilder.writeTimeout(builder.writeTimeout, TimeUnit.SECONDS);
        okBuilder.cookieJar(new CookieJarImpl(cookieStore = builder.cookieStore));
        okBuilder.followRedirects(builder.followRedirects);
        okBuilder.followSslRedirects(builder.followSslRedirects);
        okBuilder.retryOnConnectionFailure(builder.retryOnConnectionFailure);
        httpClient = okBuilder.build();
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public static final class Builder {
        final List<Interceptor> interceptors = new ArrayList<>();
        final List<Interceptor> networkInterceptors = new ArrayList<>();
        boolean followSslRedirects;
        boolean followRedirects;
        boolean retryOnConnectionFailure;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        boolean log;
        CookieStore cookieStore;

        public Builder() {
            cookieStore = new MemoryCookieStore();
            log = false;//默认不开log日志
            followRedirects = true;//默认开启OkHttp的自动重定向操作
            followSslRedirects = true;//默认开启OkHttp的自动重定向操作
            retryOnConnectionFailure = true;//设置出现错误进行重新连接
            this.connectTimeout = 30;//默认30秒
            this.readTimeout = 30;
            this.writeTimeout = 30;
        }

        public Builder setFollowSslRedirects(boolean followSslRedirects) {
            this.followSslRedirects = followSslRedirects;
            return this;
        }

        public Builder setFollowRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;

        }

        public Builder setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setLog(boolean log) {
            this.log = log;
            return this;
        }

        public Builder setCookieStore(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            networkInterceptors.add(interceptor);
            return this;
        }

        public OkHttpManager build() {
            if (log) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                interceptors.add(logging);
            }
            return new OkHttpManager(this);
        }
    }
}
