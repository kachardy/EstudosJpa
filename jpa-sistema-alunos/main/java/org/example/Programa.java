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

import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-app-pu");
        EntityManager em = emf.createEntityManager();
        Scanner sc = new Scanner(System.in);

        // Instanciando classes Dao
        AlunoDao alunoDao = new AlunoDao(em);
        CursoDao cursoDao = new CursoDao(em);
        DisciplinaDao disciplinaDao = new DisciplinaDao(em);
        EnderecoDao enderecoDao = new EnderecoDao(em);


        String opcao = "";
        while (!opcao.equals("0")) {

        System.out.println("""
                --------Menu---------
                1 - Cadastrar Aluno
                2 - Cadastrar Curso
                3 - Cadastrar Disciplina
                4 - Adicionar Aluno na Disciplina/Curso
                5 - Remover aluno
                6 - Atualizar aluno
                7 - Buscar aluno pelo ID
                8 - Exibir todos os alunos
                9 - Exibir todos os cursos
                10 - Exibir todas as disciplinas
                11 - Listar alunos por disciplinas
                0 - Sair
                --------------------------""");

            System.out.println("Digite uma opção: ");
            opcao = sc.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.println("Nome do aluno: ");
                    String nome = sc.nextLine();

                    System.out.println("CPF do aluno: ");
                    String cpf = sc.nextLine();

                    System.out.println("Endereço do aluno");
                    System.out.println("Rua: ");
                    String rua = sc.nextLine();

                    System.out.println("Cidade: ");
                    String cidade = sc.nextLine();

                    Aluno aluno = new Aluno();
                    Endereco endereco = new Endereco();

                    endereco.setRua(rua);
                    endereco.setCidade(cidade);
                    aluno.setNome(nome);
                    aluno.setCpf(cpf);
                    aluno.setEndereco(endereco);

                    enderecoDao.adicionarEndereco(endereco);
                    alunoDao.adicionarAluno(aluno);
                }

                case "2" -> {
                    System.out.println("Nome do Curso: ");
                    String nomeCurso = sc.nextLine();

                    Curso curso = new Curso();
                    curso.setNome(nomeCurso);
                    cursoDao.adicionarCurso(curso);
                }

                case "3" -> {
                    System.out.println("Nome da Disciplina: ");
                    String nomeDisciplina = sc.nextLine();

                    Disciplina disciplina = new Disciplina();
                    disciplina.setNome(nomeDisciplina);
                    disciplinaDao.adicionarDisciplina(disciplina);
                }

                case "4" -> {
                    // Por enquanto ele só paga uma disciplina por curso
                    System.out.println("Qual o id do aluno que deseja matricular? ");
                    Aluno aluno = alunoDao.buscarPorId(Long.parseLong(sc.nextLine()));

                    System.out.println("Qual o id do curso que o aluno vai fazer? ");
                    Curso curso = cursoDao.buscarPorId(Long.parseLong(sc.nextLine()));

                    System.out.println("Qual o id da disciplina que o aluno vai pagar? ");
                    Disciplina disciplina = disciplinaDao.buscarPorId(Long.parseLong(sc.nextLine()));

                    if (aluno == null || curso == null || disciplina == null) {
                        System.out.println("Tente novamente, alguns dos parâmetros não existe!");
                        break;
                    }
                    aluno.setCurso(curso);
                    aluno.getDisciplinas().add(disciplina);
                    alunoDao.atualizarAluno(aluno);
                }
                case "5" -> {
                    System.out.println("Qual o id do aluno que deseja remover? ");
                    Long id =Long.parseLong(sc.nextLine());
                    alunoDao.removerAlunoPorId(id);
                }
                case "6" -> {
                    System.out.println("Qual o id do aluno que deseja atualizar? ");
                    Aluno aluno = alunoDao.buscarPorId(Long.parseLong(sc.nextLine()));

                    if (aluno == null) {
                        System.out.println("Esse aluno não existe!");
                        break;
                    }

                    System.out.println("Nome do aluno: ");
                    String nome = sc.nextLine();

                    System.out.println("CPF do aluno: ");
                    String cpf = sc.nextLine();

                    System.out.println("Endereço do aluno");
                    System.out.println("Rua: ");
                    String rua = sc.nextLine();

                    System.out.println("Cidade: ");
                    String cidade = sc.nextLine();

                    aluno.getEndereco().setRua(rua);
                    aluno.getEndereco().setCidade(cidade);
                    aluno.setNome(nome);
                    aluno.setCpf(cpf);
                    alunoDao.atualizarAluno(aluno);
                }
                case "7" -> {
                    System.out.println("Qual o id do aluno que deseja buscar? ");
                    Long id =Long.parseLong(sc.nextLine());
                    Aluno aluno = alunoDao.buscarPorId(id);
                    if (aluno == null) {
                        System.out.println("Esse aluno não existe!");
                        break;
                    }
                    System.out.println(aluno);
                }
                case "8" -> {
                    System.out.println(alunoDao.listarTodos());
                }
                case "9" -> {
                    System.out.println(cursoDao.listarTodos());
                }
                case "10" -> {
                    System.out.println(disciplinaDao.listarTodas());
                }
                case "11" -> {
                    System.out.println("Digite o id da disciplina que deseja ver os alunos: ");
                    Disciplina disciplina = disciplinaDao.buscarPorId(Long.parseLong(sc.nextLine()));

                    if (disciplina == null) {
                        System.out.println("Essa disciplina não existe!");
                        break;
                    }
                    System.out.println(alunoDao.buscarAlunoPorDisciplina(disciplina.getId()));
                }
                case "0" -> {
                    System.out.println("Saindo do sistema...");
                }
                default -> {
                    System.out.println("Digite uma opção válida!!");
                }
            }
        }

        // Fechar as fábricas
        em.close();
        emf.close();

    }
}
