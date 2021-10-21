package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.EmprestimoDao;
import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;

@Repository
public class EmprestimoDaoImpl implements EmprestimoDao {

	public List<Emprestimo> readAll() {
		final List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM emprestimo;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Emprestimo emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
				emprestimos.add(emprestimo);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimos;
	}

	public Emprestimo readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Emprestimo emprestimo = null;

		try {
			final String sql = "SELECT * FROM emprestimo WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimo;
	}

	public int create(final int idLeitor, final int idExemplar) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);
		String sql = "";

		try {
			sql = "insert into emprestimo(codigo, datarealizacao, leitor_id) " + " values (default, null, ?)";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idLeitor);

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				preparedStatement.close();

				sql = "insert into emprestimo_exemplar(datadevolucao, devolvido, dataefetivadevolucao,"
						+ "multa, emprestimo_id, exemplar_id) " + "values(null, default, null, default, ?, ?);";

				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, idExemplar);

				preparedStatement.execute();
				resultSet = preparedStatement.getGeneratedKeys();

				if (!resultSet.next()) {
					id = -1;
				}
			}

			if (id != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}

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

	public boolean update(final Emprestimo entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String sql = "UPDATE emprestimo SET datarealizacao = ? WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTimestamp(1, entity.getDataRealizacao());
			preparedStatement.setInt(2, entity.getId());

			preparedStatement.execute();
			if (preparedStatement.getUpdateCount() != -1) {

				preparedStatement.close();
				sql = "UPDATE emprestimo_exemplar SET datadevolucao = ?, devolvido = ?, dataefetivadevolucao = ?, "
						+ "multa = ? WHERE emprestimo_id = ?";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setTimestamp(1, entity.getDataRealizacao());
				preparedStatement.setBoolean(2, entity.isDevolvido());
				preparedStatement.setTimestamp(3, entity.getDataEfetivaDevolucao());
				preparedStatement.setBigDecimal(4, entity.getMulta());
				preparedStatement.setInt(5, entity.getId());

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
			String sql = "DELETE FROM emprestimo_exemplar where emprestimo_id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			preparedStatement.execute();
			preparedStatement.close();

			sql = "DELETE FROM emprestimo where id = ?;";
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

	public List<Integer> checkAvaliableCopies(final int livroId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		List<Integer> exemplaresDisponiveis = null;

		try {
			final String sql = "select Ex.id from livro L inner join exemplar Ex on Ex.livro_id = L.id"
					+ " where Ex.id not in(select EE.exemplar_id from emprestimo_exemplar EE where EE.devolvido = false) and L.id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, livroId);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				exemplaresDisponiveis = Arrays.asList(resultSet.getInt("id"));
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return exemplaresDisponiveis;
	}

	public List<Integer> checkOpenReaderLoads(final int leitoId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		List<Integer> emprestimosAbertos = null;

		try {
			final String sql = "select E.* from emprestimo E where E.datarealizacao is null and E.leitor_id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, leitoId);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				emprestimosAbertos = Arrays.asList(resultSet.getInt("id"));
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public int addToLoads(final int emprestimoId, final int exemplarId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);
		String sql = "";

		try {
			sql = "insert into emprestimo_exemplar(datadevolucao, devolvido, dataefetivadevolucao,"
					+ "multa, emprestimo_id, exemplar_id) " + "values(null, default, null, default, ?, ?);";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, emprestimoId);
			preparedStatement.setInt(2, exemplarId);

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			if (id != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}

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

	public List<EmprestimoDTO> checkOpenUserLoans(final int idLeitor) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		final List<EmprestimoDTO> emprestimosAbertos = new ArrayList<EmprestimoDTO>();

		try {
			final String sql = "select E.id as Emprestimo_id, Ex.id as Exemplar_id, L.titulo from emprestimo E "
					+ "inner join emprestimo_Exemplar EE on E.id = EE.emprestimo_id "
					+ "inner join Exemplar Ex on Ex.id = EE.exemplar_id " + "inner join Livro L on L.id = Ex.livro_id "
					+ "where E.datarealizacao is null and E.leitor_id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, idLeitor);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				final EmprestimoDTO openLoan = new EmprestimoDTO();
				openLoan.setIdEmprestimo(resultSet.getInt("Emprestimo_id"));
				openLoan.setIdExemplar(resultSet.getInt("Exemplar_id"));
				openLoan.setNomeLivro(resultSet.getString("titulo"));
				emprestimosAbertos.add(openLoan);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public boolean terminateLoan(final int idEmprestimo) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String sql = "UPDATE emprestimo SET datarealizacao = now() WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idEmprestimo);

			preparedStatement.execute();
			if (preparedStatement.getUpdateCount() != -1) {

				preparedStatement.close();
				sql = "UPDATE emprestimo_exemplar SET datadevolucao = default WHERE emprestimo_id = ?";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, idEmprestimo);

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
			} catch (final Exception e2) {
				System.out.println(e2.getMessage());
			}

			return false;

		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}

	public boolean removeLoanBook(final EmprestimoDTO entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			final String sql = "delete from emprestimo_exemplar where emprestimo_id = ? and exemplar_id = ?;";

			connection = ConnectionFactory.getConnection();

			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, entity.getIdEmprestimo());
			preparedStatement.setInt(2, entity.getIdExemplar());

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

	public Emprestimo lastLoanRecord(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Emprestimo emprestimo = null;

		try {
			final String sql = "select * from emprestimo where id = (select max(id) from emprestimo where leitor_id = ? and datarealizacao is not null);";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimo;
	}

	public List<Emprestimo> myPreviousLoans(final int idLeitor) {
		final List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM emprestimo WHERE leitor_id = ? and datarealizacao is not null;";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idLeitor);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Emprestimo emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
				emprestimos.add(emprestimo);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimos;
	}

	public List<EmprestimoDTO> checkLoan(final int idEmprestimo, final int idLeitor) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		final List<EmprestimoDTO> emprestimosAbertos = new ArrayList<EmprestimoDTO>();

		try {
			final String sql = "select E.id as Emprestimo_id, Ex.id as Exemplar_id, L.titulo, E.codigo from emprestimo E "
					+ "inner join emprestimo_Exemplar EE on E.id = EE.emprestimo_id "
					+ "inner join Exemplar Ex on Ex.id = EE.exemplar_id " + "inner join Livro L on L.id = Ex.livro_id "
					+ "where E.leitor_id = ? and E.id = ?";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, idLeitor);
			prepareStatement.setInt(2, idEmprestimo);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				final EmprestimoDTO openLoan = new EmprestimoDTO();
				openLoan.setIdEmprestimo(resultSet.getInt("Emprestimo_id"));
				openLoan.setCodEmprestimo(resultSet.getInt("codigo"));
				openLoan.setIdExemplar(resultSet.getInt("Exemplar_id"));
				openLoan.setNomeLivro(resultSet.getString("titulo"));
				emprestimosAbertos.add(openLoan);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public List<EmprestimoDTO> openLoansList() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		final List<EmprestimoDTO> emprestimosAbertos = new ArrayList<EmprestimoDTO>();

		try {
			final String sql = "select E.id, E.codigo, En.exemplar_id, L.titulo, P.nome, Le.email, Le.id as idUser from emprestimo E "
					+ "inner join emprestimo_exemplar En on E.id = En.emprestimo_id "
					+ "inner join exemplar Ex on En.exemplar_id = Ex.id " + "inner join livro L on L.id = Ex.livro_id "
					+ "inner join leitor Le on Le.id = E.leitor_id " + "inner join pessoa P on P.id = Le.pessoa_id "
					+ "where En.dataefetivadevolucao is null and E.datarealizacao is not null;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				final EmprestimoDTO openLoan = new EmprestimoDTO();
				openLoan.setIdEmprestimo(resultSet.getInt("id"));
				openLoan.setCodEmprestimo(resultSet.getInt("codigo"));
				openLoan.setIdExemplar(resultSet.getInt("exemplar_id"));
				openLoan.setNomeLivro(resultSet.getString("titulo"));
				openLoan.setUserName(resultSet.getString("nome"));
				openLoan.setUserEmail(resultSet.getString("email"));
				openLoan.setUserID(resultSet.getInt("iduser"));
				emprestimosAbertos.add(openLoan);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public boolean returnCopy(final int idExemplar, final int idEmprestimo) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			final String sql = "UPDATE emprestimo_exemplar SET dataefetivadevolucao = now(), devolvido = true WHERE exemplar_id = ? and emprestimo_id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExemplar);
			preparedStatement.setInt(2, idEmprestimo);

			preparedStatement.execute();

			if (preparedStatement.getUpdateCount() != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}

			return true;

		} catch (

		final Exception e) {
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

	public List<EmprestimoDTO> closeLoansList() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		final List<EmprestimoDTO> emprestimosAbertos = new ArrayList<EmprestimoDTO>();

		try {
			final String sql = "select E.id, E.codigo, En.exemplar_id, L.titulo, P.nome, Le.email, Le.id as idUser from emprestimo E "
					+ "inner join emprestimo_exemplar En on E.id = En.emprestimo_id "
					+ "inner join exemplar Ex on En.exemplar_id = Ex.id " + "inner join livro L on L.id = Ex.livro_id "
					+ "inner join leitor Le on Le.id = E.leitor_id " + "inner join pessoa P on P.id = Le.pessoa_id "
					+ "where En.dataefetivadevolucao is not null and E.datarealizacao is not null;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				final EmprestimoDTO openLoan = new EmprestimoDTO();
				openLoan.setIdEmprestimo(resultSet.getInt("id"));
				openLoan.setCodEmprestimo(resultSet.getInt("codigo"));
				openLoan.setIdExemplar(resultSet.getInt("exemplar_id"));
				openLoan.setNomeLivro(resultSet.getString("titulo"));
				openLoan.setUserName(resultSet.getString("nome"));
				openLoan.setUserEmail(resultSet.getString("email"));
				openLoan.setUserID(resultSet.getInt("iduser"));
				emprestimosAbertos.add(openLoan);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}
}
