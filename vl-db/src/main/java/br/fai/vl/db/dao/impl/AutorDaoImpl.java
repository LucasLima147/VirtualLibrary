package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.AutorDao;
import br.fai.vl.model.Autor;

@Repository
public class AutorDaoImpl implements AutorDao {

	public List<Autor> readAll() {
		final List<Autor> autores = new ArrayList<Autor>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM autor;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Autor autor = new Autor();
				autor.setId(resultSet.getInt("id"));
				autor.setNome(resultSet.getString("nome"));
				autor.setObra(resultSet.getString("obra"));
				autor.setFrase(resultSet.getString("frase"));
				autores.add(autor);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return autores;
	}

	public Autor readById(final int id) {
		Autor autor = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM autor A WHERE A.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				autor = new Autor();
				autor.setId(resultSet.getInt("id"));
				autor.setNome(resultSet.getString("nome"));
				autor.setObra(resultSet.getString("obra"));
				autor.setFrase(resultSet.getString("frase"));
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return autor;
	}

	public int create(final Autor entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		// sql
		sql = "INSERT INTO autor(nome, obra, frase) " + "VALUES(?, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();

			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getNome());
			preparedStatement.setString(2, entity.getObra());
			preparedStatement.setString(3, entity.getFrase());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			connection.commit();
		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return id;
	}

	public boolean update(final Autor entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "UPDATE autor SET obra = ?, frase = ?" + " WHERE id = ?;";

		try {
			// prepara a query
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getObra());
			preparedStatement.setString(2, entity.getFrase());
			preparedStatement.setLong(3, entity.getId());

			// executa a query
			preparedStatement.execute();
			connection.commit();

			return true;
		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

			return false;
		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}

	public boolean delete(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "DELETE FROM autor WHERE id = ?;";

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

			// executa a query
			preparedStatement.execute();
			connection.commit();
			return true;

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

			return false;
		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}
}
