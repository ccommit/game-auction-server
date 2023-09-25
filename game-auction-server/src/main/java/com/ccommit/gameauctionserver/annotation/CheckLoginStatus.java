package com.ccommit.gameauctionserver.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLoginStatus {

    UserType userType();

    enum UserType {
        USER,
        ADMIN,
        ABUSER;

        public static UserType getEnumType(String type){
            return Enum.valueOf(UserType.class, type);
        }

    }



}
