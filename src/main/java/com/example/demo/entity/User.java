package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.sql.Timestamp;
import java.util.*;

/**
 * 用户实体
 *
 * @author Lhy
 * @since 2023/10/10 17:30
 */
@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "t_user")
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "int unsigned",
            updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50)",
            unique = true,
            nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_\\-@]+$", message = "用户名只能包含字母，数字，下划线，中划线和@符号")
    @Size(min = 1, max = 50, message = "用户名长度必须在1-50之间")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "bpchar(60)")
    @Size(min = 8, message = "密码长度必须大于等于8位")
    private String password;

    @Column(columnDefinition = "bpchar(11)",
            unique = true)
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(3)")
    @Size(max = 3, message = "地区长度不能超过3")
    private String province;

    @Column(columnDefinition = "smallint unsigned")
    @DecimalMin(value = "0", message = "分数必须大于等于0")
    @DecimalMax(value = "750", message = "分数必须小于等于750")
    private Short score;

    @Column(columnDefinition = "tinyint")
    private Byte subject;

    @JsonIgnore
    @Column(columnDefinition = "datetime",
            updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp created;

    @JsonIgnore
    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Timestamp updated;

    // 多对一关系映射,一个用户只能有一个角色
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Role role = this.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", province='" + province + '\'' +
                ", score=" + score +
                ", createTime=" + created +
                ", updatedTime=" + updated +
                '}';
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
