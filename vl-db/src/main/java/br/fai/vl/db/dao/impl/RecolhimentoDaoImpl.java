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
import br.fai.vl.db.dao.RecolhimentoDao;
import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;

@Repository
public class RecolhimentoDaoImpl implements RecolhimentoDao {

	public List<Recolhimento> readAll() {
		final List<Recolhimento> recolhimentos = new ArrayList<Recolhimento>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "select * from recolhimento;";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Recolhimento recolhimento = new Recolhimento();
				recolhimento.setId(resultSet.getInt("id"));
				recolhimento.setDataSolicitacao(resultSet.getTimestamp("datasolicitacao"));
				recolhimento.setDataRecolhimento(resultSet.getTimestamp("datarecolhimento"));
				recolhimento.setRecolhido(resultSet.getBoolean("recolhido"));
				recolhimento.setLeitorId(resultSet.getInt("leitor_id"));
				recolhimento.setEmprestimoId(resultSet.getInt("emprestimo_id"));
				recolhimentos.add(recolhimento);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar as recolhimentos ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return recolhimentos;
	}

	public Recolhimento readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Recolhimento recolhimento = null;

		try {
			final String sql = "SELECT * FROM recolhimento WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				recolhimento = new Recolhimento();
				recolhimento.setId(resultSet.getInt("id"));
				recolhimento.setDataSolicitacao(resultSet.getTimestamp("datasolicitacao"));
				recolhimento.setDataRecolhimento(resultSet.getTimestamp("datarecolhimento"));
				recolhimento.setRecolhido(resultSet.getBoolean("recolhido"));
				recolhimento.setLeitorId(resultSet.getInt("leitor_id"));
				recolhimento.setEmprestimoId(resultSet.getInt("emprestimo_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return recolhimento;
	}

	public int create(final Recolhimento entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		final String sql = "INSERT INTO recolhimento(datasolicitacao, datarecolhimento, recolhido, leitor_id, emprestimo_id) "
				+ "VALUES(default, null, true, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			connection = ConnectionFactory.getConnection();

			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getLeitorId());
			preparedStatement.setInt(2, entity.getEmprestimoId());

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

	public boolean update(final Recolhimento entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE recolhimento SET datarecolhimento = ?, recolhido = ? WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTimestamp(1, entity.getDataRecolhimento());
			preparedStatement.setBoolean(2, entity.isRecolhido());
			preparedStatement.setInt(3, entity.getId());

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

		final String sql = "DELETE FROM recolhimento WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

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

	public Recolhimento requestCollection(final int idEmprestimo, final int idLeitor) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Recolhimento recolhimento = null;

		try {
			final String sql = "SELECT * FROM recolhimento WHERE emprestimo_id = ? and leitor_id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idEmprestimo);
			preparedStatement.setInt(2, idLeitor);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				recolhimento = new Recolhimento();
				recolhimento.setDataSolicitacao(resultSet.getTimestamp("datasolicitacao"));
				recolhimento.setDataRecolhimento(resultSet.getTimestamp("datarecolhimento"));
				recolhimento.setRecolhido(resultSet.getBoolean("recolhido"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return recolhimento;
	}

	public List<RecolhimentoDTO> pickUpOrderList() {
		final List<RecolhimentoDTO> recohimentos = new ArrayList<RecolhimentoDTO>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "select R.id, Pe.nome, L.email, E.codigo, E.id as idEmprestimo, L.id as idUser from recolhimento R "
					+ "inner join emprestimo E on R.emprestimo_id = E.id "
					+ "inner join leitor L on R.leitor_id = L.id " + "inner join pessoa Pe on L.pessoa_id = Pe.id "
					+ "where R.datarecolhimento is null and R.recolhido is true;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final RecolhimentoDTO recohimento = new RecolhimentoDTO();
				recohimento.setIdRecolhimento(resultSet.getInt("id"));
				recohimento.setUserName(resultSet.getString("nome"));
				recohimento.setUserEmail(resultSet.getString("email"));
				recohimento.setIdEmprestimo(resultSet.getInt("idemprestimo"));
				recohimento.setCodEmprestimo(resultSet.getInt("codigo"));
				recohimento.setUserID(resultSet.getInt("iduser"));
				recohimentos.add(recohimento);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar as recohimentos ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return recohimentos;
	}

	public boolean refuseCollection(final int idRecolhimento) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE recolhimento SET recolhido = false WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRecolhimento);

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

	public boolean acceptCollection(final int idRecolhimento) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE recolhimento SET datarecolhimento = now() WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRecolhimento);

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

	public List<RecolhimentoDTO> closedPickUpOrderList() {
		final List<RecolhimentoDTO> entregas = new ArrayList<RecolhimentoDTO>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "select R.id, Pe.nome, L.email, E.codigo, E.id as idEmprestimo, L.id as idUser from recolhimento R "
					+ "inner join emprestimo E on R.emprestimo_id = E.id "
					+ "inner join leitor L on R.leitor_id = L.id " + "inner join pessoa Pe on L.pessoa_id = Pe.id "
					+ "where R.datarecolhimento is not null or R.recolhido is false;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final RecolhimentoDTO recolhimento = new RecolhimentoDTO();
				recolhimento.setIdRecolhimento(resultSet.getInt("id"));
				recolhimento.setUserName(resultSet.getString("nome"));
				recolhimento.setUserEmail(resultSet.getString("email"));
				recolhimento.setIdEmprestimo(resultSet.getInt("idemprestimo"));
				recolhimento.setCodEmprestimo(resultSet.getInt("codigo"));
				recolhimento.setUserID(resultSet.getInt("iduser"));
				entregas.add(recolhimento);
			}

		} catch (final Exception e) {
			System.out.println("Não foi possível resgatar as entregas ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return entregas;
	}

}
