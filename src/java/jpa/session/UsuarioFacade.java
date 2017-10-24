/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.Usuario;

/**
 *
 * @author Dani
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }    
    
    public Usuario loginNick(String nick){
        List<Usuario> usuariosNick = em.createNamedQuery("Usuario.findByNick")
                .setParameter("nick", nick)
                .getResultList();
        
        if(usuariosNick.size() > 0){
            return usuariosNick.get(0);
        }
        
        return null;
    }
    
    public void editUser(Usuario usuarioLogin){
        edit(usuarioLogin);
    }
}
