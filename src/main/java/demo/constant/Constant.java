package demo.constant;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 11:28
 * @Author: yqq
 * @Description:常量接口
 */

public interface Constant {
    //项目配置相关常量
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_PASSWORD = "jdbc.password";
    String KAFKA_METADATA_BROKER_LIST = "kafka.metadata.broker.list";
    String KAFKA_TOPICS = "kafka.topics";
    String KAFKA_GROUP_ID = "kafka.group.id";
    String TASK_ID = "flink.task.id";
    //任务相关常量
    String PARAM = "ratio";
}
