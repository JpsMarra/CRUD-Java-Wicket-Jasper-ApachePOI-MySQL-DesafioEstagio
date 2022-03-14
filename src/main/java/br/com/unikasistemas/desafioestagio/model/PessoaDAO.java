package br.com.unikasistemas.desafioestagio.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;

public class PessoaDAO implements Serializable {

    private static PessoaDAO instance;
    protected EntityManager entityManager;

//    Singleton, para poder usar "PessoaDAO.getInstance()"
    public static PessoaDAO getInstance(){
        if (instance == null){
            instance = new PessoaDAO();
        }
        return instance;
    }

    public PessoaDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("desafioestagio");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Pessoa getById(final long id) {
        return entityManager.find(Pessoa.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Pessoa> findAll() {
        return entityManager.createQuery("FROM " + Pessoa.class.getName()).getResultList();
    }

    public List<Pessoa> filterPessoa(Pessoa pessoa) {

        StringBuilder stringQuery = new StringBuilder("select p from Pessoa p ");
            stringQuery.append(" where tipoPessoa in (:tiposPessoa)");

        if(pessoa.getRazaoSocial() != null) {
            stringQuery.append(" and razaoSocial like :razaoSocial");
        }

        if(pessoa.getCpfCnpj() != null) {
            stringQuery.append(" and cpfCnpj like :cpfCnpj");
        }

        if(pessoa.getAtivoNome() != null){
            stringQuery.append(" and ativo = :ativo ");
        }

        Query sqlQuery = entityManager.createQuery(stringQuery.toString());

        if(pessoa.getTipoPessoa() != null){
            sqlQuery.setParameter("tiposPessoa", pessoa.getTipoPessoa());
        }else{
            sqlQuery.setParameter("tiposPessoa", pessoa.getTiposPessoa());
        }

        if(pessoa.getRazaoSocial() != null) {
            sqlQuery.setParameter("razaoSocial", "%" + pessoa.getRazaoSocial() + "%");
        }

        if(pessoa.getCpfCnpj() != null){
            sqlQuery.setParameter("cpfCnpj", "%" + pessoa.getCpfCnpj() + "%");
        }

        if(pessoa.getAtivoNome() != null){
            sqlQuery.setParameter("ativo", pessoa.isAtivo());
        }

        return sqlQuery.getResultList();
    }

    public void persist(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            pessoa = entityManager.find(Pessoa.class, pessoa.getIdPessoa());
            entityManager.remove(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final long id) {
        try {
            Pessoa pessoa = getById(id);
            remove(pessoa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Endereco getEnderecoById(final long id) {
        return entityManager.find(Endereco.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Endereco> findAllEnderecos() {
        return entityManager.createQuery("FROM " + Endereco.class.getName()).getResultList();
    }


    public void persist(Endereco endereco) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(endereco);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Endereco endereco) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(endereco);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeEndereco(Endereco endereco) {
        try {
            entityManager.getTransaction().begin();
            endereco = entityManager.find(Endereco.class, endereco.getIdEndereco());
            entityManager.remove(endereco);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeEnderecoById(final long id) {
        try {
            Endereco endereco = getEnderecoById(id);
            removeEndereco(endereco);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
