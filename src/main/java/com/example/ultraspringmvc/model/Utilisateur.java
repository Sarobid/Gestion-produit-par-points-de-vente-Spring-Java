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
@Table(name = "utilisateur")
public class Utilisateur extends ObjetBDD {
    @Sequence(name = "utilisateur_id_utilisateur_seq")
    @Column(name = "id_utilisateur")
    private int id = -1;
    @Column(name = "nom_utilisateur")
    private String nom = null;
    private PointsVente id_points_vente = null;
    private LocalDate date_affectation = null;

    public void affecter()throws Exception{
        Utilisateur[] utilisateurs = this.findQuery(null,"select * from v_affectation where id_utilisateur="+id+" and date_fin is null");
        if(utilisateurs.length > 0){
            setId_points_vente(utilisateurs[0].getId_points_vente());
        }
    }
    public Utilisateur(){}

    public void desafectation()throws Exception{
        String query = "UPDATE affectation set date_fin=now() where id_utilisateur="+id+" and id_points_vente="+id_points_vente.getId()+" and date_fin is null";
        this.executeQuery(null,query);
    }
    public void nouveauAffectation()throws Exception{
        this.date_affectation = LocalDate.now();
        String query = "INSERT INTO affectation (id_utilisateur,id_points_vente,date_affectation)VALUES("+id+","+id_points_vente.getId()+",now())";
        this.executeQuery(null,query);
    }
    public static void nouveauUtilisateur(Compte compte,String nom)throws Exception{
        compte.insert(null,null);
        String query1= "INSERT INTO authoritie(id_compte,role)VALUES("+compte.getId()+",'ROLE_USER')";
        compte.executeQuery(null,query1);
        String query2 = "INSERT INTO Utilisateur (id_compte,nom_utilisateur)VALUES("+compte.getId()+",'"+nom+"')";
        compte.executeQuery(null,query2);
    }
}
