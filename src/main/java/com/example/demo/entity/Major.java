package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 专业 实体类
 *
 * @author Lhy
 * @since 2023/11/21 17:14
 */
@Entity(name = "major")
@Table(name = "t_major")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "aClass")
public class Major implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "int unsigned")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(8)",
            nullable = false)
    @Pattern(regexp = "^[0-9]{6,7}(T|K|TK)?$")
    private String code;

    @Column(columnDefinition = "varchar(20)",
            unique = true,
            nullable = false)
    private String name;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime",
            updatable = false)
    @CreationTimestamp
    private Timestamp created;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime")
    @UpdateTimestamp
    private Timestamp updated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_class_major",
            joinColumns = @JoinColumn(name = "major_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    @JsonIgnore
    private Class aClass;
}
