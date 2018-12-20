package demo.util;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 23:40
 * @Author: yqq
 * @Description:通过连接mysql获取taskid
 */

import demo.jdbc.JDBCHelper;
import scala.tools.nsc.backend.icode.Opcodes;

import java.sql.ResultSet;
import java.util.ArrayList;

public class taskIDUtils {
    public static Long gettaskID() {
        //连接到mysql
        final ArrayList<String> list = new ArrayList<>();
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        String sql = "SELECT task_id FROM task_demo";
        jdbcHelper.executeQuery(sql, null, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                if (rs.next()) {
                    String s1 = rs.getString(1);
                   list.add(s1);
                }
            }
        });
        String str = String.join(",",list.toArray(new String[list.size()]));
        long longs = Long.valueOf(str);
        return longs;
    }
}
