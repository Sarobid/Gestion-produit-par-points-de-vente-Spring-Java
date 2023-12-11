package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

@Getter
@Setter
@Table(name = "laptop")
public class Laptop extends ObjetBDD {
    @Sequence(name = "laptop_id_laptop_seq")
    @Column(name = "id_laptop")
    private int id = -1;
    @Column(name = "id_model")
    private Models models = null;
    @Column(name = "id_processeur")
    private Processeur processeur = null;
    @Column(name = "id_ecran")
    private Ecran ecran = null;
    @Column(name = "id_ram")
    private Ram ram = null;
    @Column(name = "prix_achat")
    private double prix = -1.0;
    @Column(name = "prix_vente_magasin")
    private double prixVente = -1.0;
    public Laptop(){}

    public String affiche(){
        return models.getMarque().getNom() +" "+models.getNom()+" "+processeur.getTypeProcesseur().getNom()+""+processeur.getGeneration()+" "+processeur.getFrequence() +" Go Hz";
    }
    public void supprimerDisqueDure(DisqueDure disqueDure)throws Exception{
        String query = "DELETE FROM disque_laptop where id_laptop="+id+" and id_disque_dure="+disqueDure.getId();
        this.executeQuery(null,query);
    }
    public void nouveauDisque(DisqueDure disqueDure)throws Exception{
        String query = "INSERT INTO disque_laptop (id_laptop,id_disque_dure)values("+id+","+disqueDure.getId()+")";
        this.executeQuery(null,query);
    }
/*
    public static void supprimerRam(int id,int ram)throws Exception{
        Laptop laptop = new Laptop().findById(null,"v_laptop",id);
        Ram rams = new Ram().findById(null,"ram",ram);
        String query = "DELETE FROM ram_laptop where id_laptop="+id+" and id_ram="+ram;
        rams.executeQuery(null,query);
    }
    public void nouveauRam(Connection con,int id)throws Exception{
        Ram ram = new Ram().findById(null,"ram",id);
        String query = "INSERT INTO ram_laptop (id_laptop,id_ram)VALUES("+this.id+","+id+")";
        this.executeQuery(con,query);
    }
*/
}
