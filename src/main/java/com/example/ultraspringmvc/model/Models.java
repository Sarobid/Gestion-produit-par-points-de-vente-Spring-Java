package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "model")
public class Models extends ObjetBDD {
    @Sequence(name = "model_id_model_seq")
    @Column(name = "id_model")
    private int id= -1;
    @Column(name = "nom_model")
    private String nom = null;
    @Column(name = "id_marque")
    private Marque marque = null;
    public Models(){}
}
