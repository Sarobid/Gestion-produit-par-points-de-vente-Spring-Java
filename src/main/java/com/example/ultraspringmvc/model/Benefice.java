package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "")
public class Benefice extends ObjetBDD {
    @Column(name = "id_points_vente")
    private PointsVente pointsVente = null;
    private String mois = null;
    private String annee = null;
    private int id_mois = -1;
    @Column(name = "sommme_achat")
    private double sommeAchat = 0;
    private double vente = 0;
    private double perte = 0;
    @Column(name = "benefice_brute")
    private double beneficeBrute = 0;
    private double benefice = 0;
    private double commissionValue = 0;
    private double beneficeCom = 0;
    public Benefice(){}


    public static String color(double valeur){
        String a ="";
        if(valeur <= 2000000){
            a="#71777B";
        } else if (valeur <= 50000000) {
            a="#0BF107";
        }else{
            a="#078EF1";
        }
        return a;
    }

    public Benefice[] listeBenefice(String mois,int annee)throws Exception{
        String query = "select * from v_benefice where (perte != 0 or vente !=0 or sommme_achat != 0 or benefice_brute != 0 or  benefice != 0) ";
        if(mois.isEmpty() == false){
            query = query + " and id_mois="+mois;
        }
        if(annee > 0){
            query = query + " and annee="+annee;
        }
        return this.findQuery(null,query);
    }

    public void listeBeneficeAvecCommission(Benefice[] benefices,Commission[] commissions){
        int i = 0;
        for(i = 0 ; i < benefices.length ; i++){
            benefices[i].setCommissionValue(Commission.calculCommission(commissions,Math.sqrt(benefices[i].getVente() * benefices[i].getVente())));
            benefices[i].setBeneficeCom(benefices[i].getBenefice() - benefices[i].getCommissionValue());
        }
    }
    public static Benefice calculTotal(Benefice[] benefices){
        Benefice benefice1 = new Benefice();
        for (Benefice benefice2:benefices){
            benefice1.setVente(benefice1.getVente()+benefice2.getVente());
            benefice1.setSommeAchat(benefice1.getSommeAchat() + benefice2.getSommeAchat());
            benefice1.setBeneficeBrute(benefice1.getBeneficeBrute() + benefice2.getBeneficeBrute());
            benefice1.setPerte(benefice1.getPerte() + benefice2.getPerte());
            benefice1.setBenefice(benefice1.getBenefice()+ benefice2.getBenefice());
            benefice1.setBeneficeCom(benefice1.getBeneficeCom()+ benefice2.getBeneficeCom());
            benefice1.setCommissionValue(benefice1.getCommissionValue()+benefice2.getCommissionValue());
        }
        return benefice1;
    }
}
