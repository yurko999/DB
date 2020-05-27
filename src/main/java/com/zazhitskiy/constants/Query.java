package com.zazhitskiy.constants;

public interface Query {
    String SHOW_ALL_TABLES = "select table_name from information_schema.tables;";
    String SHOW_STRUCTURE_OF_ALL_TABLES = "SELECT table_name, column_name, data_type, " +
            "is_nullable from information_schema.columns";
    String QUERY_WITH_ALIAS = "select min(price) as min_price from cars;";
    String QUERY_WITH_FILTER = "select brand, model, price from cars where price > 80000;";
    String QUERY_WITH_OPERATOR_AND = "select name, country, budget from clients where country = 'Ukraine' and budget < 100000;";
    String QUERY_WITH_SORTING = "select  surname, rate from dealers order by rate;";
    String INSERT_INTO_CLIENTS = "insert into clients (id,name, surname, country, city, number, budget)" +
            "values (?, ?, ?, ?, ?, ?, ?);";
    String INSERT_INTO_CARS = "insert into cars (id, type, brand, model, countryOFmanufactory, gearbox,year, mileage,price) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    String UPDATE_DEALERS = "update dealers set rate = ? where id = ?;";
    String UPDATE_CLIENTS = "update clients set number = ? where id = ?;";
}
