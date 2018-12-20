package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 10:16
 * @Author: yqq
 * @Description:
 */

import java.util.Objects;

public class Demo1Event extends DemoEvent {

    private double price_change;

    public Demo1Event(String p) {
        super(p);
    }


    public double getPrice_change() {
        return price_change;
    }

    public void setPrice_change(double price_change) {
        this.price_change = price_change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Demo1Event)) return false;
        if (!super.equals(o)) return false;
        Demo1Event that = (Demo1Event) o;
        return Double.compare(that.getPrice_change(), getPrice_change()) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getPrice_change());
    }

    @Override
    public String toString() {
        return "Demo1Event{" +
                "price_change=" + price_change +
                '}';
    }
}
