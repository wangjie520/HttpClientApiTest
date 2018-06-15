package com.ccybase.util;

import com.ccybase.config.TestConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Reporter;

import java.io.IOException;
import java.util.Date;

public class PostWithBody {
    public void setPARAM(JSONObject PARAM) {
        this.PARAM = PARAM;
    }

    public void setTESTURL(String TESTURL) {
        this.TESTURL = TESTURL;
    }

    private JSONObject PARAM;
    private String TESTURL;

    public String PostWithBodyResult() throws IOException {
        HttpPost post = new HttpPost(TESTURL);
        post.setHeader("content-type", "application/json");
        post.setEntity(new StringEntity(PARAM.toString()));

        String request = EntityUtils.toString(post.getEntity());
        Reporter.log("request:" + request+new Date());
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);


        TestConfig.cookieStore = TestConfig.defaultHttpClient.getCookieStore();
       /* List<Cookie> cookieList=store.getCookies();
        for(Cookie cookie:cookieList){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println(name+":"+value);
        }*/

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        Reporter.log("response:"+new Date() + result);
        return result;
    }
}
