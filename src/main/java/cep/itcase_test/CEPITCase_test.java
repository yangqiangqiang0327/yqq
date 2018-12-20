package cep.itcase_test;/*
 * @program: FlinkTest
 * @Date: 2018/12/11 17:51
 * @Author: yqq
 * @Description:
 */

import cep.itcase.SubEvent;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;

import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;

import java.util.List;
import java.util.Map;


public class CEPITCase_test {
    public static void main(String[] args) throws Exception {
        //设置环境，时间戳类型，和事件源
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<Event_test> input = env.fromElements(
                new Event_test(0,"yqq",0.7),new Event_test(1,"sq" ,1.7 ),new Event_test(2, "hwt", 2.7),
                new Event_test(6,"hwt" ,0.9 )).assignTimestampsAndWatermarks(new IngestionTimeExtractor<>());

        //确定模式 这里的泛型是用一个输入类型和一个不确定的
        Pattern<Event_test, Event_test> pattern = Pattern.<Event_test>begin("first").subtype(Event_test.class)
                .where(new SimpleCondition<Event_test>() {
                    @Override
                    public boolean filter(Event_test event_test) throws Exception {
                        return event_test.getName().equals("sq");
                    }
                })
                .followedByAny("third").subtype(Event_test.class).where(new SimpleCondition<Event_test>() {
                    @Override
                    public boolean filter(Event_test event_test) throws Exception {
                        return event_test.getName().equals("hwt");
                    }
                });
        //确定模式流
        PatternStream<Event_test> pattern1 = CEP.pattern(input.keyBy(" "), pattern);
        //输出结果
        DataStream<String> result = pattern1.select(new PatternSelectFunction<Event_test, String>() {
            @Override
            public String select(Map<String, List<Event_test>> map) throws Exception {
                StringBuilder builder = new StringBuilder();
                builder.append(map.get("first").get(0).getId()).append(",")
                        .append(map.get("second").get(0).getId()).append(",")
                        .append(map.get("third").get(0).getId());
                return builder.toString();
            }
        });
        result.print();
        result.writeAsText("src/main/resources/result1.txt", FileSystem.WriteMode.OVERWRITE);
        env.execute("task");


    }
}
