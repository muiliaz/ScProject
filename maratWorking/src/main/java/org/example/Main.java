package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import javax.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Entity
@Table(name = "users")
class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

@Service
class UserService {
    private final UserRepository us0erRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
}

@Entity
@Table(name = "courses")
class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseID;

    private String name;
    private boolean isPaidCourse;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private ArrayList<Lesson> lessons;

    public void addLesson(Lesson lesson) {
        if (lessons == null) {
            lessons = new ArrayList<Lesson>();
        }
        lessons.add(lesson);
    }

    public Course() {
    }

    public Course(String name, boolean isPaidCourse) {
        this.name = name;
        this.isPaidCourse = isPaidCourse;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPaidCourse() {
        return isPaidCourse;
    }

    public void setPaidCourse(boolean paidCourse) {
        this.isPaidCourse = paidCourse;
    }
}

@Service
class CourseService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public void createCourse(String username, String password, String courseName, boolean isPaid) {
        // Check if the user is valid
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new IllegalArgumentException("No such user");
        }

        // TODO should be more specific? or what
        if (!username.equals("Dinara") || !password.equals("12345678")) {
            throw new IllegalArgumentException("You cannot access this function");
        }

        Course course = new Course();
        course.setName(courseName);
        course.setPaidCourse(isPaid);

        // Save the course in the "courses" table
        entityManager.persist(course);

        // Create a new table for the course
        String tableName = courseName.replaceAll("\\s+", "_"); // spaces -> underscores
        String createTableQuery = "CREATE TABLE " + tableName + " (id INT PRIMARY KEY AUTO_INCREMENT, ...)";
        entityManager.createNativeQuery(createTableQuery).executeUpdate();
    }

    @Transactional
    public void addCourseMaterial(long courseId, String lessonName, String lessonDescription, String videoLink) {
        // Find the course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Create a new lesson
        Lesson lesson = new Lesson();
        lesson.setName(lessonName);
        lesson.setDescription(lessonDescription);
        lesson.setVideoLink(videoLink);

        // Add the lesson to the course
        course.addLesson(lesson);

        // Save the updated course in the "courses" table
        entityManager.merge(course);
    }
}
interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long courseId);
}
@Entity
@Table(name = "lessons")
class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    private String name;

    private String description;

    private String videoLink;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    public Lesson() {
    }

    public Lesson(String name, String description, String videoLink) {
        this.name = name;
        this.description = description;
        this.videoLink = videoLink;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}

    @SpringBootApplication
    @EnableJpaRepositories(basePackages = "com.example.repository")
    public class Main {
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
        }
    }
