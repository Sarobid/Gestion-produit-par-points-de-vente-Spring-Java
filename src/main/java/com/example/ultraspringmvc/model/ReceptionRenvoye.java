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
@Table(name = "reception_renvoye")
public class ReceptionRenvoye extends ObjetBDD {
    @Sequence(name = "reception_renvoye_id_reception_renvoye_seq")
    @Column(name = "id_reception_renvoye")
    private int id=-1;
    @Column(name = "id_renvoye")
    private Renvoye renvoye = null;
    @Column(name = "quantite_recu_renv")
    private int quantite = -1;
    @Column(name = "id_etat")
    private Etat etat = null;
    @Column(name = "date_recu_renv")
    private LocalDate date = null;
    public ReceptionRenvoye(){}
    public void nouveauReception()throws Exception{
        renvoye = renvoye.findById(null,"v_renvoye",renvoye.getId());
        Etat[] etats = etat.calculQuantite(renvoye.getQuantite(),quantite);
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from v_stock_magasin_fille where id_stock_magasin="+renvoye.getReceptionTransfere().getTransfere().getStockMagasin().getId())[0];
        for(Etat etat1:etats){
            etat = etat1;
            quantite = etat1.getQuantite();
            this.insert(null,null);
            if(etat1.getId() == 1){
                if(etat1.getQuantite() > 0){
                    stockMagasinFille.setQuantite(stockMagasinFille.getQuantite()+etat1.getQuantite());
                    stockMagasinFille.update(null);
                }
            }
        }
    }
    public void modificationReception()throws Exception{
        ReceptionRenvoye receptionRenvoye = this.findById(null,"v_reception_renvoye",id);
        Renvoye renvoye1 = new Renvoye().findById(null,"v_renvoye",receptionRenvoye.getRenvoye().getId());
        Etat[] etats = etat.calculQuantite(renvoye1.getQuantite(),quantite);
        StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from v_stock_magasin_fille where id_stock_magasin="+renvoye1.getReceptionTransfere().getTransfere().getStockMagasin().getId())[0];
        id = -1;
        ReceptionRenvoye[] receptionRenvoyes = this.findQuery(null,"select * from v_reception_renvoye where id_renvoye="+receptionRenvoye.getRenvoye().getId());
        for(ReceptionRenvoye receptionRenvoye1:receptionRenvoyes){
            if(receptionRenvoye1.getEtat().getId() == 1){
                StockMagasinFille stockMagasinFille1 = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from v_stock_magasin_fille where id_stock_magasin="+renvoye1.getReceptionTransfere().getTransfere().getStockMagasin().getId())[0];
                stockMagasinFille1.setQuantite(stockMagasinFille1.getQuantite() - receptionRenvoye1.getQuantite());
                stockMagasinFille1.update(null);
            }
            receptionRenvoye1.delete(null);
        }
        id = -1;
        renvoye = renvoye1;
        for(Etat etat1:etats){
            etat = etat1;
            quantite = etat1.getQuantite();
            this.insert(null,null);
            if(etat1.getId() == 1){
                if(etat1.getQuantite() > 0){
                    stockMagasinFille.setQuantite(stockMagasinFille.getQuantite()+ etat1.getQuantite());
                    stockMagasinFille.update(null);
                }
            }
        }
    }

    public void supprimer(int idb)throws Exception{
        ReceptionRenvoye receptionRenvoye = this.findById(null,"v_reception_renvoye",idb);
        Renvoye renvoye1 = new Renvoye().findById(null,"v_renvoye",receptionRenvoye.getRenvoye().getId());
        ReceptionRenvoye[] receptionRenvoyes = this.findQuery(null,"select * from v_reception_renvoye where id_renvoye="+receptionRenvoye.getRenvoye().getId());
        for(ReceptionRenvoye receptionRenvoye1:receptionRenvoyes){
            if(receptionRenvoye1.getEtat().getId() == 1){
                StockMagasinFille stockMagasinFille = (StockMagasinFille) new StockMagasinFille().findQuery(null,"select * from v_stock_magasin_fille where id_stock_magasin="+renvoye1.getReceptionTransfere().getTransfere().getStockMagasin().getId())[0];
                stockMagasinFille.setQuantite(stockMagasinFille.getQuantite() - receptionRenvoye1.getQuantite());
                stockMagasinFille.update(null);
            }
            receptionRenvoye1.delete(null);
        }
    }

    public ReceptionRenvoye[] listeAll()throws Exception{
        ReceptionRenvoye[] receptionRenvoyes = this.findQuery(null,"select * from v_reception_renvoye ");
        int i = 0;
        for(i = 0 ; i < receptionRenvoyes.length ; i++){
            receptionRenvoyes[i].setRenvoye(receptionRenvoyes[i].getRenvoye().findById(null,"v_renvoye",receptionRenvoyes[i].getRenvoye().getId()));
        }
        return receptionRenvoyes;
    }
}
