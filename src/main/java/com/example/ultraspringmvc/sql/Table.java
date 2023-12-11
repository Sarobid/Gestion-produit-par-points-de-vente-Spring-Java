package com.example.ultraspringmvc.sql;

import java.lang.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String name();
}