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
@Table(name = "commission")
public class Commission extends ObjetBDD {
    @Sequence(name = "commission_id_commission_seq")
    @Column(name = "id_commission")
    private int id= -1;
    @Column(name = "min")
    private double min = -1.0;
    @Column(name = "max")
    private double max = -1.0;
    @Column(name = "commission")
    private double pourcentage = -1.0;
    public Commission(){}

    @Override
    public void insert(Connection con, String t) throws Exception {
        Commission[] commissions = this.findQuery(null,"select * from commission order by max desc");
        if(commissions.length > 0){
            if(commissions[0].getMax() > max){
                throw new Exception("le dernier valeur max est "+commissions[0].getMax());
            }
            min = commissions[0].getMax() + 1;
        }else{
            min = 0;
        }
        super.insert(con, t);
    }

    public static Commission[] listeAll()throws Exception{
        return new Commission().findQuery(null,"select * from commission order by min asc");
    }

    public static double calculCommission(Commission[] commissions,double valeur){
        double com = 0.0;
        double valeurInit = 0;
        int i = 0;
        for(i = 0 ; i < commissions.length ; i++){
            if(valeur <= commissions[i].getMax()){
                com = com + (valeur * (commissions[i].getPourcentage()/100));
                break;
            } else{
                valeurInit =  commissions[i].getMax() - valeurInit ;
                com = com + ( (valeurInit)* (commissions[i].getPourcentage()/100));
                valeur = valeur - valeurInit;
            }
        }
        return com;
    }

    public static void main(String[] args) throws Exception{
        Commission[] commissions = new Commission().findQuery(null,"select * from commission order by max asc");
        double valeur = Commission.calculCommission(commissions,6000000);
        System.out.println(valeur);
    }
}
