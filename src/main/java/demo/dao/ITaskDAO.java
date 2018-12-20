package demo.dao;
/*
 * @program: FlinkTest
 * @Date: 2018/12/17 14:24
 * @Author: yqq
 * @Description:任务管理接口
 */

import demo.domain.Task;

public interface ITaskDAO {
    /**
     * 根据task的主键查询指定任务
     */
    Task findTaskById(long taskId);
}
