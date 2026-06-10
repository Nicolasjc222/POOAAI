package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public final class Conexao {
	private static String server = "jdbc:sqlserver://10.109.8.9:1433;";
	private static String banco = "databaseName=POO_gp08;";
	private static String usuario = "user=POO_gp08;password=;encrypt=false;trustServerCertificate=true;loginTimeout=30;";
	/* conexão local do Nycollas
	 * public final class Conexao { private static String server =
	 * "jdbc:sqlserver://localhost:1433;"; private static String banco =
	 * "databaseName=POO_gp08;"; private static String usuario =
	 * "integratedSecurity=true;password=;encrypt=false;trustServerCertificate=true;loginTimeout=30;";
	 */

	public static Connection conexao; // Conecta com o banco

	public static void conectar() { // Efetua a conexão
		try {
			// Conexão com o banco
			conexao = DriverManager.getConnection(server+banco+usuario);
			//			JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso!");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro de conexão!\nERRO: " + ex.getMessage());
		}
	}

	public static void desconectar() {
		if (conexao == null) return; // ← evita o NPE
		try {
			conexao.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão!\nERRO: " + ex.getMessage());
		}
	}

}
