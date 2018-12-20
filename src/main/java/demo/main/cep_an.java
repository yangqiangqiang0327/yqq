package demo.main;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 16:54
 * @Author: yqq
 * @Description:
 */

import akka.util.ByteString;
import com.sun.corba.se.spi.ior.ObjectKey;
import demo.dao.ITaskDAO;
import demo.dao.InsertDAO;
import demo.dao.factory.DAOFactory;
import demo.domain.*;
import demo.util.ParamUtils;
import demo.util.taskIDUtils;
import org.apache.commons.math3.geometry.spherical.oned.S1Point;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;

import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchemaWrapper;
import demo.conf.ConfigurationManager;
import demo.constant.Constant;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema;

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.util.Collector;
import scala.Product1;
import scala.tools.nsc.doc.model.Val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class cep_an {
    public static void main(String[] args) throws Exception {
        //1 创建环境 修改并行度，并设置checkpoint
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //2 设置输入源kafka
        /*String topic = ConfigurationManager.getProperty(Constant.KAFKA_TOPICS);
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers",ConfigurationManager.getProperty(Constant.KAFKA_METADATA_BROKER_LIST));
        prop.setProperty(Constant.KAFKA_GROUP_ID,ConfigurationManager.getProperty(Constant.KAFKA_GROUP_ID) );
        FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<String>(topic, new SimpleStringSchema(), prop);//解析方式
*/
        String topic = "test7";
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "192.168.31.128:9092,192.168.31.75:9092,192.168.31.143:9092");
        prop.setProperty("group.id", "con1");
        FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<String>(topic, new SimpleStringSchema(), prop);
        DataStream<String> data = env.addSource(myConsumer);
        //System.out.println(data);

        /**
         * stream数据中的时间，可以设置为事件产生的时间EventTIme 事件进入Flink的时间Ingestion Time
         * 和ProcessTimes事件被处理时的时间(默认),可以设置，通常是和flink 的窗口连用结局乱序数据
         * 1 设置Event Time 2设置watermarks最多等待多长时间
         */
        //env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        /**
         * 状态容错
         * flink依靠check point机制实现 exactly-once机制，它会周期性地对Stream中各个operator/task
         * 的状态生成快照，flink程序崩溃，将快照恢复，并重放数据，实现仅一次语义
         * 它对kafka的版本有要求 0.11及以上  check point默认关闭
         * MemoryStateBackend
         * state数据保存在java堆内存中，执行checkpoint的时候，会把state的快照数据保存到jobmanager的内存中
         * 基于内存的state backend在生产环境下不建议使用
         * FsStateBackend
         * state数据保存在taskmanager的内存中，执行checkpoint的时候，会把state的快照数据保存到配置的文件系统中
         * 可以使用hdfs等分布式文件系统
         * RocksDBStateBackend
         * RocksDB跟上面的都略有不同，它会在本地文件系统中维护状态，state会直接写入本地rocksdb中。同时它需要配置一个远端的filesystem uri（一般是HDFS），在做checkpoint的时候，会把本地的数据直接复制到filesystem中。fail over的时候从filesystem中恢复到本地
         * RocksDB克服了state受内存限制的缺点，同时又能够持久化到远端文件系统中，比较适合在生产中使用
         */
      /* env.enableCheckpointing(60000);//设置check point周期
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);//设置仅一次语义 可以为至少一次
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(30000);//check point的最小间隔
        env.getCheckpointConfig().setCheckpointTimeout(10000);//检查点要在10s内完成
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);//同一时间只允许1个检查点存在
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);*/
        //执行失败保存数据，放弃不保存

        //3获取命令行传入的taskid，获取对应的参数
        ITaskDAO taskDAO = DAOFactory.getTaskDAO();
        //task id =1
        //long taskid = ParamUtils.getTaskIdFromArgs(args);
        //从数据库获取task id
       long taskid = taskIDUtils.gettaskID();
      // System.out.println(taskid);
        if (taskid != 1) {
            return;
        }
        Task task = taskDAO.findTaskById(taskid);
        //System.out.println(task);
        if (task == null){
            return;
        }
        //获取参数，以JSON的格式得到 将参数字符串转换成json格式的对象 并将参数转换为double类型
        JSONObject taskParamJSON = JSONObject.parseObject(task.getTaskParams());
        //System.out.println(taskParamJSON);
        //String taskParamString = taskParamJSON.getString(Constant.PARAM);
        //Double taskParam = taskParamJSON.getJSONArray(Constant.PARAM).getDouble(0);
        //System.out.println(taskParam);
        //double taskParam = Double.parseDouble(taskParamString);
        double taskParam = 0.4;
        // '2018-12-11', 3.53, 3.8, 3.8, 3.36, 834336.38, 0.35, 10.14, 3.554, 3.237, 3.124, 807029.24, 489392.42, 303512.67, '300325'
        //对数据进行处理
        DataStream<Double> p = data.map(new MapFunction<String, Double>() {
            @Override
            public Double map(String s) throws Exception {
                String[] s1 = s.split("，");
                String s2 = s1[6];
                double s3 = Double.valueOf(s2);
                return s3;
            }
        });
       DataStream<TestEvent> P = data.map(new MapFunction<String, TestEvent>() {

           @Override
           public TestEvent map(String s) throws Exception {
               String[] s1 = s.split(" ");
                String s2 = s1[0];
               TestEvent testEvent = new TestEvent();
               testEvent.setDate(s2);
               return testEvent;
           }
       });
       DataStream<MyDemo> data2 = data.map(new MapFunction<String, MyDemo>() {
           @Override
           public MyDemo map(String s) throws Exception {
               String[] s1 = s.split(",");
               String s2 = s1[6];
               double s3 = Double.valueOf(s2);
               String s4 = s1[14];
               MyDemo myDemo = new MyDemo();
               myDemo.setPrice_change(s3);
               myDemo.setTicket_code(s4);
               return myDemo;
           }
       });
       //System.out.println(data2);
       Pattern<MyDemo,?> demo = Pattern.<MyDemo>begin("start")
               .subtype(MyDemo.class)
               .where(new SimpleCondition<MyDemo>() {
                   @Override
                   public boolean filter(MyDemo myDemo) throws Exception {
                       return myDemo.getPrice_change() > taskParam;
                   }
               })
              // .times(3)
               .next("midle")
               .subtype(MyDemo.class)
               .where(new SimpleCondition<MyDemo>() {
                   @Override
                   public boolean filter(MyDemo myDemo) throws Exception {
                       return myDemo.getPrice_change() > taskParam;
                   }
               })
               .next("end")
               .subtype(MyDemo.class)
               .where(new SimpleCondition<MyDemo>() {
                   @Override
                   public boolean filter(MyDemo myDemo) throws Exception {
                       return myDemo.getPrice_change() > taskParam;
                   }
               });
              // .within(Time.seconds(10));

        /**
         * where条件有 new SimpleCondition 简单条件 满足本条即可 和  满足本条并可以将本条产生的数据，和下条产生的数据
         * 相加，组成一个条件。组合条件，使用and or逻辑关联 如果要求同一时间出现多吃，可以 使用 time（） 或者至少发生几次
         * timesOrMore。事件之间的发生也有三种，.next 严格连续性，中间不能发生不一样， .followedby 宽松连续性 会抛弃中间
         * 不符合的元素，followedByAny非确定性宽松连续性 不仅可以跳过不符合，还可以跳过符合的元素，寻找接下来有没有符合的
         */

      PatternStream<MyDemo> p2 = CEP.pattern(data2.keyBy("ticket_code "), demo);

            /*    DataStream<DemoEvent> select = p2.select(
                (Map<String, List<MyDemo>> pattern) -> {
                     MyDemo first = pattern.get("start").get(1);

                    return new DemoEvent(first.getTicket_code());
                }
        );*/
             DataStream<DemoEvent> res =  p2.flatSelect(
                        (Map<String, List<MyDemo>> pattern, Collector<DemoEvent> out) -> {
                            MyDemo first = pattern.get("start").get(0);
                                out.collect(new DemoEvent(first.getTicket_code()));

                        },
                        TypeInformation.of(DemoEvent.class));


        /**
         *flatselect 相较于select 可以返回一个任意数量的结果
         */
        //将结果转为string格式并输出到mysql的 demo_insert

     String selecrstr = res.toString();
        ArrayList<String> de = new ArrayList<>();
        de.add(selecrstr);
        //select.print();

        InsertDAO sertDAO = DAOFactory.getSertDAO();
        sertDAO.insertDemo(de);
        env.execute(" task");

        //确定CEP的模式
      /*  Pattern<DemoEvent,?> demoEventPattern = Pattern.<DemoEvent>begin("first")
                .where(new IterativeCondition<DemoEvent>() {
                    @Override
                    public boolean filter(DemoEvent demoEvent, Context<DemoEvent> context) throws Exception {
                       return demoEvent.getPrice_change()> taskParam;
                    }
                })
                .next("second")
                .where(new IterativeCondition<DemoEvent>() {
                    @Override
                    public boolean filter(DemoEvent demoEvent, Context<DemoEvent> context) throws Exception {
                        return demoEvent.getPrice_change()> taskParam;
                    }
                })
                .within(Time.seconds(10));*/

       /* Pattern<DemoEvent,Demo1Event> demoEventPattern1 = Pattern.<DemoEvent>begin("first")
                .subtype(Demo1Event.class)
              .where(new IterativeCondition<Demo1Event>() {
                  @Override
                  public boolean filter(Demo1Event demo1Event, Context<Demo1Event> context) throws Exception {
                      return demo1Event.getPrice_change()>taskParam;
                  }
              })
                .next("second")
                .subtype(Demo1Event.class)
                .where(new IterativeCondition<Demo1Event>() {
                    public boolean filter(Demo1Event demo1Event, Context<Demo1Event> context) throws Exception {
                        return demo1Event.getPrice_change()>taskParam;
                    }
                })
                .within(Time.seconds(10));

        PatternStream<DemoEvent> pa = CEP.pattern(data.keyBy("ticket_code"),demoEventPattern1);

        PatternStream<DemoEvent> pa1 = CEP.pattern(demoEvent,demoEventPattern1);*/

    }
}
