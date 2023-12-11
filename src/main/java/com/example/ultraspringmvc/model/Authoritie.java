package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = "authoritie")
public class Authoritie extends ObjetBDD {
    @Column(name = "role")
    private String authorithie = null;
    public Authoritie(){}
    public Collection<? extends GrantedAuthority> authoritiesCompte(int id)throws Exception{
        Authoritie[] autho = this.findQuery(null,"select * from authoritie where id_compte="+id);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authoritie authoritie:autho){
            authorities.add(new SimpleGrantedAuthority(authoritie.getAuthorithie()));
        }
        return authorities;
    }
}
