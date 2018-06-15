package com.ccybase.util;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetDataUtil {
    public static Iterator<Object[]> GetDataProvider(String sqlmapid) throws IOException {
        List<Object[]> result = new ArrayList<Object[]>();
        SqlSession session = DatabaseUtil.getSqlSession();
        List<Object> alldata = session.selectList(sqlmapid);
        Iterator it = alldata.iterator();
        while (it.hasNext()) {
            result.add(new Object[]{it.next()});
        }
        return result.iterator();
    }
}
