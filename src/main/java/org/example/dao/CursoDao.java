package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Curso;

import java.util.List;

public class CursoDao {

    private EntityManager em;

    public CursoDao (EntityManager em){
        this.em = em;
    }

    public void adicionarCurso (Curso curso) {
        try {
            em.getTransaction().begin(); // Começa a transação
            em.persist(curso); // Dá o INSERT no banco
            em.getTransaction().commit(); // Confirma a transação
        } catch (Exception e) {
            em.getTransaction().rollback(); // Se der errado volta atrás
        }
    }

    public void removerCursoPorId(Long id) {
        try {
            em.getTransaction().begin();
            // Primeiro buscamos o objeto para o EM gerenciar ele
            Curso curso = em.find(Curso.class, id);
            if (curso != null) {
                em.remove(curso);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Curso buscarPorId (Long id) {
        return em.find(Curso.class, id);
    }

    public List<Curso> listarTodos () {
        return em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList(); // Pega a lista do resultado da consulta
    }
}
