package com.example.ultraspringmvc.sql;

import java.lang.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.sql.Connection;
import java.util.*;

public class SQLRequete {

    public String getTable(Object o) throws Exception {
        Table tab = (Table) o.getClass().getAnnotation(Table.class);
        return tab.name();
    }

    private boolean isInteger(String hd) {
        boolean v = hd.matches("[+-]?\\d*(\\.\\d+)?");
        return v;
    }

    public String getValueUPDATE(Object o, String reg) throws Exception {
        ArrayList<String> value = this.getValueColonne(o);
        ArrayList<String> col = this.getColonne(o);
        ArrayList<Field> fil = this.getFieldsPointer(o);
        int i = 0;
        String sq = "";
        for (i = 0; i < value.size(); i++) {
            if (value.get(i) != null) {
                if (this.isInteger(value.get(i)) == true) {
                    if (Double.parseDouble(value.get(i)) >= 0) {
                        sq = sq + " " + col.get(i) + "=" + this.valueSQL(fil.get(i), value.get(i)) + " " + reg + "";
                    }
                } else {
                    sq = sq + " " + col.get(i) + "=" + this.valueSQL(fil.get(i), value.get(i)) + " " + reg + "";
                }
            }
        }
        if (sq.length() > 0) {
            sq = sq.substring(0, sq.length() - reg.length());
        }
        return sq;
    }

    public boolean isWhere(Object o) throws Exception {

        ArrayList<String> value = this.getValueColonne(o);

        int i = 0;
        boolean b = false;
        for (i = 0; i < value.size(); i++) {
            if (value.get(i) != null) {
                if (this.isInteger(value.get(i)) == true) {
                    if (Double.parseDouble(value.get(i)) > 0) {
                        b = true;
                        break;
                    }
                }
                else if(value.get(i).isEmpty() == false) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    public String getColonneSQLISER(Object o, boolean id) {
        ArrayList<String> colonne = this.getColonne(o);
        int i = 0;
        if (id == false) {
            colonne = this.enleveId(colonne);
        }
        return this.enModeOneLigne(colonne);
    }

    private ArrayList<String> enleveId(ArrayList<String> val) {
        int i = 0, g = 0;
        ArrayList<String> list = new ArrayList<String>();
        for (i = 1; i < val.size(); i++) {
            list.add(val.get(i));
        }
        return list;
    }


    public String getValueSQLISER(Object o, boolean id) throws Exception {

        ArrayList<String> val = new ArrayList<String>();
        ArrayList<String> value = this.getValueColonne(o);
        ArrayList<Field> fil = this.getFieldsPointer(o);
        int i = 0;
        for (i = 0; i < value.size(); i++) {
            val.add(this.valueSQL(fil.get(i), value.get(i)));
        }
        if (id == false) {
            val = this.enleveId(val);
        }
        return this.enModeOneLigne(val);
    }

    private String valueSQL(Field fil, String val) {
        String v = val;
        if (fil.getGenericType().getTypeName() != "int" && fil.getGenericType().getTypeName() != "double") {
            if (this.isInteger(val) == false) {
                v = "'" + val + "'";
            }
        }

        return v;
    }

    private String enModeOneLigne(ArrayList<String> colonne) {
        int i = 0;
        String sq = "";
        for (i = 0; i < colonne.size(); i++) {
            sq = sq + colonne.get(i) + ",";
        }
        return sq.substring(0, sq.length() - 1);
    }

    public static ArrayList<Field> getFieldsPointer(Object o) throws Exception {
        Field[] field = o.getClass().getDeclaredFields();
        int i = 0, b = 0;
        ArrayList<Field> colmn = new ArrayList<Field>();
        for (i = 0; i < field.length; i++) {
            Column a = (Column) field[i].getAnnotation(Column.class);
            if (a != null) {
                colmn.add(field[i]);
            }
        }
        return colmn;
    }

    public ArrayList<String> getColonne(Object o) {
        Field[] field = o.getClass().getDeclaredFields();
        int i = 0, b = 0;
        ArrayList<String> colmn = new ArrayList<String>();
        for (i = 0; i < field.length; i++) {
            Column a = (Column) field[i].getAnnotation(Column.class);
            Ignore ing = (Ignore) field[i].getAnnotation(Ignore.class);
            if(ing == null){
                if (a != null) {
                    colmn.add(a.name());
                }
            }
        }
        return colmn;
    }
    public ArrayList<Field> coloneToFieldType(Object ob,ArrayList<String> col){
        ArrayList<Field> fields = new ArrayList<>();
        Field[] fields1 = ob.getClass().getDeclaredFields();
        for(String c: col){
            for(Field field:fields1){
                Column column = (Column) field.getAnnotation(Column.class);
                if(column != null){
                    if(column.name().toLowerCase().equals(c.toLowerCase()) == true){
                        fields.add(field);
                        break;
                    }
                }
            }
        }
        return fields;
    }

    public ArrayList<String> getValueColonne(Object o) throws Exception {
        ArrayList<String> filName = this.getColonne(o);
        ArrayList<Field> field = this.coloneToFieldType(o,filName);
        int i = 0;
        ArrayList<String> value = new ArrayList<String>();
        String val = null;
        for (i = 0; i < field.size(); i++) {
            Method m = this.getMethodeGet(field.get(i), o);
            if (field.get(i).getGenericType().getTypeName() != "java.lang.String"
                    && field.get(i).getGenericType().getTypeName() != "int"
                    && field.get(i).getGenericType().getTypeName() != "double"
                    && field.get(i).getGenericType().getTypeName() != "java.time.LocalDateTime"
                    && field.get(i).getGenericType().getTypeName() != "java.time.LocalDate"
                    && field.get(i).getGenericType().getTypeName() != "java.time.LocalTime") {
                Object obj = m.invoke(o);
                if (obj == null) {
                    val = null;
                } else {
                    val = this.getValueColonne(obj).get(0);
                }
            } else {
                val = this.executeMethodeGet(o, m);
            }
            value.add(val);
        }
        return value;
    }

    private String executeMethodeGet(Object o, Method m) throws Exception {
        String value = null;
        if (m.invoke(o) != null) {
            value = m.invoke(o).toString();
        }
        return value;
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