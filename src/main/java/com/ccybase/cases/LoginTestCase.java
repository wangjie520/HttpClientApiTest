package com.ccybase.cases;

import com.ccybase.config.TestConfig;
import com.ccybase.model.LoginTest;
import com.ccybase.model.TestApiName;
import com.ccybase.util.ConfigFile;
import com.ccybase.util.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LoginTestCase {
    //获取测试数据

    @BeforeTest(groups="login",description = "登录相关测试")
    public void beforeTest(){
        TestConfig.LoginUrl=ConfigFile.getUrl(TestApiName.LOGIN);
        TestConfig.QueryVcUrl=ConfigFile.getUrl(TestApiName.QUERYVC);
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
        List<Object[]> result=new ArrayList<Object[]>();
        SqlSession session=DatabaseUtil.getSqlSession();
        List<Object> alldata=session.selectList("loginTestAll");
        Iterator it=alldata.iterator();
        while(it.hasNext()){
        result.add(new Object[]{ it.next() });
        }
        return  result.iterator();

    }

    private String getResult(com.ccybase.model.LoginTest loginTest) throws IOException {
        JSONObject param=new JSONObject();
        param.put("mobile",loginTest.getMobile());
        param.put("password",loginTest.getPassword());

        HttpPost post=new HttpPost(TestConfig.LoginUrl);
        post.setHeader("content-type","application/json");
        post.setEntity(new StringEntity(param.toString()));
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);

        TestConfig.cookieStore=TestConfig.defaultHttpClient.getCookieStore();
       /* List<Cookie> cookieList=store.getCookies();
        for(Cookie cookie:cookieList){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println(name+":"+value);
        }*/

        String result= EntityUtils.toString(response.getEntity(),"utf-8");
        return  result;
    }
}

