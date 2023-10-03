package org.example.exception;

import org.example.util.ConsoleColors;

public class CalendarExceptionHandler {
    public static void handleException(Exception exception) {
        if (exception instanceof AuthenticationFailedException) {
            System.out.println(ConsoleColors.RED + exception.getMessage() + ConsoleColors.RESET);
        } else if (exception instanceof UserExpiredException) {
            System.out.println(ConsoleColors.YELLOW + exception.getMessage() + ConsoleColors.RESET);
        } else if (exception instanceof DBConnectionException) {
            System.out.println(exception.getMessage());
        } else if (exception instanceof MySQLException) {
            System.out.println(exception.getMessage());
        } else {
            System.out.println("An unexpected error occurred.");
            exception.printStackTrace();
        }
    }
}
