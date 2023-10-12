package org.example.controller;

import org.example.dao.MeetingCalendarDao;
import org.example.dao.MeetingDao;
import org.example.dao.UserDao;
import org.example.exception.CalendarExceptionHandler;
import org.example.model.Meeting;
import org.example.model.MeetingCalendar;
import org.example.model.User;
import org.example.view.CalendarView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CalendarController {
    // The Controller class acts as a bridge between the model and the view It controls the data and transfer it to DAO layer.
    // **Dependency** injection for access functionalities inside another class:
    private CalendarView view;
    private UserDao userDao;
    private MeetingCalendarDao calendarDao;
    private MeetingDao meetingDao;
    //Class fields:
    private Boolean isLoggedIn;
    private String username;


    public CalendarController(CalendarView view, UserDao userDao, MeetingCalendarDao calendarDao, MeetingDao meetingDao) {
        this.view = view;
        this.userDao = userDao;
        this.calendarDao = calendarDao;
        this.meetingDao = meetingDao;
    }


    public void run() {
        while (true) {
            view.displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 0:
                    register();
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    createCalendar();
                    break;
                case 3:
                    createNewMeeting();
                    break;
                case 4:
                    deleteSelectedCalendar();
                    break;
                case 5:
                    displayCalendar();
                    break;
                case 6:
                    isLoggedIn = false;
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    view.displayWarningMessage("Invalid choice. Please select a valid option.");
            }
        }
    }

    private int getUserChoice() {
        String operationType = view.promoteString();
        int choice = -1;
        try {
            choice = Integer.parseInt(operationType);

        } catch (NumberFormatException e) {
            view.displayErrorMessage("Invalid input, please enter a number.");
        }
        return choice;
    }

    private void register() {
        view.displayMessage("Enter your username");
        String username = view.promoteString();
        User registeredUser = userDao.createUser(username);
        view.displayUserInfo(registeredUser);

    }

    private void login() {
        User user = view.promoteUserForm();
        try {
            isLoggedIn = userDao.authenticate(user);
            username = user.getUsername();
            view.displaySuccessMessage("Login successful.");
        } catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }
    }

    public void createCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to login first.");
            return;
        }
        String calendarTitle = view.promoteCalendarForm();
        MeetingCalendar createdMeetingCalendar = calendarDao.createMeetingCalendar(calendarTitle, username);
        view.displaySuccessMessage("Calendar created successfully. ;)");
        view.displayCalendar(createdMeetingCalendar);
    }

    public void createNewMeeting() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to login first.");
            return;
        }
        System.out.println("Available calendars: ");
        Collection<MeetingCalendar> calendars = calendarDao.findByUsername(username);
        for (MeetingCalendar calendar :
                calendars) {
            System.out.println("Calendar title: " + calendar.getTitle());
        }
        String calendarTitle = view.promoteCalendarForm();
        Optional<MeetingCalendar> meetingCalendarOptional = calendarDao.findByTitleAndUsername(calendarTitle, username);
        if (meetingCalendarOptional.isEmpty()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        Meeting newMeeting = view.promoteMeetingForm();
        newMeeting.setCalendar(meetingCalendarOptional.get());

        Meeting createdMeeting = new Meeting(
                newMeeting.getTitle(),
                newMeeting.getStartTime(),
                newMeeting.getEndTime(),
                newMeeting.getDescription(),
                newMeeting.getCalendar()
        );

        createdMeeting = meetingDao.createMeeting(createdMeeting);
        view.displaySuccessMessage("Meeting created successfully.");
        view.displayMeetings(Collections.singletonList(createdMeeting));

    }

    public void deleteSelectedCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to log in first.");
            return;
        }

        System.out.println("Choose title from available calendars: ");
        Collection<MeetingCalendar> calendars = calendarDao.findByUsername(username);
        for (MeetingCalendar calendar : calendars) {
            System.out.println("Title: " + calendar.getTitle());
        }

        String calendarTitle = view.promoteString();
        Optional<MeetingCalendar> meetingCalendarOptional = calendarDao.findByTitleAndUsername(calendarTitle, username);

        if (meetingCalendarOptional.isEmpty()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        MeetingCalendar meetingCalendar = meetingCalendarOptional.get();

        // Delete associated meetings first
        for (Meeting meeting : meetingDao.findAllMeetingByCalendarId(meetingCalendar.getId())) {
            meetingDao.deleteMeeting(meeting.getId());
        }

        boolean isDeleted = calendarDao.deleteCalendar(meetingCalendar.getId());

        if (isDeleted) {
            view.displaySuccessMessage("Calendar deleted successfully.");
        } else {
            view.displayWarningMessage("Failed to delete the calendar.");
        }
    }

    public void displayCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to log in first.");
            return;
        }

        System.out.println("Choose a calendar to display: ");
        Collection<MeetingCalendar> calendars = calendarDao.findByUsername(username);
        for (MeetingCalendar calendar : calendars) {
            System.out.println("Title: " + calendar.getTitle());
        }

        String calendarTitle = view.promoteString();
        Optional<MeetingCalendar> meetingCalendarOptional = calendarDao.findByTitleAndUsername(calendarTitle, username);

        if (meetingCalendarOptional.isEmpty()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        MeetingCalendar selectedCalendar = meetingCalendarOptional.get();

        view.displayCalendar(selectedCalendar);

        List<Meeting> meetings = meetingDao.findAllMeetingByCalendarId(selectedCalendar.getId());
        view.displayMeetings(meetings);
    }
}
