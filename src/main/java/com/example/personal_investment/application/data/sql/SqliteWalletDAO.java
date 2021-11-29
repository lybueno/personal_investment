package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.findUserUseCase;


public class SqliteWalletDAO implements WalletDAO {

    @Override
    public List<Investment> getIncome(Wallet wallet) {
        return null;
    }

    @Override
    public List<Wallet> findAllByUser(User user) {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM Wallet WHERE user = ?";
        return null;
    }

    @Override
    public String insert(Wallet wallet) {
        String sql = "INSERT INTO Wallet(id, name, user, stockType) VALUES (?, ?, ?, ?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, wallet.getId());
            stmt.setString(2, wallet.getName());
            stmt.setString(3, String.valueOf(wallet.getUser()));
            stmt.setString(4, String.valueOf(wallet.getType()));
            stmt.execute();
            return wallet.getId();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Wallet> findOne(String key) {
        return findOneByAttribute("id", key);
    }

    private Optional<Wallet> findOneByAttribute(String attribute, String value) {
        String sql = "SELECT * FROM Wallet WHERE " + attribute + " = ?";
        Wallet wallet = null;
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, value);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                wallet = resultSetToEntity(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(wallet);
    }

    @Override
    public List<Wallet> findAll() {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM Wallet WHERE user = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Wallet wallet = resultSetToEntity(rs);
                wallets.add(wallet);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return wallets;
    }

    private Wallet resultSetToEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("user");
        User user = findUserUseCase.findOneById(id).get();
        return new Wallet(
                rs.getString("id"),
                rs.getString("name"),
                StockType.toEnum(rs.getString("stockType")),
                user
        );
    }

    @Override
    public void update(Wallet type) {

    }

    @Override
    public void delete(Wallet type) {

    }
}
