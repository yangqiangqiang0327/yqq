package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 15:19
 * @Author: yqq
 * @Description:
 */

import java.util.Objects;

public class Echo {
    private double price_change;
    private String ticket_code;

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

    @Override
    public String toString() {
        return "Echo{" +
                "price_change=" + price_change +
                ", ticket_code='" + ticket_code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Echo)) return false;
        Echo echo = (Echo) o;
        return Double.compare(echo.getPrice_change(), getPrice_change()) == 0 &&
                Objects.equals(getTicket_code(), echo.getTicket_code());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrice_change(), getTicket_code());
    }
}
