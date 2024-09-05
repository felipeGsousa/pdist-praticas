package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UsuarioDAO {

    @PersistenceContext(unitName="default")
    private EntityManager em;

    public void inserir(Usuario novoUsuario){
        em.persist(novoUsuario);
    }

    public List<Usuario> listar() {
        return em.createQuery("FROM Usuario").getResultList();
    }
}
