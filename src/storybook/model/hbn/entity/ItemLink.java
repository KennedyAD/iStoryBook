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

// TODO: Auto-generated Javadoc
/**
 * The Class ItemLink.
 *
 * @hibernate.subclass discriminator-value="1"
 */
public class ItemLink extends AbstractTagLink {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1107713512313169659L;
	
	/** The item. */
	private Item item;

	/**
	 * Instantiates a new item link.
	 */
	public ItemLink() {
	}

	/**
	 * Instantiates a new item link.
	 *
	 * @param item the item
	 * @param type the type
	 * @param startScene the start scene
	 * @param endScene the end scene
	 * @param person the person
	 * @param location the location
	 */
	public ItemLink(Item item, Integer type, Scene startScene, Scene endScene, Person person, Location location) {
		super(type, startScene, endScene, person, location);
		this.item = item;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractTagLink#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		ItemLink test = (ItemLink) obj;
		boolean ret = true;
		ret = ret && equalsLongNullValue(item.id, test.getItem().getId());
		return ret;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 * @hibernate.many-to-one column="tag_id" cascade="none"
	 */
	public Item getItem() {
		return this.item;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractTagLink#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + item.hashCode();
		return hash;
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(Item item) {
		this.item = item;
	}
}
