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

import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractTag.
 *
 * @hibernate.class table="TAG" discriminator-value="-1"
 * @hibernate.discriminator type="integer" column="type"
 */
public abstract class AbstractTag extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8952254375477593986L;
	
	/** The Constant TYPE_TAG. */
	public static final int TYPE_TAG = 0;
	
	/** The Constant TYPE_ITEM. */
	public static final int TYPE_ITEM = 1;
	
	/** The Constant TYPE_LINK. */
	public static final int TYPE_LINK = 10;
	
	/** The Constant TYPE_MEMO. */
	public static final int TYPE_MEMO = 20;

	/** The type. */
	protected Integer type;
	
	/** The category. */
	private String category;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The notes. */
	private String notes;

	/**
	 * Instantiates a new abstract tag.
	 */
	public AbstractTag() {
	}

	/**
	 * Instantiates a new abstract tag.
	 *
	 * @param type the type
	 * @param category the category
	 * @param name the name
	 * @param description the description
	 * @param notes the notes
	 */
	public AbstractTag(Integer type, String category, String name, String description, String notes) {
		this.type = type;
		this.category = category;
		this.name = name;
		this.description = description;
		this.notes = notes;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		AbstractTag test = (AbstractTag) obj;
		if (!Objects.equals(type, test.type)) {
			return false;
		}
		boolean ret = true;
		ret = ret && equalsStringNullValue(name, test.getName());
		ret = ret && equalsStringNullValue(category, test.getCategory());
		ret = ret && equalsStringNullValue(description, test.getDescription());
		ret = ret && equalsStringNullValue(notes, test.getNotes());
		return ret;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public Item getItem() {
		if (type != TYPE_ITEM) {
			return null;
		}
		return (Item) this;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return this.notes;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public Tag getTag() {
		if (type != TYPE_TAG) {
			return null;
		}
		return (Tag) this;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (type != null ? type.hashCode() : 0);
		hash = hash * 31 + (name != null ? name.hashCode() : 0);
		hash = hash * 31 + (category != null ? category.hashCode() : 0);
		hash = hash * 31 + (description != null ? description.hashCode() : 0);
		hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
		return hash;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = (category == null ? "" : category);
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
}
