package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Setter
@Table(name = "vente")
public class Vente extends ObjetBDD {
    @Sequence(name = "vente_id_vente_seq")
    @Column(name = "id_vente")
    private int id = -1;
    @Column(name = "id_stock_points")
    private StockPoints stockPoints = null;
    @Column(name = "quantite_vente")
    private int quantite = -1;
    @Column(name = "prix")
    private double prix = -1.0;
    @Column(name = "date_vente")
    private LocalDate dates = null;
    public Vente(){}

    @Override
    public void delete(Connection con) throws Exception {
        Vente vente = this.findById(null,"v_vente",id);
        StockPoints stockPoints1 = vente.getStockPoints();
        stockPoints1.setQuantite(stockPoints1.getQuantite() + vente.getQuantite());
        stockPoints1.update(null);
        super.delete(con);
    }

    public Vente[] listeVente(PointsVente pointsVente, String reference, int prixMin, int prixMax)throws Exception{
        String query = "select * from v_vente where 1=1";
        query = query + " and id_points_vente="+pointsVente.getId();
        if(prixMin > 0){
            query = query + " and prix >="+prixMin;
        }
        if(prixMax > 0){
            query = query + " and prix <="+prixMax;
        }
        String[] refL = null;
        if(reference.isEmpty() == false){
            refL = reference.split(" ");
            query = query + this.recherche(refL,"nom_marque");
            query = query + this.recherche(refL,"nom_model");
        }
        return this.findQuery(null,query);
    }

    private String recherche(String[] reference,String colonne)throws Exception{
        String a = "";
        for(String ref:reference){
            if(this.findQuery(null,"select * from v_vente where "+colonne+" like '%"+ref+"%'").length > 0){
                if(a.isEmpty() == true){
                    a = "and ("+colonne +" like '%"+ref+"%' ";
                }else{
                    a = a + " or "+colonne +" like '%"+ref+"%' ";
                }
            }
        }
        if(a.isEmpty() == false){
            a = a + " )";
        }
        return a;
    }

    
    public void nouveau()throws Exception{
        stockPoints = stockPoints.findById(null,"v_stock_points",stockPoints.getId());
        if(stockPoints.getQuantite() < quantite || stockPoints.getQuantite() == 0){
            throw new Exception("stock insuffisant");
        }

        stockPoints.setQuantite(stockPoints.getQuantite() - quantite);
        stockPoints.update(null);
        prix = stockPoints.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().getPrixVente();
        this.insert(null,null);
    }
}
