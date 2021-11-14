package com.example.personal_investment.domain.entities.transaction;

import com.example.personal_investment.domain.entities.brokerage_note.TransactionType;
import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
    Pontos a analisar com o grupo:
        A verificação de tipo de ação e tipo de carteira será feita apenas no UC de adicionar ação, correto?
        O sistema irá calcular o valor total da operação, esse cálculo será feito no UC ou na classe?
 */
public class Transaction {

    private TransactionType type;
    private Stock stock;
    private BigDecimal quantity;
    private LocalDate date;
    private BigDecimal amount;


    public Transaction(TransactionType type, Investment investment, BigDecimal quantity, LocalDate date) {
        this.type = type;
        this.stock = investment.getStock();
        this.quantity = quantity;
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", stock=" + stock +
                ", quantity=" + quantity +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
