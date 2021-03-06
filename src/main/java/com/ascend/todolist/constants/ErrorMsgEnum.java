package com.ascend.todolist.constants;


import lombok.Getter;

/**
 * Created by BiG on 5/28/2017 AD.
 */
@Getter
public enum ErrorMsgEnum {
    TODO_NOT_FOUND("Todo id %s is not found"),
    TODO_ITEM_NOT_FOUND("Todo item id %s is not found");

    private final String msg;

    ErrorMsgEnum(String msg) {
        this.msg = msg;
    }
}
