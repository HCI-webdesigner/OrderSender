package org.sel.server;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Server extends HttpServlet {

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/project";
	private String user = "root";
	private String password = "13702573107";
	private Connection connection;
	private Statement statement;
	private ResultSet rs;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("SQLExecption");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String order_id = "";
		String content = "";
		String buyer = "";
		String tel = "";
		String address = "";
		OutputStream os = resp.getOutputStream();

		try {
			rs = statement
					.executeQuery("select * from order_form where isSent=false");
			while (rs.next()) {
				order_id = rs.getString(1);
				content = rs.getString(2);
				buyer = rs.getString(3);
				tel = rs.getString(4);
				address = rs.getString(5);
				os.write((order_id + "\t" + content + "\t" + buyer + "\t" + tel
						+ "\t" + address + "\n").getBytes("GBK"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		os.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		try {
			statement
					.executeUpdate("update order_form set isSent=true where order_id='"
							+ id + "';");
		} catch (SQLException e) {
               e.printStackTrace();
		}

	}

	@Override
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		super.destroy();
	}

}
