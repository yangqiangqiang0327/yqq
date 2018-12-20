package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 14:56
 * @Author: yqq
 * @Description:对task任务的自定义实体类
 */

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private long taskId;
    private String taskName;
    private String taskParams;

    public static long getSerialVersionUID() { return serialVersionUID; }

    public Task() {

    }

    public long getTaskId() { return taskId; }

    public void setTaskId(long taskId) { this.taskId = taskId; }

    public String getTaskName() { return taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getTaskParams() { return taskParams; }

    public void setTaskParams(String taskParams) { this.taskParams = taskParams; }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskParams='" + taskParams + '\'' +
                '}';
    }

    public Task(long taskId, String taskName, String taskParams) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskParams = taskParams;
    }
}
