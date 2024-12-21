package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 学校实体类
 *
 * @author Lhy
 * @since 2023/11/20 17:27
 */
@Entity(name = "school")
@Table(name = "t_school")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class School implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "int unsigned",
            updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "char(5)",
            unique = true,
            nullable = false)
    @Pattern(regexp = "[0-9]{5}$")
    private String code;

    @Column(columnDefinition = "varchar(30)",
            unique = true,
            nullable = false)
    @Size(max = 30, message = "学校名称长度不能超过30")
    private String name;

    @Column(columnDefinition = "varchar(3)")
    @Size(max = 3, message = "学校省份长度不能超过3")
    private String province;

    @Column(columnDefinition = "varchar(11)")
    @Size(max = 11, message = "学校所属部门长度不能超过11")
    private String department;

    @Column(name = "img_url",
            columnDefinition = "varchar(255)")
    @Size(max = 255, message = "校徽地址长度不能超过255")
    private String imgUrl;

    @Column(columnDefinition = "varchar(24)")
    @Size(max = 24, message = "学校类别长度不能超过24")
    private String mold;

    @Column(columnDefinition = "numeric(2,1)")
    @DecimalMin(value = "0", message = "学校评分不能小于0")
    @DecimalMax(value = "5", message = "学校评分不能大于5")
    private Float evaluation;

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
}
