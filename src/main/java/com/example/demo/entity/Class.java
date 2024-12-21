package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 专业类实体类
 *
 * @author Lhy
 * @since 2023/11/20 22:56
 */
@Entity(name = "class")
@Table(name = "t_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"category", "children"})
public class Class implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "int unsigned", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(10)", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime", updatable = false)
    @CreationTimestamp
    private Timestamp created;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime")
    @UpdateTimestamp
    private Timestamp updated;

    @ManyToOne
    @JoinTable(name = "t_category_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnore
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_class_major",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id"))
    private Set<Major> children;

    // 前端要求字段名为 children，包装一层
    public Set<Major> getMajors() {
        return getChildren();
    }

    public void setMajors(Set<Major> majors) {
        setChildren(majors);
    }
}
