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

/**
 * Ideas generated by hbm2java
 *
 * @hibernate.class table="ATTRIBUTE"
 */
public class Attribute extends AbstractEntity {

	private String key;
	private String value;

	public Attribute() {
	}

	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Attribute test = (Attribute) obj;
		boolean ret = true;
		ret = ret && equalsStringNullValue(key, test.getKey());
		ret = ret && equalsStringNullValue(value, test.getValue());
		return ret;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (key != null ? key.hashCode() : 0);
		hash = hash * 31 + (value != null ? value.hashCode() : 0);
		return hash;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return key + ": " + value;
	}
}
