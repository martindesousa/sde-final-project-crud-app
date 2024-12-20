package edu.virginia.sde.reviews;

import jakarta.persistence.*;

@Entity
@Table(name = "COURSES")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COURSE_ID")
    private int id;

    @Column(name = "SUBJECT_MNEMONIC", nullable = false)
    private String subjectMnemonic;

    @Column(name = "COURSE_NUMBER", nullable = false)
    private int courseNumber;

    @Column(name = "COURSE_TITLE", nullable = false)
    private String courseTitle;

    @Column(name = "AVG_RATING", nullable = false)
    private double avgRating;

    public Course(String subjectMnemonic, int courseNumber, String courseTitle) {
        //this.id = id;
        this.courseNumber = courseNumber;
        this.subjectMnemonic = subjectMnemonic;
        this.courseTitle = courseTitle;
        this.avgRating = 0;
    }

    public Course()
    {

    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectMnemonic() {
        return subjectMnemonic;
    }

    public void setSubjectMnemonic(String subjectMnemonic) {
        this.subjectMnemonic = subjectMnemonic;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public boolean shareTitle(Course course) {
        if(this.courseTitle.equals(course.courseTitle))
        {
            return true;
        }
        return false;
    }

    public boolean equals(Course otherCourse){
        if(this.courseTitle.equals(otherCourse.courseTitle)
                && this.courseNumber == otherCourse.courseNumber
                && this.subjectMnemonic.equals(otherCourse.subjectMnemonic)){
            return true;
        }
        return false;
    }
}