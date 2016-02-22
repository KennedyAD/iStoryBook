/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2015 FaVdB

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

import java.util.List;

import storybook.toolkit.I18N;
import storybook.toolkit.Period;

// TODO: Auto-generated Javadoc
/**
 * The Class Relationship.
 */
public class Relationship extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1442946726999591208L;
	
	/** The person1. */
	private Person person1;
	
	/** The person2. */
	private Person person2;
	
	/** The description. */
	String description;
	
	/** The start scene. */
	Scene startScene;
	
	/** The end scene. */
	Scene endScene;
	
	/** The notes. */
	String notes;
	
	/** The persons. */
	private List<Person> persons;
	
	/** The items. */
	private List<Item> items;
	
	/** The locations. */
	private List<Location> locations;

	/**
	 * Instantiates a new relationship.
	 */
	public Relationship() {
	}

	/**
	 * Instantiates a new relationship.
	 *
	 * @param person1 the person1
	 * @param person2 the person2
	 * @param description the description
	 * @param startScene the start scene
	 * @param endScene the end scene
	 * @param notes the notes
	 */
	public Relationship(Person person1, Person person2, String description, Scene startScene, Scene endScene,
			String notes) {
		this.person1 = person1;
		this.person2 = person2;
		this.description = description;
		this.startScene = startScene;
		this.endScene = endScene;
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
		return (this.toString().equals(obj.toString()));
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the end scene.
	 *
	 * @return the end scene
	 */
	public Scene getEndScene() {
		return this.endScene;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	public List<Location> getLocations() {
		return locations;
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
	 * Gets the period.
	 *
	 * @return the period
	 */
	public Period getPeriod() {
		if (hasPeriod()) {
			return new Period(getStartScene().getSceneTs(), getEndScene().getSceneTs());
		}
		if (hasStartScene()) {
			return new Period(getStartScene().getSceneTs(), getStartScene().getSceneTs());
		}
		return null;
	}

	/**
	 * Gets the person1.
	 *
	 * @return the person1
	 */
	public Person getPerson1() {
		return this.person1;
	}

	/**
	 * Gets the person2.
	 *
	 * @return the person2
	 */
	public Person getPerson2() {
		return this.person2;
	}

	/**
	 * Gets the persons.
	 *
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Gets the start scene.
	 *
	 * @return the start scene
	 */
	public Scene getStartScene() {
		return this.startScene;
	}

	/**
	 * Checks for end scene.
	 *
	 * @return true, if successful
	 */
	public boolean hasEndScene() {
		return endScene != null;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (person1 != null ? person1.hashCode() : 0);
		hash = hash * 31 + (person2 != null ? person2.hashCode() : 0);
		hash = hash * 31 + (description != null ? description.hashCode() : 0);
		hash = hash * 31 + (startScene != null ? startScene.hashCode() : 0);
		hash = hash * 31 + (endScene != null ? endScene.hashCode() : 0);
		hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
		return hash;
	}

	/**
	 * Checks for only scene.
	 *
	 * @return true, if successful
	 */
	public boolean hasOnlyScene() {
		return startScene != null && endScene == null && person1 == null && person2 == null && description == null
				&& numberOfPersons() == 0 && numberOfItems() == 0 && numberOfLocations() == 0;
	}

	/**
	 * Checks for period.
	 *
	 * @return true, if successful
	 */
	public boolean hasPeriod() {
		return (this.getStartScene() != null && this.getEndScene() != null);
	}

	/**
	 * Checks for person1.
	 *
	 * @return true, if successful
	 */
	public boolean hasPerson1() {
		return person1 != null;
	}

	/**
	 * Checks for person2.
	 *
	 * @return true, if successful
	 */
	public boolean hasPerson2() {
		return person2 != null;
	}

	/**
	 * Checks for start scene.
	 *
	 * @return true, if successful
	 */
	public boolean hasStartScene() {
		return this.startScene != null;
	}

	/**
	 * Number of items.
	 *
	 * @return the int
	 */
	public int numberOfItems() {
		return (items.size());
	}

	/**
	 * Number of locations.
	 *
	 * @return the int
	 */
	public int numberOfLocations() {
		return (locations.size());
	}

	/**
	 * Number of persons.
	 *
	 * @return the int
	 */
	public int numberOfPersons() {
		return (persons.size());
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
	 * Sets the end scene.
	 *
	 * @param endScene the new end scene
	 */
	public void setEndScene(Scene endScene) {
		this.endScene = endScene;
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
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Sets the locations.
	 *
	 * @param locations the new locations
	 */
	public void setLocations(List<Location> locations) {
		this.locations = locations;
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
	 * Sets the person1.
	 */
	public void setPerson1() {
		this.person1 = null;
	}

	/**
	 * Sets the person1.
	 *
	 * @param person the new person1
	 */
	public void setPerson1(Person person) {
		this.person1 = person;
	}

	/**
	 * Sets the person2.
	 */
	public void setPerson2() {
		this.person2 = null;
	}

	/**
	 * Sets the person2.
	 *
	 * @param person the new person2
	 */
	public void setPerson2(Person person) {
		this.person2 = person;
	}

	/**
	 * Sets the persons.
	 *
	 * @param persons the new persons
	 */
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	/**
	 * Sets the start scene.
	 *
	 * @param startScene the new start scene
	 */
	public void setStartScene(Scene startScene) {
		this.startScene = startScene;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isTransient()) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		/*
		 * if (hasPerson1()) { buf.append(person1.toString()); } if
		 * (hasPerson2()) { if (buf.length() > 0) { buf.append(", "); }
		 * buf.append(person2.toString()); }
		 */
		if (this.description != null) {
			if (buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(description);
		}
		if (hasOnlyScene()) {
			buf.append(" ");
			buf.append(I18N.getMsg("msg.common.scene"));
			buf.append(" ");
			buf.append(startScene.getChapterSceneNo(false));
		} else {
			if (hasStartScene()) {
				if (buf.length() > 0) {
					buf.append(",");
				}
				buf.append(" ");
				buf.append(I18N.getMsg("msg.common.scene"));
				buf.append(" ");
				buf.append(startScene.getChapterSceneNo(false));
			}
			if (hasPeriod()) {
				buf.append(" - ");
			}
			if (hasEndScene()) {
				buf.append(endScene.getChapterSceneNo(false));
			}
			if (hasPeriod()) {
				buf.append(" (");
				buf.append(getPeriod().getShortString());
				buf.append(")");
			}
		}
		return buf.toString();
	}
}
