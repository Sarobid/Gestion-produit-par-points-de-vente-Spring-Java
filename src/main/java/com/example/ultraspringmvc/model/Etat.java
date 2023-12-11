package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "etat")
public class Etat extends ObjetBDD {
    @Sequence(name = "etat_id_etat_seq")
    @Column(name = "id_etat")
    private int id = -1;
    @Column(name = "nom_etat")
    private String nom = null;
    private int quantite = 0;
    public Etat(){}
    public Etat[] calculQuantite(int total,int quant)throws Exception{
        quantite = quant;
        if(total < quantite){
            throw new Exception("quantite eleve");
        }
        Etat[] etats = this.findQuery(null,"select * from etat");
        int i = 0;
        for (i= 0 ; i < etats.length ; i++){
            if(etats[i].getId() == id){
                etats[i].setQuantite(quantite);
            }else{
                etats[i].setQuantite(total-quantite);
            }
        }
        return etats;
    }
}
