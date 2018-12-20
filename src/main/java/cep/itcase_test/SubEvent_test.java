package cep.itcase_test;/*
 * @program: FlinkTest
 * @Date: 2018/12/11 17:11
 * @Author: yqq
 * @Description:
 */

import cep.itcase.SubEvent;

import java.util.Objects;

public class SubEvent_test extends Event_test {
    private final double volume;

    public SubEvent_test(int id,String name,double price,double volume ){
        super(id,name,price);
        this.volume=volume;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubEvent_test)) return false;
        if (!super.equals(o)) return false;
        SubEvent_test that = (SubEvent_test) o;
        return Double.compare(that.getVolume(), getVolume()) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getVolume());
    }
}
