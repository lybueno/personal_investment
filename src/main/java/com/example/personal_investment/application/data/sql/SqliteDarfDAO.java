package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteDarfDAO implements DarfDAO {

    private Darf resultSetToEntity(ResultSet rs) throws SQLException {

        return new Darf(
                rs.getString("id"),
                rs.getString("userName"),
                StockType.toEnum(rs.getString("stockType")),
                LocalDate.parse(rs.getString("dueDate")),
                rs.getBigDecimal("taxAmount"),
                rs.getBigDecimal("saleValue"),
                rs.getBigDecimal("averagePurchaseValue")
        );
    }

    @Override
    public String insert(Darf darf) {
        String sql = "INSERT INTO Darf (id, stockType, dueDate, taxAmount, saleValue, averagePurchaseValue, userName)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            stmt.setString(1, darf.getId());
            stmt.setString(2, darf.getStockType().toString());
            stmt.setString(3, darf.getDueDate().toString());
            stmt.setBigDecimal(4, darf.getTaxAmount());
            stmt.setBigDecimal(5, darf.getSaleValue());
            stmt.setBigDecimal(6, darf.getAveragePurchaseValue());
            stmt.setString(7, darf.getUserName());
            stmt.execute();

            return darf.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Darf> findOne(String key) {
        String sql = "SELECT * FROM Darf WHERE id = ?";
        Darf darf = null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                darf = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(darf);
    }

    @Override
    public List<Darf> findAll() {
        String sql = "SELECT * FROM Darf";
        List<Darf> darfs = new ArrayList<>();

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Darf darf = resultSetToEntity(rs);
                darfs.add(darf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return darfs;
    }

    @Override
    public void update(Darf darf) {
        String sql = "UPDATE Darf SET stockType = ?, dueDate = ?, taxAmount = ?, saleValue = ?, " +
                "averagePurchaseValue = ? WHERE id = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, darf.getStockType().toString());
            stmt.setString(2, darf.getDueDate().toString());
            stmt.setBigDecimal(3, darf.getTaxAmount());
            stmt.setBigDecimal(4, darf.getSaleValue());
            stmt.setBigDecimal(5, darf.getAveragePurchaseValue());
            stmt.setString(6, darf.getId());
            stmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Darf darf) {

        if(darf == null || darf.getId() == null){
            throw new IllegalArgumentException("Darf or Darf ID cannot be null.");
        }
        deleteById(darf.getId());
    }

    private void deleteById(String id) {
        String sql = "DELETE FROM Darf WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, id);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
