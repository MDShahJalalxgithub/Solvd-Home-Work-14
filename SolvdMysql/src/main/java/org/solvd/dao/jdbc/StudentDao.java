package org.solvd.dao.jdbc;

import org.solvd.dao.IStudentDao;
import org.solvd.model.BasicConnectionPool;
import org.solvd.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements IStudentDao {

    static BasicConnectionPool connectionPool;

    static {
        try {
            connectionPool = BasicConnectionPool.create();
        } catch (Exception e) {
            System.out.println("Error creating connection pool: " + e.getMessage());
            connectionPool = null;
        }
    }

    @Override
    public Student getEntityById(int index) {
        if (connectionPool == null) {
            throw new IllegalStateException("Connection pool is not initialized.");
        }

        Student student = null;
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM mydb.Students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, index);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setFirstName(resultSet.getString("first_name"));
                student.setCarId(resultSet.getInt("Cars_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return student;
    }

    @Override
    public List<Student> getEntities() {
        if (connectionPool == null) {
            throw new IllegalStateException("Connection pool is not initialized.");
        }

        List<Student> students = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM mydb.Students";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setFirstName(resultSet.getString("first_name"));
                student.setCarId(resultSet.getInt("Cars_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return students;
    }

    @Override
    public void insert(Student student) {
        if (connectionPool == null) {
            throw new IllegalStateException("Connection pool is not initialized.");
        }

        String query = "INSERT INTO mydb.Students (first_name, Cars_id) VALUES (?, ?)";
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setInt(2, student.getCarId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void delete(int index) {
        if (connectionPool == null) {
            throw new IllegalStateException("Connection pool is not initialized.");
        }

        String query = "DELETE FROM mydb.Students WHERE id = ?";
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, index);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void update(int index, Student student) {
        if (connectionPool == null) {
            throw new IllegalStateException("Connection pool is not initialized.");
        }

        String query = "UPDATE mydb.Students SET first_name = ?, Cars_id = ? WHERE id = ?";
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setInt(2, student.getCarId());
            preparedStatement.setInt(3, index);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}