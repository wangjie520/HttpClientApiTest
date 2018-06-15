package com.ccybase.util;



import com.ccybase.model.TestApiName;

import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle=ResourceBundle.getBundle("application");
    public static String getUrl(TestApiName name){
        String address=bundle.getString("test.url");
        String uri="";
        String testUrl;
        if(name==TestApiName.LOGIN){
            uri=bundle.getString("login.uri");
        }
        if(name==TestApiName.QUERYVC){
            uri=bundle.getString("vc.uri");
        }
        if(name==TestApiName.TOTALRESULTS){
            uri=bundle.getString("results");
        }
        testUrl=address+uri;
        return  testUrl;

    }

}
