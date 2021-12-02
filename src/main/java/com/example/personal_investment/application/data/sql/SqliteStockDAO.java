package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.usecases.stock.StockDAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteStockDAO implements StockDAO {

    @Override
    public Optional<Stock> findById(String id) {
        return findOneByAttribute("id", id);
    }

    @Override
    public Optional<Stock> findByTicker(String ticker) {
        return findOneByAttribute("ticker", ticker);
    }

    @Override
    public List<Stock> findByCNPJ(String cnpj) {
        return findAllByAttribute("cnpj", cnpj);
    }

    @Override
    public List<Stock> findByCompanyName(String name) {
        return findAllByAttribute("companyName", name);
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Stock WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            stmt.setString(1, id);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String insert(Stock stock) {
        String sql = "INSERTT INTO Stock(id, stockType, ticker, companyName, cnpj, stockQuote) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, stock.getId());
            stmt.setString(2, String.valueOf(stock.getType()));
            stmt.setString(3, stock.getTicker());
            stmt.setString(4, stock.getCompanyName());
            stmt.setString(5, stock.getCnpj());
            stmt.setBigDecimal(6, stock.getStockQuote());

            stmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Stock> findOne(String key) {
        return findOneByAttribute("id", key);
    }

    private Optional<Stock> findOneByAttribute(String attribute, String key) {
        String sql = "SELECT * FROM Stock WHERE " + attribute + " = ?";
        Stock stock = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                stock = resultSetToEntity(rs);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(stock);
    }

    public List<Stock> findAllByAttribute(String attribute, String value) {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock WHERE " + attribute + " = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            stmt.setString(1, value);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Stock stock = resultSetToEntity(resultSet);
                stocks.add(stock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    private Stock resultSetToEntity(ResultSet rs) throws SQLException{
        String id = rs.getString("id");
        StockType stockType = StockType.toEnum(rs.getString("stockType"));
        String ticker = rs.getString("ticker");
        String companyName = rs.getString("companyName");
        String cnpj = rs.getString("cnpj");
        BigDecimal stockQuote = rs.getBigDecimal("stockQuote");

        return new Stock(id, stockType, ticker, companyName,cnpj, stockQuote);
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Stock stock = resultSetToEntity(resultSet);
                stocks.add(stock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    @Override
    public void update(Stock stock) {
        String sql = "UPDATE Stock WHERE stockType = ?, ticker = ?, companyName = ?," +
                " cnpj = ?, stockQuote = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, String.valueOf(stock.getType()));
            stmt.setString(2, stock.getTicker());
            stmt.setString(3, stock.getCompanyName());
            stmt.setString(4, stock.getCnpj());
            stmt.setBigDecimal(5, stock.getStockQuote());
            stmt.setString(6, stock.getId());

            stmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Stock stock) {
        if(stock == null || stock.getId() == null){
            throw new IllegalArgumentException("Stock or stock ID cannot be null");
        }
        deleteById(stock.getId());
    }
}
