package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 专业门类实体类
 *
 * @author Lhy
 * @since 2023/11/20 22:51
 */
@Entity(name = "category")
@Table(name = "t_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "children")
public class Category implements Serializable {

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_category_class", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<Class> children;

    // 前端要求字段名为 children，包装一层
    public void setClasses(HashSet<Class> hashSet) {
        setChildren(hashSet);
    }

    public Set<Class> getClasses() {
        return getChildren();
    }
}
