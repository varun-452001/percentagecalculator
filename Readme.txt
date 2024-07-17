Here's a simplified version of the core components for your quiz platform. This will include basic code for user registration and login, quiz creation, and taking a quiz. This is a basic outline and should be expanded with proper error handling, security features, and more functionality as per your requirements.

### Project Structure
```
src
│   Main.java
│
├───auth
│       Login.java
│       Register.java
│
├───db
│       DatabaseConnection.java
│       UserDAO.java
│       QuizDAO.java
│
├───models
│       User.java
│       Quiz.java
│       Question.java
│
├───quiz
│       QuizDashboard.java
│       QuizScreen.java
│
└───utils
        Utils.java
```

### 1. DatabaseConnection.java
```java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### 2. User.java
```java
package models;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    // Getters and setters
}
```

### 3. UserDAO.java
```java
package db;

import models.User;
import java.sql.*;

public class UserDAO {
    public boolean registerUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, "USER");
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

### 4. Register.java
```java
package auth;

import db.UserDAO;
import models.User;
import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Register");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 80, 25);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 10, 160, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);
        frame.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 70, 80, 25);
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 70, 160, 25);
        frame.add(emailField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 100, 250, 25);
        frame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUsername(usernameField.getText());
                user.setPassword(new String(passwordField.getPassword()));
                user.setEmail(emailField.getText());

                UserDAO userDAO = new UserDAO();
                if (userDAO.registerUser(user)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed.");
                }
            }
        });

        frame.setVisible(true);
    }
}
```

### 5. Login.java
```java
package auth;

import db.UserDAO;
import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 80, 25);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 10, 160, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 70, 250, 25);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.loginUser(usernameField.getText(), new String(passwordField.getPassword()));
                if (user != null) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Proceed to quiz dashboard
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials.");
                }
            }
        });

        frame.setVisible(true);
    }
}
```

### 6. Utils.java
```java
package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {
    public static void closeQuietly(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ignored) {}
    }

    public static void closeQuietly(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ignored) {}
    }

    public static void closeQuietly(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ignored) {}
    }
}
```

### 7. Quiz.java
```java
package models;

import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private String category;
    private List<Question> questions;

    // Getters and setters
}
```

### 8. Question.java
```java
package models;

public class Question {
    private int id;
    private int quizId;
    private String questionText;
    private String questionType;
    private String correctAnswer;

    // Getters and setters
}
```

### 9. QuizDAO.java
```java
package db;

import models.Quiz;
import models.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM quizzes";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(resultSet.getInt("id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setCategory(resultSet.getString("category"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM questions WHERE quiz_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getInt("id"));
                question.setQuizId(resultSet.getInt("quiz_id

"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setQuestionType(resultSet.getString("question_type"));
                question.setCorrectAnswer(resultSet.getString("correct_answer"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
```

### 10. QuizDashboard.java
```java
package quiz;

import db.QuizDAO;
import models.Quiz;
import models.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizDashboard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        QuizDAO quizDAO = new QuizDAO();
        List<Quiz> quizzes = quizDAO.getAllQuizzes();

        JComboBox<Quiz> quizComboBox = new JComboBox<>();
        for (Quiz quiz : quizzes) {
            quizComboBox.addItem(quiz);
        }
        quizComboBox.setBounds(10, 10, 360, 25);
        frame.add(quizComboBox);

        JButton startButton = new JButton("Start Quiz");
        startButton.setBounds(10, 50, 360, 25);
        frame.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Quiz selectedQuiz = (Quiz) quizComboBox.getSelectedItem();
                if (selectedQuiz != null) {
                    QuizScreen quizScreen = new QuizScreen(selectedQuiz);
                    quizScreen.setVisible(true);
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }
}
```

### 11. QuizScreen.java
```java
package quiz;

import db.QuizDAO;
import models.Question;
import models.Quiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizScreen extends JFrame {
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton nextButton;
    private int score = 0;

    public QuizScreen(Quiz quiz) {
        setTitle(quiz.getTitle());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        QuizDAO quizDAO = new QuizDAO();
        questions = quizDAO.getQuestionsByQuizId(quiz.getId());

        questionLabel = new JLabel();
        questionLabel.setBounds(10, 10, 360, 25);
        add(questionLabel);

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(10, 50 + (i * 30), 360, 25);
            buttonGroup.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        nextButton.setBounds(10, 200, 360, 25);
        add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadQuestion();
                } else {
                    JOptionPane.showMessageDialog(null, "Quiz finished! Your score: " + score);
                    dispose();
                    new QuizDashboard().main(null);
                }
            }
        });

        loadQuestion();
    }

    private void loadQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestionText());
        String[] options = { "Option 1", "Option 2", "Option 3", "Option 4" }; // Replace with actual options
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
        }
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected() && optionButtons[i].getText().equals(question.getCorrectAnswer())) {
                score++;
                break;
            }
        }
    }
}
```

### 12. Main.java
```java
public class Main {
    public static void main(String[] args) {
        auth.Login.main(args);
    }
}
```

This is a basic example to get you started. You will need to expand and enhance the functionality, add error handling, security features, and proper user interface elements as per your project's requirements. Make sure to also write comprehensive tests and documentation for each component.