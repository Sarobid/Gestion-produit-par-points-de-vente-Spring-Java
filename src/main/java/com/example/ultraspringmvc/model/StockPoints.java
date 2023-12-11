package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "stock_points")
public class StockPoints extends ObjetBDD {
    @Sequence(name = "stock_points_id_stock_points_seq")
    @Column(name = "id_stock_points")
    private int id = -1;
    @Column(name = "quantite_stock_point")
    private int quantite = -1;
    @Column(name = "id_reception_transfere")
    private ReceptionTransfere receptionTransfere = null;
    public StockPoints(){}
}
