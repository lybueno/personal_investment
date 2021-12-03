package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.user.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.searchWalletUC;

public class SqliteUserDAO implements UserDAO {

    @Override
    public String insert(User user) {
        String sql = "INSERT INTO User(username, password) values (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();
            int generatedId = resultSet.getInt(1);
            return user.getUsername();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<User> findOne(String key) {
        String sql = "SELECT * FROM User WHERE id = ?";
        User user = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(key));
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user = resultSetToEntity(resultSet);
                List<Wallet> wallets = searchWalletUC.findWalletByUser(user);
                user.setWallets(wallets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }


    private User resultSetToEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        return new User(id, username, password);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User user = resultSetToEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE User SET username = ?, password = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {

        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and user ID must not be null.");
        } else {
            String sql = "DELETE FROM User WHERE id = ?";

            try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
                stmt.setString(1, user.getId());
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        User user = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user = resultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
}
