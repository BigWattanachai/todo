package com.ascend.todolist.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BiG on 5/30/2017 AD.
 */
@Entity
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class Todo extends BaseEntity {

    private String content;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> todoItems = new ArrayList<>();

}
