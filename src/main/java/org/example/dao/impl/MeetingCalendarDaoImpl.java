package org.example.dao.impl;

import org.example.dao.MeetingCalendarDao;
import org.example.model.MeetingCalendar;

import java.util.Collection;
import java.util.Optional;

public class MeetingCalendarDaoImpl implements MeetingCalendarDao {
    public MeetingCalendarDaoImpl() {
        super();
    }

    @Override
    public MeetingCalendar createMeetingCalendar(String title, String username) {
        return null;
    }

    @Override
    public Optional<MeetingCalendar> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<MeetingCalendar> findByUsername(String username) {
        return null;
    }

    @Override
    public Optional<MeetingCalendar> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCalendar(int id) {
        return false;
    }
}
