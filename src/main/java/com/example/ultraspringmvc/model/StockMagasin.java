package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "stock_magasin")
public class StockMagasin extends ObjetBDD {
    @Sequence(name = "stock_magasin_id_stock_magasin_seq")
    @Column(name = "id_stock_magasin")
    private int id = -1;
    @Column(name = "id_laptop")
    private Laptop laptop = null;
    @Column(name = "quantite_magasin")
    private int quantite = -1;
    @Column(name = "date_entree")
    private LocalDate dateEntree = null;
    public StockMagasin(){}
}
