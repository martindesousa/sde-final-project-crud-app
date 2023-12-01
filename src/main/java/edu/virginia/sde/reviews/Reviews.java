package edu.virginia.sde.reviews;

import jakarta.persistence.*;

@Entity
@Table(name = "REVIEWS")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

   // @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "USERNAME")
    //private Users user;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COURSE")
    private Courses course;

    @Column(name = "COMMENT", nullable = false)
    private String comment;
}