package org.example;

import java.sql.*;
import java.util.Objects;

class User{
    private String email, password, school, name_of_school, grade, location, name, username, schoolData;

    public User(String email, String password, String school, String name_of_school,
                String grade, String location, String name, String username, String schoolData) {
        this.email = email;
        this.password = password;
        this.school = school;
        this.name_of_school = name_of_school;
        this.grade = grade;
        this.location = location;
        this.name = name;
        this.username = username;
        this.schoolData = schoolData;
    }
    // Unnecessary stuff from here{
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getNameOfSchool() {
        return name_of_school;
    }

    public void setNameOfSchool(String name_of_school) {
        this.name_of_school = name_of_school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSchoolData() {
        return schoolData;
    }

    public void setSchoolData(String schoolData) {
        this.schoolData = schoolData;
    }
    // }to here
}

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

    }



    public static void createCourse(User user) throws SQLException {
        User currentUser = getUserProperties();
        boolean is_stuff = stuffChecker(currentUser.getName(), currentUser.getPassword());
        if(is_stuff){

        }else {
            System.out.println("У вас нет прав для использования этой функции");
        }
    }

    private static boolean stuffChecker(String name, String password) {
        //TODO should be completed
        return Objects.equals(name, "Dinara") && Objects.equals(password, "12345678");
    }

    public static User getUserProperties() throws SQLException {
        //logging into the database
        String url = "jdbc:mysql://localhost:3306/project";
        String uname = "root";
        String pass = "E869608b";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(url, uname, pass);
        Statement statement = connection.createStatement();
        //end of the logining into

        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        // TODO "columnLabels" should be more specific to find a specific user
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String school = resultSet.getString("school");
        String name_of_school = resultSet.getString("name_of_school");
        String grade = resultSet.getString("grade");
        String location = resultSet.getString("location");
        String name = resultSet.getString("name");
        String username = resultSet.getString("username");
        String schoolData = resultSet.getString("schoolData");

        resultSet.close();
        statement.close();

        return new User(email,password,school,name_of_school,grade,location,name,username,schoolData);
    }
}
