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

public class Persongrp extends AbstractEntity {

	String description;
	Scene startScene;
	Scene endScene;
	String notes;
	
	private List<Person> persons;
    private List<Item> items;
    private List<Location> locations;
    
	public Persongrp() {
	}

	public Persongrp(String description, Scene startScene, Scene endScene, String notes) {
		this.description = description;
		this.startScene = startScene;
		this.endScene = endScene;
		this.notes = notes;
	}

	@Override
	public Long getId() {return this.id;}

	public void setId(Long id) {this.id = id;}

	public List<Person> getPersons() {return this.persons;}

	public boolean hasPersons() {return persons != null;}

	public void setPersons(List<Person> persons) {this.persons = persons;}

	public void setPersons() {this.persons = null;}

	public List<Item> getItems() {return this.items;}

	public boolean hasItems() {return items != null;}

	public void setItems(List<Item> items) {this.items = items;}

	public void setItems() {this.items = null;}

	public List<Location> getLocations() {return this.locations;}

	public boolean hasLocations() {return locations != null;}

	public void setLocations(List<Location> locations) {this.locations = locations;}

	public void setLocations() {this.locations = null;}

	public String getDescription() {return this.description;}

	public void setDescription(String description) {this.description = description;}

	public Scene getStartScene() {return this.startScene;}

	public boolean hasStartScene() {return this.startScene != null;}

	public void setStartScene(Scene startScene) {this.startScene = startScene;}

	public Scene getEndScene() {return this.endScene;}

	public boolean hasEndScene() {return endScene != null;}

	public void setEndScene(Scene endScene) {this.endScene = endScene;}

	public boolean hasOnlyScene() {
		return startScene != null && endScene == null && persons == null && description == null;
	}

	public boolean hasPeriod() {
		return (this.getStartScene() != null && this.getEndScene() != null);
	}

	public Period getPeriod() {
		if (hasPeriod()) {
			return new Period(getStartScene().getSceneTs(), getEndScene().getSceneTs());
		}
		if (hasStartScene()) {
			return new Period(getStartScene().getSceneTs(), getStartScene().getSceneTs());
		}
		return null;
	}

	public String getNotes() {return this.notes;}

	public void setNotes(String notes) {this.notes = notes;}

	@Override
	public String toString() {
		if (isTransient()) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		if (this.description!=null) {
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

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return(this.toString().equals(obj.toString()));
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (persons != null ? persons.hashCode() : 0);
		hash = hash * 31 + (items != null ? items.hashCode() : 0);
		hash = hash * 31 + (locations != null ? locations.hashCode() : 0);
		hash = hash * 31 + (description != null ? description.hashCode() : 0);
		hash = hash * 31 + (startScene != null ? startScene.hashCode() : 0);
		hash = hash * 31 + (endScene != null ? endScene.hashCode() : 0);
		hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
		return hash;
	}
}
