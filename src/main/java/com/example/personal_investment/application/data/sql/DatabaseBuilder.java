package com.example.personal_investment.application.data.sql;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.createStatement()) {
            statement.addBatch(createUserTable());
            statement.addBatch(createStockTypeTable());
            statement.addBatch(createStockTable());
            statement.addBatch(createWalletTable());
            statement.addBatch(createUserWalletsTable());
            statement.addBatch(createInvestmentTable());
            statement.addBatch(createPortfolioInvestmentsTable());
            statement.addBatch(createTransactonTypeTable());
            statement.addBatch(createStockTransactionTable());
            statement.addBatch(createDarfTable());
            statement.executeBatch();

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String createUserTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS User (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("username TEXT NOT NULL, \n");
        builder.append("password TEXT NOT NULL\n");

        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createStockTypeTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS StockType (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("name TEXT NOT NULL\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createStockTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Stock (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("stockType TEXT NOT NULL, \n");
        builder.append("ticker TEXT NOT NULL UNIQUE, \n");
        builder.append("companyName TEXT NOT NULL, \n");
        builder.append("cnpj TEXT NOT NULL, \n");
        builder.append("stockQuote NUMERIC NOT NULL, \n");
        builder.append("FOREIGN KEY(stockType) REFERENCES StockType(name)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createWalletTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Wallet (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("user INTEGER NOT NULL, \n");
        builder.append("stockType TEXT NOT NULL, \n");
        builder.append("FOREIGN KEY(user) REFERENCES User(id),\n");
        builder.append("FOREIGN KEY(stockType) REFERENCES StockType(name)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createUserWalletsTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS UserWallets (\n");
        builder.append("user INTEGER NOT NULL, \n");
        builder.append("wallet TEXT NOT NULL, \n");
        builder.append("PRIMARY KEY(user, wallet), \n");
        builder.append("FOREIGN KEY(user) REFERENCES User(id),\n");
        builder.append("FOREIGN KEY(wallet) REFERENCES Wallet(id)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createInvestmentTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Investment (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("wallet TEXT NOT NULL, \n");
        builder.append("stock TEXT NOT NULL, \n");
        builder.append("quantity INTEGER NOT NULL, \n");
        builder.append("totalAmount NUMERIC NOT NULL, \n");
        builder.append("FOREIGN KEY(wallet) REFERENCES Wallet(id),\n");
        builder.append("FOREIGN KEY(stock) REFERENCES Stock(id)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createPortfolioInvestmentsTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS PortfolioInvestments (\n");
        builder.append("wallet TEXT NOT NULL, \n");
        builder.append("investment TEXT NOT NULL, \n");
        builder.append("PRIMARY KEY(wallet, investment), \n");
        builder.append("FOREIGN KEY(wallet) REFERENCES Wallet(id),\n");
        builder.append("FOREIGN KEY(investment) REFERENCES Investment(id)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTransactonTypeTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS TransactionType (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("name TEXT NOT NULL\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createStockTransactionTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS StockTransaction (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("wallet TEXT NOT NULL, \n");
        builder.append("stock TEXT NOT NULL, \n");
        builder.append("transactionDate DATETIME NOT NULL, \n");
        builder.append("quantity INTEGER NOT NULL, \n");
        builder.append("unitaryValue NUMERIC NOT NULL, \n");
        builder.append("transactionType TEXT NOT NULL, \n");
        builder.append("FOREIGN KEY(wallet) REFERENCES Wallet(id),\n");
        builder.append("FOREIGN KEY(stock) REFERENCES Stock(id),\n");
        builder.append("FOREIGN KEY(transactionType) REFERENCES TransactionType(name)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createDarfTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Darf (\n");
        builder.append("id TEXT PRIMARY KEY, \n");
        builder.append("transactionType TEXT NOT NULL, \n");
        builder.append("dueDate DATETIME NOT NULL, \n");
        builder.append("taxAmount NUMERIC NOT NULL, \n");
        builder.append("saleValue NUMERIC NOT NULL, \n");
        builder.append("averagePurchaseValue NUMERIC NOT NULL, \n");
        builder.append("FOREIGN KEY(transactionType) REFERENCES TransactionType(name)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
