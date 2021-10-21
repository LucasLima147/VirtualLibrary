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
import br.fai.vl.db.dao.LeitorDao;
import br.fai.vl.model.Leitor;

@Repository
public class LeitorDaoImpl implements LeitorDao {

	public List<Leitor> readAll() {
		final List<Leitor> leitors = new ArrayList<Leitor>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM leitor L inner join pessoa P on L.pessoa_id = P.id;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Leitor leitor = new Leitor();
				leitor.setId(resultSet.getInt("id"));
				leitor.setCpf(resultSet.getString("cpf"));
				leitor.setNome(resultSet.getString("nome"));
				leitor.setRua(resultSet.getString("rua"));
				leitor.setNumero(resultSet.getInt("numero"));
				leitor.setBairro(resultSet.getString("bairro"));
				leitor.setCidade(resultSet.getString("cidade"));
				leitor.setEstado(resultSet.getString("estado"));
				leitor.setTelefone(resultSet.getString("telefone"));
				leitor.setMatricula(resultSet.getInt("matricula"));
				leitor.setEmail(resultSet.getString("email"));
				leitor.setSenha(resultSet.getString("senha"));
				leitor.setPessoaId(resultSet.getInt("pessoa_id"));
				leitors.add(leitor);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return leitors;
	}

	public Leitor readById(final int id) {
		Leitor leitor = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM leitor L INNER JOIN pessoa P on L.pessoa_id = P.id WHERE L.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				leitor = new Leitor();
				leitor.setId(resultSet.getInt("id"));
				leitor.setCpf(resultSet.getString("cpf"));
				leitor.setNome(resultSet.getString("nome"));
				leitor.setRua(resultSet.getString("rua"));
				leitor.setNumero(resultSet.getInt("numero"));
				leitor.setBairro(resultSet.getString("bairro"));
				leitor.setCidade(resultSet.getString("cidade"));
				leitor.setEstado(resultSet.getString("estado"));
				leitor.setId(resultSet.getInt("id"));
				leitor.setTelefone(resultSet.getString("telefone"));
				leitor.setMatricula(resultSet.getInt("matricula"));
				leitor.setEmail(resultSet.getString("email"));
				leitor.setSenha(resultSet.getString("senha"));
				leitor.setPessoaId(resultSet.getInt("pessoa_id"));
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return leitor;
	}

	public int create(final Leitor entity) {
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

			connection.setAutoCommit(false);

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
				sql = "INSERT INTO leitor(matricula, email, senha, pessoa_id)" + "VALUES(?, ?, ?, ?);";

				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, entity.getMatricula());
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

	public boolean update(final Leitor entity) {
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
				sql = "UPDATE leitor SET email = ?, senha = ?" + " WHERE id = ?;";

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
		final String sql = "delete from pessoa P where P.id = (select L.pessoa_id from leitor L where L.id = ?);";

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

	public List<Leitor> login() {
		final List<Leitor> leitors = new ArrayList<Leitor>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM leitor;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Leitor leitor = new Leitor();
				leitor.setId(resultSet.getInt("id"));
				leitor.setEmail(resultSet.getString("email"));
				leitor.setSenha(resultSet.getString("senha"));
				leitors.add(leitor);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return leitors;
	}

	public int checkEmail(final String email) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int id = -1;

		try {
			final String sql = "select id from leitor where email like ?;";

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

		final String sql = "UPDATE leitor SET senha = ?" + " WHERE id = ?;";
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
}
