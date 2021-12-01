package br.fai.vl.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Está classe é responsável por realizar a abrir e fechar as conexões
 * realizadas ao Bancos de Dados
 *
 * @author LucasLima
 *
 */
public class ConnectionFactory {

	private static final String url = "jdbc:postgresql://localhost:5433/virtualLibrary";
//	private static final String url = "jdbc:postgresql://localhost:5432/virtualLibrary";
	private static final String userName = "postgres";
	private static final String password = "postgres";

	private static Connection connection = null;

	/**
	 * este método é responsável por abrir a conexão com o Banco de Dados
	 *
	 * @return connection
	 */
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, userName, password);

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	/**
	 * este método é responsável por fechar a conexão com o Banco de Dados
	 *
	 * @return
	 */
	private static void closeConnection() {
		try {
			connection.close();
		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * este método é responsável por fechar o ResultSet toda vez que for utilizado
	 *
	 * @return
	 */
	private static void closeResultSet(final ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (final Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * este método é responsável por fechar o PreparedStatement toda vez que for
	 * utilizado
	 *
	 * @return
	 */
	private static void closePreparedStatement(final PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (final Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * este método é responsável por fechar as conexões feitas ao Banco de Dados e
	 * que utilizarem do: ResultSet, PreparedStatement e Connection. Isso permitirá
	 * mais facildade na hora de fechar a conexão com o BD
	 *
	 * @return
	 */
	public static void close(final ResultSet resultSet, final PreparedStatement preparedStatement,
			final Connection connection) {
		closeConnection();
		closePreparedStatement(preparedStatement);
		closeResultSet(resultSet);
	}

	/**
	 * este método é responsável por fechar as conexões feitas ao Banco de Dados e
	 * que utilizarem do: PreparedStatement e Connection. Isso permitirá mais
	 * facildade na hora de fechar a conexão com o BD
	 *
	 * @return
	 */
	public static void close(final PreparedStatement preparedStatement, final Connection connection) {
		closeConnection();
		closePreparedStatement(preparedStatement);
	}
}
