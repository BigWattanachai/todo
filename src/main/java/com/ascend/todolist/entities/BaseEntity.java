package com.ascend.todolist.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by BiG on 5/31/2017 AD.
 */
@Data
@MappedSuperclass
class BaseEntity {
    @Id
    @GeneratedValue
    Long id;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedDate;

}
