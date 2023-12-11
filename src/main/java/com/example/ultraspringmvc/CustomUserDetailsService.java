package com.example.ultraspringmvc;
import com.example.ultraspringmvc.model.Compte;
import com.example.ultraspringmvc.model.PointsVente;
import com.example.ultraspringmvc.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Compte compte = null;
        CustomUser userDetails = null;
        try {
            compte = new Compte().authentification(username);
        } catch (Exception ex) {
        } finally {
            if (compte == null) {
                throw new UsernameNotFoundException("Not found username");
            }

            userDetails = new CustomUser(compte.getUsername(), compte.getPassword(), compte.getAuthorities());
            try {
                Utilisateur[] utilisateurs = new Utilisateur().findQuery(null,"select * from utilisateur where id_compte="+compte.getId());
                if(utilisateurs.length > 0){
                    utilisateurs[0].affecter();
                    userDetails.setUtilisateur(utilisateurs[0]);
                }
            }catch (Exception e){}
        }
        return userDetails;
    }
}

