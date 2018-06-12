package com.ccybase.cases;

import com.ccybase.config.TestConfig;
import com.ccybase.model.VcTest;
import com.ccybase.util.DatabaseUtil;
import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class QueryVcTestCase {
   //如果不执行beforeClass,则跟登录使用同一个httpclient，不用再设置cookies信息
    @BeforeClass
    public void beforeClass(){
            TestConfig.defaultHttpClient=new DefaultHttpClient();
            TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
    }
    @Test
    public  void testQueryVc() throws IOException {
        SqlSession session=DatabaseUtil.getSqlSession();
        VcTest vcTest =session.selectOne("vcTest",1);

        //发起请求，获取响应
        String response=getResult(vcTest);

        //响应断言
        JSONObject rj=new JSONObject(response);
        String code=rj.getInt("code")+"";
        Assert.assertEquals(code,vcTest.getExpected());
}

    private String getResult(VcTest vcTest) throws IOException {

       //封装Get参数
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("vc_id", vcTest.getVcId()));
        String str = "";
        str = EntityUtils.toString(new UrlEncodedFormEntity(params,"utf-8"));
        //发起get请求
        HttpGet get=new HttpGet(TestConfig.QueryVcUrl+"?"+str);


        HttpResponse response=TestConfig.defaultHttpClient.execute(get);
        String result= EntityUtils.toString(response.getEntity());
        return  result;
    }
    }


