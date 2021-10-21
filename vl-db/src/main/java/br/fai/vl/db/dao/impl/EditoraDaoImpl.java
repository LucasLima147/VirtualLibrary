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
import br.fai.vl.db.dao.EditoraDao;
import br.fai.vl.model.Editora;

@Repository
public class EditoraDaoImpl implements EditoraDao {

	public List<Editora> readAll() {
		final List<Editora> editoras = new ArrayList<Editora>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM editora";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Editora editora = new Editora();
				editora.setId(resultSet.getInt("id"));
				editora.setEmail(resultSet.getString("email"));
				editora.setRazaoSocial(resultSet.getString("razaosocial"));
				editora.setNomeFastasia(resultSet.getString("nomefantasia"));
				editoras.add(editora);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return editoras;
	}

	public Editora readById(final int id) {
		Editora editora = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM editora WHERE id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				editora = new Editora();
				editora.setId(resultSet.getInt("id"));
				editora.setEmail(resultSet.getString("email"));
				editora.setRazaoSocial(resultSet.getString("razaosocial"));
				editora.setNomeFastasia(resultSet.getString("nomefantasia"));
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return editora;
	}

	public int create(final Editora entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		// sql
		sql = "INSERT INTO editora(email, razaosocial, nomefantasia) " + "VALUES( ?, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();

			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setString(2, entity.getRazaoSocial());
			preparedStatement.setString(3, entity.getNomeFastasia());

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

	public boolean update(final Editora entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "UPDATE editora SET email = ?, razaosocial = ?, nomefantasia = ?" + " WHERE id = ?;";

		try {
			// prepara a query
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setString(2, entity.getRazaoSocial());
			preparedStatement.setString(3, entity.getNomeFastasia());
			preparedStatement.setLong(4, entity.getId());

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
		final String sql = "DELETE FROM editora WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
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
