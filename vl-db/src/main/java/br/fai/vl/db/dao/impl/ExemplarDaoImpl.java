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
import br.fai.vl.db.dao.ExemplarDao;
import br.fai.vl.model.Exemplar;

@Repository
public class ExemplarDaoImpl implements ExemplarDao {

	public List<Exemplar> readAll() {
		final List<Exemplar> exemplares = new ArrayList<Exemplar>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM exemplar;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Exemplar exemplar = new Exemplar();
				exemplar.setId(resultSet.getInt("id"));
				exemplar.setEdicao(resultSet.getInt("edicao"));
				exemplar.setEstadoConservacao(resultSet.getString("estadoconservacao"));
				exemplar.setLivroId(resultSet.getInt("livro_id"));
				exemplares.add(exemplar);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return exemplares;
	}

	public Exemplar readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Exemplar exemplar = null;

		try {
			final String sql = "SELECT * FROM exemplar WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				exemplar = new Exemplar();
				exemplar.setId(resultSet.getInt("id"));
				exemplar.setEdicao(resultSet.getInt("edicao"));
				exemplar.setEstadoConservacao(resultSet.getString("estadoconservacao"));
				exemplar.setLivroId(resultSet.getInt("livro_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return exemplar;
	}

	public int create(final Exemplar entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);

		try {
			final String sql = "INSERT INTO exemplar(edicao ,estadoconservacao, livro_id) VALUES(?, ?, ?);";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getEdicao());
			preparedStatement.setString(2, entity.getEstadoConservacao());
			preparedStatement.setInt(3, entity.getLivroId());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			connection.commit();
		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
				System.out.println(e2.getMessage());
			}
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return id;
	}

	public boolean update(final Exemplar entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			final String sql = "UPDATE exemplar SET edicao = ?, estadoconservacao = ?, livro_id = ? WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, entity.getEdicao());
			preparedStatement.setString(2, entity.getEstadoConservacao());
			preparedStatement.setInt(3, entity.getLivroId());
			preparedStatement.setInt(4, entity.getId());

			preparedStatement.execute();
			connection.commit();

			return true;

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
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

		try {
			final String sql = "DELETE FROM exemplar WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

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
