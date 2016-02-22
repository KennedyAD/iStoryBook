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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import storybook.model.oldModel.MigrationConstants.ProjectSetting;

// TODO: Auto-generated Javadoc
/**
 * The Class Internal.
 */
//@Deprecated
public class Internal extends DbTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6169725317352972761L;

	/** The logger. */
	private static Logger logger = Logger.getLogger(Internal.class);

	/** The Constant TABLE_NAME. */
	public static final String TABLE_NAME = "internal";
	
	/** The Constant COLUMN_ID. */
	public static final String COLUMN_ID = "id";
	
	/** The Constant COLUMN_KEY. */
	public static final String COLUMN_KEY = "key";
	
	/** The Constant COLUMN_STRING_VALUE. */
	public static final String COLUMN_STRING_VALUE = "string_value";
	
	/** The Constant COLUMN_INTEGER_VALUE. */
	public static final String COLUMN_INTEGER_VALUE = "integer_value";
	
	/** The Constant COLUMN_BOOLEAN_VALUE. */
	public static final String COLUMN_BOOLEAN_VALUE = "boolean_value";
	
	/** The Constant COLUMN_OLD_VALUE. */
	public static final String COLUMN_OLD_VALUE = "value";

	/** The key. */
	private String key = null;
	
	/** The string value. */
	private String stringValue = null;
	
	/** The integer value. */
	private Integer integerValue = null;
	
	/** The boolean value. */
	private Boolean booleanValue = null;

	/**
	 * Instantiates a new internal.
	 */
	public Internal() {
		super(TABLE_NAME);
		isNew = true;
	}

	/**
	 * This method must be packaged private! It is used by {@link InternalPeer}
	 * only.
	 *
	 * @param id
	 *            the id
	 */
	Internal(int id) {
		super(TABLE_NAME);
		this.id = id;
		isNew = false;
	}

	/**
	 * Gets the boolean value.
	 *
	 * @return the boolean value
	 */
	public Boolean getBooleanValue() {
		return booleanValue;
	}

	/**
	 * Gets the integer value.
	 *
	 * @return the integer value
	 */
	public Integer getIntegerValue() {
		return integerValue;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see storybook.model.oldModel.DbTable#getLabelText()
	 */
	@Override
	public String getLabelText() {
		return toString();
	}

	/**
	 * Gets the project setting.
	 *
	 * @return the project setting
	 */
	public ProjectSetting getProjectSetting() {
		return ProjectSetting.valueOfText(stringValue);
	}

	/**
	 * Gets the string value.
	 *
	 * @return the string value
	 */
	public String getStringValue() {
		return stringValue;
	}

	/* (non-Javadoc)
	 * @see storybook.model.oldModel.DbTable#save()
	 */
	@Override
	public boolean save() throws Exception {
		try {
			String sql;
			if (isNew) {
				// insert
				sql = "insert into " + TABLE_NAME + "(" + COLUMN_KEY + ", " + COLUMN_STRING_VALUE + ", "
						+ COLUMN_INTEGER_VALUE + ", " + COLUMN_BOOLEAN_VALUE + ") values(?, ?, ?, ?)";
			} else {
				// update
				sql = "update " + TABLE_NAME + " set " + COLUMN_KEY + " = ?, " + COLUMN_STRING_VALUE + " = ?, "
						+ COLUMN_INTEGER_VALUE + " = ?, " + COLUMN_BOOLEAN_VALUE + " = ? " + "where " + COLUMN_ID
						+ " = ?";
			}
			PreparedStatement stmt = ModelMigration.getInstance().getConnection().prepareStatement(sql);
			// sets for insert & update
			stmt.setString(1, getKey());
			stmt.setString(2, getStringValue() == null ? "" : getStringValue());
			stmt.setInt(3, getIntegerValue() == null ? Integer.MIN_VALUE : getIntegerValue());
			stmt.setBoolean(4, getBooleanValue() == null ? false : getBooleanValue());
			if (!isNew) {
				// sets for update only
				stmt.setInt(5, getId());
			}
			if (stmt.executeUpdate() != 1) {
				throw new SQLException(isNew ? "insert" : "update" + " failed");
			}
			if (isNew) {
				ResultSet rs = stmt.getGeneratedKeys();
				int count = 0;
				while (rs.next()) {
					if (count > 0) {
						throw new SQLException("error: got more than one id");
					}
					this.id = rs.getInt(1);
					logger.debug("save (insert): " + this);
					++count;
				}
				isNew = false;
			} else {
				logger.debug("save (update): " + this);
			}
			return true;
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Sets the boolean value.
	 *
	 * @param booleanValue the new boolean value
	 */
	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	/**
	 * Sets the integer value.
	 *
	 * @param integerValue the new integer value
	 */
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	/**
	 * Sets the key.
	 *
	 * @param ps the new key
	 */
	public void setKey(ProjectSetting ps) {
		this.key = ps.toString();
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the string value.
	 *
	 * @param ps the new string value
	 */
	public void setStringValue(ProjectSetting ps) {
		setStringValue(ps.toString());
	}

	/**
	 * Sets the string value.
	 *
	 * @param stringValue the new string value
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/* (non-Javadoc)
	 * @see storybook.model.oldModel.DbTable#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("ID=").append(getId());
		buf.append(", key=").append(getKey());
		if (getStringValue() != null) {
			buf.append(", stringValue=").append(getStringValue());
		}
		if (getIntegerValue() != null) {
			buf.append(", integerValue=").append(getIntegerValue());
		}
		if (getBooleanValue() != null) {
			buf.append(", booleanValue=").append(getBooleanValue());
		}
		return buf.toString();
	}
}
