package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "transfere")
public class Transfere extends ObjetBDD{
    @Sequence(name = "transfere_id_transfert_seq")
    @Column(name = "id_transfert")
    private int id = -1;
    @Column(name = "id_stock_magasin")
    private StockMagasin stockMagasin = null;
    @Column(name = "quantite_transfere")
    private int quantite = -1;
    @Column(name = "id_points_vente")
    private PointsVente pointsVente = null;
    @Column(name = "date_transfere")
    private LocalDate date = null;
    public Transfere(){}

    public void setQuantite(int quantite) throws Exception{
        this.quantite = quantite;
    }

    public void nouveauTransfere()throws Exception{
        stockMagasin = stockMagasin.findById(null,"v_stock_magasin",stockMagasin.getId());
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+stockMagasin.getId())[0];
        int reste = stockMagasinFille.getQuantite() - quantite;
        if(reste < 0 || quantite==0){
            throw new Exception("quantite insuffisant");
        }
        this.insert(null,null);
        stockMagasinFille.setQuantite(stockMagasinFille.getQuantite() -quantite);
        stockMagasinFille.update(null);
    }

    public void modifier()throws Exception{
        Transfere transfere = this.findById(null,"v_transfere",id);
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+transfere.getStockMagasin().getId())[0];
        stockMagasinFille.setQuantite(stockMagasinFille.getQuantite() + transfere.getQuantite());
        stockMagasin = transfere.getStockMagasin();
        if(stockMagasinFille.getQuantite() < quantite){
            throw new Exception("quantite insuffisant");
        }
        stockMagasinFille.update(null);
        System.out.println(quantite);
        this.update(null);
        stockMagasinFille.setQuantite(stockMagasinFille.getQuantite() -quantite);
        stockMagasinFille.update(null);
    }

    public void supprimer(int id)throws Exception{
        Transfere transfere = this.findById(null,"v_transfere",id);
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from stock_magasin_fille where id_stock_magasin="+transfere.getStockMagasin().getId())[0];
        stockMagasinFille.setQuantite(stockMagasinFille.getQuantite() + transfere.getQuantite());
        transfere.delete(null);
        stockMagasinFille.update(null);
    }

}
