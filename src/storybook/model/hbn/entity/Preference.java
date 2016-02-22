/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package storybook.model.hbn.entity;

import storybook.SbConstants.PreferenceKey;

// TODO: Auto-generated Javadoc
/**
 * The Class Preference.
 */
public class Preference extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8910428674106426466L;
	
	/** The key. */
	private String key;
	
	/** The string value. */
	private String stringValue;
	
	/** The integer value. */
	private Integer integerValue;
	
	/** The boolean value. */
	private Boolean booleanValue;
	
	/** The bin value. */
	private byte[] binValue;

	/**
	 * Instantiates a new preference.
	 */
	public Preference() {
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param booleanValue the boolean value
	 */
	public Preference(PreferenceKey key, Boolean booleanValue) {
		this(key.toString(), booleanValue);
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param integerValue the integer value
	 */
	public Preference(PreferenceKey key, Integer integerValue) {
		this(key.toString(), integerValue);
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param stringValue the string value
	 */
	public Preference(PreferenceKey key, String stringValue) {
		this(key.toString(), stringValue);
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 */
	public Preference(String key) {
		this.key = key;
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param booleanValue the boolean value
	 */
	public Preference(String key, Boolean booleanValue) {
		this.key = key;
		this.booleanValue = booleanValue;
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param binValue the bin value
	 */
	public Preference(String key, byte[] binValue) {
		this.key = key;
		this.binValue = binValue;
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param integerValue the integer value
	 */
	public Preference(String key, Integer integerValue) {
		this.key = key;
		this.integerValue = integerValue;
	}

	/**
	 * Instantiates a new preference.
	 *
	 * @param key the key
	 * @param stringValue the string value
	 */
	public Preference(String key, String stringValue) {
		this.key = key;
		this.stringValue = stringValue;
	}

	/**
	 * Gets the bin value.
	 *
	 * @return the bin value
	 */
	public byte[] getBinValue() {
		return binValue;
	}

	/**
	 * Gets the boolean value.
	 *
	 * @return the boolean value
	 */
	public Boolean getBooleanValue() {
		return booleanValue;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
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

	/**
	 * Gets the string value.
	 *
	 * @return the string value
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * Sets the bin value.
	 *
	 * @param binValue the new bin value
	 */
	public void setBinValue(byte[] binValue) {
		this.binValue = binValue;
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
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getKey() + ": '" + getStringValue() + "' / " + getIntegerValue() + " / " + getBooleanValue();
	}
}
