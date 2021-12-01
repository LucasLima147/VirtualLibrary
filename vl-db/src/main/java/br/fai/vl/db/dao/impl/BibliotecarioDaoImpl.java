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
import br.fai.vl.db.dao.BibliotecarioDao;
import br.fai.vl.model.Bibliotecario;

@Repository
public class BibliotecarioDaoImpl implements BibliotecarioDao {

	public List<Bibliotecario> readAll() {
		final List<Bibliotecario> bibliotecarios = new ArrayList<Bibliotecario>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM bibliotecario B inner join pessoa P on B.pessoa_id = P.id;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Bibliotecario bibliotecario = new Bibliotecario();
				bibliotecario.setId(resultSet.getInt("id"));
				bibliotecario.setCpf(resultSet.getString("cpf"));
				bibliotecario.setNome(resultSet.getString("nome"));
				bibliotecario.setRua(resultSet.getString("rua"));
				bibliotecario.setNumero(resultSet.getInt("numero"));
				bibliotecario.setBairro(resultSet.getString("bairro"));
				bibliotecario.setCidade(resultSet.getString("cidade"));
				bibliotecario.setEstado(resultSet.getString("estado"));
				bibliotecario.setTelefone(resultSet.getString("telefone"));
				bibliotecario.setRegistro(resultSet.getInt("registro"));
				bibliotecario.setEmail(resultSet.getString("email"));
				bibliotecario.setSenha(resultSet.getString("senha"));
				bibliotecario.setPessoaId(resultSet.getInt("pessoa_id"));
				bibliotecario.setTipo("BIBLIOTECARIO");
				bibliotecarios.add(bibliotecario);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return bibliotecarios;
	}

	public Bibliotecario readById(final int id) {
		Bibliotecario bibliotecario = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM  bibliotecario B left JOIN pessoa P on P.id = B.pessoa_id WHERE B.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				bibliotecario = new Bibliotecario();
				bibliotecario.setId(resultSet.getInt("id"));
				bibliotecario.setCpf(resultSet.getString("cpf"));
				bibliotecario.setNome(resultSet.getString("nome"));
				bibliotecario.setRua(resultSet.getString("rua"));
				bibliotecario.setNumero(resultSet.getInt("numero"));
				bibliotecario.setBairro(resultSet.getString("bairro"));
				bibliotecario.setCidade(resultSet.getString("cidade"));
				bibliotecario.setEstado(resultSet.getString("estado"));
				bibliotecario.setTelefone(resultSet.getString("telefone"));
				bibliotecario.setRegistro(resultSet.getInt("registro"));
				bibliotecario.setEmail(resultSet.getString("email"));
				bibliotecario.setSenha(resultSet.getString("senha"));
				bibliotecario.setPessoaId(resultSet.getInt("pessoa_id"));
				bibliotecario.setTipo("BIBLIOTECARIO");
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return bibliotecario;
	}

	public int create(final Bibliotecario entity) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		// sql
		sql = "INSERT INTO pessoa(cpf, nome, rua, numero, bairro, cidade, estado, telefone) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();

			// para impedir que, caso for inserir dados em mais de uma tabela, não seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getCpf());
			preparedStatement.setString(2, entity.getNome());
			preparedStatement.setString(3, entity.getRua());
			preparedStatement.setInt(4, entity.getNumero());
			preparedStatement.setString(5, entity.getBairro());
			preparedStatement.setString(6, entity.getCidade());
			preparedStatement.setString(7, entity.getEstado());
			preparedStatement.setString(8, entity.getTelefone());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				preparedStatement.close();
				sql = "INSERT INTO bibliotecario(registro, email, senha, pessoa_id)" + "VALUES(?, ?, ?, ?);";

				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, entity.getRegistro());
				preparedStatement.setString(2, entity.getEmail());
				preparedStatement.setString(3, entity.getSenha());
				preparedStatement.setInt(4, id);

				preparedStatement.execute();
				resultSet = preparedStatement.getGeneratedKeys();

				if (resultSet.next()) {
					id = resultSet.getInt("id");
				}
				result = true;
			}
			;
			if (result) {
				connection.commit();
			} else {
				connection.rollback();
			}

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

	public boolean update(final Bibliotecario entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "UPDATE pessoa SET nome = ?, telefone = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?"
				+ " WHERE id = ?;";
		try {
			connection = ConnectionFactory.getConnection();

			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getNome());
			preparedStatement.setString(2, entity.getTelefone());
			preparedStatement.setString(3, entity.getRua());
			preparedStatement.setInt(4, entity.getNumero());
			preparedStatement.setString(5, entity.getBairro());
			preparedStatement.setString(6, entity.getCidade());
			preparedStatement.setString(7, entity.getEstado());
			preparedStatement.setInt(8, entity.getPessoaId());

			preparedStatement.execute();
			if (preparedStatement.getUpdateCount() != -1) {

				preparedStatement.close();
				sql = "UPDATE bibliotecario SET email = ?, senha = ?" + " WHERE id = ?;";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, entity.getEmail());
				preparedStatement.setString(2, entity.getSenha());
				preparedStatement.setLong(3, entity.getId());

				preparedStatement.execute();
				if (preparedStatement.getUpdateCount() != -1) {
					connection.commit();
				} else {
					connection.rollback();
				}
			}
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
		final String sql = "delete from pessoa P where P.id = (select L.pessoa_id from bibliotecario L where L.id = ?);";

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

	public int checkEmail(final String email) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {
			final String sql = "select id from bibliotecario where email like ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, email);

			resultSet = prepareStatement.executeQuery();

			if (resultSet != null) {

				while (resultSet.next()) {
					id = resultSet.getInt("id");
				}
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return id;

	}

	public boolean recoveryPasswor(final int idUser, final String newPassword) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE bibliotecario SET senha = ?" + " WHERE id = ?;";
		try {
			connection = ConnectionFactory.getConnection();

			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, idUser);

			preparedStatement.execute();

			if (preparedStatement.getUpdateCount() != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}
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

	public Bibliotecario validateUsernameAndPassword(final String username, final String password) {
		Bibliotecario bibliotecario = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM bibliotecario B left JOIN pessoa P on P.id = B.pessoa_id WHERE email = ? AND senha = ?;";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				bibliotecario = new Bibliotecario();
				bibliotecario.setId(resultSet.getInt("id"));
				bibliotecario.setCpf(resultSet.getString("cpf"));
				bibliotecario.setNome(resultSet.getString("nome"));
				bibliotecario.setRua(resultSet.getString("rua"));
				bibliotecario.setNumero(resultSet.getInt("numero"));
				bibliotecario.setBairro(resultSet.getString("bairro"));
				bibliotecario.setCidade(resultSet.getString("cidade"));
				bibliotecario.setEstado(resultSet.getString("estado"));
				bibliotecario.setTelefone(resultSet.getString("telefone"));
				bibliotecario.setRegistro(resultSet.getInt("registro"));
				bibliotecario.setEmail(resultSet.getString("email"));
				bibliotecario.setSenha(resultSet.getString("senha"));
				
				bibliotecario.setTipo("BIBLIOTECARIO");

			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return bibliotecario;
	}
}
