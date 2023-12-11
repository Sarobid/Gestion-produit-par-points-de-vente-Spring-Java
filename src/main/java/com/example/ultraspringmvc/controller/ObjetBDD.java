package com.example.ultraspringmvc.controller;

import com.example.ultraspringmvc.proprety.Page;
import com.example.ultraspringmvc.sql.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.*;

import java.lang.reflect.Field;
import java.sql.*;

@Controller
@Service
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
public class ObjetBDD {
    @Autowired
    DataSource dataSource;
    private int limit = 20;
    private int totalPage = 0;
    private static SQLRequete req = null;
    private static SQLExecute exec = null;
    private Page page = null;

    public String url(String query){
        String rep = "?";
        if(query != null){
            String[] qs = query.split("&num");

            if(qs.length > 0){
                rep = "?"+qs[0];
            }
        }
        return rep;
    }
    private  static  Operateur operateur = null;
    public ObjetBDD() {
        this.req = new SQLRequete();
        this.exec = new SQLExecute();
        this.operateur = new Operateur();
        this.page = new Page();
    }

    public Object refactorisation()throws Exception{
        return ObjetBDD.operateur.initialisationObejt(this);
    }
    public int getTotalPage(){
        return totalPage;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public<T> T[] findQueryPagine(Connection con , String query, int num)throws Exception{
        T[] list = findQuery(con,query);
        this.totalPage = (int)(list.length / limit);
        if(list.length % limit != 0){
            this.totalPage = this.totalPage + 1;
        }
        if(num <= 0){
            num = 1;
        }
        page.setNumero(num);
        num = num -1;
        if(num > 0){
            num = num + limit;
        }
        page.setTotalPage(this.totalPage);
        return findQuery(null,query + " LIMIT "+limit+" OFFSET "+num);
    }

    public<T> T[] recherchePaginer(Connection con,String table,String suite,int num)throws Exception{
        T[] list = this.rechercheAvance(con,table,suite);
        this.totalPage = (int)(list.length / limit);
        System.out.println("\n"+" nombre page="+this.totalPage);
        if(list.length % limit != 0){
            this.totalPage = this.totalPage + 1;
        }
        if(num <= 0){
            num = 1;
        }
        page.setNumero(num);
        num = num -1;
        if(num > 0){
            num = num + limit - 1;
        }
        System.out.println("\n"+" nombre page="+this.totalPage+" LIST="+list.length);
        page.setTotalPage(this.totalPage);
        if(suite == null){
            suite = "";
        }
        return this.rechercheAvance(null,table,suite + " LIMIT "+limit+" OFFSET "+num);
    }

    public<T> T findById(Connection con,String t,int id)throws Exception{
        String table = this.req.getTable(this);
        if(t != null){table=t;}
        String query = "select * from "+table+" where "+this.getColumnNameId(this)+"="+id;
        T[] list = this.findQuery(con,query);
        if(list.length <= 0){throw new Exception("not found");}
        return list[0];
    }

    private String getColumnNameId(Object ob){
        Field[] fields = ob.getClass().getDeclaredFields();
        Column col = fields[0].getAnnotation(Column.class);
        String na = fields[0].getName();
        if(col != null){
            na = col.name();
        }
        return na;
    }

    public<T> T[] rechercheAvance(Connection con,String table,String suite)throws Exception{
        String query = "select * from "+table+" where 1=1";
        query = query + this.operateur.rechercheAvance(this);
        if(suite != null){
            query = query +" " +suite;
        }
        return this.findQuery(con,query);
    }
    public void executeQuery(Connection con,String query)throws Exception{
        try {
            if(con == null){
                con = new Connexion().getConnection();
            }
            if(con.isClosed() ==true){
                con = new Connexion().getConnection();
            }
            System.out.println(query);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
            // throw new Exception(query);
        }
    }

    public void insert(Connection con, String t) throws Exception {
        Object o = this;
        String table = this.req.getTable(o);
        if (t != null) {
            table = t;
        }
        try {
            if(con == null){
                con = new Connexion().getConnection();
            }
            this.exec.setID(this,null);
            String query = "INSERT INTO " + table + " (" + this.req.getColonneSQLISER(o, true) + ") VALUES("
                    + this.req.getValueSQLISER(o, true) + ")";
            System.out.println(query);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
            // throw new Exception(query);
        }
    }
    public void id()throws Exception{
        this.exec.setID(this,null);
    }

    public static String toName(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1,name.length()).toLowerCase();
    }

    public void update(Connection con) throws Exception {
        Object o = this;
        String query = "UPDATE  " + this.req.getTable(o) + " set " + this.req.getValueUPDATE(o, ",") + " where "
                + this.req.getColonne(o).get(0) + " = " + this.req.getValueColonne(o).get(0);
        System.out.println(query);
        try {

            if(con == null){
                con = con = new Connexion().getConnection();
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
        }
    }

    public void delete(Connection con) throws Exception {
        Object o = this;
        String query = "DELETE FROM  " + this.req.getTable(o) + " where " + this.req.getColonne(o).get(0) + " = "
                + this.req.getValueColonne(o).get(0);
        System.out.println(query);
        try {

            if(con == null){
                con = new Connexion().getConnection();
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw new Exception("on ne peut pas le supprimer");
        }
    }

    public static double arrandie(double d) {
        double roundDbl = Math.round(d * 100.0) / 100.0;
        return roundDbl;
    }


    public <T> T[] find(Connection con, String ta) throws Exception {
        Object o = this;
        String table = this.req.getTable(o);
        if (ta != null) {
            table = ta;
        }
        String query = "SELECT * FROM  " + table + " ";
        if (this.req.isWhere(this) == true) {
            query = query + " where " + this.req.getValueUPDATE(o, "and");
        }
        return this.findQuery(con,query);
    }

    public <T> T[] findQuery(Connection con, String query) throws Exception {
        Object o = this;
        T[] t = null;
        System.out.print(query);
        try {

            if(con == null){
                con = new Connexion().getConnection();
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            t = this.operateur.getALLOBJECT(o, stmt.executeQuery(query));
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
        }
        return t;
    }

    public static <T> T detailler(ObjetBDD o) throws Exception {
        T[] t = o.find(null, null);
        return t[0];
    }

    public static void execute(Connection con, String query) throws Exception {
        System.out.println(query);
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
        }
    }
    public static boolean isInteger(String hd) {
        boolean v = hd.matches("[+-]?\\d*(\\.\\d+)?");
        return v;
    }
    public static int getValueSequence(Connection con, String seq) throws Exception {
        String query = "SELECT NEXTVAL('" + seq + "')";
        int val = 0;
        try {
            if(con == null){
                con = new Connexion().getConnection();
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                val = res.getInt(1);
            }
            con.close();
        } catch (Exception ex) {
            if (con != null) {
                con.close();
            }
            throw ex;
        }
        return val;
    }
}