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
@Table(name = "reception_transfere")
public class ReceptionTransfere extends ObjetBDD {
    @Sequence(name = "reception_transfere_id_reception_transfere_seq")
    @Column(name = "id_reception_transfere")
    private int id = -1;
    @Column(name = "id_transfert")
    private Transfere transfere = null;
    @Column(name = "quantite_recu_trans")
    private int quantite = -1;
    @Column(name = "id_etat")
    private Etat etat = null;
    @Column(name = "date_recu_trans")
    private LocalDate date = null;
    public ReceptionTransfere(){}

    public void nouveauReception()throws Exception{
        transfere = transfere.findById(null,"transfere",transfere.getId());
        Etat[] etats = etat.calculQuantite(transfere.getQuantite(),quantite);
        if(date.isBefore(transfere.getDate())){
            throw new Exception("date incorrecte");
        }
        StockPoints stockPoints = new StockPoints();
        for(Etat etat1:etats){
            etat = etat1;
            quantite = etat1.getQuantite();
            this.insert(null,null);
            if(etat1.getId() == 1){
                if(etat1.getQuantite() > 0){
                    stockPoints.setReceptionTransfere(this);
                    stockPoints.setQuantite(etat1.getQuantite());
                    stockPoints.insert(null,null);
                }
            }
        }
    }

    public void modification()throws Exception{
        ReceptionTransfere receptionTransfere = this.findById(null,"v_reception_trans",id);
        ReceptionTransfere[] receptionTransferes = this.findQuery(null,"select * from v_reception_trans where id_transfert="+receptionTransfere.getTransfere().getId());
        Transfere transfere1 = new Transfere().findById(null,"v_transfere",receptionTransfere.getTransfere().getId());
        Etat[] etats = etat.calculQuantite(transfere1.getQuantite(),quantite);
        for(ReceptionTransfere receptionTransfere1:receptionTransferes){
            if(receptionTransfere1.getEtat().getId() == 1){
                StockPoints stockPoints1 = (StockPoints) new StockPoints().findQuery(null,"select * from stock_points where id_reception_transfere="+receptionTransfere1.getId())[0];
                stockPoints1.delete(null);
            }
            receptionTransfere1.delete(null);
        }
        id = -1;
        transfere = transfere1;
        if(date.isBefore(transfere.getDate())){
            throw new Exception("date incorrecte");
        }
        StockPoints stockPoints = new StockPoints();
        for(Etat etat1:etats){
            etat = etat1;
            quantite = etat1.getQuantite();
            this.insert(null,null);
            if(etat1.getId() == 1){
                if(etat1.getQuantite() > 0){
                    stockPoints.setReceptionTransfere(this);
                    stockPoints.setQuantite(etat1.getQuantite());
                    stockPoints.insert(null,null);
                }
            }
        }
    }
    public void supprimer(int id)throws  Exception{
        ReceptionTransfere receptionTransfere = this.findById(null,"v_reception_trans",id);
        ReceptionTransfere[] receptionTransferes = this.findQuery(null,"select * from v_reception_trans where id_transfert="+receptionTransfere.getTransfere().getId());
        Transfere transfere1 = new Transfere().findById(null,"v_transfere",receptionTransfere.getTransfere().getId());
        for(ReceptionTransfere receptionTransfere1:receptionTransferes){
            if(receptionTransfere1.getEtat().getId() == 1){
                StockPoints stockPoints1 = (StockPoints) new StockPoints().findQuery(null,"select * from stock_points where id_reception_transfere="+receptionTransfere1.getId())[0];
                stockPoints1.delete(null);
            }
            receptionTransfere1.delete(null);
        }
    }

}
