package org.example.dao;

import org.example.model.Meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingDao {
    Meeting createMeeting(Meeting meeting);
    Optional<Meeting> findById(int meetingId);
    List<Meeting> findAllMeetingByCalendarId(int calendarId);
    boolean deleteMeeting(int meetingId);
    // todo: Add method for updating meetings as needed

}
