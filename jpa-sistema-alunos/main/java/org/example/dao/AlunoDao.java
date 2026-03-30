package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Aluno;

import java.util.List;

public class AlunoDao {

    private EntityManager em;

    public AlunoDao(EntityManager em) {
        this.em = em;
    }

    public void adicionarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin(); // Começa a transação
            em.persist(aluno); // Dá o INSERT no banco
            em.getTransaction().commit(); // Confirma a transação
        } catch (Exception e) {
            em.getTransaction().rollback(); // Se der errado volta atrás
        }
    }

    public void removerAlunoPorId(Long id) {
        try {
            em.getTransaction().begin();
            // Primeiro buscamos o objeto para o EM gerenciar ele
            Aluno aluno = buscarPorId(id);
            if (aluno != null) {
                em.remove(aluno);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public List<Aluno> buscarAlunoPorDisciplina(Long idDisciplina) {
        String jpql = "SELECT a from Aluno a JOIN a.disciplinas d WHERE d.id = :idRecebido";
        return em.createQuery(jpql, Aluno.class)
                .setParameter("idRecebido", idDisciplina) // Vincula o parâmetro
                .getResultList();
    }

    public void atualizarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Aluno buscarPorId(Long id) {
        return em.find(Aluno.class, id);
    }

    public List<Aluno> listarTodos() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList(); // Pega a lista do resultado da consulta
    }
}
