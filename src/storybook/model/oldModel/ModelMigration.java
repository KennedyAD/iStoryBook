/*
 * SbApp: Open Source software for novelists and authors.
 * Original idea 2008 - 2012 Martin Mustun
 * Copyrigth (C) Favdb
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package storybook.model.oldModel;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import storybook.SbApp;
import storybook.SbConstants;
import storybook.model.DbFile;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.ExceptionDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelMigration.
 */
//@Deprecated
public class ModelMigration {

	/** The persistence manager. */
	private static ModelMigration thePersistenceManager;

	/**
	 * Gets the single instance of ModelMigration.
	 *
	 * @return single instance of ModelMigration
	 */
	public static ModelMigration getInstance() {
		SbApp.trace("ModelMigration.getInstance()");
		if (thePersistenceManager == null) {
			thePersistenceManager = new ModelMigration();
		}
		return thePersistenceManager;
	}
	
	/** The database name. */
	private String databaseName;
	
	/** The init. */
	private boolean init;
	
	/** The open only if exists. */
	private boolean openOnlyIfExists;
	
	/** The connection. */
	private Connection connection;
	
	/** The file. */
	private File file;
	
	/** The stmt. */
	private Statement stmt;
	
	/** The main frame. */
	private MainFrame mainFrame;
	
	/** The old db version. */
	private String oldDbVersion;

	/** The new db version. */
	private String newDbVersion;

	/**
	 * Instantiates a new model migration.
	 */
	private ModelMigration() {
		// make the constructor private
		init = false;
		connection = null;
		databaseName = null;
	}

	/**
	 * Alter from1_5to4_0.
	 *
	 * @throws Exception the exception
	 */
	private void alterFrom1_5to4_0() throws Exception {
		SbApp.trace("Updating file version from 1.4 to 1.5 ...");
		String sql = "";

		// location
		sql = "alter table location alter column name varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table location alter column city varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table location alter column country varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table location alter column address varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table location alter column description varchar(32768)";
		executeSQLStatement(sql, stmt);
		sql = "alter table location alter column notes varchar(32768)";
		executeSQLStatement(sql, stmt);

		// person
		sql = "ALTER TABLE PERSON ALTER COLUMN CATEGORY RENAME TO CATEGORY_ID;";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE PERSON ALTER COLUMN CATEGORY_ID long;";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column firstname varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column lastname varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column abbreviation varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column occupation varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column description varchar(32768)";
		executeSQLStatement(sql, stmt);
		sql = "alter table person alter column notes varchar(32768)";
		executeSQLStatement(sql, stmt);

		// scene
		sql = "update scene set date = null where id in(select id from scene where scene.RELATIVE_SCENE_ID!=-1)";
		executeSQLStatement(sql, stmt);
		sql = "alter table scene alter column title varchar(2048)";
		executeSQLStatement(sql, stmt);
		sql = "alter table scene alter column summary varchar(32768)";
		executeSQLStatement(sql, stmt);
		sql = "alter table scene alter column notes varchar(32768)";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE SCENE ALTER COLUMN RELATIVE_SCENE_ID long;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE SCENE SET chapter_id=NULL WHERE chapter_id=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE SCENE SET RELATIVE_SCENE_ID=NULL WHERE RELATIVE_SCENE_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE SCENE SET RELATIVE_DATE_DIFFERENCE=NULL WHERE RELATIVE_DATE_DIFFERENCE=0;";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE SCENE ALTER COLUMN DATE RENAME TO SCENE_TS;";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE SCENE ALTER COLUMN SCENE_TS timestamp;";
		executeSQLStatement(sql, stmt);

		// chapter
		sql = "alter table chapter alter column title varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table chapter alter column description varchar(32768)";
		executeSQLStatement(sql, stmt);
		sql = "alter table chapter alter column notes varchar(32768)";
		executeSQLStatement(sql, stmt);

		// gender
		sql = "alter table gender alter column name varchar(256)";
		executeSQLStatement(sql, stmt);

		// idea
		sql = "alter table ideas alter column category varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table ideas alter column note varchar(32768)";
		executeSQLStatement(sql, stmt);

		// part
		sql = "alter table part alter column name varchar(256)";
		executeSQLStatement(sql, stmt);

		// strand
		sql = "alter table strand alter column name varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table strand alter column abbreviation varchar(256)";
		executeSQLStatement(sql, stmt);
		sql = "alter table strand alter column notes varchar(32768)";
		executeSQLStatement(sql, stmt);

		// tag link
		sql = "UPDATE TAG_LINK SET START_SCENE_ID=NULL WHERE START_SCENE_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET START_SCENE_ID=NULL WHERE START_SCENE_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET END_SCENE_ID=NULL WHERE END_SCENE_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET END_SCENE_ID=NULL WHERE END_SCENE_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET CHARACTER_ID=NULL WHERE CHARACTER_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET CHARACTER_ID=NULL WHERE CHARACTER_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET LOCATION_ID=NULL WHERE LOCATION_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK SET LOCATION_ID=NULL WHERE LOCATION_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE TAG_LINK ADD TYPE integer;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE TAG_LINK AS TL SET TYPE=(SELECT T.TYPE FROM TAG AS T WHERE T.ID=TL.TAG_ID);";
		executeSQLStatement(sql, stmt);

		// person link
		sql = "UPDATE PERSON_LINK SET START_SCENE_ID=NULL WHERE START_SCENE_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK SET START_SCENE_ID=NULL WHERE START_SCENE_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK SET END_SCENE_ID=NULL WHERE END_SCENE_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK SET END_SCENE_ID=NULL WHERE END_SCENE_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK SET PERSON1_ID=NULL WHERE CHARACTER_ID=0;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK SET PERSON2_ID=NULL WHERE CHARACTER_ID=-1;";
		executeSQLStatement(sql, stmt);
		sql = "ALTER TABLE PERSON_LINK ADD TYPE integer;";
		executeSQLStatement(sql, stmt);
		sql = "UPDATE PERSON_LINK AS TL SET TYPE=(SELECT T.TYPE FROM PERSON AS T WHERE T.ID=TL.PERSON_ID);";
		executeSQLStatement(sql, stmt);

		// category
		sql = "CREATE TABLE CATEGORY(ID bigint PRIMARY KEY NOT NULL,SORT integer,NAME varchar(256));";
		executeSQLStatement(sql, stmt);
		sql = "INSERT INTO CATEGORY (ID,SORT,NAME) VALUES (1, 1, 'major');";
		executeSQLStatement(sql, stmt);
		sql = "INSERT INTO CATEGORY (ID,SORT,NAME) VALUES (2, 2, 'minor');";
		executeSQLStatement(sql, stmt);

		// internal
		sql = "alter table internal alter column key varchar(512)";
		executeSQLStatement(sql, stmt);
		sql = "alter table internal alter column string_value varchar(4096)";
		executeSQLStatement(sql, stmt);

		InternalPeer.setDbModelVersion(SbConstants.Storybook.DB_VERSION.toString());
	}

	/**
	 * Check and alter model.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkAndAlterModel() throws Exception {
		oldDbVersion = InternalPeer.getDbModelVersion();
		if (oldDbVersion == null) {
			return true;
		}
		newDbVersion = SbConstants.Storybook.DB_VERSION.toString();

		if (oldDbVersion.equals(newDbVersion)) {
			// model matches, nothing to do
			return true;
		}

		// alter models
		stmt = ModelMigration.getInstance().getConnection().createStatement();
		// old versions
		if (oldDbVersion.equals("0") || oldDbVersion.equals("0.1") || oldDbVersion.equals("0.1")
				|| oldDbVersion.equals("0.2") || oldDbVersion.equals("0.3") || oldDbVersion.equals("0.4")
				|| oldDbVersion.equals("0.5") || oldDbVersion.equals("0.6") || oldDbVersion.equals("0.7")
				|| oldDbVersion.equals("0.8") || oldDbVersion.equals("0.9") || oldDbVersion.equals("1.0")
				|| oldDbVersion.equals("1.1") || oldDbVersion.equals("1.2") || oldDbVersion.equals("1.3")
				|| oldDbVersion.equals("1.4")) {
			throw new Exception("File version too old. Update to the latest version of Storybook 3 first.");
		}

		// backup current file
		String fn = FilenameUtils.removeExtension(file.getAbsolutePath());
		fn = fn + ".bak";
		File backupFile = new File(fn);
		try {
			if (backupFile.exists()) {
				backupFile.delete();
			}
			FileUtils.copyFile(file, backupFile);
		} catch (IOException e1) {
			int n = JOptionPane
					.showConfirmDialog(mainFrame,
							I18N.getMsg("msg.migration.error.backup") + "\n" + backupFile.getAbsolutePath() + "\n"
									+ I18N.getMsg("msg.migration.wanttocontinue"),
							"Backup failed", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
				return false;
			}
		}

		if (oldDbVersion.equals("1.5")) {
			// 1.5 -> 4.0
			alterFrom1_5to4_0();
		}

		return true;
	}

	/**
	 * Close connection.
	 */
	public void closeConnection() {
		SbApp.trace("ModelMigration.closeConnection()");
		if (!isConnectionOpen()) {
			return;
		}
		try {
			this.connection.close();
			this.init = false;
			this.connection = null;
			this.databaseName = null;
		} catch (SQLException e) {
			SbApp.error("ModelMigration.closeConnection()", e);
		}
	}

	/**
	 * Closes the prepare statement.
	 *
	 * @param prepare            The PreparedStatement that needs to close
	 */
	public void closePrepareStatement(PreparedStatement prepare) {
		try {
			if (prepare != null) {
				prepare.close();
			}
		} catch (SQLException se) {
			SbApp.error("*** ModelMigration.closePrepareStatement(" + prepare.toString() + ")", se);
		}
	}

	/**
	 * Closes the result set.
	 *
	 * @param result            The ResultSet that needs to close
	 */
	public void closeResultSet(ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			SbApp.error("*** ModelMigration.closeResultSet(" + result.toString() + ")", se);
		}
	}

	/**
	 * Closes the statement.
	 *
	 * @param stmt            The Statement that needs to close
	 */
	public void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			SbApp.error("*** ModelMigration.closeStatement(" + stmt.toString() + ")", se);
		}
	}

	/**
	 * Execute sql statement.
	 *
	 * @param sql the sql
	 * @param stmt the stmt
	 */
	private void executeSQLStatement(String sql, Statement stmt) {
		SbApp.trace("ModelMigration.executeSQLStatement(" + sql.toString() + "," + stmt.toString() + ")");
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			SbApp.error("ModelMigration.executeSQLStatement(" + sql + "," + stmt.toString() + ")", e);
			ExceptionDialog dlg = new ExceptionDialog(e);
			SwingUtil.showModalDialog(dlg, mainFrame);
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		SbApp.trace("ModelMigration.getConnection()");
		if (!init) {
			return null;
		}
		if (connection == null) {
			String connectionStr = "jdbc:h2:" + databaseName;
			if (openOnlyIfExists) {
				connectionStr = connectionStr + ";IFEXISTS=TRUE";
			}
			if (SbApp.getTraceHibernate()) {
				connectionStr += ";TRACE_LEVEL_FILE=3;TRACE_LEVEL_SYSTEM_OUT=3";
			} else {
				connectionStr += ";TRACE_LEVEL_FILE=0;TRACE_LEVEL_SYSTEM_OUT=0";
			}
			SbApp.trace("ModelMigration.getConnection() to " + connectionStr);
			try {
				Class.forName("org.h2.Driver");
				connection = DriverManager.getConnection(connectionStr, "sa", "");
			} catch (ClassNotFoundException | SQLException e) {
				SbApp.error("ModelMigration.getConnection()", e);
			}
		}
		return connection;
	}

	/**
	 * Gets the database name.
	 *
	 * @return the database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Gets the generated id.
	 *
	 * @param stmt the stmt
	 * @return the generated id
	 * @throws SQLException the SQL exception
	 */
	public int getGeneratedId(PreparedStatement stmt) throws SQLException {
		int retour = -1;
		ResultSet rs = null;
		try {
			rs = stmt.getGeneratedKeys();
			int count = 0;
			while (rs.next()) {
				if (count > 0) {
					throw new SQLException("error: got more than one id");
				}
				retour = rs.getInt(1);
				++count;
			}
		} catch (SQLException exc) {
			SbApp.error("*** ModelMigration.getGeneratedId(" + stmt.toString() + ")", exc);
		} finally {
			this.closeResultSet(rs);
		}
		return retour;
	}

	/**
	 * Gets the main frame.
	 *
	 * @return the main frame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Gets the new db version.
	 *
	 * @return the new db version
	 */
	public String getNewDbVersion() {
		return newDbVersion;
	}

	/**
	 * Gets the old db version.
	 *
	 * @return the old db version
	 */
	public String getOldDbVersion() {
		return oldDbVersion;
	}

	/**
	 * Checks for altered db model.
	 *
	 * @return true, if successful
	 */
	public boolean hasAlteredDbModel() {
		if (oldDbVersion == null) {
			return false;
		}
		return !oldDbVersion.equals(newDbVersion);
	}

	/**
	 * Checks if is connection open.
	 *
	 * @return true, if is connection open
	 */
	public boolean isConnectionOpen() {
		return connection != null;
	}

	/**
	 * Open.
	 *
	 * @param dbFile the db file
	 */
	public void open(DbFile dbFile) {
		SbApp.trace("ModelMigration.open(" + dbFile.getDbName() + ")");
		this.file = dbFile.getFile();
		this.databaseName = dbFile.getDbName();
		this.openOnlyIfExists = true;
		this.init = true;
		try {
			getConnection();
		} catch (Exception e) {
			SbApp.error("ModelMigration.open(" + dbFile.getName() + ")", e);
		}
		SbApp.trace("ModelMigration.open(" + this.databaseName + ")");
	}

	/**
	 * Sets the main frame.
	 *
	 * @param mainFrame the new main frame
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}
