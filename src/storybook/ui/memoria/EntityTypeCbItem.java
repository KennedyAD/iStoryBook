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
package storybook.ui.memoria;

import javax.swing.Icon;

import storybook.model.EntityUtil;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTypeCbItem.
 */
public class EntityTypeCbItem {

	/**
	 * The Enum Type.
	 */
	public static enum Type {

		/** The person. */
		PERSON, /** The scene. */
 SCENE, /** The location. */
 LOCATION, /** The tag. */
 TAG, /** The item. */
 ITEM;
	}

	/** The type. */
	private Type type;

	/**
	 * Instantiates a new entity type cb item.
	 *
	 * @param paramType the param type
	 */
	public EntityTypeCbItem(Type paramType) {
		this.type = paramType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object paramObject) {
		if (getClass() != paramObject.getClass()) {
			return false;
		}
		EntityTypeCbItem localEntityTypeCbItem = (EntityTypeCbItem) paramObject;
		boolean bool = true;
		bool = (bool) && (this.type.equals(localEntityTypeCbItem.type));
		return bool;
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	private AbstractEntity getEntity() {
		if (this.type == Type.SCENE) {
			return new Scene();
		}
		if (this.type == Type.PERSON) {
			return new Person();
		}
		if (this.type == Type.LOCATION) {
			return new Location();
		}
		if (this.type == Type.TAG) {
			return new Tag();
		}
		if (this.type == Type.ITEM) {
			return new Item();
		}
		return null;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public Icon getIcon() {
		return EntityUtil.getEntityIcon(getEntity());
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return EntityUtil.getEntityTitle(getEntity(), Boolean.valueOf(false));
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int i = super.hashCode();
		i = i * 31 + (this.type != null ? this.type.hashCode() : 0);
		return i;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getText();
	}
}