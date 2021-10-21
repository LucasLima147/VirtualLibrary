package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.AdministradorDao;
import br.fai.vl.model.Administrador;

@Repository
public class AdministradorDaoImpl implements AdministradorDao {

	public List<Administrador> readAll() {
		final List<Administrador> administradores = new ArrayList<Administrador>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM pessoa p inner join administrador A on p.id = A.pessoa_id;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Administrador administrador = new Administrador();
				administrador.setId(resultSet.getInt("id"));
				administrador.setCpf(resultSet.getString("cpf"));
				administrador.setNome(resultSet.getString("nome"));
				administrador.setRua(resultSet.getString("rua"));
				administrador.setNumero(resultSet.getInt("numero"));
				administrador.setBairro(resultSet.getString("bairro"));
				administrador.setCidade(resultSet.getString("cidade"));
				administrador.setEstado(resultSet.getString("estado"));
				administrador.setTelefone(resultSet.getString("telefone"));
				administrador.setEmail(resultSet.getString("email"));
				administrador.setSenha(resultSet.getString("senha"));
				administrador.setPessoaId(resultSet.getInt("pessoa_id"));
				administradores.add(administrador);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return administradores;
	}

	public Administrador readById(final int id) {
		final Administrador administrador = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conexão
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM administrador A INNER JOIN pessoa P on A.id = P.id WHERE A.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				administrador.setId(resultSet.getInt("id"));
				administrador.setCpf(resultSet.getString("cpf"));
				administrador.setNome(resultSet.getString("nome"));
				administrador.setRua(resultSet.getString("rua"));
				administrador.setNumero(resultSet.getInt("numero"));
				administrador.setBairro(resultSet.getString("bairro"));
				administrador.setCidade(resultSet.getString("cidade"));
				administrador.setEstado(resultSet.getString("estado"));
				administrador.setTelefone(resultSet.getString("telefone"));
				administrador.setEmail(resultSet.getString("email"));
				administrador.setSenha(resultSet.getString("senha"));
				administrador.setPessoaId(resultSet.getInt("pessoa_id"));
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return administrador;
	}

	public boolean update(final Administrador entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE administrador SET email = ?, senha = ?" + " WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setString(2, entity.getSenha());
			preparedStatement.setLong(3, entity.getId());
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
