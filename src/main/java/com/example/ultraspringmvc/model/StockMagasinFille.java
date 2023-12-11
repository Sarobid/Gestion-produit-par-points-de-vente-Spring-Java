package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "stock_magasin_fille")
public class StockMagasinFille extends ObjetBDD {
    @Sequence(name = "stock_magasin_fille_id_stock_magasin_fille_seq")
    @Column(name = "id_stock_magasin_fille")
    private int id = -1;
    @Column(name = "id_stock_magasin")
    private StockMagasin stockMagasin = null;
    @Column(name = "quantite_magasin_fille")
    private int quantite = -1;
    public StockMagasinFille(){}
}
