package com.example.realtimestatisticsapi.transactions;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Transaction {

    private final UUID uuid = UUID.randomUUID();

    @NotNull
    @PastOrPresent
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss.sssZ")
    private final Date timestamp;

    @NotNull
    private final BigDecimal amount;

    public Transaction(Date timestamp, BigDecimal amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public UUID getId() {
        return uuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "timestamp=" + timestamp +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public long getTransactionAgeInSeconds(){
        if (timestamp == null){
            return 0;
        }
        return Instant.now().getEpochSecond() - timestamp.toInstant().getEpochSecond();
    }

}
