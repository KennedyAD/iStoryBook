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
 * The Class TagLink.
 *
 * @hibernate.subclass discriminator-value="0"
 */
public class TagLink extends AbstractTagLink {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6245390568453929449L;
	
	/** The tag. */
	private Tag tag;

	/**
	 * Instantiates a new tag link.
	 */
	public TagLink() {
	}

	/**
	 * Instantiates a new tag link.
	 *
	 * @param tag the tag
	 * @param type the type
	 * @param startScene the start scene
	 * @param endScene the end scene
	 * @param person the person
	 * @param location the location
	 */
	public TagLink(Tag tag, Integer type, Scene startScene, Scene endScene, Person person, Location location) {
		super(type, startScene, endScene, person, location);
		this.tag = tag;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractTagLink#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		TagLink test = (TagLink) obj;
		boolean ret = true;
		ret = ret && equalsLongNullValue(tag.id, test.getTag().getId());
		return ret;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 * @hibernate.many-to-one column="tag_id" cascade="none"
	 */
	public Tag getTag() {
		return this.tag;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractTagLink#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + tag.hashCode();
		return hash;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag the new tag
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
