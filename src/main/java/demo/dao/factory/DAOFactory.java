package demo.dao.factory;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 16:51
 * @Author: yqq
 * @Description:DAO工厂类
 */

import demo.dao.ITaskDAO;
import demo.dao.InsertDAO;
import demo.dao.impl.SertDAOImpl;
import demo.dao.impl.TaskDAOImpl;

public class DAOFactory {
    public static ITaskDAO getTaskDAO(){
        return new TaskDAOImpl();
    }
    public static InsertDAO getSertDAO(){
            return new SertDAOImpl();
    }
}
