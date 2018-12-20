package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 14:46
 * @Author: yqq
 * @Description:
 */

import java.util.Objects;

public class MyDemo {
    private double price_change;
    private String ticket_code;

    @Override
    public String toString() {
        return "MyDemo{" +
                "price_change=" + price_change +
                ", ticket_code='" + ticket_code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyDemo)) return false;
        MyDemo myDemo = (MyDemo) o;
        return Double.compare(myDemo.getPrice_change(), getPrice_change()) == 0 &&
                Objects.equals(getTicket_code(), myDemo.getTicket_code());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrice_change(), getTicket_code());
    }

    public double getPrice_change() {
        return price_change;
    }

    public void setPrice_change(double price_change) {
        this.price_change = price_change;
    }

    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }
}
