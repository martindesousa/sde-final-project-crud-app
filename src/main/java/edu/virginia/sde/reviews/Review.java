package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USERNAME")
    private User user;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "COURSE")
    private Course course;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "TIME", nullable = false)
    private Timestamp time;

    /*

    public String subjectMnemonic;
    public int courseNumber;

    public String getSubjectMnemonic(){
        return course.getSubjectMnemonic();
    }

    public int getCourseNumber(){
        return course.getCourseNumber();
    }

     */

    public Review(User user, int rating, Course course, String comment, Timestamp time)
    {
        this.user = user;
        this.rating = rating;
        this.course = course;
        /*
        this.subjectMnemonic = course.getSubjectMnemonic();
        this.courseNumber = course.getCourseNumber();

         */
        this.comment = comment;
        this.time = time;
    }

    public Review() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}