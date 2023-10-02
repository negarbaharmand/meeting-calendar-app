package org.example.dao.imp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MeetingCalendarDbConnection {
    private static final String DB_NAME = "meeting_calendar_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "negar";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME ;


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
        } catch (SQLException e) {
    throw new DBConnectionException("Failed to connect to DB (" + DB_NAME + ")", e);
            }
    }
}
