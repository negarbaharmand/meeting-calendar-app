package org.example;

import org.example.controller.CalendarController;
import org.example.dao.MeetingCalendarDao;
import org.example.dao.MeetingDao;
import org.example.dao.UserDao;
import org.example.dao.impl.MeetingCalendarDaoImpl;
import org.example.dao.impl.MeetingDaoImpl;
import org.example.dao.impl.UserDaoImpl;
import org.example.dao.impl.db.MeetingCalendarDbConnection;
import org.example.view.CalendarConsoleUI;
import org.example.view.CalendarView;

import java.sql.Connection;

public class App
{
    public static void main( String[] args )
    {
        System.setProperty("log4j.configurationFile", "log4j2.properties");
        Connection mysqlConnection = MeetingCalendarDbConnection.getConnection();
        CalendarView view = new CalendarConsoleUI();
        UserDao userDao = new UserDaoImpl(mysqlConnection);
        MeetingCalendarDao calendarDao = new MeetingCalendarDaoImpl(mysqlConnection);
        MeetingDao meetingDao = new MeetingDaoImpl(mysqlConnection);
        CalendarController controller = new CalendarController(view, userDao,calendarDao, meetingDao);
        controller.run();
    }
}
