package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "processeur")
public class Processeur extends ObjetBDD {
    @Sequence(name = "processeur_id_processeur_seq")
    @Column(name = "id_processeur")
    private int id = -1;
    @Column(name = "id_type_processeur")
    private TypeProcesseur typeProcesseur = null;
    @Column(name = "generation")
    private int generation = -1;
    @Column(name = "frequence")
    private double frequence = -1.0;
    public Processeur(){}


}
