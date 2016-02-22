/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2015 Martin Mustun, Pete Keller

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

import static storybook.toolkit.DateUtil.clearTime;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import storybook.toolkit.swing.ColorUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 *
 * @hibernate.class table="PERSON"
 */
public class Person extends AbstractEntity implements Comparable<Person> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -184234734318218754L;
	
	/** The gender. */
	private Gender gender;
	
	/** The firstname. */
	private String firstname = "";
	
	/** The lastname. */
	private String lastname = "";
	
	/** The abbreviation. */
	private String abbreviation = "";
	
	/** The birthday. */
	private Date birthday;
	
	/** The dayofdeath. */
	private Date dayofdeath;
	
	/** The occupation. */
	private String occupation = "";
	
	/** The description. */
	private String description = "";
	
	/** The color. */
	private Integer color;
	
	/** The notes. */
	private String notes = "";
	
	/** The category. */
	private Category category;
	
	/** The attributes. */
	private List<Attribute> attributes;

	/**
	 * Instantiates a new person.
	 */
	public Person() {
		super();
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param gender the gender
	 * @param firstname the firstname
	 * @param lastname the lastname
	 * @param abbreviation the abbreviation
	 * @param birthday the birthday
	 * @param dayofdeath the dayofdeath
	 * @param occupation the occupation
	 * @param description the description
	 * @param color the color
	 * @param notes the notes
	 * @param category the category
	 * @param attributes the attributes
	 */
	public Person(Gender gender, String firstname, String lastname, String abbreviation, Date birthday, Date dayofdeath,
			String occupation, String description, Integer color, String notes, Category category,
			List<Attribute> attributes) {
		this.gender = gender;
		this.firstname = firstname;
		this.lastname = lastname;
		this.abbreviation = abbreviation;
		this.birthday = birthday;
		this.dayofdeath = dayofdeath;
		this.occupation = occupation;
		this.description = description;
		this.color = color;
		this.notes = notes;
		this.category = category;
		this.attributes = attributes;
	}

	/**
	 * Calculate age.
	 *
	 * @param now the now
	 * @return the int
	 */
	public int calculateAge(Date now) {
		if (birthday == null) {
			return -1;
		}

		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(birthday);
		dateOfBirth = clearTime(dateOfBirth);

		if (isDead(now)) {
			Calendar death = new GregorianCalendar();
			death.setTime(getDayofdeath());
			death = clearTime(death);

			int age = death.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

			Calendar dateOfBirth2 = new GregorianCalendar();
			dateOfBirth2.setTime(birthday);
			dateOfBirth2 = clearTime(dateOfBirth2);
			dateOfBirth2.add(Calendar.YEAR, age);

			if (death.before(dateOfBirth2)) {
				age--;
			}
			return age;
		}

		Calendar today = new GregorianCalendar();
		today.setTime(now);

		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		dateOfBirth.add(Calendar.YEAR, age);

		if (today.before(dateOfBirth))
			age--;
		return age;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Person o) {
		if (category == null && o == null) {
			return 0;
		}
		if (category != null && o.getCategory() == null) {
			return -1;
		}
		if (o.getCategory() != null && category == null) {
			return -1;
		}
		int cmp = category.getSort().compareTo(o.getCategory().getSort());
		if (cmp == 0) {
			return getFullName().compareTo(o.getFullName());
		}
		return cmp;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Person test = (Person) obj;
		boolean ret = true;
		ret = ret && equalsStringNullValue(abbreviation, test.getAbbreviation());
		ret = ret && equalsStringNullValue(firstname, test.getFirstname());
		ret = ret && equalsStringNullValue(lastname, test.getLastname());
		ret = ret && equalsObjectNullValue(gender, test.getGender());
		ret = ret && equalsDateNullValue(birthday, test.getBirthday());
		ret = ret && equalsDateNullValue(dayofdeath, test.getDayofdeath());
		ret = ret && equalsIntegerNullValue(color, test.getColor());
		ret = ret && equalsObjectNullValue(category, test.getCategory());
		ret = ret && equalsStringNullValue(description, test.getDescription());
		ret = ret && equalsStringNullValue(notes, test.getNotes());
		ret = ret && equalsListNullValue(attributes, test.getAttributes());
		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#getAbbr()
	 */
	@Override
	public String getAbbr() {
		return abbreviation;
	}

	/**
	 * Gets the abbreviation.
	 *
	 * @return the abbreviation
	 * @hibernate.property 
	 */
	public String getAbbreviation() {
		return this.abbreviation == null ? "" : this.abbreviation;
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Gets the birthday.
	 *
	 * @return the birthday
	 * @hibernate.property 
	 */
	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 * @hibernate.many-to-one column="category_id" cascade="none" lazy="false"
	 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 * @hibernate.property 
	 */
	public Integer getColor() {
		return this.color;
	}

	/**
	 * Gets the dayofdeath.
	 *
	 * @return the dayofdeath
	 * @hibernate.property 
	 */
	public Date getDayofdeath() {
		return this.dayofdeath;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 * @hibernate.property 
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 * @hibernate.property 
	 */
	public String getFirstname() {
		return this.firstname == null ? "" : this.firstname;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return getFirstname() + " " + getLastname();
	}

	/**
	 * Gets the full name abbr.
	 *
	 * @return the full name abbr
	 */
	public String getFullNameAbbr() {
		return getFirstname() + " " + getLastname() + " [" + getAbbreviation() + "]";
	}

	// cascade: none,all,save-update,delete
	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 * @hibernate.many-to-one column="gender_id" cascade="none"
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * Gets the HTML color.
	 *
	 * @return the HTML color
	 */
	public String getHTMLColor() {
		return ColorUtil.getHTMLName(getJColor());
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#getIcon()
	 */
	@Override
	public Icon getIcon() {
		if (gender != null) {
			return gender.getIcon();
		}
		return new ImageIcon();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 * @hibernate.id column="ID" generator-class="increment"
	 *               unsaved-value="null"
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * Gets the j color.
	 *
	 * @return the j color
	 */
	public Color getJColor() {
		if (color == null) {
			return null;
		}
		return new Color(color);
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 * @hibernate.property 
	 */
	public String getLastname() {
		return this.lastname == null ? "" : this.lastname;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 * @hibernate.property 
	 */
	public String getNotes() {
		return this.notes;
	}

	/**
	 * Gets the occupation.
	 *
	 * @return the occupation
	 * @hibernate.property 
	 */
	public String getOccupation() {
		return this.occupation;
	}

	/* (non-Javadoc)
	 * @see storybook.model.hbn.entity.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (abbreviation != null ? abbreviation.hashCode() : 0);
		hash = hash * 31 + (firstname != null ? firstname.hashCode() : 0);
		hash = hash * 31 + (lastname != null ? lastname.hashCode() : 0);
		hash = hash * 31 + (gender != null ? gender.hashCode() : 0);
		hash = hash * 31 + (birthday != null ? birthday.hashCode() : 0);
		hash = hash * 31 + (dayofdeath != null ? dayofdeath.hashCode() : 0);
		hash = hash * 31 + (color != null ? color.hashCode() : 0);
		hash = hash * 31 + (category != null ? category.hashCode() : 0);
		hash = hash * 31 + (description != null ? description.hashCode() : 0);
		hash = hash * 31 + (notes != null ? notes.hashCode() : 0);
		hash = hash * 31 + (attributes != null ? getListHashCode(attributes) : 0);
		return hash;
	}

	/**
	 * Checks if is dead.
	 *
	 * @param now the now
	 * @return the boolean
	 */
	public Boolean isDead(Date now) {
		if (getDayofdeath() == null || now == null) {
			return false;
		}
		return (now.after(getDayofdeath()));
	}

	/**
	 * Sets the abbreviation.
	 *
	 * @param abbreviation the new abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes the new attributes
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * Sets the dayofdeath.
	 *
	 * @param dayofdeath the new dayofdeath
	 */
	public void setDayofdeath(Date dayofdeath) {
		this.dayofdeath = dayofdeath;
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
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
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
	 * Sets the j color.
	 *
	 * @param color the new j color
	 */
	public void setJColor(Color color) {
		if (color == null) {
			this.color = null;
			return;
		}
		this.color = color.getRGB();
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	 * Sets the occupation.
	 *
	 * @param occupation the new occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isTransient()) {
			// return I18N.getMsg("msg.common.person") + " [" + getTransientId()
			// + "]";
			return "";
		}
		return getFullNameAbbr();
	}
}
