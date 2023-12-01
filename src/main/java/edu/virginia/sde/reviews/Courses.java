package edu.virginia.sde.reviews;

import jakarta.persistence.*;

@Entity
@Table(name = "COURSES")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COURSE_ID")
    private int id;

    @Column(name = "SUBJECT_PNEUMONIC", nullable = false)
    private String subjectPneumonic;

    @Column(name = "COURSE_NUMBER", nullable = false)
    private int courseNumber;

    @Column(name = "COURSE_TITLE", nullable = false)
    private String courseTitle;
}