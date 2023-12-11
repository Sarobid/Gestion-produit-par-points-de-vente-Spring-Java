package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "type_disque_dure")
public class TypeDisque extends ObjetBDD {
    @Sequence(name = "type_disque_dure_id_type_disque_dure_seq")
    @Column(name = "id_type_disque_dure")
    private int id = -1;
    @Column(name = "nom_disque_dure")
    private String nom = null;
    public TypeDisque(){}
}
