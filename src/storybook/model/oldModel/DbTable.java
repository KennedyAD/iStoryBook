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

/**
 *
 * @author favdb
 */
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import storybook.SbApp;

// TODO: Auto-generated Javadoc
/**
 * The Class DbTable.
 */
//@Deprecated
public abstract class DbTable implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1116409961625485131L;
	
	/** The volatile id. */
	private static int volatileId = -100;
	
	/** The is volatile. */
	boolean isVolatile = false;

	/** The table name. */
	protected String tableName;
	
	/** The id. */
	protected int id = -1;
	
	/** The real id. */
	protected int realId = -1;
	
	/** The is new. */
	protected boolean isNew;
	
	/** The to string used for list. */
	protected boolean toStringUsedForList;

	/**
	 * Instantiates a new db table.
	 *
	 * @param tableName the table name
	 */
	public DbTable(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Instantiates a new db table.
	 *
	 * @param tableName the table name
	 * @param isVolatile the is volatile
	 */
	public DbTable(String tableName, boolean isVolatile) {
		this(tableName);
		this.isVolatile = isVolatile;
		this.id = DbTable.volatileId--;
	}

	/**
	 * Change id.
	 *
	 * @param newId the new id
	 * @return true, if successful
	 */
	public boolean changeId(int newId) {
		PreparedStatement stmt = null;
		boolean retour = false;
		try {
			if (newId == getId()) {
				retour = true;
			} else {
				int oldId = getId();
				this.id = newId;
				String sql = "update " + getTablename() + " set id = ? where id = ?";
				stmt = ModelMigration.getInstance().getConnection().prepareStatement(sql);
				stmt.setInt(1, newId);
				stmt.setInt(2, oldId);
				if (stmt.executeUpdate() != 1) {
					this.id = oldId;
					throw new SQLException("update failed, newId: " + newId);
				}
				SbApp.trace("ID manually changed: oldId=" + oldId + ", newId=" + getId() + " " + this.getTablename());
				retour = true;
			}
		} catch (SQLException e) {
			SbApp.error("oldModel.DbTable.changeId(" + newId + ")", e);
		} finally {
			ModelMigration.getInstance().closePrepareStatement(stmt);
		}
		return retour;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == null || obj == null) {
			return false;
		}
		try {
			return this.getId() == ((DbTable) obj).getId();
		} catch (ClassCastException e) {
			SbApp.error("oldModel.DbTable.equals(" + obj.toString() + ")", e);
		}
		return false;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the label text.
	 *
	 * @return the label text
	 */
	public abstract String getLabelText();

	/**
	 * Gets the real id.
	 *
	 * @return the real id
	 */
	public int getRealId() {
		return realId;
	}

	/**
	 * Gets the tablename.
	 *
	 * @return the tablename
	 */
	public String getTablename() {
		return tableName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// int hash = 1;
		// hash = hash * 31 + getTablename().hashCode() + getId();
		// hash = hash * 31 + new Integer(getId()).hashCode();

		int hash = 1;
		hash = hash * 31 + getTablename().hashCode();
		hash = hash * 31 + new Integer(getId()).hashCode();
		return hash;
	}

	/**
	 * Checks if is clone.
	 *
	 * @return true, if is clone
	 */
	public boolean isClone() {
		return id <= -1000;
	}

	/**
	 * Checks if is marked as expired.
	 *
	 * @return true, if is marked as expired
	 */
	public boolean isMarkedAsExpired() {
		return (id == -1);
	}

	/**
	 * Checks if is new.
	 *
	 * @return true, if is new
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * Checks if is to string used for list.
	 *
	 * @return true, if is to string used for list
	 */
	public boolean isToStringUsedForList() {
		return toStringUsedForList;
	}

	/**
	 * Checks if is volatile.
	 *
	 * @return true, if is volatile
	 */
	public boolean isVolatile() {
		return this.isVolatile;
	}

	/**
	 * Mark as expired.
	 */
	public void markAsExpired() {
		id = -1;
	}

	/**
	 * Save.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public abstract boolean save() throws Exception;

	/**
	 * Sets the to string used for list.
	 *
	 * @param toStringUsedForList the new to string used for list
	 */
	public void setToStringUsedForList(boolean toStringUsedForList) {
		this.toStringUsedForList = toStringUsedForList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + getId();
	}
}
