package com.ccybase.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
//定义测试中使用的接口url和浏览器,cookie管理器
public class TestConfig {
    public  static String LoginUrl;
    public static String QueryVcUrl;
    public static String TotalResultsUrl;

    public static DefaultHttpClient defaultHttpClient;

    public static CookieStore cookieStore;
}
