package com.ascend.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by BiG on 5/31/2017 AD.
 */
@Entity
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class TodoItem extends BaseEntity {
    private String content;

    private Boolean complete;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    @JsonIgnore
    Todo todo;
}
