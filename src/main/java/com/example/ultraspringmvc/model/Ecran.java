package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "ecran")
public class Ecran extends ObjetBDD {
    @Sequence(name = "ecran_id_ecran_seq")
    @Column(name = "id_ecran")
    private int id=-1;
    @Column(name = "valeur")
    private double valeur = -1.0;
    public Ecran(){}
}
