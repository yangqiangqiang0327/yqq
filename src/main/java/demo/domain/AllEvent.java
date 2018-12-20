package demo.domain;/*
 * @program: FlinkTest
 * @Date: 2018/12/18 14:33
 * @Author: yqq
 * @Description:
 */

import java.util.Objects;

public class AllEvent {
    private String date;
    private double open;
    private double high;
    private double close;
    private double low;
    private double volime;
    private double price_change;
    private double p_change;
    private double ma5;
    private double ma10;
    private double ma20;
    private double v_ma5;
    private double v_ma10;
    private double v_ma20;
    private String ticket_code;

    @Override
    public String toString() {
        return "AllEvent{" +
                "date='" + date + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", close=" + close +
                ", low=" + low +
                ", volime=" + volime +
                ", price_change=" + price_change +
                ", p_change=" + p_change +
                ", ma5=" + ma5 +
                ", ma10=" + ma10 +
                ", ma20=" + ma20 +
                ", v_ma5=" + v_ma5 +
                ", v_ma10=" + v_ma10 +
                ", v_ma20=" + v_ma20 +
                ", ticket_code='" + ticket_code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllEvent)) return false;
        AllEvent allEvent = (AllEvent) o;
        return Double.compare(allEvent.getOpen(), getOpen()) == 0 &&
                Double.compare(allEvent.getHigh(), getHigh()) == 0 &&
                Double.compare(allEvent.getClose(), getClose()) == 0 &&
                Double.compare(allEvent.getLow(), getLow()) == 0 &&
                Double.compare(allEvent.getVolime(), getVolime()) == 0 &&
                Double.compare(allEvent.getPrice_change(), getPrice_change()) == 0 &&
                Double.compare(allEvent.getP_change(), getP_change()) == 0 &&
                Double.compare(allEvent.getMa5(), getMa5()) == 0 &&
                Double.compare(allEvent.getMa10(), getMa10()) == 0 &&
                Double.compare(allEvent.getMa20(), getMa20()) == 0 &&
                Double.compare(allEvent.getV_ma5(), getV_ma5()) == 0 &&
                Double.compare(allEvent.getV_ma10(), getV_ma10()) == 0 &&
                Double.compare(allEvent.getV_ma20(), getV_ma20()) == 0 &&
                Objects.equals(getDate(), allEvent.getDate()) &&
                Objects.equals(getTicket_code(), allEvent.getTicket_code());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDate(), getOpen(), getHigh(), getClose(), getLow(), getVolime(), getPrice_change(), getP_change(), getMa5(), getMa10(), getMa20(), getV_ma5(), getV_ma10(), getV_ma20(), getTicket_code());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVolime() {
        return volime;
    }

    public void setVolime(double volime) {
        this.volime = volime;
    }

    public double getPrice_change() {
        return price_change;
    }

    public void setPrice_change(double price_change) {
        this.price_change = price_change;
    }

    public double getP_change() {
        return p_change;
    }

    public void setP_change(double p_change) {
        this.p_change = p_change;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getV_ma5() {
        return v_ma5;
    }

    public void setV_ma5(double v_ma5) {
        this.v_ma5 = v_ma5;
    }

    public double getV_ma10() {
        return v_ma10;
    }

    public void setV_ma10(double v_ma10) {
        this.v_ma10 = v_ma10;
    }

    public double getV_ma20() {
        return v_ma20;
    }

    public void setV_ma20(double v_ma20) {
        this.v_ma20 = v_ma20;
    }

    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }
}
