package com.example.ultraspringmvc.sql;

import java.lang.Object;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

import com.example.ultraspringmvc.controller.ObjetBDD;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SQLExecute {

	private SQLRequete base = null;

	public SQLExecute() {
		this.base = new SQLRequete();
	}

	public <T> T[] getTotatObject(Object o, ResultSet rsed) throws Exception {
		String[][] value = this.responseRequette(o, rsed);
		Object[] resultat = new Object[value.length];
		T[] liste = (T[]) java.lang.reflect.Array.newInstance(o.getClass(), value.length);
		int i = 0;
		int g = 0;
		for (i = 0; i < value.length; i++) {
			Object og = this.executeMethodeSet(o, value[i]);
			liste[i] = (T) og.getClass().cast(og);
		}
		return liste;
	}


	public Object executeMethodeSet(Object o, String[] value) throws Exception {
		Constructor[] constructor = o.getClass().getDeclaredConstructors();
		Object object = constructor[0].newInstance();
		ArrayList<Field> field = SQLRequete.getFieldsPointer(o);
		int i = 0;
		Vector ak = this.transValue(field, value);
		for (i = 0; i < field.size(); i++) {
			this.getMethodeSet(field.get(i), o).invoke(object, ak.get(i));
		}
		return object;
	}



	private Vector transValue(ArrayList<Field> fil, String[] value) throws Exception {
		Vector ch = new Vector();
		int i = 0;
		for (i = 0; i < value.length; i++) {
			if (fil.get(i).getType().getName() == "int") {
				ch.addElement(Integer.parseInt(value[i]));
			} else if (fil.get(i).getType().getName() == "double") {
				ch.addElement(Double.parseDouble(value[i]));
			} else if (fil.get(i).getType().getName() == "float") {
				ch.addElement(Float.parseFloat(value[i]));
			} else {
				if (fil.get(i).getType().getName() != "java.lang.String"
						&& fil.get(i).getType().getName() != "java.time.LocalDateTime"
						&& fil.get(i).getType().getName() != "java.time.LocalDate" &&
                                        fil.get(i).getType().getName() != "java.time.LocalTime") {
					Constructor[] constructor = Class.forName(fil.get(i).getType().getName()).getDeclaredConstructors();
					Object object = constructor[0].newInstance();
					ArrayList<Field> field = SQLRequete.getFieldsPointer(object);
					this.getMethodeSet(field.get(0), object).invoke(object, Integer.parseInt(value[i]));
					ch.addElement(object);
				} else if (fil.get(i).getType().getName() == "java.time.LocalDateTime") {
					ch.addElement(LocalDateTime.parse(value[i].replace(" ", "T")));
				} else if (fil.get(i).getType().getName() == "java.time.LocalDate") {
					ch.addElement(LocalDate.parse(value[i]));
				}else if (fil.get(i).getType().getName() == "java.time.LocalTime") {
					ch.addElement(LocalTime.parse(value[i]));
				} else {
					ch.addElement(value[i]);
				}
			}
		}
		return ch;
	}

	public void setID(Object o, Connection con)throws Exception{
		Field[] field = o.getClass().getDeclaredFields();
		int i = 0, b = 0;
		for (i = 0; i < field.length; i++) {
			Sequence a = (Sequence) field[i].getAnnotation(Sequence.class);
			if (a != null) {
				this.getMethodeSet(field[i],o).invoke(o,ObjetBDD.getValueSequence(con,a.name()));
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

	private String[][] responseRequette(Object o, ResultSet resd) throws Exception {
		ResultSetMetaData af = resd.getMetaData();
		ResultSet newRes = resd;
		String[][] table = new String[getNumberResult(newRes)][af.getColumnCount()];
		ArrayList<String> col = base.getColonne(o);
		int i = 0;
		int g = 0;
		int c = 0;
		while (resd.next()) {
			c = 0;
			for (g = 0; g < col.size(); g++) {
				table[i][c] = resd.getString(col.get(g));
				c++;
			}
			i++;
		}
		return table;
	}

	private int getNumberResult(ResultSet res) throws SQLException, Exception {
		int num = 0;
		while (res.next()) {
			num++;
		}
		res.beforeFirst();
		return num;
	}
}