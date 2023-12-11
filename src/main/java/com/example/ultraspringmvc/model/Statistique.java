package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "statistique")
public class Statistique extends ObjetBDD {
    private double nombre = 0;
    private String mois = null;
    @Column(name = "id_points_vente")
    private PointsVente pointsVente = null;
    private double benefice = 0.0;
    public Statistique(){}

    public Statistique[] venteParMoisGlobal(String mois,int annee)throws Exception{
        String query = "select TO_CHAR(date_vente, 'Month')  as mois,SUM(quantite_vente * prix) as nombre,id_points_vente,nom_points_vente\n" +
                "from v_vente where 1=1 ";
        if(mois.isEmpty() == false){
            query = query +" and EXTRACT(month from date_vente)="+mois+"";
        }
        if(annee > 0){
            query = query +" and EXTRACT(year FROM date_vente)="+annee;
        }
        query = query + " group by TO_CHAR(date_vente, 'Month'),id_points_vente,nom_points_vente " ;
        return this.findQuery(null,query);
    }
    public static double totalVente(Statistique[] statistiques){
        double total = 0;
        for(Statistique statistique:statistiques){
            total = total + statistique.getNombre();
        }
        return total;
    }

    public Statistique[] venteParMoisParPointsVente(String mois,int annee)throws Exception{
        String query = "select TO_CHAR(date_vente, 'Month')  as mois,SUM(quantite_vente * prix) as nombre,id_points_vente,nom_points_vente\n" +
                "from v_vente where 1=1 ";
        if(mois.isEmpty() == false){
            query = query +" and EXTRACT(month from date_vente)="+mois+"";
        }
        if(annee > 0){
            query = query +" and EXTRACT(year FROM date_vente)="+annee;
        }
        query = query +" group by TO_CHAR(date_vente, 'Month'),id_points_vente,nom_points_vente";
        System.out.println(query);
        return this.findQuery(null,query);
    }

    public Statistique[] beneficeParMois(int annee) throws Exception{

        String query = "";
        if(annee <= 0){
            query = "select v_liste.mois,COALESCE(vr.rendement,0) - COALESCE(vd.depense) as benefice from v_liste\n" +
                    "left join (select\n" +
                    "        TO_CHAR(date_entree, 'Month')  as mois,sum(quantite_magasin * prix_achat) as depense\n" +
                    "from v_stock_magasin group by TO_CHAR(date_entree, 'Month')) vd on v_liste.mois = vd.mois\n" +
                    "left join (select\n" +
                    "               TO_CHAR(date_vente, 'Month')  as mois,SUM(quantite_vente*prix) as rendement\n" +
                    "           from v_vente group by TO_CHAR(date_vente, 'Month')) vr on v_liste.mois = vr.mois;\n";
        }else{
            query = "select v_liste.mois,COALESCE(vr.rendement,0) - COALESCE(vd.depense) as benefice from v_liste\n" +
                    "left join (select\n" +
                    "        TO_CHAR(date_vente, 'Month')  as mois,sum(quantite_magasin * prix_achat) as depense\n" +
                    "from v_stock_magasin where EXTRACT(year FROM date_vente)="+annee+" group by TO_CHAR(date_vente, 'Month')) vd on v_liste.mois = vd.mois\n" +
                    "left join (select\n" +
                    "               TO_CHAR(date_vente, 'Month')  as mois,SUM(quantite_vente*prix) as rendement\n" +
                    "           from v_vente where EXTRACT(year FROM date_vente)="+annee+"group by TO_CHAR(date_vente, 'Month')) vr on v_liste.mois = vr.mois;\n";
        }

        return this.findQuery(null,query);
    }
}
