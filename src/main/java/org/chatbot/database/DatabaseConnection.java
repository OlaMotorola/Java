package org.chatbot.database;

import java.sql.*;

public class DatabaseConnection implements IDatabaseConnection {
    private final Connection connection;

    public DatabaseConnection(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void addReservation(String customerName, String reservationTime, int numberOfGuests) throws SQLException {
        String sql = "INSERT INTO reservations (customer_name, reservation_time, number_of_guests) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, reservationTime);
            preparedStatement.setInt(3, numberOfGuests);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteReservation(int reservationId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public ResultSet listReservations() throws SQLException {
        String sql = "SELECT * FROM reservations";
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
