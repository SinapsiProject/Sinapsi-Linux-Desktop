package com.sinapsi.desktop.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sinapsi.client.persistence.LocalDBManager;
import com.sinapsi.model.MacroInterface;

public class DesktopLocalDBManager implements LocalDBManager{

	private final String filename;

	private static final String TABLE_MACROS = "macros";
	private static final String TABLE_ACTION_LISTS = "action_lists";

	//TODO: define schema

	private static final String[] allTables = new String[]{
			TABLE_ACTION_LISTS,
			TABLE_MACROS
	};

	public DesktopLocalDBManager(String filename){
		this.filename = filename;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected Connection openConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:"+filename);
	}

	private void disconnetti(Connection c, Statement s, ResultSet r){
		try {
			r.close();
		} catch (Throwable t)
		{
			//Vuoto
		}

		try {
			s.close();
		} catch (Throwable t)
		{
			//Vuoto
		}

		try {
			c.close();
		} catch (Throwable t)
		{
			//Vuoto
		}
	}

	private void disconnetti(Connection c, Statement s){
		disconnetti(c,s,null);
	}

	@Override
	public boolean addOrUpdateMacro(MacroInterface macro) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MacroInterface> getAllMacros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeMacro(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearDB() {
		Connection conn = null;
		Statement s = null;
		try {
			conn = openConnection();
			s = conn.createStatement();
			for(String table: allTables){
				s.executeUpdate("delete from "+table);
			}
		} catch (SQLException e) {
			// Unable to open the connection
			e.printStackTrace();
		}
		disconnetti(conn,s);
	}

	@Override
	public int getMinMacroId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean containsMacro(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MacroInterface getMacroWithId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMacrosWithNegativeId() {
		// TODO Auto-generated method stub

	}

}
