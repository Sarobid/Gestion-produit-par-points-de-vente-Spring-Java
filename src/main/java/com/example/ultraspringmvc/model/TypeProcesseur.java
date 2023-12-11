package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "type_processeur")
public class TypeProcesseur extends ObjetBDD {
    @Sequence(name = "type_processeur_id_type_processeur_seq")
    @Column(name = "id_type_processeur")
    private int id = -1;
    @Column(name="nom_processeur")
    private String nom = null;
    public TypeProcesseur(){}
}
