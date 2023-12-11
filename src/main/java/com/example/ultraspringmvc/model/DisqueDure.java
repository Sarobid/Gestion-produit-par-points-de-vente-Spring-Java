package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "disque_dure")
public class DisqueDure extends ObjetBDD {
    @Sequence(name = "disque_dure_id_disque_dure_seq")
    @Column(name = "id_disque_dure")
    private int id=-1;
    @Column(name = "id_type_disque_dure")
    private TypeDisque typeDisque = null;
    @Column(name = "memoire")
    private double memoire = -1.0;
    public DisqueDure(){}
}
