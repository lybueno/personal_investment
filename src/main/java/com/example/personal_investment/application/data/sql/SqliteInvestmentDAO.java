package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.stock_transaction.InvestmentsDAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.*;

public class SqliteInvestmentDAO implements InvestmentsDAO {

    private Investment resultSetToEntity(ResultSet rs) throws SQLException {

        String id = rs.getString("id");
        String walletId = rs.getString("wallet");
        Wallet wallet = searchWalletUC.findById(walletId).get();
        String stockId = rs.getString("stock");
        Stock stock = searchStockUC.findById(stockId).get();
        Integer quantity = rs.getInt("quantity");
        BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
        return new Investment(id, wallet, stock, quantity,totalAmount);
    }

    private Optional<Investment> findOneByAttribute(String attribute, String value) {
        String sql = "SELECT * FROM Investment WHERE " + attribute + " = ?";
        Investment investment = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                investment = resultSetToEntity(rs);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(investment);
    }

    @Override
    public Optional<Investment> findOneByTicker(String ticker) {
        String sql = "select i.id, i.wallet, i.stock, i.quantity, i.totalAmount from Stock s, Investment i WHERE s.id = i.stock AND s.ticker = ?";
        Investment investment = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, ticker);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                investment = resultSetToEntity(rs);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(investment);
    }


    @Override
    public Optional<Investment> findById(String id) {
        return findOneByAttribute("id", id);
    }


    public List<Investment> findAllByWallet(String walletId) {
        List<Investment> investments = new ArrayList<>();
        String sql = "SELECT * FROM Investment WHERE wallet = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, walletId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Investment investment = resultSetToEntity(rs);
                investments.add(investment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investments;
    }


    @Override
    public String insert(Investment investment) {
        String sql = "INSERT INTO Investment(id, wallet, stock, quantity,totalAmount) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, investment.getId());
            stmt.setString(2, investment.getWallet().getId());
            stmt.setString(3, investment.getStock().getId());
            stmt.setInt(4, investment.getQuantity());
            stmt.setBigDecimal(5, investment.getTotalAmount());
            stmt.execute();
            return investment.getId();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Investment> findOne(String key) {
        return findOneByAttribute("id", key);
    }

    @Override
    public List<Investment> findAll() {

        List<Investment> investments = new ArrayList<>();
        String sql = "SELECT * FROM Investment";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Investment investment = resultSetToEntity(rs);
                investments.add(investment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investments;
    }

    @Override
    public void update(Investment investment) {
        String sql = "UPDATE Investment SET wallet = ?, stock = ?, quantity = ?,totalAmount =? WHERE id = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, investment.getWallet().getId());
            stmt.setString(2, investment.getStock().getId());
            stmt.setInt(3, investment.getQuantity());
            stmt.setBigDecimal(4, investment.getTotalAmount());
            stmt.setString(5, investment.getId());
            stmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Investment investment) {
        if(investment == null || investment.getId() == null){
            throw new IllegalArgumentException("Investment or investment ID cannot be null.");
        }
        deleteByKey(investment.getId());
    }

    private void deleteByKey(String key) {
        String sql = "DELETE FROM Investment WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            stmt.setString(1, key);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
