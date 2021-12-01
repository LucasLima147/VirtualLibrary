package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.LivroDao;
import br.fai.vl.model.Livro;

@Repository
public class LivroDaoImpl implements LivroDao {

	public List<Livro> readAll() {
		final List<Livro> livros = new ArrayList<Livro>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM livro;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Livro livro = new Livro();
				livro.setId(resultSet.getInt("id"));
				livro.setIsbn(resultSet.getLong("isbn"));
				livro.setTitulo(resultSet.getString("titulo"));
				livro.setSinopse(resultSet.getString("sinopse"));
				livro.setNumPaginas(resultSet.getInt("numpaginas"));
				livro.setAtivo(resultSet.getBoolean("ativo"));
				livro.setEditoraId(resultSet.getInt("editora_id"));
				livro.setGeneroId(resultSet.getInt("genero_id"));
				livro.setAutorId(resultSet.getInt("autor_id"));
				livros.add(livro);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return livros;
	}

	public Livro readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Livro livro = null;

		try {
			final String sql = "SELECT * FROM livro WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				livro = new Livro();
				livro.setId(resultSet.getInt("id"));
				livro.setIsbn(resultSet.getLong("isbn"));
				livro.setTitulo(resultSet.getString("titulo"));
				livro.setSinopse(resultSet.getString("sinopse"));
				livro.setNumPaginas(resultSet.getInt("numpaginas"));
				livro.setAtivo(resultSet.getBoolean("ativo"));
				livro.setEditoraId(resultSet.getInt("editora_id"));
				livro.setGeneroId(resultSet.getInt("genero_id"));
				livro.setAutorId(resultSet.getInt("autor_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return livro;
	}

	public int create(final Livro entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);

		try {
			String sql = "INSERT INTO livro(isbn ,titulo, sinopse, numPaginas, ativo, editora_id, genero_id, autor_id) "
					+ "VALUES(?, ?, ?, ?, default, ?, ?, ?);";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, entity.getIsbn());
			preparedStatement.setString(2, entity.getTitulo());
			preparedStatement.setString(3, entity.getSinopse());
			preparedStatement.setInt(4, entity.getNumPaginas());
			preparedStatement.setInt(5, entity.getEditoraId());
			preparedStatement.setInt(6, entity.getGeneroId());
			preparedStatement.setInt(7, entity.getAutorId());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				preparedStatement.close();

				for (int i = 0; i < entity.getQtdExemplar(); i++) {
					final Random random = new Random();

					sql = "INSERT INTO exemplar(edicao, estadoconservacao, livro_id) VALUES(?, ?, ?);";
					preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setInt(1, random.nextInt(10) + 1);
					preparedStatement.setString(2, "OTIMO");
					preparedStatement.setInt(3, id);

					preparedStatement.execute();
					resultSet = preparedStatement.getGeneratedKeys();

					if (!resultSet.next()) {
						connection.rollback();
						ConnectionFactory.close(resultSet, preparedStatement, connection);
						return -1;
					}
				}

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

	public boolean update(final Livro entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			final String sql = "UPDATE livro SET titulo = ?, sinopse = ?, numpaginas = ?, ativo = ?, "
					+ "editora_id = ?, genero_id = ?, autor_id = ? WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getTitulo());
			preparedStatement.setString(2, entity.getSinopse());
			preparedStatement.setInt(3, entity.getNumPaginas());
			preparedStatement.setBoolean(4, entity.isAtivo());
			preparedStatement.setInt(5, entity.getEditoraId());
			preparedStatement.setInt(6, entity.getGeneroId());
			preparedStatement.setInt(7, entity.getAutorId());
			preparedStatement.setInt(8, entity.getId());

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
			final String sql = "DELETE FROM livro WHERE id = ?;";

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
