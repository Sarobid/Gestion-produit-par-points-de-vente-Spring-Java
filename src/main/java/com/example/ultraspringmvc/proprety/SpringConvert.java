package com.example.ultraspringmvc.proprety;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SpringConvert extends PropertyEditorSupport {
    private Class T = null;

    public SpringConvert(Class t) {
        T = t;
    }
    @Override
    public void setAsText(String text)throws IllegalArgumentException {
        Object ob = null;
        String a = "";
        if(text.isEmpty()){
            throw new IllegalArgumentException("obligatoire");
        }
        if(T == LocalDateTime.class){
            System.out.println(text+":00");
            ob = LocalDateTime.parse((text+":00").replace('T',' '), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (T == LocalTime.class) {
            ob = LocalTime.parse(text+":00");
        }else if(T == LocalDate.class){
            System.out.println(text.replace('/','-'));
            ob = LocalDate.parse(text.replace('/','-'));
        }
        setValue(ob);
    }
}
