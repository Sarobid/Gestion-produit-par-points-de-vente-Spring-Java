package com.example.ultraspringmvc.model;

import com.example.ultraspringmvc.controller.ObjetBDD;
import com.example.ultraspringmvc.sql.Column;
import com.example.ultraspringmvc.sql.Sequence;
import com.example.ultraspringmvc.sql.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Table(name = "compte")
public class Compte extends ObjetBDD {
    @Sequence(name = "compte_id_compte_seq")
    @Column(name = "id_compte")
    private int id = -1;
    @Column(name = "username")
    private String username = null;
    @Column(name = "password")
    private String password = null;
    private Collection<? extends GrantedAuthority> authorities = null;

    public Compte(){}

    public void setUsername(String username)throws Exception {
        this.username = username;
        if(this.username.isEmpty() == true){
            throw new Exception("username obligatoire");
        }
    }

    public void setPassword(String password) throws Exception{
        this.password = password;
        if(this.password.isEmpty() == true){
            throw new Exception("mot de passe obligatoire");
        }
    }

    public Compte authentification(String username)throws Exception{
        Compte[] comptes = this.findQuery(null,"select * from compte where username='"+username+"'");
        Compte compte = null;
        if(comptes.length > 0){
            compte = comptes[0];
            compte.setAuthorities(new Authoritie().authoritiesCompte(compte.getId()));
        }
        return compte;
    }
}
