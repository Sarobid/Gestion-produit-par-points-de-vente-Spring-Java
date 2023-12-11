package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "marque")
public class Marque extends ObjetBDD {
    @Sequence(name = "marque_id_marque_seq")
    @Column(name = "id_marque")
    private int id = -1;
    @Column(name = "nom_marque")
    private String nom = null;
    public Marque(){}
}
