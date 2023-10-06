package org.example.dao.impl;

import org.example.dao.MeetingCalendarDao;
import org.example.exception.MySQLException;
import org.example.model.MeetingCalendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MeetingCalendarDaoImpl implements MeetingCalendarDao {


    private Connection connection;

    public MeetingCalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MeetingCalendar createMeetingCalendar(String title, String username) {
        String query = "Insert into meeting_calendars (username, title) VALUES (?, ?)";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, title);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                String errorMessage = "Creating calendar failed, no rows affected.";
                throw new MySQLException(errorMessage);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int calendarId = generatedKeys.getInt(1);
                    return new MeetingCalendar(calendarId, username, title);
                } else {
                    String errorMessage = "Creating calendar failed, no ID obtained.";
                    throw new MySQLException(errorMessage);
                }
            }
        }catch (SQLException e) {
            throw new MySQLException("An error occurred while creating a calendar.", e);
        }
    }

    @Override
    public Optional<MeetingCalendar> findById(int id) {
        String selectQuery = "SELECT * FROM meeting_calendars WHERE id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String title = resultSet.getString("title");
                    return Optional.of(new MeetingCalendar(id, username, title));
                }
            }

        } catch (SQLException e) {
            String errorMessage = "Error occurred while finding MeetingCalendar by ID: " + id;
            throw new MySQLException(errorMessage, e);
        }

        return Optional.empty();
    }

    @Override
    public Collection<MeetingCalendar> findByUsername(String username) {
        String selectQuery = "SELECT * FROM meeting_calendars WHERE username = ?";
        List<MeetingCalendar> calendars = new ArrayList<>();

        try (

                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)
        ) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    calendars.add(new MeetingCalendar(id, username, title));
                }
            }

        } catch (SQLException e) {
            String errorMessage = "Error occurred while finding MeetingCalendars by username: " + username;
            throw new MySQLException(errorMessage, e);
        }
        return calendars;
    }

    @Override
    public Optional<MeetingCalendar> findByTitle(String title) {
        String selectQuery = "SELECT * FROM meeting_calendars WHERE title = ?";
        MeetingCalendar calendar = null;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, title);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    calendar = new MeetingCalendar(id, username, title);
                }
            }

        } catch (SQLException e) {
            String errorMessage = ("Error occurred while finding MeetingCalendar by title: " + title);
            throw new MySQLException(errorMessage, e);
        }

        return Optional.ofNullable(calendar);
    }

    @Override
    public boolean deleteCalendar(int id) {
        String query = "DELETE FROM meeting_calendars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            String errorMessage = "Error occurred while deleting MeetingCalendar by ID: " + id;
            throw new MySQLException(errorMessage, e);
        }
    }
}