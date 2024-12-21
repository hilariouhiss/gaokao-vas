package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 权限实体类
 *
 * @author Lhy
 * @since 2023/10/24 19:24
 */

@Entity(name = "permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_permission")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned",
            updatable = false)
    private Integer id;

    @Column(columnDefinition = "varchar(50)",
            unique = true,
            nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(255)",
            nullable = false)
    private String url;

    @Column(columnDefinition = "varchar(255)",
            nullable = false)
    private String description;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_permission",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + name + '\'' +
                ", permissionUrl='" + url + '\'' +
                ", permissionDescription='" + description + '\'' +
                ", createTime=" + created +
                ", updatedTime=" + updated +
                '}';
    }
}
