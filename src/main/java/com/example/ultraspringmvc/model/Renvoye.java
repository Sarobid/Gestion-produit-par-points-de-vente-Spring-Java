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
@Table(name = "renvoye")
public class Renvoye extends ObjetBDD {
    @Sequence(name = "renvoye_id_renvoye_seq")
    @Column(name = "id_renvoye")
    private int id= -1;
    @Column(name = "id_reception_transfere")
    private ReceptionTransfere receptionTransfere = null;
    @Column(name = "quantite_renvoyer")
    private int quantite = -1;
    @Column(name = "date_renvoye")
    private LocalDate date = null;
    public Renvoye(){}

    public void nouveauRenvoye(int idStock)throws Exception{
        StockPoints stockPoints = new StockPoints().findById(null,"stock_points",idStock);
        if(quantite > stockPoints.getQuantite()){
            throw new Exception("quantite eleve");
        }
        this.insert(null,null);
        stockPoints.setQuantite(stockPoints.getQuantite() - quantite);
        stockPoints.update(null);
    }

    public void supprimer(int id)throws Exception{
        Renvoye renvoye = this.findById(null,"v_renvoye",id);
        StockPoints stockPoints = (StockPoints) new StockPoints().findQuery(null,"select * from stock_points where id_reception_transfere="+renvoye.getReceptionTransfere().getId())[0];
        stockPoints.setQuantite(stockPoints.getQuantite() + renvoye.getQuantite());
        stockPoints.update(null);
        renvoye.delete(null);
    }

    public void supprimer(){

    }
}
