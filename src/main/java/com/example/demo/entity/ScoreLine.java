package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * 学校分数线实体类
 *
 * @author Lhy
 * @since 2023/12/09 15:07
 */

@Entity(name = "score_line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_score_line")
public class ScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned")
    private Integer id;

    @Column(name = "school_name", nullable = false,
            columnDefinition = "varchar(30)")
    private String schoolName;

    @Column(nullable = false, columnDefinition = "smallint unsigned")
    private Short year;

    @Column(nullable = false, columnDefinition = "tinyint")
    private Byte batch;

    @Column(nullable = false, columnDefinition = "tinyint")
    private Byte subject;

    @Column(name = "total_score", nullable = false,
            columnDefinition = "smallint unsigned")
    private Short totalScore;

    @Column(columnDefinition = "tinyint unsigned")
    private Short chinese;

    @Column(columnDefinition = "tinyint unsigned")
    private Short math;

    @Column(columnDefinition = "smallint unsigned")
    private Short comprehensive;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp created;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Timestamp updated;
}
