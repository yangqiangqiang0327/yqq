package demo.dao;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 15:54
 * @Author: yqq
 * @Description:将得到的数据返回1到数据库
 */

import demo.domain.DemoEvent;

import java.util.List;

public interface InsertDAO {
 void insertDemo (List<String> InsertDemoEvent);
}
