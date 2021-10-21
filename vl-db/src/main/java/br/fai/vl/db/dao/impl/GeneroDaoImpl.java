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
import br.fai.vl.db.dao.GeneroDao;
import br.fai.vl.model.Genero;

@Repository
public class GeneroDaoImpl implements GeneroDao {

	public List<Genero> readAll() {
		final List<Genero> generos = new ArrayList<Genero>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM genero";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Genero genero = new Genero();
				genero.setId(resultSet.getInt("id"));
				genero.setCodigo(resultSet.getInt("codigo"));
				genero.setNome(resultSet.getString("nome"));
				genero.setDescricao(resultSet.getString("descricao"));
				generos.add(genero);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return generos;
	}

	public Genero readById(final int id) {
		Genero genero = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM genero WHERE id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				genero = new Genero();
				genero.setId(resultSet.getInt("id"));
				genero.setCodigo(resultSet.getInt("codigo"));
				genero.setNome(resultSet.getString("nome"));
				genero.setDescricao(resultSet.getString("descricao"));
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return genero;
	}

	public int create(final Genero entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		// sql
		sql = "INSERT INTO genero(codigo, nome, descricao) " + "VALUES(default, ?, ?);";
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
			preparedStatement.setString(2, entity.getDescricao());

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

	public boolean update(final Genero entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "UPDATE genero SET nome = ?, descricao = ?" + " WHERE id = ?;";

		try {
			// prepara a query
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getNome());
			preparedStatement.setString(2, entity.getDescricao());
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
		final String sql = "DELETE FROM genero WHERE id = ?;";

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