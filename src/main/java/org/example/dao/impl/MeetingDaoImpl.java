package org.example.dao.impl;

import org.example.dao.MeetingDao;
import org.example.exception.MySQLException;
import org.example.model.Meeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MeetingDaoImpl implements MeetingDao {
    public MeetingDaoImpl() {
        super();
    }

    private Connection connection;

    public MeetingDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        String query = "INSERT INTO meetings(title, start_time, end_time, _description, calendar_id) VALUES(?,?,?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, meeting.getTitle());
            preparedStatement.setDate(2, java.sql.Date.valueOf(meeting.getStartTime().toLocalDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(meeting.getEndTime().toLocalDate()));
            preparedStatement.setString(4, meeting.getDescription());
            preparedStatement.setInt(5, meeting.getCalendar().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new MySQLException("Creating meeting failed, no rows affected.");
            }
            return meeting;

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while creating meeting: " + meeting, e);
        }
    }

    @Override
    public Optional<Meeting> findById(int meetingId) {
        String query = "Select * from meetings where id = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, meetingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                String description = resultSet.getString("_description");
                int calendarId = resultSet.getInt("calendar_id");
                Meeting meeting = new Meeting(id, title, startTime, endTime, description);
                return Optional.of(meeting);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while finding meeting by ID: " + meetingId, e);
        }
    }

    @Override
    public Collection<Meeting> findAllMeetingByCalendarId(int calendarId) {
        return null;
    }

    @Override
    public boolean deleteMeeting(int meetingId) {
        return false;
    }
}
