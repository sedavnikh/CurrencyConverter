package org.converter.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversion_history")
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id") // User could be removed, but we keep the history
    private Long userId;

    @Column(name = "currency_from", nullable = false)
    private String currencyFrom;

    @Column(name = "currency_to", nullable = false)
    private String currencyTo;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "historical_date")
    private String historicalDate;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public ConversionHistory() {
    }

    public ConversionHistory(Long userId, String currencyFrom, String currencyTo, String amount, String result, String historicalDate, Date timestamp) {
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
        this.result = result;
        this.historicalDate = historicalDate;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHistoricalDate() {
        return historicalDate;
    }

    public void setHistoricalDate(String historicalDate) {
        this.historicalDate = historicalDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
