package com.ccybase.util;

import com.ccybase.config.TestConfig;
import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Reporter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class GetWithParam {
    public void setTESTURL(String TESTURL) {
        this.TESTURL = TESTURL;
    }

    String TESTURL;
    List<NameValuePair> params=Lists.newArrayList();

    public void addParam(String name,String value){
        params.add(new BasicNameValuePair(name,value));
    }
    public String getWithParamResult() throws IOException {
        String str = "";
        str = EntityUtils.toString(new UrlEncodedFormEntity(params,"utf-8"));
        //发起get请求
        HttpGet get=new HttpGet(TestConfig.QueryVcUrl+"?"+str);
        String request=get.toString();
        Reporter.log("requst:"+new Date()+request);
        HttpResponse response=TestConfig.defaultHttpClient.execute(get);
        String result= EntityUtils.toString(response.getEntity());
        Reporter.log("response:"+new Date()+result);
        return  result;
    }

}
