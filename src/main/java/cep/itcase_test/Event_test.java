package cep.itcase_test;/*
 * @program: FlinkTest
 * @Date: 2018/12/11 16:43
 * @Author: yqq
 * @Description:模拟事件 数据
 */

import cep.itcase.Event;

import java.util.Objects;

public class Event_test {
    private String name;
    private double price;
    private int id;

    public Event_test(int id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Event_test{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event_test)) return false;
        Event_test that = (Event_test) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 &&
                getId() == that.getId() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getPrice(), getId());
    }
}
