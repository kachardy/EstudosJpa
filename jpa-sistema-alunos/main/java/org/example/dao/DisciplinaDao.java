package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Disciplina;


import java.util.List;

public class DisciplinaDao {

    private EntityManager em;

    public DisciplinaDao (EntityManager em){
        this.em = em;
    }

    public void adicionarDisciplina (Disciplina disciplina) {
        try {
            em.getTransaction().begin(); // Começa a transação
            em.persist(disciplina); // Dá o INSERT no banco
            em.getTransaction().commit(); // Confirma a transação
        } catch (Exception e) {
            em.getTransaction().rollback(); // Se der errado volta atrás
        }
    }

    public void removerCursoPorId(Long id) {
        try {
            em.getTransaction().begin();
            // Primeiro buscamos o objeto para o EM gerenciar ele
            Disciplina disciplina = em.find(Disciplina.class, id);
            if (disciplina != null) {
                em.remove(disciplina);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Disciplina buscarPorId (Long id) {
        return em.find(Disciplina.class, id);
    }

    public List<Disciplina> listarTodas () {
        return em.createQuery("SELECT d FROM Disciplina d", Disciplina.class).getResultList(); // Pega a lista do resultado da consulta
    }
}
