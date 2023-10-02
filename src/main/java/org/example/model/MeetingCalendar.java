package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class MeetingCalendar {
    private int id;
    private String title;
    private List<Meeting> meetings;
    private String username;

    // We can chain constructors when we have several constructors with duplicates
    // Used for creating Calendar
    public MeetingCalendar(String title, String username) {
        this(title);
        this.username = username;
    }

    // Used for finding a calendar by its title
    public MeetingCalendar(String title) {
        this.title = title;
    }

    // Used for fetching data from Calendar table

    public MeetingCalendar(int id, String title, String username) {
        this(title, username);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public List<Meeting> getMeetings() {
        if (this.meetings == null) meetings = new ArrayList<>();
        return meetings;
    }

    public void addMeeting(Meeting meeting) {
        if (this.meetings == null) meetings = new ArrayList<>();
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        if (meetings == null) throw new IllegalArgumentException("Meeting list is null.");
        if (meeting == null) throw new IllegalArgumentException("Meeting Data is null.");
        meetings.remove(meeting);
    }


    public String calendarInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calendar Info:").append("\n");
        stringBuilder.append("Id:").append(id).append("\n");
        stringBuilder.append("Title:").append(title).append("\n");
        stringBuilder.append("Username:").append(username).append("\n");
        return stringBuilder.toString();

    }
}
