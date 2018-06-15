package com.ccybase.cases;

import com.ccybase.config.TestConfig;
import com.ccybase.model.LoginTest;
import com.ccybase.model.TestApiName;
import com.ccybase.util.ConfigFile;

import com.ccybase.util.GetDataUtil;
import com.ccybase.util.PostWithBody;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import java.util.Iterator;



public class LoginTestCase {
    //获取测试数据

    @BeforeTest(groups="login",description = "登录相关测试")
    public void beforeTest(){
        TestConfig.LoginUrl=ConfigFile.getUrl(TestApiName.LOGIN);
        TestConfig.QueryVcUrl=ConfigFile.getUrl(TestApiName.QUERYVC);
        TestConfig.TotalResultsUrl=ConfigFile.getUrl(TestApiName.TOTALRESULTS);
        TestConfig.defaultHttpClient=new DefaultHttpClient();

    }
    @Test(groups="login",dataProvider = "loginData")

    public void loginTestCase(LoginTest loginTest) throws IOException {
        //用测试数据发起请求，获取响应
        String response=getResult(loginTest);

        //响应断言
        JSONObject rj=new JSONObject(response);
        String code=rj.getInt("code")+"";
        Assert.assertEquals(code,loginTest.getExpected());

    }
    @DataProvider(name="loginData")
    private Iterator<Object[]> LoginDataProvider() throws IOException {
        Iterator<Object[]> logintestdata=GetDataUtil.GetDataProvider("loginTestAll");
        return logintestdata;

    }

    private String getResult(com.ccybase.model.LoginTest loginTest) throws IOException {
        JSONObject param=new JSONObject();
        param.put("mobile",loginTest.getMobile());
        param.put("password",loginTest.getPassword());
        PostWithBody post=new PostWithBody();
        post.setPARAM(param);
        post.setTESTURL(TestConfig.LoginUrl);
        String result=post.PostWithBodyResult();
        return  result;

    }
}

