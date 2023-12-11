package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "points_vente")
public class PointsVente extends ObjetBDD {
    @Sequence(name = "points_vente_id_points_vente_seq")
    @Column(name = "id_points_vente")
    private int id = -1;
    @Column(name = "nom_points_vente")
    private String nom = null;
    private double montant = -1.0;
    private double commission = -1.0;
    public PointsVente(){}

    public PointsVente[] listeMontantVente(String mois,int annee) throws Exception{
        String query = "select \n" +
                "    sum(v_vente.quantite_vente * v_vente.prix) as montant,id_points_vente,nom_points_vente\n" +
                "    from v_vente where 1=1 ";
        if(mois.isEmpty() == false){
            query = query +" and EXTRACT(month from date_vente)="+mois+"";
        }
        if(annee > 0){
            query = query +" and EXTRACT(year FROM date_vente)="+annee;
        }
        query = query + " group by id_points_vente,nom_points_vente";
        return this.findQuery(null,query);
    }

    public PointsVente[] listeCommission(String mois,int annee)throws Exception{
        PointsVente[] pointsVentes = this.listeMontantVente(mois,annee);
        Commission[] commissions = new Commission().findQuery(null,"select * from commission order by max asc");
        int i = 0;
        for(i = 0 ; i < pointsVentes.length ; i++){
            pointsVentes[i].commissionner(commissions);
        }
        return pointsVentes;
    }

    public void commissionner(Commission[] commissions){
        commission = 0;
        double valeur = montant;
        int i = 0;
        for(i = 0 ; i < commissions.length; i++){
            if((valeur < commissions[i].getMax()) && (valeur > commissions[0].getMin())){
                if(i > 0){
                    commission = commission +  (commissions[i - 1].getMax() * commissions[i-1].getPourcentage());
                    valeur = valeur - commissions[i - 1].getMax();
                }else{
                    commission = commission + (valeur * commissions[i].getPourcentage());
                    valeur = 0;
                }
            }else if(valeur == commissions[i].getMax()){
                commission = commission + (commissions[i].getMax() * commissions[i].getPourcentage());
                valeur = valeur - commissions[i].getMax();
            }
        }
    }
}
