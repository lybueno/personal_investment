package com.example.personal_investment.application.data.sql;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

//    public void buildDatabaseIfMissing() {
//        if (isDatabaseMissing()) {
//            System.out.println("Database is missing. Building database: \n");
//            buildTables();
//        }
//    }

//    private boolean isDatabaseMissing() {
//        return !Files.exists(Paths.get("name_bd.db"));
//    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.createStatement()) {
            //statement.addBatch(createUserTable());
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
        builder.append("password TEXT NOT NULL, \n");

        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTableStock() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Stock (\n");
        // add attributes
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTableWallet() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Wallet (\n");
        // add attributes
        builder.append("FOREIGN KEY(user) REFERENCES User(id),\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTableInvestment() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Investment (\n");
        // add attributes
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTableStockTransaction() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS StockTransaction (\n");
        // add attributes
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTableDarf() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS Darf (\n");
        // add attributes
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
