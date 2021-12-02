package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;
import javafx.scene.layout.BackgroundImage;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.searchStockUC;
import static com.example.personal_investment.application.main.Main.searchWalletUC;


public class SqliteStockTransactionDAO implements BrokerageNoteDAO {

    @Override
    public Optional<StockTransaction> findById(String id) {
        return findOneByAttribute("id", id);
    }

    @Override
    public Optional<StockTransaction> findByStock(String ticker) {
        return findOneByAttribute("ticker", ticker);
    }

    private Optional<StockTransaction> findOneByAttribute(String attribute, String value) {

        String sql = "SELECT * FROM StockTransaction WHERE " + attribute + " = ?";
        StockTransaction stockTransaction = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                stockTransaction = resultSetToEntity(rs);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(stockTransaction);

    }

    private StockTransaction resultSetToEntity(ResultSet rs) throws SQLException{

        String id = rs.getString("id");

        String walletId = rs.getString("wallet");
        Wallet wallet = searchWalletUC.findById(walletId).get();

        String stockId = rs.getString("stock");
        Stock stock = searchStockUC.findById(stockId).get();

        String transactionDate = rs.getString("transactionDate");
        LocalDate convertedDate = null;
        if(transactionDate != null){
            convertedDate = LocalDate.parse(transactionDate);
        }

        Integer quantity = rs.getInt("quantity");
        BigDecimal unitaryValue = rs.getBigDecimal("unitaryValue");

        return new StockTransaction(id, stock, wallet, convertedDate, quantity,
                unitaryValue, TransactionType.toEnum(rs.getString("stockType")));
    }


    @Override
    public List<StockTransaction> findTransactionsBetween(LocalDate initialDate, LocalDate finalDate) {
        return null;
    }

    @Override
    public String insert(StockTransaction type) {
        return null;
    }

    @Override
    public Optional<StockTransaction> findOne(String key) {
        return Optional.empty();
    }

    @Override
    public List<StockTransaction> findAll() {
        return null;
    }

    @Override
    public void update(StockTransaction type) {

    }

    @Override
    public void delete(StockTransaction type) {

    }
}
