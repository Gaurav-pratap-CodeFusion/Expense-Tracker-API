package com.gpcf.expense_tracker_api.exception;

public class UserAlreadyExistsException extends  RuntimeException{
    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
