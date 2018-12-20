package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 9:26
 * @Author: yqq
 * @Description:事件的实体类
 */

import org.apache.flink.streaming.api.datastream.DataStream;

import java.util.Objects;

public class DemoEvent{

    private String ticket_code;
    public DemoEvent(String ticket_code){
        this.ticket_code = ticket_code;
    }


    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoEvent)) return false;
        DemoEvent demoEvent = (DemoEvent) o;
        return Objects.equals(getTicket_code(), demoEvent.getTicket_code());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTicket_code());
    }

    @Override
    public String toString() {
        return "DemoEvent{" +
                "ticket_code='" + ticket_code + '\'' +
                '}';
    }
}
