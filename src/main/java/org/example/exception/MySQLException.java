package org.example.exception;

import java.sql.SQLException;

public class MySQLException extends SQLException {
    public MySQLException(String reason) {
        super(reason);
    }

    public MySQLException(String reason, String SQLState) {
        super(reason, SQLState);
    }
}
