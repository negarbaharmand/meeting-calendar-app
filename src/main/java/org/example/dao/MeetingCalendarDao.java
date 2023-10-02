package org.example.dao;

import org.example.model.MeetingCalendar;

import java.util.Collection;
import java.util.Optional;

public interface MeetingCalendarDao {
    MeetingCalendar createMeetingCalendar(String title, String username);
    Optional<MeetingCalendar> findById(int id);
    Collection<MeetingCalendar> findByUsername(String username);
    Optional<MeetingCalendar> findByTitle(String title);
    boolean deleteCalendar(int id);
    // todo: Add method for updating calendars as needed

}
