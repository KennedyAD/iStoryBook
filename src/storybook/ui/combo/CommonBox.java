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
package storybook.ui.combo;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.text.Position;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.hbn.dao.AttributeDAOImpl;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.dao.ChapterDAOImpl;
import storybook.model.hbn.dao.GenderDAOImpl;
import storybook.model.hbn.dao.IdeaDAOImpl;
import storybook.model.hbn.dao.ItemDAOImpl;
import storybook.model.hbn.dao.ItemLinkDAOImpl;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.dao.StrandDAOImpl;
import storybook.model.hbn.dao.TagDAOImpl;
import storybook.model.hbn.entity.Attribute;
import storybook.model.hbn.entity.Category;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Gender;
import storybook.model.hbn.entity.Idea;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.ItemLink;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Strand;
import storybook.model.hbn.entity.Tag;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonBox.
 *
 * @author favdb
 */
public class CommonBox {

	/** The lb status. */
	public static String[] lbStatus = { I18N.getMsg("msg.status.outline"), I18N.getMsg("msg.status.draft"),
			I18N.getMsg("msg.status.1st.edit"), I18N.getMsg("msg.status.2nd.edit"), I18N.getMsg("msg.status.done") };

	/**
	 * Find gender.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the gender
	 */
	public static Gender findGender(MainFrame mainFrame, String str) {
		Gender rgender = null;
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		GenderDAOImpl dao = new GenderDAOImpl(session);
		List<Gender> genders = dao.findAll();
		for (Gender gender : genders) {
			if (str.equals(gender.getName())) {
				rgender = gender;
				break;
			}
		}
		model.commit();
		return (rgender);
	}

	/**
	 * Find item.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the item
	 */
	public static Item findItem(MainFrame mainFrame, String str) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> items = dao.findAll();
		Item r = null;
		for (Item u : items) {
			if (u.getName().equals(str)) {
				r = u;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Find location.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the location
	 */
	public static Location findLocation(MainFrame mainFrame, String str) {
		if ("".equals(str))
			return (null);
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> locations = dao.findAll();
		Location r = null;
		for (Location location : locations) {
			if (location.getFullName().equals(str)) {
				r = location;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Find part.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the part
	 */
	public static Part findPart(MainFrame mainFrame, String str) {
		if ("".equals(str)) {
			return (null);
		}
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		Part rpart = null;
		for (Part part : parts) {
			if (part.getNumberName().equals(str)) {
				rpart = part;
				break;
			}
		}
		model.commit();
		return (rpart);
	}

	/**
	 * Find person.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the person
	 */
	public static Person findPerson(MainFrame mainFrame, String str) {
		if ("".equals(str))
			return (null);
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		Person r = null;
		for (Person person : persons) {
			if (person.getFullName().equals(str)) {
				r = person;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Find person abbreviation.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return true, if successful
	 */
	public static boolean findPersonAbbreviation(MainFrame mainFrame, String str) {
		if (!"".equals(str)) {
			return (false);
		}
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		boolean r = false;
		for (Person person : persons) {
			if (person.getAbbreviation().equals(str)) {
				r = true;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Find scene.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the scene
	 */
	public static Scene findScene(MainFrame mainFrame, String str) {
		if ("".equals(str))
			return (null);
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findAll();
		Scene r = null;
		for (Scene scene : scenes) {
			if (scene.getFullTitle().equals(str)) {
				r = scene;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Find tag.
	 *
	 * @param mainFrame the main frame
	 * @param str the str
	 * @return the tag
	 */
	public static Tag findTag(MainFrame mainFrame, String str) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<Tag> tags = dao.findAll();
		Tag r = null;
		for (Tag u : tags) {
			if (u.getName().equals(str)) {
				r = u;
				break;
			}
		}
		model.commit();
		return (r);
	}

	/**
	 * Gets the cb category.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @return the cb category
	 */
	public static Category getCbCategory(MainFrame mainFrame, JComboBox<?> cb) {
		Category rcategory = null;
		if (cb.getSelectedIndex() == -1) {
			return (rcategory);
		}
		String str = cb.getSelectedItem().toString();
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		List<Category> categories = dao.findAll();
		for (Category category : categories) {
			if (category.getName().equals(str)) {
				rcategory = category;
				break;
			}
		}
		model.commit();
		return (rcategory);
	}

	/**
	 * Checks if is cb equals.
	 *
	 * @param cb the cb
	 * @param str the str
	 * @return true, if is cb equals
	 */
	public static boolean isCbEquals(JComboBox<String> cb, String str) {
		if (cb.getSelectedIndex() == -1) {
			if (str.equals("")) {
				return (true);
			}
		} else {
			if (str.equals("")) {
				return (false);
			}
			if (cb.getSelectedItem().equals(str)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Checks if is multi lb contains.
	 *
	 * @param lb the lb
	 * @param ls the ls
	 * @return true, if is multi lb contains
	 */
	public static boolean isMultiLbContains(JList<?> lb, List<?> ls) {
		List<?> lx = lb.getSelectedValuesList();
		if (lx.equals(ls)) {
			return (true);
		}
		return (false);
	}

	/**
	 * Load cb categories.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param item the item
	 */
	public static void loadCbCategories(MainFrame mainFrame, JComboBox<String> cb, Item item) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<String> categories = dao.findCategories();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = item.getCategory();
		for (String category : categories) {
			cb.addItem(category);
			if ((!x.isEmpty()) && (x.equals(category))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb categories.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param tag the tag
	 */
	public static void loadCbCategories(MainFrame mainFrame, JComboBox<String> cb, Tag tag) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<String> categories = dao.findCategories();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = tag.getCategory();
		for (String category : categories) {
			cb.addItem(category);
			if ((!x.isEmpty()) && (x.equals(category))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb category.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param idea the idea
	 */
	public static void loadCbCategory(MainFrame mainFrame, JComboBox<String> cb, Idea idea) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		IdeaDAOImpl dao = new IdeaDAOImpl(session);
		List<String> ideas = dao.findCategories();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String strIdea = idea.getCategory();
		for (String str : ideas) {
			cb.addItem(str);
			if ((!strIdea.equals("")) && (strIdea.equals(str))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb category.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param person the person
	 */
	public static void loadCbCategory(MainFrame mainFrame, JComboBox<String> cb, Person person) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		List<Category> categories = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (Category category : categories) {
			cb.addItem(category.getName());
			if ((!person.getFullName().equals(" ")) && (person.getCategory().equals(category))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb chapters.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param scene the scene
	 */
	public static void loadCbChapters(MainFrame mainFrame, JComboBox<String> cb, Scene scene) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ChapterDAOImpl dao = new ChapterDAOImpl(session);
		List<Chapter> chapters = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (Chapter chapter : chapters) {
			cb.addItem(chapter.getPart() + "." + chapter.getChapternoStr() + ". " + chapter.getTitle());
			if ((scene.hasChapter()) && (scene.getChapter().equals(chapter))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb cities.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param location the location
	 */
	public static void loadCbCities(MainFrame mainFrame, JComboBox<String> cb, Location location) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<String> cities = dao.findCities();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (String city : cities) {
			cb.addItem(city);
			if ((!location.getId().equals(new Long(-1))) && (location.getCity().equals(city))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb countries.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param location the location
	 */
	public static void loadCbCountries(MainFrame mainFrame, JComboBox<String> cb, Location location) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<String> countries = dao.findCountries();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (String country : countries) {
			cb.addItem(country);
			if ((location.getCountry() != null) && (location.getCountry().equals(country))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb genders.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param person the person
	 */
	public static void loadCbGenders(MainFrame mainFrame, JComboBox<String> cb, Person person) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		GenderDAOImpl dao = new GenderDAOImpl(session);
		List<Gender> genders = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (Gender gender : genders) {
			cb.addItem(gender.getName());
			if ((!person.getFullName().equals(" ")) && (person.getGender().equals(gender))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb items.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param item the item
	 */
	public static void loadCbItems(MainFrame mainFrame, JComboBox<String> cb, Item item) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> items = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = item.getName();
		for (Item u : items) {
			cb.addItem(u.getName());
			if ((!x.isEmpty()) && (x.equals(u.getName()))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb locations.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param location the location
	 */
	public static void loadCbLocations(MainFrame mainFrame, JComboBox<String> cb, Location location) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> locations = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = location.getFullName();
		for (Location u : locations) {
			cb.addItem(u.getFullName());
			if ((!x.isEmpty()) && (x.equals(u.getFullName()))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}


	/**
	 * Load cb locations.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param scene the scene
	 */
	public static void loadCbLocations(MainFrame mainFrame, JComboBox<String> cb, Scene scene) {
		if (!"".equals(scene.getTitle())) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			LocationDAOImpl dao = new LocationDAOImpl(session);
			List<Location> locations = dao.findAll();
			int ix = -1, i = 0;
			cb.removeAllItems();
			List<Location> sceneloc = scene.getLocations();
			for (Location location : locations) {
				cb.addItem(location.getFullName());
				/* if ((sceneloc != null) && (!sceneloc.isEmpty())) { */
				if ((sceneloc.contains(location))) {
					ix = i;
				}
				/* } */
				i++;
			}
			cb.setSelectedIndex(ix);
			model.commit();
		}
	}


	/**
	 * Load cb parts.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param chapter the chapter
	 */
	public static void loadCbParts(MainFrame mainFrame, JComboBox<String> cb, Chapter chapter) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (Part part : parts) {
			cb.addItem(part.getNumberName());
			if ((chapter.hasPart()) && (chapter.getPart().equals(part))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}


	/**
	 * Load cb persons.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param person the person
	 */
	public static void loadCbPersons(MainFrame mainFrame, JComboBox<String> cb, Person person) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = person.getFullName();
		for (Person u : persons) {
			cb.addItem(u.getFullName());
			if ((!x.isEmpty()) && (x.equals(u.getFullName()))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb scenes.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param scene the scene
	 */
	public static void loadCbScenes(MainFrame mainFrame, JComboBox<String> cb, Scene scene) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = scene.getFullTitle();
		for (Scene u : scenes) {
			cb.addItem(u.getFullTitle());
			if ((!x.isEmpty()) && (x.equals(u.getFullTitle()))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load cb sites.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param location the location
	 */
	public static void loadCbSites(MainFrame mainFrame, JComboBox<Location> cb, Location location) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> sites = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		for (Location site : sites) {
			cb.addItem(site);
			if ((!location.getId().equals(new Long(-1))) && (location.getSite().equals(site))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}


	/**
	 * Load cb status.
	 *
	 * @param cb the cb
	 * @param idea the idea
	 */
	public static void loadCbStatus(JComboBox<String> cb, Idea idea) {
		String[] lbSt = { I18N.getMsg("msg.ideas.status.not_started"), I18N.getMsg("msg.ideas.status.started"),
				I18N.getMsg("msg.ideas.status.completed"), I18N.getMsg("msg.ideas.status.abandoned") };
		cb.removeAllItems();
		for (String x : lbSt) {
			cb.addItem(x);
		}
		if (idea.getStatus() != null)
			cb.setSelectedIndex(idea.getStatus());
	}


	/**
	 * Load cb status.
	 *
	 * @param cb the cb
	 * @param scene the scene
	 */
	public static void loadCbStatus(JComboBox<String> cb, Scene scene) {
		cb.removeAllItems();
		Integer ix = 0, i = 0;
		for (String x : lbStatus) {
			cb.addItem(x);
			if ((scene.hasChapter()) && (scene.getStatus().equals(i))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
	}

	/**
	 * Load cb tags.
	 *
	 * @param mainFrame the main frame
	 * @param cb the cb
	 * @param tag the tag
	 */
	public static void loadCbTags(MainFrame mainFrame, JComboBox<String> cb, Tag tag) {
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<Tag> tags = dao.findAll();
		cb.removeAllItems();
		int ix = -1, i = 0;
		String x = tag.getName();
		for (Tag u : tags) {
			cb.addItem(u.getName());
			if ((!x.isEmpty()) && (x.equals(u.getName()))) {
				ix = i;
			}
			i++;
		}
		cb.setSelectedIndex(ix);
		model.commit();
	}

	/**
	 * Load lb attributes.
	 *
	 * @param mainFrame the main frame
	 * @param lb the lb
	 * @param person the person
	 */
	public static void loadLbAttributes(MainFrame mainFrame, JList<String> lb, Person person) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (person.getAttributes() != null) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			AttributeDAOImpl dao = new AttributeDAOImpl(session);
			List<Attribute> attributes = dao.findAll();
			int ix = -1, i = 0;
			for (Attribute attribute : attributes) {
				listModel.addElement(attribute.getKey() + ":" + attribute.getValue());
				if ((!person.getFullName().equals("")) && (attribute.equals(attribute))) {
					ix = i;
				}
				i++;
			}
			lb.setModel(listModel);
			lb.setSelectedIndex(ix);
			model.commit();
		} else {
			lb.setModel(listModel);
		}
	}

	/**
	 * Load lb items.
	 *
	 * @param mainFrame the main frame
	 * @param lb the lb
	 * @return the default list model
	 */
	static DefaultListModel<String> loadLbItems(MainFrame mainFrame, JList<String> lb) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> items = dao.findAll();
		for (Item item : items) {
			listModel.addElement(item.getName());
		}
		return (listModel);
	}

	/**
	 * Load lb items.
	 *
	 * @param mainFrame the main frame
	 * @param lb the lb
	 * @param scene the scene
	 */
	static void loadLbItems(MainFrame mainFrame, JList<String> lb, Scene scene) {
		DefaultListModel<String> listModel = loadLbItems(mainFrame, lb);
		lb.setModel(listModel);
		if (!"".equals(scene.getTitle())) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			ItemLinkDAOImpl dao = new ItemLinkDAOImpl(session);
			List<ItemLink> items = dao.findByScene(scene);
			int i;
			int[] indices = {};
			for (ItemLink item : items) {
				i = lb.getNextMatch(item.getItem().getName(), 0, Position.Bias.Forward);
				if (i != -1)
					indices[indices.length] = i + 1;
			}
			lb.setSelectedIndices(indices);
			model.commit();
		}
	}

	/**
	 * Load lb persons.
	 *
	 * @param mainFrame the main frame
	 * @param lb the lb
	 * @param scene the scene
	 */
	public static void loadLbPersons(MainFrame mainFrame, JList<String> lb, Scene scene) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (!"".equals(scene.getTitle())) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			PersonDAOImpl dao = new PersonDAOImpl(session);
			List<Person> persons = dao.findAll();
			int i = 0;
			int[] indices = {};
			for (Person person : persons) {
				listModel.addElement(person.getFullName());
				if ((scene.getPersons() != null) && (scene.getPersons().contains(person))) {
					indices[indices.length] = i;
				}
				i++;
			}
			lb.setModel(listModel);
			lb.setSelectedIndices(indices);
			model.commit();
		} else {
			lb.setModel(listModel);
		}
	}

	/**
	 * Load lb strands.
	 *
	 * @param mainFrame the main frame
	 * @param lb the lb
	 * @param scene the scene
	 */
	public static void loadLbStrands(MainFrame mainFrame, JList<String> lb, Scene scene) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (!"".equals(scene.getTitle())) {
			BookModel model = mainFrame.getBookModel();
			Session session = model.beginTransaction();
			StrandDAOImpl dao = new StrandDAOImpl(session);
			List<Strand> strands = dao.findAll();
			int ix = -1, i = 0;
			for (Strand strand : strands) {
				listModel.addElement(strand.getName());
				if ((scene.getStrand() != null) && (scene.getStrand().equals(strand))) {
					ix = i;
				}
				i++;
			}
			lb.setModel(listModel);
			lb.setSelectedIndex(ix);
			model.commit();
		} else {
			lb.setModel(listModel);
		}
	}

}
