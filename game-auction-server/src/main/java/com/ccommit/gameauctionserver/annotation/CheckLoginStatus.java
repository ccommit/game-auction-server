package com.ccommit.gameauctionserver.annotation;


import com.ccommit.gameauctionserver.dto.user.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLoginStatus {
   public UserType userType();
}
