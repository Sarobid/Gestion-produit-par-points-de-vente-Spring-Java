package com.example.ultraspringmvc.sql;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Vector;

public class Operateur {

    public Object initialisationObejt(Object ob)throws Exception{
        Field[] fields = ob.getClass().getDeclaredFields();
        for (Field field:fields){
            System.out.println(field.getType().getName());
            if(field.getType().getName() == "int" || field.getType().getName() == "float" || field.getType().getName() == "double"){
                this.getMethodeSet(field,ob).invoke(ob,0);
            }else{
                if (field.getType().getName() != "java.lang.String"
                        && field.getType().getName() != "java.time.LocalDateTime"
                        && field.getType().getName() != "java.time.LocalDate" &&
                        field.getType().getName() != "java.time.LocalTime") {
                    Constructor[] constructor = Class.forName(field.getType().getName()).getDeclaredConstructors();
                    Object object = constructor[0].newInstance();
                    object = this.initialisationObejt(object);
                    this.getMethodeSet(field,ob).invoke(ob,object);
                }else {
                    this.getMethodeSet(field,ob).invoke(ob,"");
                }
            }
        }
        return ob;
    }
    public <T> T[]  getALLOBJECT(Object o, ResultSet resd) throws Exception {
        ResultSetMetaData af = resd.getMetaData();
        ResultSet newRes = resd;
        int i = 0;
        int g = 0;
        int c = 0;
        Constructor[] constructor = null;
        T[] liste = (T[]) java.lang.reflect.Array.newInstance(o.getClass(), this.getNumberResult(resd));
        Object object = null;
        while (resd.next()) {
            constructor = o.getClass().getDeclaredConstructors();
            object = constructor[0].newInstance();
            object = this.setObjet(object,resd,af);
            liste[i] = (T) object.getClass().cast(object);
            i++;
        }
        return liste;
    }

    public Object setObjet(Object o,ResultSet resd,ResultSetMetaData meta)throws Exception{
        Field[] fields = o.getClass().getDeclaredFields();
        int i = 0;
        String nameColmn = null;
        String value = null;
        for(i = 0 ; i < fields.length ; i ++){
            Column col = fields[i].getAnnotation(Column.class);
            Views views = fields[i].getAnnotation(Views.class);
            nameColmn = fields[i].getName();
            if(col != null){
                nameColmn = col.name();
            }
            if(views != null){
                nameColmn = views.name();
            }
            value = this.getValue(resd,meta,nameColmn);
            if(value != null){
                this.setValue(fields[i],o,value,resd,meta);
            }
        }
        return o;
    }

    private void setValue(Field field,Object ob,String value,ResultSet resd,ResultSetMetaData meta)throws Exception{
        Method method = this.getMethodeSet(field,ob);
        if (field.getType().getName() == "int") {
            method.invoke(ob,Integer.parseInt(value));
        } else if (field.getType().getName() == "double") {
            method.invoke(ob,Double.parseDouble(value));
        } else if (field.getType().getName() == "float") {
            method.invoke(ob,Float.parseFloat(value));
        } else {
            if (field.getType().getName() != "java.lang.String"
                    && field.getType().getName() != "java.time.LocalDateTime"
                    && field.getType().getName() != "java.time.LocalDate" &&
                    field.getType().getName() != "java.time.LocalTime") {
                Constructor[] constructor = Class.forName(field.getType().getName()).getDeclaredConstructors();
                Object object = constructor[0].newInstance();
                this.getMethodeSet(object.getClass().getDeclaredFields()[0],object).invoke(object,Integer.parseInt(value));
                method.invoke(ob,this.setObjet(object,resd,meta));
            } else if (field.getType().getName() == "java.time.LocalDateTime") {
                method.invoke(ob,LocalDateTime.parse(value.replace(" ", "T")));
            } else if (field.getType().getName() == "java.time.LocalDate") {
                method.invoke(ob,LocalDate.parse(value));
            } else if (field.getType().getName() == "java.time.LocalTime") {
                method.invoke(ob,LocalTime.parse(value));
            } else {
                method.invoke(ob,value);
            }
        }
    }

    private Method getMethodeSet(Field att, Object o) {
        Method[] meth = o.getClass().getDeclaredMethods();
        int i = 0;
        Method m = null;
        for (i = 0; i < meth.length; i++) {
            if (meth[i].getName().toLowerCase().equals("set" + att.getName().toLowerCase()) == true) {
                m = meth[i];
                break;
            }
        }
        return m;
    }
    public String getValue(ResultSet resd,ResultSetMetaData meta,String nameColmn)throws Exception{
        String rep = null;
        int i = 0;
        for(i = 0 ; i < meta.getColumnCount() ; i++){
            if(nameColmn.equals(meta.getColumnName(i+1))){
                rep = resd.getString(nameColmn);
                break;
            }
        }
        return rep;
    }
    private int getNumberResult(ResultSet res) throws SQLException, Exception {
        int num = 0;
        while (res.next()) {
            num++;
        }
        res.beforeFirst();
        return num;
    }


    public  String rechercheAvance(Object ob)throws Exception{
        Field[] fields = ob.getClass().getDeclaredFields();
        String query = " ";
        String col = "";
        for(Field field:fields){
            col = field.getName();
            Views views = field.getAnnotation(Views.class);
            Column column = (Column) field.getAnnotation(Column.class);
            if(column != null){
                col = column.name();
            }
            if(views != null){
                col = views.name();
            }
            if(field.getGenericType().getTypeName() == "int"){
                if(Integer.parseInt(this.getMethodeGet(field,ob).invoke(ob).toString()) > 0){
                    query = query + " and "+col+"="+ this.getMethodeGet(field,ob).invoke(ob).toString();
                }
            } else if (field.getGenericType().getTypeName() == "double") {
                if(Double.parseDouble(this.getMethodeGet(field,ob).invoke(ob).toString()) > 0){
                    query = query + " and "+col+"="+ this.getMethodeGet(field,ob).invoke(ob).toString();
                }

            } else if (field.getType().getName() != "java.lang.String"
                    && field.getType().getName() != "java.time.LocalDateTime"
                    && field.getType().getName() != "java.time.LocalDate" &&
                    field.getType().getName() != "java.time.LocalTime") {
                if(this.getMethodeGet(field,ob).invoke(ob) != null){
                    query = query + this.rechercheAvance(this.getMethodeGet(field,ob).invoke(ob));
                }

            } else if (field.getType().getName() == "java.time.LocalDateTime"
                    || field.getType().getName() == "java.time.LocalDate" ||
                    field.getType().getName() == "java.time.LocalTime") {
                if(this.getMethodeGet(field,ob).invoke(ob) != null){
                    query = query + " and "+col+"='"+this.getMethodeGet(field,ob).invoke(ob).toString()+"'";
                }
            } else{
                if(this.getMethodeGet(field,ob).invoke(ob) != null){
                    String val = this.getMethodeGet(field,ob).invoke(ob).toString();
                    if(val.isEmpty() == false){
                        query = query + " and "+col+" like '%"+val+"%' ";
                    }
                }
            }
        }
        return query;
    }
    private Method getMethodeGet(Field att, Object o) {
        Method[] meth = o.getClass().getDeclaredMethods();
        int i = 0;
        Method m = null;
        for (i = 0; i < meth.length; i++) {
            if (meth[i].getName().toLowerCase().equals("get" + att.getName().toLowerCase()) == true) {
                m = meth[i];
                break;
            }
        }
        return m;
    }
}
