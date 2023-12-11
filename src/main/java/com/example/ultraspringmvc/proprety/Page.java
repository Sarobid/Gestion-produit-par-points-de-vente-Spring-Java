package com.example.ultraspringmvc.proprety;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
    private int numero = 0;
    private String url = "";
    private int totalPage = 0;
}
