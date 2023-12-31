package com.example.ultraspringmvc.sql;

import java.lang.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
}