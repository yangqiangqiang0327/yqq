package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 14:40
 * @Author: yqq
 * @Description:
 */

import java.util.Objects;

public class TestEvent {
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestEvent)) return false;
        TestEvent testEvent = (TestEvent) o;
        return Objects.equals(getDate(), testEvent.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDate());
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "date='" + date + '\'' +
                '}';
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
