package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Endereco;

import java.util.List;

public class EnderecoDao {

    private EntityManager em;

    public EnderecoDao (EntityManager em){
        this.em = em;
    }

    public void adicionarEndereco (Endereco endereco) {
        try {
            em.getTransaction().begin(); // Começa a transação
            em.persist(endereco); // Dá o INSERT no banco
            em.getTransaction().commit(); // Confirma a transação
        } catch (Exception e) {
            em.getTransaction().rollback(); // Se der errado volta atrás
        }
    }

    public void removerEnderecoPorId(Long id) {
        try {
            em.getTransaction().begin();
            // Primeiro buscamos o objeto para o EM gerenciar ele
            Endereco endereco = em.find(Endereco.class, id);
            if (endereco != null) {
                em.remove(endereco);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Endereco buscarPorId (Long id) {
        return em.find(Endereco.class, id);
    }

    public List<Endereco> listarTodos () {
        return em.createQuery("SELECT e FROM Endereco e", Endereco.class).getResultList(); // Pega a lista do resultado da consulta
    }
}
