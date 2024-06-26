package com.fu.springbootjpademo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 人员
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(appliesTo = "person", comment = "人员表")//appliesTo指表的名称（必填），MySQL表名小写，用Person会抛异常。
@EntityListeners(AuditingEntityListener.class)
//前置条件：引入 @EnableJpaAuditing 注解。才能使用@Version、@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy
public class Person {
    @Id//主键标识
    @GeneratedValue(strategy = GenerationType.IDENTITY)//数据库主键策略，默认是AUTO。
    @Comment("主键ID")
    private Long id;

    @Column(name = "name", length = 20, unique = true, nullable = false)//字段属性设置
    @Comment("名称")//字段备注
    private String name;

    @Comment("性别")
    private Integer sex;

    @Transient//此注解表示数据库不创建该字段
    private String ignore;//忽略字段

    @Column(name = "created_by")
    @Comment("创建人")
    @CreatedBy//自动填充创建人
    private String createdBy;

    @Column(name = "created_date")
    @Comment("创建日期时间")
    @CreatedDate//自动填充创建日期时间，需要注意jpa创建日期时间精度问题，默认mysql的datetime精度为6位，需要手动把毫秒数抹掉。即把长度改成0
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

    @Column(name = "last_updated_by")
    @Comment("最后修改人")
    @LastModifiedBy
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    @Comment("最后修改日期时间")
    @LastModifiedDate//自动填充最后修改日期时间，需要注意jpa创建日期时间精度问题，默认mysql的datetime精度为6位，需要手动把毫秒数抹掉。即把长度改成0
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedDate;

    /*@Column(name = "object_version" )
    @Comment("版本号")
    @Version//版本号一致才能修改、删除。一般不建议使用此注解。
    private Long objectVersion;*/

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
