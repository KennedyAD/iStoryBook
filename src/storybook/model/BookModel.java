/*
Storybook: Open Source software for novelists and authors.
Copyright (C) 2008 - 2012 Martin Mustun, 2015 FaVdB

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

package storybook.model;

import java.awt.Component;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import storybook.SbApp;
import storybook.SbConstants.ViewName;
import storybook.controller.BookController;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.dao.ChapterDAOImpl;
import storybook.model.hbn.dao.GenderDAOImpl;
import storybook.model.hbn.dao.IdeaDAOImpl;
import storybook.model.hbn.dao.InternalDAOImpl;
import storybook.model.hbn.dao.ItemDAOImpl;
import storybook.model.hbn.dao.ItemLinkDAOImpl;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.dao.MemoDAOImpl;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.dao.RelationshipDAOImpl;
import storybook.model.hbn.dao.SceneDAOImpl;
import storybook.model.hbn.dao.StrandDAOImpl;
import storybook.model.hbn.dao.TagDAOImpl;
import storybook.model.hbn.dao.TagLinkDAOImpl;
import storybook.model.hbn.dao.TimeEventDAOImpl;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Category;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Gender;
import storybook.model.hbn.entity.Idea;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Item;
import storybook.model.hbn.entity.ItemLink;
import storybook.model.hbn.entity.Location;
import storybook.model.hbn.entity.Memo;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Person;
import storybook.model.hbn.entity.Relationship;
import storybook.model.hbn.entity.Scene;
import storybook.model.hbn.entity.Strand;
import storybook.model.hbn.entity.Tag;
import storybook.model.hbn.entity.TagLink;
import storybook.model.hbn.entity.TimeEvent;
import storybook.model.state.SceneState;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.ColorUtil;
import storybook.ui.MainFrame;
import storybook.ui.SbView;
import storybook.ui.panel.book.BookPanel;
import storybook.ui.panel.chrono.ChronoPanel;
import storybook.ui.panel.manage.ManagePanel;
import storybook.ui.panel.reading.ReadingPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class BookModel.
 *
 * @author martin
 */
public class BookModel extends AbstractModel {

	/**
	 * Instantiates a new book model.
	 *
	 * @param m the m
	 */
	public BookModel(MainFrame m) {
		super(m);
	}

	/* (non-Javadoc)
	 * @see storybook.model.AbstractModel#fireAgain()
	 */
	@Override
	public void fireAgain() {
		SbApp.trace("BookModel.fireAgain()");

		fireAgainCategories();
		fireAgainChapters();
		fireAgainGenders();
		fireAgainIdeas();
		fireAgainInternals();
		fireAgainItems();
		fireAgainItemLinks();
		fireAgainLocations();
		fireAgainParts();
		fireAgainPersons();
		fireAgainPlan();
		fireAgainRelationships();
		fireAgainScenes();
		fireAgainStrands();
		fireAgainTags();
		fireAgainTagLinks();
		fireAgainTimeEvent();
	}

	/**
	 * Fire again.
	 *
	 * @param view the view
	 */
	public void fireAgain(SbView view) {
		SbApp.trace("BookModel.fireAgain(" + view.getName() + ")");
		if (ViewName.CHRONO.compare(view)) {
			fireAgainScenes();
		} else if (ViewName.BOOK.compare(view)) {
			fireAgainScenes();
		} else if (ViewName.READING.compare(view)) {
			fireAgainChapters();
		} else if (ViewName.MANAGE.compare(view)) {
			fireAgainChapters();
		} else if (ViewName.SCENES.compare(view)) {
			fireAgainScenes();
		} else if (ViewName.CHAPTERS.compare(view)) {
			fireAgainChapters();
		} else if (ViewName.PARTS.compare(view)) {
			fireAgainParts();
		} else if (ViewName.LOCATIONS.compare(view)) {
			fireAgainLocations();
		} else if (ViewName.PERSONS.compare(view)) {
			fireAgainPersons();
		} else if (ViewName.RELATIONSHIPS.compare(view)) {
			fireAgainRelationships();
		} else if (ViewName.GENDERS.compare(view)) {
			fireAgainGenders();
		} else if (ViewName.CATEGORIES.compare(view)) {
			fireAgainCategories();
		} else if (ViewName.STRANDS.compare(view)) {
			fireAgainStrands();
		} else if (ViewName.IDEAS.compare(view)) {
			fireAgainIdeas();
		} else if (ViewName.MEMOS.compare(view)) {
			fireAgainMemos();
		} else if (ViewName.TAGS.compare(view)) {
			fireAgainTags();
		} else if (ViewName.ITEMS.compare(view)) {
			fireAgainItems();
		} else if (ViewName.TAGLINKS.compare(view)) {
			fireAgainTagLinks();
		} else if (ViewName.ITEMLINKS.compare(view)) {
			fireAgainItemLinks();
		} else if (ViewName.INTERNALS.compare(view)) {
			fireAgainInternals();
		} else if (ViewName.PLAN.compare(view)) {
			fireAgainPlan();
		} else if (ViewName.TIMEEVENT.compare(view)) {
			fireAgainTimeEvent();
		}
	}

	/**
	 * Fire again categories.
	 */
	private void fireAgainCategories() {
		SbApp.trace("BookModel.fireAgainCategories()");
		Session session = beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		List<Category> categories = dao.findAll();
		commit();
		firePropertyChange(BookController.CategoryProps.INIT.toString(), null, categories);
	}

	/**
	 * Fire again chapters.
	 */
	private void fireAgainChapters() {
		SbApp.trace("BookModel.fireAgainChapters()");
		Session session = beginTransaction();
		ChapterDAOImpl dao = new ChapterDAOImpl(session);
		List<Chapter> chapters = dao.findAll();
		commit();
		firePropertyChange(BookController.ChapterProps.INIT.toString(), null, chapters);
	}

	/**
	 * Fire again genders.
	 */
	private void fireAgainGenders() {
		SbApp.trace("BookModel.fireAgainGenders()");
		Session session = beginTransaction();
		GenderDAOImpl dao = new GenderDAOImpl(session);
		List<Gender> genders = dao.findAll();
		commit();
		firePropertyChange(BookController.GenderProps.INIT.toString(), null, genders);
	}

	/**
	 * Fire again ideas.
	 */
	private void fireAgainIdeas() {
		SbApp.trace("BookModel.fireAgainIdeas()");
		Session session = beginTransaction();
		IdeaDAOImpl dao = new IdeaDAOImpl(session);
		List<Idea> ideas = dao.findAll();
		commit();
		firePropertyChange(BookController.IdeaProps.INIT.toString(), null, ideas);
	}

	/**
	 * Fire again internals.
	 */
	private void fireAgainInternals() {
		SbApp.trace("BookModel.fireAgainInternals()");
		Session session = beginTransaction();
		InternalDAOImpl dao = new InternalDAOImpl(session);
		List<Internal> internals = dao.findAll();
		commit();
		firePropertyChange(BookController.InternalProps.INIT.toString(), null, internals);
	}

	/**
	 * Fire again item links.
	 */
	private void fireAgainItemLinks() {
		SbApp.trace("BookModel.fireAgainItemLinks()");
		Session session = beginTransaction();
		ItemLinkDAOImpl dao = new ItemLinkDAOImpl(session);
		List<ItemLink> itemLinks = dao.findAll();
		commit();
		firePropertyChange(BookController.ItemLinkProps.INIT.toString(), null, itemLinks);
	}

	/**
	 * Fire again items.
	 */
	private void fireAgainItems() {
		SbApp.trace("BookModel.fireAgainItems()");
		Session session = beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> items = dao.findAll();
		commit();
		firePropertyChange(BookController.ItemProps.INIT.toString(), null, items);
	}

	/**
	 * Fire again locations.
	 */
	private void fireAgainLocations() {
		SbApp.trace("BookModel.fireAgainLocations()");
		Session session = beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> locations = dao.findAll();
		commit();
		firePropertyChange(BookController.LocationProps.INIT.toString(), null, locations);
	}

	/**
	 * Fire again memos.
	 */
	private void fireAgainMemos() {
		SbApp.trace("BookModel.fireAgainMemos()");
		Session session = beginTransaction();
		MemoDAOImpl dao = new MemoDAOImpl(session);
		List<Memo> memos = dao.findAll();
		commit();
		firePropertyChange(BookController.MemoProps.INIT.toString(), null, memos);
	}

	/**
	 * Fire again parts.
	 */
	private void fireAgainParts() {
		SbApp.trace("BookModel.fireAgainParts()");
		Session session = beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		commit();
		firePropertyChange(BookController.PartProps.INIT.toString(), null, parts);
	}

	/**
	 * Fire again persons.
	 */
	private void fireAgainPersons() {
		SbApp.trace("BookModel.fireAgainPersons()");
		Session session = beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> persons = dao.findAll();
		commit();
		firePropertyChange(BookController.PersonProps.INIT.toString(), null, persons);
	}

	/**
	 * Fire again plan.
	 */
	private void fireAgainPlan() {
	}

	/**
	 * Fire again relationships.
	 */
	private void fireAgainRelationships() {
		SbApp.trace("BookModel.fireAgainRelationships()");
		Session session = beginTransaction();
		RelationshipDAOImpl dao = new RelationshipDAOImpl(session);
		List<Relationship> relationships = dao.findAll();
		commit();
		firePropertyChange(BookController.RelationshipProps.INIT.toString(), null, relationships);
	}

	/**
	 * Fire again scenes.
	 */
	private void fireAgainScenes() {
		SbApp.trace("BookModel.fireAgainScenes()");
		Session session = beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findAll();
		commit();
		firePropertyChange(BookController.SceneProps.INIT.toString(), null, scenes);
	}

	/**
	 * Fire again strands.
	 */
	private void fireAgainStrands() {
		SbApp.trace("BookModel.fireAgainStrands()");
		Session session = beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		List<Strand> strands = dao.findAll();
		commit();
		firePropertyChange(BookController.StrandProps.INIT.toString(), null, strands);
	}

	/**
	 * Fire again tag links.
	 */
	private void fireAgainTagLinks() {
		SbApp.trace("BookModel.fireAgainTagLinks()");
		Session session = beginTransaction();
		TagLinkDAOImpl dao = new TagLinkDAOImpl(session);
		List<TagLink> tagLinks = dao.findAll();
		commit();
		firePropertyChange(BookController.TagLinkProps.INIT.toString(), null, tagLinks);
	}

	/**
	 * Fire again tags.
	 */
	private void fireAgainTags() {
		SbApp.trace("BookModel.fireAgainTags()");
		Session session = beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		List<Tag> tags = dao.findAll();
		commit();
		firePropertyChange(BookController.TagProps.INIT.toString(), null, tags);
	}

	/**
	 * Fire again time event.
	 */
	private void fireAgainTimeEvent() {
		SbApp.trace("BookModel.fireAgainTimeEvent()");
		Session session = beginTransaction();
		TimeEventDAOImpl dao = new TimeEventDAOImpl(session);
		List<TimeEvent> internals = dao.findAll();
		commit();
		firePropertyChange(BookController.TimeEventProps.INIT.toString(), null, internals);
	}

	/**
	 * Inits the entites.
	 */
	public synchronized void initEntites() {
		SbApp.trace("BookModel.initEntities()");
		Session session = beginTransaction();

		// default strand
		Strand strand = new Strand();
		strand.setName(I18N.getMsg("db.init.strand.name"));
		strand.setAbbreviation(I18N.getMsg("db.init.strand.abbr"));
		strand.setSort(1);
		strand.setJColor(ColorUtil.getNiceBlue());
		strand.setNotes("");
		session.save(strand);

		// default part
		Part part = new Part(1, I18N.getMsg("db.init.part"), "", null, new Timestamp(new Date().getTime()), null, null);
		session.save(part);

		// first chapter
		Chapter chapter = new Chapter();
		chapter.setPart(part);
		chapter.setChapterno(1);
		chapter.setTitle(I18N.getMsg("msg.common.chapter") + " 1");
		chapter.setDescription("");
		chapter.setNotes("");
		chapter.setCreationTime(new Timestamp(new Date().getTime()));
		chapter.setObjectiveTime(null);
		chapter.setDoneTime(null);
		session.save(chapter);

		// first scene
		Scene scene = EntityUtil.createScene(strand, chapter);
		session.save(scene);

		// default genders
		Gender male = new Gender(I18N.getMsg("msg.dlg.person.gender.male"), 12, 6, 47, 14);
		session.save(male);
		Gender female = new Gender(I18N.getMsg("msg.dlg.person.gender.female"), 12, 6, 47, 14);
		session.save(female);

		// default categories
		Category major = new Category(1, I18N.getMsg("msg.category.central.character"), null);
		session.save(major);
		Category minor = new Category(2, I18N.getMsg("msg.category.minor.character"), null);
		session.save(minor);

		commit();
	}

	/* (non-Javadoc)
	 * @see storybook.model.AbstractModel#initSession(java.lang.String)
	 */
	@Override
	public synchronized void initSession(String dbName) {
		SbApp.trace("BookModel.initSession(" + dbName + ")");
		try {
			super.initSession(dbName);
			Session session = beginTransaction();
			// test queries
			sessionFactory.query(new PartDAOImpl(session));
			sessionFactory.query(new ChapterDAOImpl(session));
			commit();
			SbApp.trace("test query OK");
		} catch (Exception e) {
			SbApp.trace("test query not OK");
		}
	}

	/**
	 * Sets the book height factor.
	 *
	 * @param val the new book height factor
	 */
	public void setBookHeightFactor(Integer val) {
		firePropertyChange(BookController.BookViewProps.HEIGHT_FACTOR.toString(), null, val);
	}

	/**
	 * Sets the book show entity.
	 *
	 * @param chapter the new book show entity
	 */
	public void setBookShowEntity(Chapter chapter) {
		firePropertyChange(BookController.BookViewProps.SHOW_ENTITY.toString(), null, chapter);
	}

	/**
	 * Sets the book show entity.
	 *
	 * @param scene the new book show entity
	 */
	public void setBookShowEntity(Scene scene) {
		firePropertyChange(BookController.BookViewProps.SHOW_ENTITY.toString(), null, scene);
	}

	/**
	 * Sets the book zoom.
	 *
	 * @param val the new book zoom
	 */
	// book view
	public void setBookZoom(Integer val) {
		firePropertyChange(BookController.BookViewProps.ZOOM.toString(), null, val);
	}

	/**
	 * Sets the change part.
	 *
	 * @param part the new change part
	 */
	public synchronized void setChangePart(Part part) {
		firePropertyChange(BookController.PartProps.CHANGE.toString(), null, part);
	}

	/**
	 * Sets the chrono layout direction.
	 *
	 * @param val the new chrono layout direction
	 */
	public void setChronoLayoutDirection(Boolean val) {
		firePropertyChange(BookController.ChronoViewProps.LAYOUT_DIRECTION.toString(), null, val);
	}

	/**
	 * Sets the chrono show date difference.
	 *
	 * @param val the new chrono show date difference
	 */
	public void setChronoShowDateDifference(Boolean val) {
		firePropertyChange(BookController.ChronoViewProps.SHOW_DATE_DIFFERENCE.toString(), null, val);
	}

	/**
	 * Sets the chrono show entity.
	 *
	 * @param chapter the new chrono show entity
	 */
	public void setChronoShowEntity(Chapter chapter) {
		firePropertyChange(BookController.ChronoViewProps.SHOW_ENTITY.toString(), null, chapter);
	}

	/**
	 * Sets the chrono show entity.
	 *
	 * @param scene the new chrono show entity
	 */
	public void setChronoShowEntity(Scene scene) {
		firePropertyChange(BookController.ChronoViewProps.SHOW_ENTITY.toString(), null, scene);
	}

	/**
	 * Sets the chrono zoom.
	 *
	 * @param val the new chrono zoom
	 */
	// chrono view
	public void setChronoZoom(Integer val) {
		firePropertyChange(BookController.ChronoViewProps.ZOOM.toString(), null, val);
	}

	/**
	 * Sets the delete category.
	 *
	 * @param category the new delete category
	 */
	public synchronized void setDeleteCategory(Category category) {
		if (category.getId() == null) {
			return;
		}
		// set category of affected persons to "minor"
		Session session = beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		Category minor = dao.findMinor();
		List<Person> persons = dao.findPersons(category);
		commit();
		for (Person person : persons) {
			person.setCategory(minor);
			setUpdatePerson(person);
		}
		// delete category
		session = beginTransaction();
		session.delete(category);
		commit();
		firePropertyChange(BookController.CategoryProps.DELETE.toString(), category, null);
	}

	/**
	 * Sets the delete chapter.
	 *
	 * @param chapter the new delete chapter
	 */
	public synchronized void setDeleteChapter(Chapter chapter) {
		if (chapter.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		// find scenes, set chapter to null
		ChapterDAOImpl dao = new ChapterDAOImpl(session);
		List<Scene> scenes = dao.findScenes(chapter);
		commit();
		for (Scene scene : scenes) {
			scene.setChapter();
			setUpdateScene(scene);
		}
		// delete chapter
		session = beginTransaction();
		session.delete(chapter);
		commit();
		firePropertyChange(BookController.ChapterProps.DELETE.toString(), chapter, null);
	}

	/**
	 * Sets the delete gender.
	 *
	 * @param gender the new delete gender
	 */
	public synchronized void setDeleteGender(Gender gender) {
		if (gender.getId() == null) {
			return;
		}
		// set gender of affected persons to "male"
		Session session = beginTransaction();
		GenderDAOImpl dao = new GenderDAOImpl(session);
		Gender male = dao.findMale();
		List<Person> persons = dao.findPersons(gender);
		commit();
		for (Person person : persons) {
			person.setGender(male);
			setUpdatePerson(person);
		}
		// delete gender
		session = beginTransaction();
		session.delete(gender);
		commit();

		firePropertyChange(BookController.GenderProps.DELETE.toString(), gender, null);
	}

	/**
	 * Sets the delete idea.
	 *
	 * @param idea the new delete idea
	 */
	public synchronized void setDeleteIdea(Idea idea) {
		if (idea.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		session.delete(idea);
		commit();
		firePropertyChange(BookController.IdeaProps.DELETE.toString(), idea, null);
	}

	/**
	 * Sets the delete internal.
	 *
	 * @param internal the new delete internal
	 */
	public synchronized void setDeleteInternal(Internal internal) {
		if (internal.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		session.delete(internal);
		commit();
		firePropertyChange(BookController.InternalProps.DELETE.toString(), internal, null);
	}

	/**
	 * Sets the delete item.
	 *
	 * @param item the new delete item
	 */
	public synchronized void setDeleteItem(Item item) {
		if (item.getId() == null) {
			return;
		}
		// delete item assignments
		Session session = beginTransaction();
		ItemLinkDAOImpl dao = new ItemLinkDAOImpl(session);
		List<ItemLink> links = dao.findByItem(item);
		commit();
		for (ItemLink link : links) {
			setDeleteItemLink(link);
		}
		// delete relationship
		session = beginTransaction();
		RelationshipDAOImpl daoR = new RelationshipDAOImpl(session);
		List<Relationship> relations = daoR.findByItemLink(item);
		commit();
		for (Relationship relation : relations) {
			relation.getItems().remove(item);
			session.update(relation);
		}
		// delete item
		session = beginTransaction();
		session.delete(item);
		commit();
		firePropertyChange(BookController.ItemProps.DELETE.toString(), item, null);
	}

	/**
	 * Sets the delete item link.
	 *
	 * @param itemLink the new delete item link
	 */
	public synchronized void setDeleteItemLink(ItemLink itemLink) {
		if (itemLink.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		session.delete(itemLink);
		commit();
		firePropertyChange(BookController.ItemLinkProps.DELETE.toString(), itemLink, null);
	}

	/**
	 * Sets the delete location.
	 *
	 * @param location the new delete location
	 */
	public synchronized void setDeleteLocation(Location location) {
		if (location.getId() == null) {
			return;
		}
		try {
			// delete scene links
			Session session = beginTransaction();
			SceneDAOImpl dao = new SceneDAOImpl(session);
			List<Scene> scenes = dao.findByLocationLink(location);
			for (Scene scene : scenes) {
				scene.getLocations().remove(location);
				session.update(scene);
			}
			commit();
			for (Scene scene : scenes) {
				setUpdateScene(scene);
			}
			// delete tag / item links
			EntityUtil.deleteTagAndItemLinks(this, location);
			// delete relationship
			session = beginTransaction();
			RelationshipDAOImpl daoR = new RelationshipDAOImpl(session);
			List<Relationship> relations = daoR.findByLocationLink(location);
			commit();
			for (Relationship relation : relations) {
				relation.getLocations().remove(location);
				session.update(relation);
			}
			// delete location
			session = beginTransaction();
			session.delete(location);
			commit();
		} catch (ConstraintViolationException e) {
			SbApp.error("BookModel.setDeleteLocation(" + location.getName() + ")", e);
		}
		firePropertyChange(BookController.LocationProps.DELETE.toString(), location, null);
	}

	/**
	 * Sets the delete memo.
	 *
	 * @param memo the new delete memo
	 */
	public synchronized void setDeleteMemo(Memo memo) {
		if (memo.getId() == null) {
			return;
		}
		// delete memo
		Session session = beginTransaction();
		session.delete(memo);
		commit();
		firePropertyChange(BookController.MemoProps.DELETE.toString(), memo, null);
	}

	/**
	 * Sets the delete multi categories.
	 *
	 * @param ids the new delete multi categories
	 */
	public synchronized void setDeleteMultiCategories(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			CategoryDAOImpl dao = new CategoryDAOImpl(session);
			Category old = dao.find(id);
			commit();
			session = beginTransaction();
			dao = new CategoryDAOImpl(session);
			dao.removeById(id);
			commit();
			firePropertyChange(BookController.CategoryProps.DELETE.toString(), old, null);
		}
	}

	/**
	 * Sets the delete multi chapters.
	 *
	 * @param ids the new delete multi chapters
	 */
	public synchronized void setDeleteMultiChapters(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			ChapterDAOImpl dao = new ChapterDAOImpl(session);
			Chapter old = dao.find(id);
			commit();
			session = beginTransaction();
			dao = new ChapterDAOImpl(session);
			dao.removeById(id);
			commit();
			firePropertyChange(BookController.ChapterProps.DELETE.toString(), old, null);
		}
	}

	/**
	 * Sets the delete multi genders.
	 *
	 * @param ids the new delete multi genders
	 */
	public synchronized void setDeleteMultiGenders(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			GenderDAOImpl dao = new GenderDAOImpl(session);
			Gender old = dao.find(id);
			commit();
			session = beginTransaction();
			dao = new GenderDAOImpl(session);
			dao.removeById(id);
			commit();
			firePropertyChange(BookController.GenderProps.DELETE.toString(), old, null);
		}
	}

	/**
	 * Sets the delete multi ideas.
	 *
	 * @param ids the new delete multi ideas
	 */
	public synchronized void setDeleteMultiIdeas(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			IdeaDAOImpl dao = new IdeaDAOImpl(session);
			Idea old = dao.find(id);
			commit();
			session = beginTransaction();
			dao = new IdeaDAOImpl(session);
			dao.removeById(id);
			commit();
			firePropertyChange(BookController.IdeaProps.DELETE.toString(), old, null);
		}
	}

	/**
	 * Sets the delete multi internals.
	 *
	 * @param ids the new delete multi internals
	 */
	public synchronized void setDeleteMultiInternals(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			InternalDAOImpl dao = new InternalDAOImpl(session);
			Internal old = dao.find(id);
			commit();
			setDeleteInternal(old);
		}
	}

	/**
	 * Sets the delete multi item links.
	 *
	 * @param ids the new delete multi item links
	 */
	public synchronized void setDeleteMultiItemLinks(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			ItemLinkDAOImpl dao = new ItemLinkDAOImpl(session);
			ItemLink old = dao.find(id);
			commit();
			setDeleteItemLink(old);
		}
	}

	/**
	 * Sets the delete multi items.
	 *
	 * @param ids the new delete multi items
	 */
	public synchronized void setDeleteMultiItems(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			ItemDAOImpl dao = new ItemDAOImpl(session);
			Item old = dao.find(id);
			commit();
			setDeleteItem(old);
		}
	}

	/**
	 * Sets the delete multi locations.
	 *
	 * @param ids the new delete multi locations
	 */
	public synchronized void setDeleteMultiLocations(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			LocationDAOImpl dao = new LocationDAOImpl(session);
			Location old = dao.find(id);
			commit();
			setDeleteLocation(old);
		}
	}

	/**
	 * Sets the delete multi memos.
	 *
	 * @param ids the new delete multi memos
	 */
	public synchronized void setDeleteMultiMemos(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			MemoDAOImpl dao = new MemoDAOImpl(session);
			Memo old = dao.find(id);
			commit();
			setDeleteMemo(old);
		}
	}

	/**
	 * Sets the delete multi parts.
	 *
	 * @param ids the new delete multi parts
	 */
	public synchronized void setDeleteMultiParts(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			PartDAOImpl dao = new PartDAOImpl(session);
			Part old = dao.find(id);
			commit();
			setDeletePart(old);
		}
	}

	/**
	 * Sets the delete multi persons.
	 *
	 * @param ids the new delete multi persons
	 */
	public synchronized void setDeleteMultiPersons(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			PersonDAOImpl dao = new PersonDAOImpl(session);
			Person old = dao.find(id);
			commit();
			setDeletePerson(old);
		}
	}

	/**
	 * Sets the delete multi relationships.
	 *
	 * @param ids the new delete multi relationships
	 */
	public synchronized void setDeleteMultiRelationships(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			RelationshipDAOImpl dao = new RelationshipDAOImpl(session);
			Relationship old = dao.find(id);
			commit();
			setDeleteRelationship(old);
		}
	}

	/**
	 * Sets the delete multi scenes.
	 *
	 * @param ids the new delete multi scenes
	 */
	public synchronized void setDeleteMultiScenes(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			SceneDAOImpl dao = new SceneDAOImpl(session);
			Scene old = dao.find(id);
			commit();
			setDeleteScene(old);
		}
	}

	/**
	 * Sets the delete multi strands.
	 *
	 * @param ids the new delete multi strands
	 */
	public synchronized void setDeleteMultiStrands(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			StrandDAOImpl dao = new StrandDAOImpl(session);
			Strand old = dao.find(id);
			commit();
			setDeleteStrand(old);
		}
	}

	/**
	 * Sets the delete multi tag links.
	 *
	 * @param ids the new delete multi tag links
	 */
	public synchronized void setDeleteMultiTagLinks(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			TagLinkDAOImpl dao = new TagLinkDAOImpl(session);
			TagLink old = dao.find(id);
			commit();
			setDeleteTagLink(old);
		}
	}

	/**
	 * Sets the delete multi tags.
	 *
	 * @param ids the new delete multi tags
	 */
	public synchronized void setDeleteMultiTags(ArrayList<Long> ids) {
		for (Long id : ids) {
			Session session = beginTransaction();
			TagDAOImpl dao = new TagDAOImpl(session);
			Tag old = dao.find(id);
			commit();
			setDeleteTag(old);
		}
	}

	/**
	 * Sets the delete part.
	 *
	 * @param part the new delete part
	 */
	public synchronized void setDeletePart(Part part) {
		if (part.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		// delete chapters
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Chapter> chapters = dao.findChapters(part);
		commit();
		for (Chapter chapter : chapters) {
			setDeleteChapter(chapter);
		}
		// delete part
		session = beginTransaction();
		session.delete(part);
		commit();
		firePropertyChange(BookController.PartProps.DELETE.toString(), part, null);
	}

	/**
	 * Sets the delete person.
	 *
	 * @param person the new delete person
	 */
	public synchronized void setDeletePerson(Person person) {
		if (person.getId() == null) {
			return;
		}
		try {
			// delete scene links
			Session session = beginTransaction();
			SceneDAOImpl dao = new SceneDAOImpl(session);
			List<Scene> scenes = dao.findByPersonLink(person);
			for (Scene scene : scenes) {
				scene.getPersons().remove(person);
				session.update(scene);
			}
			commit();
			for (Scene scene : scenes) {
				setUpdateScene(scene);
			}
			// delete tag / item links
			EntityUtil.deleteTagAndItemLinks(this, person);
			// delete relationship
			session = beginTransaction();
			RelationshipDAOImpl daoR = new RelationshipDAOImpl(session);
			List<Relationship> relations = daoR.findByPersonLink(person);
			commit();
			for (Relationship relation : relations) {
				relation.getPersons().remove(person);
				session.update(relation);
			}
			// delete person
			session = beginTransaction();
			session.delete(person);
			commit();
		} catch (ConstraintViolationException e) {
			SbApp.error("BookModel.setDeletePerson(" + person.getFullName() + ")", e);
		}
		firePropertyChange(BookController.PersonProps.DELETE.toString(), person, null);
	}

	/**
	 * Sets the delete relationship.
	 *
	 * @param r the new delete relationship
	 */
	public synchronized void setDeleteRelationship(Relationship r) {
		if (r.getId() == null) {
			return;
		}
		try {
			// delete scene links
			// delete Relationship
			Session session = beginTransaction();
			session.delete(r);
			commit();
		} catch (ConstraintViolationException e) {
			SbApp.error("BookModel.setDeleteRelationship(" + r.getPerson1() + "-" + r.getPerson2() + ")", e);
		}
		firePropertyChange(BookController.RelationshipProps.DELETE.toString(), r, null);
	}

	/**
	 * Sets the delete scene.
	 *
	 * @param scene the new delete scene
	 */
	public synchronized void setDeleteScene(Scene scene) {
		if (scene.getId() == null) {
			return;
		}
		// delete tag / item links
		EntityUtil.deleteTagAndItemLinks(this, scene);
		// remove relative scene of affected scenes
		Session session = beginTransaction();
		SceneDAOImpl dao = new SceneDAOImpl(session);
		List<Scene> scenes = dao.findScenesWithRelativeSceneId(scene);
		commit();
		for (Scene scene2 : scenes) {
			scene2.removeRelativeScene();
			setUpdateScene(scene2);
		}
		// delete scene
		session = beginTransaction();
		session.delete(scene);
		commit();
		firePropertyChange(BookController.SceneProps.DELETE.toString(), scene, null);
	}

	/**
	 * Sets the delete strand.
	 *
	 * @param strand the new delete strand
	 */
	public synchronized void setDeleteStrand(Strand strand) {
		if (strand.getId() == null) {
			return;
		}
		try {
			// delete scene links
			Session session = beginTransaction();
			SceneDAOImpl sceneDao = new SceneDAOImpl(session);
			List<Scene> scenes = sceneDao.findByStrandLink(strand);
			for (Scene scene : scenes) {
				scene.getStrands().remove(strand);
				session.update(scene);
			}
			commit();
			for (Scene scene : scenes) {
				setUpdateScene(scene);
			}
			// delete scenes
			session = beginTransaction();
			StrandDAOImpl strandDao = new StrandDAOImpl(session);
			scenes = strandDao.findScenes(strand);
			commit();
			for (Scene scene : scenes) {
				setDeleteScene(scene);
			}
			// delete strand
			session = beginTransaction();
			session.delete(strand);
			commit();
		} catch (ConstraintViolationException e) {
			SbApp.error("BookModel.setDeleteStrand(" + strand.getName() + ")", e);
		}
		firePropertyChange(BookController.StrandProps.DELETE.toString(), strand, null);
	}

	/**
	 * Sets the delete tag.
	 *
	 * @param tag the new delete tag
	 */
	public synchronized void setDeleteTag(Tag tag) {
		if (tag.getId() == null) {
			return;
		}
		// delete tag assignments
		Session session = beginTransaction();
		TagLinkDAOImpl dao = new TagLinkDAOImpl(session);
		List<TagLink> links = dao.findByTag(tag);
		commit();
		for (TagLink link : links) {
			setDeleteTagLink(link);
		}
		// delete tag
		session = beginTransaction();
		session.delete(tag);
		commit();
		firePropertyChange(BookController.TagProps.DELETE.toString(), tag, null);
	}

	/**
	 * Sets the delete tag link.
	 *
	 * @param tagLink the new delete tag link
	 */
	public synchronized void setDeleteTagLink(TagLink tagLink) {
		if (tagLink.getId() == null) {
			return;
		}
		Session session = beginTransaction();
		session.delete(tagLink);
		commit();
		firePropertyChange(BookController.TagLinkProps.DELETE.toString(), tagLink, null);
	}

	/**
	 * Sets the delete time event.
	 *
	 * @param entity the new delete time event
	 */
	public synchronized void setDeleteTimeEvent(TimeEvent entity) {
		if (entity.getId() == null) {
			return;
		}
		// delete chapter
		Session session = beginTransaction();
		session.delete(entity);
		commit();
		firePropertyChange(BookController.TimeEventProps.DELETE.toString(), entity, null);
	}

	/**
	 * Sets the edits the category.
	 *
	 * @param entity the new edits the category
	 */
	// category
	public void setEditCategory(Category entity) {
		// firePropertyChange(BookController.CategoryProps.EDIT.toString(),
		// null, entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the chapter.
	 *
	 * @param entity the new edits the chapter
	 */
	// chapter
	public void setEditChapter(Chapter entity) {
		// firePropertyChange(BookController.ChapterProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the gender.
	 *
	 * @param entity the new edits the gender
	 */
	// gender
	public void setEditGender(Gender entity) {
		// firePropertyChange(BookController.GenderProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the idea.
	 *
	 * @param entity the new edits the idea
	 */
	// idea
	public void setEditIdea(Idea entity) {
		// firePropertyChange(BookController.IdeaProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the internal.
	 *
	 * @param internal the new edits the internal
	 */
	// internals
	public void setEditInternal(Internal internal) {
		// firePropertyChange(BookController.InternalProps.EDIT.toString(),
		// null, entity);
		editEntity(internal);
	}

	/**
	 * Sets the edits the item.
	 *
	 * @param entity the new edits the item
	 */
	// items
	public void setEditItem(Item entity) {
		// firePropertyChange(BookController.ItemProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the item link.
	 *
	 * @param entity the new edits the item link
	 */
	// item links
	public void setEditItemLink(ItemLink entity) {
		// firePropertyChange(BookController.ItemLinkProps.EDIT.toString(),
		// null, entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the location.
	 *
	 * @param entity the new edits the location
	 */
	// location
	public void setEditLocation(Location entity) {
		// firePropertyChange(BookController.LocationProps.EDIT.toString(),
		// null, entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the memo.
	 *
	 * @param entity the new edits the memo
	 */
	// memos
	public void setEditMemo(Memo entity) {
		// firePropertyChange(BookController.TagProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the part.
	 *
	 * @param entity the new edits the part
	 */
	// part
	public void setEditPart(Part entity) {
		// firePropertyChange(BookController.PartProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the person.
	 *
	 * @param entity the new edits the person
	 */
	// person
	public void setEditPerson(Person entity) {
		// firePropertyChange(BookController.PersonProps.EDIT.toString(),null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the relationship.
	 *
	 * @param entity the new edits the relationship
	 */
	// relationship
	public void setEditRelationship(Relationship entity) {
		// firePropertyChange(BookController.RelationshipProps.EDIT.toString(),null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the scene.
	 *
	 * @param entity the new edits the scene
	 */
	// scenes
	public void setEditScene(Scene entity) {
		SbApp.trace("BookModel.setEditScene(" + entity.toString() + ")");
		// firePropertyChange(BookController.SceneProps.EDIT.toString(), null,
		// editScene);
		editEntity(entity);
	}

	/**
	 * Sets the edits the strand.
	 *
	 * @param entity the new edits the strand
	 */
	// strand
	public void setEditStrand(Strand entity) {
		// firePropertyChange(BookController.StrandProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the tag.
	 *
	 * @param entity the new edits the tag
	 */
	// tags
	public void setEditTag(Tag entity) {
		// firePropertyChange(BookController.TagProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the tag link.
	 *
	 * @param entity the new edits the tag link
	 */
	// tag links
	public void setEditTagLink(TagLink entity) {
		// firePropertyChange(BookController.TagLinkProps.EDIT.toString(), null,
		// entity);
		editEntity(entity);
	}

	/**
	 * Sets the edits the time event.
	 *
	 * @param entity the new edits the time event
	 */
	// chapter
	public void setEditTimeEvent(TimeEvent entity) {
		editEntity(entity);
	}

	/**
	 * Sets the export.
	 *
	 * @param view the new export
	 */
	public void setExport(SbView view) {
		firePropertyChange(BookController.CommonProps.EXPORT.toString(), null, view);
	}

	/**
	 * Sets the filter scenes.
	 *
	 * @param state the new filter scenes
	 */
	public void setFilterScenes(SceneState state) {
		firePropertyChange(BookController.SceneProps.FILTER.toString(), null, state);
	}

	/**
	 * Sets the filter strand.
	 *
	 * @param strand the new filter strand
	 */
	public void setFilterStrand(String strand) {
		firePropertyChange(BookController.SceneProps.FILTERSTRAND.toString(), null, strand);
	}

	/**
	 * Sets the manage columns.
	 *
	 * @param val the new manage columns
	 */
	public void setManageColumns(Integer val) {
		firePropertyChange(BookController.ManageViewProps.COLUMNS.toString(), null, val);
	}

	/**
	 * Sets the manage show entity.
	 *
	 * @param chapter the new manage show entity
	 */
	public void setManageShowEntity(Chapter chapter) {
		firePropertyChange(BookController.ManageViewProps.SHOW_ENTITY.toString(), null, chapter);
	}

	/**
	 * Sets the manage show entity.
	 *
	 * @param scene the new manage show entity
	 */
	public void setManageShowEntity(Scene scene) {
		firePropertyChange(BookController.ManageViewProps.SHOW_ENTITY.toString(), null, scene);
	}

	/**
	 * Sets the manage zoom.
	 *
	 * @param val the new manage zoom
	 */
	// manage view
	public void setManageZoom(Integer val) {
		firePropertyChange(BookController.ManageViewProps.ZOOM.toString(), null, val);
	}

	/**
	 * Sets the memoria balloon.
	 *
	 * @param val the new memoria balloon
	 */
	// memoria view
	public void setMemoriaBalloon(Boolean val) {
		firePropertyChange(BookController.MemoriaViewProps.BALLOON.toString(), null, val);
	}

	/**
	 * Sets the new category.
	 *
	 * @param category the new new category
	 */
	public synchronized void setNewCategory(Category category) {
		Session session = beginTransaction();
		session.save(category);
		commit();
		firePropertyChange(BookController.CategoryProps.NEW.toString(), null, category);
	}

	/**
	 * Sets the new chapter.
	 *
	 * @param chapter the new new chapter
	 */
	public synchronized void setNewChapter(Chapter chapter) {
		Session session = beginTransaction();
		session.save(chapter);
		commit();
		firePropertyChange(BookController.ChapterProps.NEW.toString(), null, chapter);
	}

	/**
	 * Sets the new gender.
	 *
	 * @param gender the new new gender
	 */
	public synchronized void setNewGender(Gender gender) {
		Session session = beginTransaction();
		session.save(gender);
		commit();

		firePropertyChange(BookController.GenderProps.NEW.toString(), null, gender);
	}

	/**
	 * Sets the new idea.
	 *
	 * @param idea the new new idea
	 */
	public synchronized void setNewIdea(Idea idea) {
		Session session = beginTransaction();
		session.save(idea);
		commit();
		firePropertyChange(BookController.IdeaProps.NEW.toString(), null, idea);
	}

	/**
	 * Sets the new internal.
	 *
	 * @param internal the new new internal
	 */
	public synchronized void setNewInternal(Internal internal) {
		Session session = beginTransaction();
		session.save(internal);
		commit();
		firePropertyChange(BookController.InternalProps.NEW.toString(), null, internal);
	}

	/**
	 * Sets the new item.
	 *
	 * @param item the new new item
	 */
	public synchronized void setNewItem(Item item) {
		Session session = beginTransaction();
		session.save(item);
		commit();
		firePropertyChange(BookController.ItemProps.NEW.toString(), null, item);
	}

	/**
	 * Sets the new item link.
	 *
	 * @param itemLink the new new item link
	 */
	public synchronized void setNewItemLink(ItemLink itemLink) {
		Session session = beginTransaction();
		session.save(itemLink);
		commit();
		firePropertyChange(BookController.ItemLinkProps.NEW.toString(), null, itemLink);
	}

	/**
	 * Sets the new location.
	 *
	 * @param location the new new location
	 */
	public synchronized void setNewLocation(Location location) {
		Session session = beginTransaction();
		session.save(location);
		commit();
		firePropertyChange(BookController.LocationProps.NEW.toString(), null, location);
	}

	/**
	 * Sets the new memo.
	 *
	 * @param memo the new new memo
	 */
	public synchronized void setNewMemo(Memo memo) {
		Session session = beginTransaction();
		session.save(memo);
		commit();
		firePropertyChange(BookController.MemoProps.NEW.toString(), null, memo);
	}

	/**
	 * Sets the new part.
	 *
	 * @param part the new new part
	 */
	public synchronized void setNewPart(Part part) {
		Session session = beginTransaction();
		session.save(part);
		commit();
		firePropertyChange(BookController.PartProps.NEW.toString(), null, part);
	}

	/**
	 * Sets the new person.
	 *
	 * @param person the new new person
	 */
	public synchronized void setNewPerson(Person person) {
		try {
			Session session = beginTransaction();
			session.save(person);
			commit();
			firePropertyChange(BookController.PersonProps.NEW.toString(), null, person);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Sets the new relationship.
	 *
	 * @param r the new new relationship
	 */
	public synchronized void setNewRelationship(Relationship r) {
		Session session = beginTransaction();
		session.save(r);
		commit();
		firePropertyChange(BookController.RelationshipProps.NEW.toString(), null, r);
	}

	/**
	 * Sets the new scene.
	 *
	 * @param scene the new new scene
	 */
	public synchronized void setNewScene(Scene scene) {
		Session session = beginTransaction();
		session.save(scene);
		commit();
		firePropertyChange(BookController.SceneProps.NEW.toString(), null, scene);
	}

	/**
	 * Sets the new strand.
	 *
	 * @param strand the new new strand
	 */
	public synchronized void setNewStrand(Strand strand) {
		Session session = beginTransaction();
		session.save(strand);
		commit();
		firePropertyChange(BookController.StrandProps.NEW.toString(), null, strand);
	}

	/**
	 * Sets the new tag.
	 *
	 * @param tag the new new tag
	 */
	public synchronized void setNewTag(Tag tag) {
		Session session = beginTransaction();
		session.save(tag);
		commit();
		firePropertyChange(BookController.TagProps.NEW.toString(), null, tag);
	}

	/**
	 * Sets the new tag link.
	 *
	 * @param tagLink the new new tag link
	 */
	public synchronized void setNewTagLink(TagLink tagLink) {
		Session session = beginTransaction();
		session.save(tagLink);
		commit();
		firePropertyChange(BookController.TagLinkProps.NEW.toString(), null, tagLink);
	}

	/**
	 * Sets the new time event.
	 *
	 * @param entity the new new time event
	 */
	public synchronized void setNewTimeEvent(TimeEvent entity) {
		Session session = beginTransaction();
		session.save(entity);
		commit();
		firePropertyChange(BookController.TimeEventProps.NEW.toString(), null, entity);
	}

	/**
	 * Sets the order down category.
	 *
	 * @param category the new order down category
	 */
	public synchronized void setOrderDownCategory(Category category) {
		firePropertyChange(BookController.CategoryProps.ORDER_DOWN.toString(), null, category);
	}

	/**
	 * Sets the order down strand.
	 *
	 * @param strand the new order down strand
	 */
	public synchronized void setOrderDownStrand(Strand strand) {
		firePropertyChange(BookController.StrandProps.ORDER_DOWN.toString(), null, strand);
	}

	/**
	 * Sets the order up category.
	 *
	 * @param category the new order up category
	 */
	public synchronized void setOrderUpCategory(Category category) {
		firePropertyChange(BookController.CategoryProps.ORDER_UP.toString(), null, category);
	}

	/**
	 * Sets the order up strand.
	 *
	 * @param strand the new order up strand
	 */
	public synchronized void setOrderUpStrand(Strand strand) {
		firePropertyChange(BookController.StrandProps.ORDER_UP.toString(), null, strand);
	}

	/**
	 * Sets the prints the.
	 *
	 * @param view the new prints the
	 */
	public void setPrint(SbView view) {
		firePropertyChange(BookController.CommonProps.PRINT.toString(), null, view);
	}

	/**
	 * Sets the reading font size.
	 *
	 * @param val the new reading font size
	 */
	public void setReadingFontSize(Integer val) {
		firePropertyChange(BookController.ReadingViewProps.FONT_SIZE.toString(), null, val);
	}

	/**
	 * Sets the reading zoom.
	 *
	 * @param val the new reading zoom
	 */
	// reading view
	public void setReadingZoom(Integer val) {
		firePropertyChange(BookController.ReadingViewProps.ZOOM.toString(), null, val);
	}

	/**
	 * Sets the refresh.
	 *
	 * @param view the new refresh
	 */
	// common
	public void setRefresh(SbView view) {
		SbApp.trace("BookModel.setRefresh(" + view.getName() + ")");
		firePropertyChange(BookController.CommonProps.REFRESH.toString(), null, view);
		try {
			if (view.getComponentCount() == 0) {
				return;
			}
			Component comp = view.getComponent();
			if (comp instanceof ChronoPanel || comp instanceof BookPanel || comp instanceof ManagePanel
					|| comp instanceof ReadingPanel) {
				// these views don't need a "fire again"
				return;
			}
			fireAgain(view);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		}
	}

	/**
	 * Sets the show info.
	 *
	 * @param entity the new show info
	 */
	public void setShowInfo(AbstractEntity entity) {
		firePropertyChange(BookController.CommonProps.SHOW_INFO.toString(), null, entity);
	}

	/**
	 * Sets the show info.
	 *
	 * @param category the new show info
	 */
	public void setShowInfo(Category category) {
		setShowInfo((AbstractEntity) category);
	}

	/**
	 * Sets the show info.
	 *
	 * @param chapter the new show info
	 */
	public void setShowInfo(Chapter chapter) {
		setShowInfo((AbstractEntity) chapter);
	}

	/**
	 * Sets the show info.
	 *
	 * @param dbFile the new show info
	 */
	public void setShowInfo(DbFile dbFile) {
		firePropertyChange(BookController.CommonProps.SHOW_INFO.toString(), null, dbFile);
	}

	/**
	 * Sets the show info.
	 *
	 * @param gender the new show info
	 */
	public void setShowInfo(Gender gender) {
		setShowInfo((AbstractEntity) gender);
	}

	/**
	 * Sets the show info.
	 *
	 * @param idea the new show info
	 */
	public void setShowInfo(Idea idea) {
		setShowInfo((AbstractEntity) idea);
	}

	/**
	 * Sets the show info.
	 *
	 * @param item the new show info
	 */
	public void setShowInfo(Item item) {
		setShowInfo((AbstractEntity) item);
	}

	/**
	 * Sets the show info.
	 *
	 * @param itemLink the new show info
	 */
	public void setShowInfo(ItemLink itemLink) {
		setShowInfo((AbstractEntity) itemLink);
	}

	/**
	 * Sets the show info.
	 *
	 * @param location the new show info
	 */
	public void setShowInfo(Location location) {
		setShowInfo((AbstractEntity) location);
	}

	/**
	 * Sets the show info.
	 *
	 * @param part the new show info
	 */
	public void setShowInfo(Part part) {
		setShowInfo((AbstractEntity) part);
	}

	/**
	 * Sets the show info.
	 *
	 * @param person the new show info
	 */
	public void setShowInfo(Person person) {
		setShowInfo((AbstractEntity) person);
	}

	/**
	 * Sets the show info.
	 *
	 * @param entity the new show info
	 */
	public void setShowInfo(Relationship entity) {
		setShowInfo((AbstractEntity) entity);
	}

	/**
	 * Sets the show info.
	 *
	 * @param scene the new show info
	 */
	public void setShowInfo(Scene scene) {
		setShowInfo((AbstractEntity) scene);
	}

	/**
	 * Sets the show info.
	 *
	 * @param strand the new show info
	 */
	public void setShowInfo(Strand strand) {
		setShowInfo((AbstractEntity) strand);
	}

	/**
	 * Sets the show info.
	 *
	 * @param tag the new show info
	 */
	public void setShowInfo(Tag tag) {
		setShowInfo((AbstractEntity) tag);
	}

	/**
	 * Sets the show info.
	 *
	 * @param tagLink the new show info
	 */
	public void setShowInfo(TagLink tagLink) {
		setShowInfo((AbstractEntity) tagLink);
	}

	/**
	 * Sets the show info.
	 *
	 * @param event the new show info
	 */
	public void setShowInfo(TimeEvent event) {
		setShowInfo((AbstractEntity) event);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param entity the new show in memoria
	 */
	public void setShowInMemoria(AbstractEntity entity) {
		firePropertyChange(BookController.CommonProps.SHOW_IN_MEMORIA.toString(), null, entity);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param item the new show in memoria
	 */
	public void setShowInMemoria(Item item) {
		setShowInMemoria((AbstractEntity) item);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param location the new show in memoria
	 */
	public void setShowInMemoria(Location location) {
		setShowInMemoria((AbstractEntity) location);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param person the new show in memoria
	 */
	public void setShowInMemoria(Person person) {
		setShowInMemoria((AbstractEntity) person);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param p the new show in memoria
	 */
	public void setShowInMemoria(Relationship p) {
		setShowInMemoria((AbstractEntity) p);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param scene the new show in memoria
	 */
	public void setShowInMemoria(Scene scene) {
		setShowInMemoria((AbstractEntity) scene);
	}

	/**
	 * Sets the show in memoria.
	 *
	 * @param tag the new show in memoria
	 */
	public void setShowInMemoria(Tag tag) {
		setShowInMemoria((AbstractEntity) tag);
	}

	/**
	 * Sets the show memo.
	 *
	 * @param entity the new show memo
	 */
	public void setShowMemo(AbstractEntity entity) {
		firePropertyChange(BookController.CommonProps.SHOW_MEMO.toString(), null, entity);
	}

	/**
	 * Sets the show options.
	 *
	 * @param view the new show options
	 */
	public void setShowOptions(SbView view) {
		firePropertyChange(BookController.CommonProps.SHOW_OPTIONS.toString(), null, view);
	}

	/**
	 * Sets the unload editor.
	 */
	public void setUnloadEditor() {
		firePropertyChange(BookController.CommonProps.UNLOAD_EDITOR.toString(), null, null);
	}

	/**
	 * Sets the update category.
	 *
	 * @param category the new update category
	 */
	public synchronized void setUpdateCategory(Category category) {
		Session session = beginTransaction();
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		Category old = dao.find(category.getId());
		commit();
		session = beginTransaction();
		session.update(category);
		commit();
		firePropertyChange(BookController.CategoryProps.UPDATE.toString(), old, category);
	}

	/**
	 * Sets the update chapter.
	 *
	 * @param chapter the new update chapter
	 */
	public synchronized void setUpdateChapter(Chapter chapter) {
		Session session = beginTransaction();
		ChapterDAOImpl dao = new ChapterDAOImpl(session);
		Chapter old = dao.find(chapter.getId());
		commit();
		session = beginTransaction();
		session.update(chapter);
		commit();
		firePropertyChange(BookController.ChapterProps.UPDATE.toString(), old, chapter);
	}

	/**
	 * Sets the update gender.
	 *
	 * @param gender the new update gender
	 */
	public synchronized void setUpdateGender(Gender gender) {
		Session session = beginTransaction();
		GenderDAOImpl dao = new GenderDAOImpl(session);
		Gender old = dao.find(gender.getId());
		commit();

		session = beginTransaction();
		session.update(gender);
		commit();

		firePropertyChange(BookController.GenderProps.UPDATE.toString(), old, gender);
	}

	/**
	 * Sets the update idea.
	 *
	 * @param idea the new update idea
	 */
	public synchronized void setUpdateIdea(Idea idea) {
		Session session = beginTransaction();
		IdeaDAOImpl dao = new IdeaDAOImpl(session);
		Idea old = dao.find(idea.getId());
		commit();
		session = beginTransaction();
		session.update(idea);
		commit();
		firePropertyChange(BookController.IdeaProps.UPDATE.toString(), old, idea);
	}

	/**
	 * Sets the update internal.
	 *
	 * @param internal the new update internal
	 */
	public synchronized void setUpdateInternal(Internal internal) {
		Session session = beginTransaction();
		InternalDAOImpl dao = new InternalDAOImpl(session);
		Internal old = dao.find(internal.getId());
		commit();
		session = beginTransaction();
		session.update(internal);
		commit();
		firePropertyChange(BookController.InternalProps.UPDATE.toString(), old, internal);
	}

	/**
	 * Sets the update item.
	 *
	 * @param item the new update item
	 */
	public synchronized void setUpdateItem(Item item) {
		Session session = beginTransaction();
		ItemDAOImpl dao = new ItemDAOImpl(session);
		Item old = dao.find(item.getId());
		commit();
		session = beginTransaction();
		session.update(item);
		commit();
		firePropertyChange(BookController.ItemProps.UPDATE.toString(), old, item);
	}

	/**
	 * Sets the update item link.
	 *
	 * @param itemLink the new update item link
	 */
	public synchronized void setUpdateItemLink(ItemLink itemLink) {
		Session session = beginTransaction();
		ItemLinkDAOImpl dao = new ItemLinkDAOImpl(session);
		ItemLink old = dao.find(itemLink.getId());
		commit();
		session = beginTransaction();
		session.update(itemLink);
		commit();
		firePropertyChange(BookController.ItemLinkProps.UPDATE.toString(), old, itemLink);
	}

	/**
	 * Sets the update location.
	 *
	 * @param location the new update location
	 */
	public synchronized void setUpdateLocation(Location location) {
		Session session = beginTransaction();
		LocationDAOImpl dao = new LocationDAOImpl(session);
		Location old = dao.find(location.getId());
		commit();
		session = beginTransaction();
		session.update(location);
		commit();
		firePropertyChange(BookController.LocationProps.UPDATE.toString(), old, location);
	}

	/**
	 * Sets the update memo.
	 *
	 * @param memo the new update memo
	 */
	public synchronized void setUpdateMemo(Memo memo) {
		Session session = beginTransaction();
		MemoDAOImpl dao = new MemoDAOImpl(session);
		Memo old = dao.find(memo.getId());
		commit();
		session = beginTransaction();
		session.update(memo);
		commit();
		firePropertyChange(BookController.MemoProps.UPDATE.toString(), old, memo);
	}

	/**
	 * Sets the update part.
	 *
	 * @param part the new update part
	 */
	public synchronized void setUpdatePart(Part part) {
		Session session = beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		Part old = dao.find(part.getId());
		commit();
		session = beginTransaction();
		session.update(part);
		commit();
		firePropertyChange(BookController.PartProps.UPDATE.toString(), old, part);
	}

	/**
	 * Sets the update person.
	 *
	 * @param person the new update person
	 */
	public synchronized void setUpdatePerson(Person person) {
		Session session = beginTransaction();
		PersonDAOImpl dao = new PersonDAOImpl(session);
		Person old = dao.find(person.getId());
		commit();
		session = beginTransaction();
		session.update(person);
		commit();
		firePropertyChange(BookController.PersonProps.UPDATE.toString(), old, person);
	}

	/**
	 * Sets the update relationship.
	 *
	 * @param relationship the new update relationship
	 */
	public synchronized void setUpdateRelationship(Relationship relationship) {
		Session session = beginTransaction();
		RelationshipDAOImpl dao = new RelationshipDAOImpl(session);
		Relationship old = dao.find(relationship.getId());
		commit();
		session = beginTransaction();
		session.update(relationship);
		commit();
		firePropertyChange(BookController.RelationshipProps.UPDATE.toString(), old, relationship);
	}

	/**
	 * Sets the update scene.
	 *
	 * @param scene the new update scene
	 */
	public synchronized void setUpdateScene(Scene scene) {
		// needed, see ChronoPanel.modelPropertyChange()
		Session session = beginTransaction();
		Scene old = (Scene) session.get(Scene.class, scene.getId());
		commit();
		try {
			session = beginTransaction();
			session.update(scene);
			commit();
		} catch (ConstraintViolationException e) {
			SbApp.error("BookModel.setUpdateScene(" + scene.getTitle() + ")", e);
		}
		firePropertyChange(BookController.SceneProps.UPDATE.toString(), old, scene);
	}

	/**
	 * Sets the update strand.
	 *
	 * @param strand the new update strand
	 */
	public synchronized void setUpdateStrand(Strand strand) {
		Session session = beginTransaction();
		StrandDAOImpl dao = new StrandDAOImpl(session);
		Strand old = dao.find(strand.getId());
		commit();
		session = beginTransaction();
		session.update(strand);
		commit();
		firePropertyChange(BookController.StrandProps.UPDATE.toString(), old, strand);
	}

	/**
	 * Sets the update tag.
	 *
	 * @param tag the new update tag
	 */
	public synchronized void setUpdateTag(Tag tag) {
		Session session = beginTransaction();
		TagDAOImpl dao = new TagDAOImpl(session);
		Tag old = dao.find(tag.getId());
		commit();
		session = beginTransaction();
		session.update(tag);
		commit();
		firePropertyChange(BookController.TagProps.UPDATE.toString(), old, tag);
	}

	/**
	 * Sets the update tag link.
	 *
	 * @param tagLink the new update tag link
	 */
	public synchronized void setUpdateTagLink(TagLink tagLink) {
		Session session = beginTransaction();
		TagLinkDAOImpl dao = new TagLinkDAOImpl(session);
		TagLink old = dao.find(tagLink.getId());
		commit();
		session = beginTransaction();
		session.update(tagLink);
		commit();
		firePropertyChange(BookController.TagLinkProps.UPDATE.toString(), old, tagLink);
	}

	/**
	 * Sets the update time event.
	 *
	 * @param entity the new update time event
	 */
	public synchronized void setUpdateTimeEvent(TimeEvent entity) {
		Session session = beginTransaction();
		TimeEventDAOImpl dao = new TimeEventDAOImpl(session);
		TimeEvent old = dao.find(entity.getId());
		commit();
		session = beginTransaction();
		session.update(entity);
		commit();
		firePropertyChange(BookController.TimeEventProps.UPDATE.toString(), old, entity);
	}
}
