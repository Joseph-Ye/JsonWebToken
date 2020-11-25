package com.jwt.demo.exception;

/**
 * @author jhye4
 * @date 2020/11/23 17:22
 */

public class UserException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Type type;
    public  Type type(){
        return type;
    }
    public enum Type{
        WRONG_PAGE_NUM,LACK_PARAMTER,USER_NOT_LOGIN,USER_NOT_FOUND,USER_AUTH_FAIL;
    }

    public UserException(String message){
        super(message);
        type = Type.LACK_PARAMTER;
    }

    public UserException(Type type,String message){
        super(message);
        this.type = type;
    }

}
