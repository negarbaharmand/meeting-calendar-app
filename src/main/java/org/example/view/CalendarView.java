package org.example.view;

import org.example.model.Meeting;
import org.example.model.MeetingCalendar;
import org.example.model.User;
import org.example.util.ConsoleColors;

import java.util.List;

public interface CalendarView {

    default void displayMenu() {
        System.out.println("Calendar Options");
        System.out.println("0. Register");
        System.out.println("1. Login");
        System.out.println("2. Add Calendar");
        System.out.println("3. Add Meeting");
        System.out.println("4. Delete Calendar");
        System.out.println("5. Display Calendar Meeting");
        System.out.println("6. Logout");
        System.out.println("7. Exit");
        System.out.println("8. Enter your choice: ");

    }

    ;

    void displayUserInfo(User user);

    void displayCalendar(MeetingCalendar calendar);

    void displayMeetings(List<Meeting>  meetings);

    default void displayMessage(String message) {
        System.out.println(ConsoleColors.BLUE + message + ConsoleColors.RESET);
    }

    default void displaySuccessMessage(String message) {
        System.out.println(ConsoleColors.GREEN + message + ConsoleColors.RESET);
    }

    default void displayErrorMessage(String message) {
        System.out.println(ConsoleColors.RED + message + ConsoleColors.RESET);
    }

    default void displayWarningMessage(String message) {
        System.out.println(ConsoleColors.YELLOW + message + ConsoleColors.RESET);
    }

    String promoteString();

    Meeting promoteMeetingForm();

    String promoteCalendarForm();

    User promoteUserForm();
}
