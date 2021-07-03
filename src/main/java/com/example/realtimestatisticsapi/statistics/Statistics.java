package com.example.realtimestatisticsapi.statistics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Statistics {

    private final static int SCALE = 2;
    private final static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private long count;
    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;

    public Statistics() {
        count = 0;
        sum = BigDecimal.ZERO;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum.setScale(SCALE, ROUNDING_MODE);
    }

    public BigDecimal getAvg() {
        if (count == 0){
            return BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);
        }
        return sum.divide(BigDecimal.valueOf(count), SCALE, ROUNDING_MODE);
    }

    public BigDecimal getMax() {
        if (max == null){
            return null;
        }
        return max.setScale(SCALE, ROUNDING_MODE);
    }

    public BigDecimal getMin() {
        if (min == null){
            return null;
        }
        return min.setScale(SCALE, ROUNDING_MODE);
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public synchronized void update(BigDecimal value){
        updateCount();
        updateSum(value);
        updateMax(value);
        updateMin(value);
    }

    public synchronized void updateCount(){
        count++;
    }

    public synchronized void updateSum(BigDecimal value){
        sum = sum.add(value);
    }

    public synchronized void updateMax(BigDecimal value){
        if (max == null || value.compareTo(max) > 0){
            max = value;
        }
    }

    public synchronized void updateMin(BigDecimal value){
        if (min == null || value.compareTo(min) < 0){
            min = value;
        }
    }

    public void reset(){
        count = 0;
        sum = BigDecimal.ZERO;
        max = null;
        min = null;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "count=" + count +
                ", sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
