package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.AlunoDao;
import org.example.dao.CursoDao;
import org.example.dao.DisciplinaDao;
import org.example.dao.EnderecoDao;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Disciplina;
import org.example.model.Endereco;

public class Programa {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-app-pu");
        EntityManager em = emf.createEntityManager();

        // Instanciando classes Dao
        AlunoDao alunoDao = new AlunoDao(em);
        CursoDao cursoDao = new CursoDao(em);
        DisciplinaDao disciplinaDao = new DisciplinaDao(em);
        EnderecoDao enderecoDao = new EnderecoDao(em);

        // Criando curso
        Curso curso = new Curso();
        curso.setNome("Análise e Desenvolvimento de Sistemas");

        // Criando Disciplinas
        Disciplina bancodeDados = new Disciplina();
        Disciplina poo = new Disciplina();
        bancodeDados.setNome("Banco de Dados");
        poo.setNome("POO");

        // Criando endereço
        Endereco endereco1 = new Endereco();
        endereco1.setCidade("João Pessoa");
        endereco1.setRua("Epitácio Pessoa");

        // Criando Aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Kauê");
        aluno.setCpf("1234");

        aluno.setEndereco(endereco1); // Liga o aluno ao endereço
        aluno.setCurso(curso);        // Liga o aluno ao curso
        aluno.getDisciplinas().add(bancodeDados); // Adiciona disciplina na lista
        aluno.getDisciplinas().add(poo);

        // Salvando as dependências
        cursoDao.adicionarCurso(curso);
        disciplinaDao.adicionarDisciplina(bancodeDados);
        disciplinaDao.adicionarDisciplina(poo);
        enderecoDao.adicionarEndereco(endereco1);

        // Salvando o aluno
        alunoDao.adicionarAluno(aluno);


        Aluno alunoRecuperado = alunoDao.buscarPorId(aluno.getId());

        System.out.println("Aluno: " + alunoRecuperado.getNome());
        System.out.println("Cidade: " + alunoRecuperado.getEndereco().getCidade());
        System.out.println("Curso: " + alunoRecuperado.getCurso().getNome());
        System.out.println("Disciplinas matriculadas:");
        alunoRecuperado.getDisciplinas().forEach(d -> System.out.println("- " + d.getNome()));

        // Fechar as fábricas
        em.close();
        emf.close();

    }
}
