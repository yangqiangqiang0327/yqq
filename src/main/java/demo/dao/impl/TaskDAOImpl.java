package demo.dao.impl;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 15:11
 * @Author: yqq
 * @Description:任务管理DAO的实现类
 */

import demo.dao.ITaskDAO;
import demo.domain.Task;
import demo.jdbc.JDBCHelper;
import demo.jdbc.JDBCHelper.QueryCallback;

import java.sql.ResultSet;

public class TaskDAOImpl implements ITaskDAO {

    @Override
    public Task findTaskById(long taskId) {
        final Task task = new Task();
        String sql = "SELECT * FROM task_demo WHERE task_id = ?";
        Object[] params = new Object[]{taskId};

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        jdbcHelper.executeQuery(sql,params, new QueryCallback(){

            @Override
            public void process(ResultSet rs) throws Exception {
                if (rs.next()){
                    long taskid = rs.getLong(1);
                    String taskName = rs.getString(2);
                    String taskParam = rs.getString(3);
                    task.setTaskId(taskid);
                    task.setTaskName(taskName);
                    task.setTaskParams(taskParam);
                }
            }
        });
        return task;
    }
}
