package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import com.example.ultraspringmvc.sql.Views;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "ram")
public class Ram extends ObjetBDD {
    @Sequence(name = "ram_id_ram_seq")
    @Column(name = "id_ram")
    private int id = -1;
    @Views(name = "ram")
    @Column(name = "valeur")
    private double valeur = -1.0;
    public Ram(){

    }
}
