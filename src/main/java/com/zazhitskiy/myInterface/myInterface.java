package com.zazhitskiy.myInterface;

import com.zazhitskiy.database.ConnectionToDB;
import com.zazhitskiy.exception.NotMappingException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.zazhitskiy.constants.Messages.NO_MAPPING_FOR;
import static com.zazhitskiy.constants.Parameters.*;
import static com.zazhitskiy.constants.Query.*;

public class myInterface {
    private Map<String, String> menu;
    private Map<String, Printable> methodsMenu;
    private static Scanner input = new Scanner(System.in);

    public myInterface() {
        menu = new LinkedHashMap<>();
        menu.put("1", "  1 - Show all tables in my DataBase");
        menu.put("2", "  2 - Show structure of all tables in my DataBase");
        menu.put("3", "  3 - Show query result with alias and math actions");
        menu.put("4", "  4 - Show query result with filter");
        menu.put("5", "  5 - Show query result with 'AND', 'OR', 'NOT' operators");
        menu.put("6", "  6 - Show query result with sorting information in DataBase");
        menu.put("7", "  7 - Add data to table clients");
        menu.put("8", "  8 - Add data to table cars");
        menu.put("9", "  9 - Update data in dealers table");
        menu.put("10", " 10 - Update data in clients table");
        menu.put("0", "  0 - exit");

        methodsMenu = new LinkedHashMap<>();
        methodsMenu.put("1", this::pressButton1);
        methodsMenu.put("2", this::pressButton2);
        methodsMenu.put("3", this::pressButton3);
        methodsMenu.put("4", this::pressButton4);
        methodsMenu.put("5", this::pressButton5);
        methodsMenu.put("6", this::pressButton6);
        methodsMenu.put("7", this::pressButton7);
        methodsMenu.put("8", this::pressButton8);
        methodsMenu.put("9", this::pressButton9);
        methodsMenu.put("10", this::pressButton10);
    }


    private void showResult(String query, String... parameters) {
        Connection connection = ConnectionToDB.getInstance().getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                for (String parameter : parameters) {
                    System.out.print(" " + resultSet.getString(parameter));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pressButton1() {
        System.out.println("Tables :");
        showResult(SHOW_ALL_TABLES, TABLE_NAME);
    }

    private void pressButton2() {
        System.out.println("Table name; column name; data type; is nullable");
        showResult(SHOW_STRUCTURE_OF_ALL_TABLES, TABLE_NAME, COLUMN_NAME, DATA_TYPE, IS_NULLABLE);
    }

    private void pressButton3() {
        System.out.println("Min price: ");
        showResult(QUERY_WITH_ALIAS, MIN_PRICE);
    }

    private void pressButton4() {
        System.out.println("Show all cars which price > 80000");
        showResult(QUERY_WITH_FILTER, BRAND, MODEL, PRICE);
    }

    private void pressButton5() {

        showResult(QUERY_WITH_OPERATOR_AND, NAME, COUNTRY, BUDGET);
    }

    private void pressButton6() {

        showResult(QUERY_WITH_SORTING, SURNAME, RATE);
    }

    private void pressButton7() {
        insertData(INSERT_INTO_CLIENTS, 13, "Oksana", "Adamivna", "Ukraine", "Lviv", "38065432176", 50000);
        System.out.println("Successfully insert data in table clients");
    }

    private void pressButton8() {
        insertData(INSERT_INTO_CARS, 14, "SUV", "Toyota", "HighLander", "Japan", "AUTO", 2015, 24500, 40000);
        System.out.println("Successfully insert data in table cars");
    }

    private void pressButton9() {
        insertData(UPDATE_DEALERS, 5, 2);
        System.out.println("Successfully updated data in table dealers");
    }


    private void pressButton10() {
        insertData(UPDATE_CLIENTS, 76879073, 1);
        System.out.println("Successfully updated data in table clients");
    }

    private void insertData(String query, Object... parameters) {
        Connection connection = ConnectionToDB.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            insertParameters(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertParameters(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] == null) {
                    statement.setNull(i + 1, Types.NULL);
                } else if (parameters[i] instanceof Integer) {
                    statement.setInt(i + 1, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    statement.setLong(i + 1, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    statement.setString(i + 1, (String) parameters[i]);
                } else {
                    throw new NotMappingException(NO_MAPPING_FOR + parameters[i].getClass());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void outputMenu() {
        System.out.println("\nMENU:");
        for (String str : menu.values()) {
            System.out.println(str);
        }
    }

    public void show() {
        String keyMenu;
        do {
            outputMenu();
            System.out.println("Please, choose what you want to do.");
            keyMenu = input.nextLine().toUpperCase();
            try {
                methodsMenu.get(keyMenu).print();
            } catch (Exception ignored) {
            }
        } while (!keyMenu.equals("0"));
    }
}

