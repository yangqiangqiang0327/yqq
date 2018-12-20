package demo.dao.impl;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 16:14
 * @Author: yqq
 * @Description:
 */

import demo.dao.InsertDAO;
import demo.domain.DemoEvent;
import demo.jdbc.JDBCHelper;

import java.util.ArrayList;
import java.util.List;

public class SertDAOImpl implements InsertDAO {
    @Override
    public void insertDemo(List<String> InsertDemoEvent) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        String sql = "INSERT INTO demo_insert VALUES(?)";
        List<Object[]> params = new ArrayList<>();
        for (String str: InsertDemoEvent){
            params.add(new Object[]{str});
        }
        jdbcHelper.executeBatch(sql,params );

    }

}
