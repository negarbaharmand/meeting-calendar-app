package org.example.model;

import java.time.LocalDateTime;

public class Meeting {

    private int id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private MeetingCalendar calendar;

    // When we want to ask user to enter meeting information and get data from console no calendar needed (first they choose calendar)
    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    // Creating meeting
    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, String description, MeetingCalendar calendar) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.calendar = calendar;
    }


    // For fetching data
    public Meeting(int id, String title, LocalDateTime startTime, LocalDateTime endTime, String description, MeetingCalendar calendar) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.calendar = calendar;
    }

    // Getting a meeting with all the field except for calendar
    public Meeting(int id, String title, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public void setCalendar(MeetingCalendar calendar) {
        this.calendar = calendar;
    }

    public String getDescription() {
        return description;
    }

    public MeetingCalendar getCalendar() {
        return calendar;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String meetingInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Meeting Info:").append("\n");
        stringBuilder.append("Id:").append(id).append("\n");
        stringBuilder.append("Title:").append(title).append("\n");
        stringBuilder.append("Start time:").append(startTime).append("\n");
        stringBuilder.append("End time:").append(endTime).append("\n");
        stringBuilder.append("Description:").append(description).append("\n");
        return stringBuilder.toString();
    }

    private void timeValidation() {
        LocalDateTime currentTime = LocalDateTime.now();

        // Check if startTime is before endTime and both are in the future
        if (startTime.isBefore(endTime) && startTime.isAfter(currentTime) && endTime.isAfter(currentTime)) {
        } else {
            throw new IllegalArgumentException("Invalid meeting time");
        }
    }
}
