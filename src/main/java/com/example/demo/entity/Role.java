package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色实体类
 *
 * @author Lhy
 * @since 2023/10/10 20:35
 */
@Entity(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "permissions")
@Table(name = "t_role")
public class Role implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "int unsigned", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50)", unique = true, nullable = false)
    @Pattern(regexp = "ROLE_[A-Z]+", message = "角色名必须以ROLE_开头")
    @Size(min = 1, max = 50, message = "角色名长度必须在1-50之间")
    private String name;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Size(max = 255, message = "角色描述长度不能超过255")
    private String description;

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

    // 多对多关系映射,一个角色可以赋予多个权限
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    @JsonIgnore
    private List<Permission> permissions;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> userList;

    public Set<String> getPermissions() {
        return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Role{" + "id='" + id + '\'' + ", name=" + name + ", description='" + description + '\'' + ", createTime=" + created + ", updatedTime=" + updated + '}';
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
