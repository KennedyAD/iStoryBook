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
package storybook.controller;

import java.util.ArrayList;

import storybook.SbApp;
import storybook.model.BlankModel;
import storybook.model.BookModel;
import storybook.model.DbFile;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Attribute;
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
import storybook.model.state.SceneStateModel;
import storybook.ui.MainFrame;
import storybook.ui.SbView;

/**
 * @author martin
 *
 */
public class BookController extends AbstractController {

	public enum AttributeProps {

		INIT("InitAttributes"), EDIT("EditAttribute"), DELETE("DeleteAttribute"), DELETE_MULTI(
				"DeleteMultiAttributes"), NEW("NewAttribute"), UPDATE("UpdateAttribute");
		final private String text;

		private AttributeProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum BookViewProps {

		ZOOM("BookZoom"), HEIGHT_FACTOR("BookHeightFactor"), SHOW_ENTITY("BookShowEntity");
		final private String text;

		private BookViewProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum CategoryProps {

		INIT("InitCategories"), EDIT("EditCategory"), DELETE("DeleteCategory"), DELETE_MULTI(
				"DeleteMultiCategories"), NEW("NewCategory"), ORDER_UP("OrderUpCategory"), ORDER_DOWN(
						"OrderDownCategory"), UPDATE("UpdateCategory");
		final private String text;

		private CategoryProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum ChapterProps {

		INIT("InitChapters"), EDIT("EditChapter"), DELETE("DeleteChapter"), DELETE_MULTI("DeleteMultiChapters"), NEW(
				"NewChapter"), UPDATE("UpdateChapter");
		final private String text;

		private ChapterProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum ChronoViewProps {

		ZOOM("ChronoZoom"), LAYOUT_DIRECTION("ChronoLayoutDirection"), SHOW_DATE_DIFFERENCE(
				"ChronoShowDateDifference"), SHOW_ENTITY("ChronoShowEntity");
		final private String text;

		private ChronoViewProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum CommonProps {

		REFRESH("Refresh"), SHOW_OPTIONS("ShowOptions"), SHOW_INFO("ShowInfo"), SHOW_MEMO("ShowMemo"), PRINT(
				"Print"), EXPORT("Export"), SHOW_TASKLIST("ShowTaskList"), SHOW_IN_MEMORIA(
						"ShowInMemoria"), UNLOAD_EDITOR("UnloadEditor");
		final private String text;

		private CommonProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum GenderProps {

		INIT("InitGenders"), EDIT("EditGender"), DELETE("DeleteGender"), DELETE_MULTI("DeleteMultiGenders"), NEW(
				"NewGender"), UPDATE("UpdateGender");
		final private String text;

		private GenderProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum IdeaProps {

		INIT("InitIdeas"), EDIT("EditIdea"), DELETE("DeleteIdea"), DELETE_MULTI("DeleteMultiIdeas"), NEW(
				"NewIdea"), UPDATE("UpdateIdea");
		final private String text;

		private IdeaProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum InternalProps {

		INIT("InitInternal"), EDIT("EditInternal"), DELETE("DeleteInternal"), DELETE_MULTI("DeleteMultiInternals"), NEW(
				"NewInternal"), UPDATE("UpdateInternal");
		final private String text;

		private InternalProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum ItemLinkProps {

		INIT("InitItemLink"), EDIT("EditItemLink"), DELETE("DeleteItemLink"), DELETE_MULTI("DeleteMultiItemLinks"), NEW(
				"NewItemLink"), UPDATE("UpdateItemLink");
		final private String text;

		private ItemLinkProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum ItemProps {

		INIT("InitItem"), EDIT("EditItem"), DELETE("DeleteItem"), DELETE_MULTI("DeleteMultiItems"), NEW(
				"NewItem"), UPDATE("UpdateItem");
		final private String text;

		private ItemProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum LocationProps {

		INIT("InitLocations"), EDIT("EditLocation"), DELETE("DeleteLocation"), DELETE_MULTI(
				"DeleteMultiLocations"), NEW("NewLocation"), UPDATE("UpdateLocation");
		final private String text;

		private LocationProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum ManageViewProps {

		ZOOM("ManageZoom"), COLUMNS("ManageColumns"), SHOW_ENTITY("ManageShowEntity");
		final private String text;

		private ManageViewProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum MemoProps {

		INIT("InitMemos"), EDIT("EditMemo"), DELETE("DeleteMemo"), DELETE_MULTI("DeleteMultiMemos"), NEW(
				"NewMemo"), UPDATE("UpdateMemo");
		final private String text;

		private MemoProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum MemoriaViewProps {

		BALLOON("MemoriaBalloon");
		final private String text;

		private MemoriaViewProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum PartProps {

		INIT("InitParts"), EDIT("EditPart"), DELETE("DeletePart"), DELETE_MULTI("DeleteMultiParts"), NEW(
				"NewPart"), UPDATE("UpdatePart"), CHANGE("ChangePart");
		final private String text;

		private PartProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum PersonProps {

		INIT("InitPersons"), EDIT("EditPerson"), DELETE("DeletePerson"), DELETE_MULTI("DeleteMultiPersons"), NEW(
				"NewPerson"), UPDATE("UpdatePerson");
		final private String text;

		private PersonProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum ReadingViewProps {

		ZOOM("ReadingZoom"), FONT_SIZE("ReadingFontSize");
		final private String text;

		private ReadingViewProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum RelationshipProps {

		INIT("InitRelationship"), EDIT("EditRelationship"), DELETE("DeleteRelationship"), DELETE_MULTI(
				"DeleteMultiRelationships"), NEW("NewRelationship"), UPDATE("UpdateRelationship");
		final private String text;

		private RelationshipProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum SceneProps {

		INIT("InitScene"), EDIT("EditScene"), DELETE("DeleteScene"), DELETE_MULTI("DeleteMultiScenes"), NEW(
				"NewScene"), UPDATE("UpdateScene"), FILTER("FilterScenes"), FILTERSTRAND("FilterStrand"), FILTERPERSON(
						"FilterPerson"), FILTERITEM("FilterItem"), FILTERLOCATION("FilterLocation");
		final private String text;

		private SceneProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum StrandProps {

		INIT("InitStrands"), EDIT("EditStrand"), DELETE("DeleteStrand"), DELETE_MULTI("DeleteMultiStrands"), NEW(
				"NewStrand"), ORDER_UP("OrderUpStrand"), ORDER_DOWN("OrderDownStrand"), UPDATE("UpdateStrand");
		final private String text;

		private StrandProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum TagLinkProps {

		INIT("InitTagLink"), EDIT("EditTagLink"), DELETE("DeleteTagLink"), DELETE_MULTI("DeleteMultiTagLinks"), NEW(
				"NewTagLink"), UPDATE("UpdateTagLink");
		final private String text;

		private TagLinkProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum TagProps {

		INIT("InitTags"), EDIT("EditTag"), DELETE("DeleteTag"), DELETE_MULTI("DeleteMultiTags"), NEW("NewTag"), UPDATE(
				"UpdateTag");
		final private String text;

		private TagProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	public enum TimeEventProps {

		INIT("InitTimeEvent"), EDIT("EditTimeEvent"), DELETE("DeleteTimeEvent"), NEW("NewTimeEvent"), UPDATE(
				"UpdateTimeEvent");
		final private String text;

		private TimeEventProps(String text) {
			this.text = text;
		}

		public boolean check(String prop) {
			return text.equals(prop);
		}

		@Override
		public String toString() {
			return text;
		}
	};

	MainFrame mainFrame;;

	public BookController(MainFrame m) {
		super();
		mainFrame = m;
	};

	public BookController(MainFrame m, BlankModel model) {
		super();
		mainFrame = m;
		attachModel(model);
	};

	public BookController(MainFrame m, BookModel model) {
		super();
		mainFrame = m;
		attachModel(model);
	};

	public void bookSetHeightFactor(Integer val) {
		setModelProperty(BookViewProps.HEIGHT_FACTOR.toString(), val);
	};

	// book view
	public void bookSetZoom(Integer val) {
		setModelProperty(BookViewProps.ZOOM.toString(), val);
	};

	public void bookShowEntity(AbstractEntity entity) {
		setModelProperty(BookViewProps.SHOW_ENTITY.toString(), entity);
	};

	public void changePart(Part part) {
		setModelProperty(PartProps.CHANGE.toString(), part);
	};

	public void chronoSetLayoutDirection(Boolean val) {
		setModelProperty(ChronoViewProps.LAYOUT_DIRECTION.toString(), val);
	}

	public void chronoSetShowDateDifference(Boolean val) {
		setModelProperty(ChronoViewProps.SHOW_DATE_DIFFERENCE.toString(), val);
	}

	// chrono view
	public void chronoSetZoom(Integer val) {
		setModelProperty(ChronoViewProps.ZOOM.toString(), val);
	}

	public void chronoShowEntity(AbstractEntity entity) {
		setModelProperty(ChronoViewProps.SHOW_ENTITY.toString(), entity);
	}

	public void deleteAttribute(Attribute entity) {
		setModelProperty(GenderProps.DELETE.toString(), entity);
	}

	public void deleteCategory(Category categoryToDeleted) {
		setModelProperty(CategoryProps.DELETE.toString(), categoryToDeleted);
	}

	public void deleteChapter(Chapter chapterToDeleted) {
		setModelProperty(ChapterProps.DELETE.toString(), chapterToDeleted);
	}

	public void deleteEntity(AbstractEntity entity) {
		SbApp.trace("BookController.deleteEntity(" + entity.getClass().getName() + ")");
		try {
			if (entity instanceof Chapter) {
				deleteChapter((Chapter) entity);
			} else if (entity instanceof Part) {
				deletePart((Part) entity);
			} else if (entity instanceof Location) {
				deleteLocation((Location) entity);
			} else if (entity instanceof Person) {
				deletePerson((Person) entity);
			} else if (entity instanceof Relationship) {
				deleteRelationship((Relationship) entity);
			} else if (entity instanceof Gender) {
				deleteGender((Gender) entity);
			} else if (entity instanceof Category) {
				deleteCategory((Category) entity);
			} else if (entity instanceof Strand) {
				deleteStrand((Strand) entity);
			} else if (entity instanceof Idea) {
				deleteIdea((Idea) entity);
			} else if (entity instanceof Memo) {
				deleteMemo((Memo) entity);
			} else if (entity instanceof Tag) {
				deleteTag((Tag) entity);
			} else if (entity instanceof TagLink) {
				deleteTagLink((TagLink) entity);
			} else if (entity instanceof Item) {
				deleteItem((Item) entity);
			} else if (entity instanceof ItemLink) {
				deleteItemLink((ItemLink) entity);
			} else if (entity instanceof Scene) {
				deleteScene((Scene) entity);
			} else if (entity instanceof Internal) {
				deleteInternal((Internal) entity);
			} else if (entity instanceof TimeEvent) {
				deleteTimeEvent((TimeEvent) entity);
			} else
				throw new Exception("Entity type not found.");
		} catch (Exception e) {
			SbApp.error("BookController.deleteEntity(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	public void deleteGender(Gender genderToDeleted) {
		setModelProperty(GenderProps.DELETE.toString(), genderToDeleted);
	}

	public void deleteIdea(Idea ideaToDeleted) {
		setModelProperty(IdeaProps.DELETE.toString(), ideaToDeleted);
	}

	public void deleteInternal(Internal internalToDeleted) {
		setModelProperty(InternalProps.DELETE.toString(), internalToDeleted);
	}

	public void deleteItem(Item itemToDeleted) {
		setModelProperty(ItemProps.DELETE.toString(), itemToDeleted);
	}

	public void deleteItemLink(ItemLink itemLinkToDeleted) {
		setModelProperty(ItemLinkProps.DELETE.toString(), itemLinkToDeleted);
	}

	public void deleteLocation(Location locationToDeleted) {
		setModelProperty(LocationProps.DELETE.toString(), locationToDeleted);
	}

	private void deleteMemo(Memo memo) {
		setModelProperty(MemoProps.DELETE.toString(), memo);
	}

	public void deleteMultiAttributes(ArrayList<Long> ids) {
		setModelProperty(GenderProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiCategories(ArrayList<Long> ids) {
		setModelProperty(CategoryProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiChapters(ArrayList<Long> ids) {
		setModelProperty(ChapterProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiGenders(ArrayList<Long> ids) {
		setModelProperty(GenderProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiIdeas(ArrayList<Long> ids) {
		setModelProperty(IdeaProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiInternals(ArrayList<Long> ids) {
		setModelProperty(InternalProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiItemLinks(ArrayList<Long> ids) {
		setModelProperty(ItemLinkProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiItems(ArrayList<Long> ids) {
		setModelProperty(ItemProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiLocations(ArrayList<Long> ids) {
		setModelProperty(LocationProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiParts(ArrayList<Long> ids) {
		setModelProperty(PartProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiPersons(ArrayList<Long> ids) {
		setModelProperty(PersonProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiRelationships(ArrayList<Long> ids) {
		setModelProperty(RelationshipProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiScenes(ArrayList<Long> ids) {
		setModelProperty(SceneProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiStrands(ArrayList<Long> ids) {
		setModelProperty(StrandProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiTagLinks(ArrayList<Long> ids) {
		setModelProperty(TagLinkProps.DELETE_MULTI.toString(), ids);
	}

	public void deleteMultiTags(ArrayList<Long> ids) {
		setModelProperty(TagProps.DELETE_MULTI.toString(), ids);
	}

	public void deletePart(Part partToDeleted) {
		setModelProperty(PartProps.DELETE.toString(), partToDeleted);
	}

	public void deletePerson(Person personToDeleted) {
		setModelProperty(PersonProps.DELETE.toString(), personToDeleted);
	}

	public void deleteRelationship(Relationship r) {
		setModelProperty(RelationshipProps.DELETE.toString(), r);
	}

	public void deleteScene(Scene sceneToDeleted) {
		setModelProperty(SceneProps.DELETE.toString(), sceneToDeleted);
	}

	public void deleteStrand(Strand strandToDeleted) {
		setModelProperty(StrandProps.DELETE.toString(), strandToDeleted);
	}

	public void deleteTag(Tag tagToDeleted) {
		setModelProperty(TagProps.DELETE.toString(), tagToDeleted);
	}

	public void deleteTagLink(TagLink tagLinkToDeleted) {
		setModelProperty(TagLinkProps.DELETE.toString(), tagLinkToDeleted);
	}

	public void deleteTimeEvent(TimeEvent chapterToDeleted) {
		setModelProperty(TimeEventProps.DELETE.toString(), chapterToDeleted);
	}

	public void export(SbView view) {
		setModelProperty(CommonProps.EXPORT.toString(), view);
	}

	public void filterScenes(SceneState state) {
		setModelProperty(SceneProps.FILTER.toString(), state);
	}

	public void filterScenesItem(String item) {
		setModelProperty(SceneProps.FILTERITEM.toString(), item);
	}

	public void filterScenesLocation(String location) {
		setModelProperty(SceneProps.FILTERLOCATION.toString(), location);
	}

	public void filterScenesPerson(String person) {
		setModelProperty(SceneProps.FILTERPERSON.toString(), person);
	}

	public void filterScenesStrand(String strand) {
		setModelProperty(SceneProps.FILTERSTRAND.toString(), strand);
	}

	public void manageSetColumns(Integer val) {
		setModelProperty(ManageViewProps.COLUMNS.toString(), val);
	}

	// manage view
	public void manageSetZoom(Integer val) {
		setModelProperty(ManageViewProps.ZOOM.toString(), val);
	}

	public void manageShowEntity(AbstractEntity entity) {
		setModelProperty(ManageViewProps.SHOW_ENTITY.toString(), entity);
	}

	// memoria view
	public void memoriaSetBalloonLayout(Boolean val) {
		setModelProperty(MemoriaViewProps.BALLOON.toString(), val);
	}

	public void newAttribute(Attribute entity) {
		setModelProperty(GenderProps.NEW.toString(), entity);
	}

	public void newCategory(Category newCategory) {
		setModelProperty(CategoryProps.NEW.toString(), newCategory);
	}

	public void newChapter(Chapter newChapter) {
		setModelProperty(ChapterProps.NEW.toString(), newChapter);
	}

	public void newEntity(AbstractEntity entity) {
		SbApp.trace("BookController.newEntity(" + entity.getClass().getName() + ")");
		try {
			if (entity instanceof Chapter) {
				newChapter((Chapter) entity);
			} else if (entity instanceof Part) {
				newPart((Part) entity);
			} else if (entity instanceof Location) {
				newLocation((Location) entity);
			} else if (entity instanceof Person) {
				newPerson((Person) entity);
			} else if (entity instanceof Relationship) {
				newRelationship((Relationship) entity);
			} else if (entity instanceof Gender) {
				newGender((Gender) entity);
			} else if (entity instanceof Category) {
				newCategory((Category) entity);
			} else if (entity instanceof Strand) {
				newStrand((Strand) entity);
			} else if (entity instanceof Idea) {
				newIdea((Idea) entity);
			} else if (entity instanceof Memo) {
				newMemo((Memo) entity);
			} else if (entity instanceof Tag) {
				newTag((Tag) entity);
			} else if (entity instanceof TagLink) {
				newTagLink((TagLink) entity);
			} else if (entity instanceof Item) {
				newItem((Item) entity);
			} else if (entity instanceof ItemLink) {
				newItemLink((ItemLink) entity);
			} else if (entity instanceof Scene) {
				newScene((Scene) entity);
			} else if (entity instanceof Internal) {
				newInternal((Internal) entity);
			} else if (entity instanceof Attribute) {
				newAttribute((Attribute) entity);
			} else if (entity instanceof TimeEvent) {
				newTimeEvent((TimeEvent) entity);
			} else
				throw new Exception("Entity type not found.");
		} catch (Exception e) {
			SbApp.error("BookController.newEntity(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	public void newGender(Gender newGender) {
		setModelProperty(GenderProps.NEW.toString(), newGender);
	}

	public void newIdea(Idea newIdea) {
		setModelProperty(IdeaProps.NEW.toString(), newIdea);
	}

	public void newInternal(Internal newInternal) {
		setModelProperty(InternalProps.NEW.toString(), newInternal);
	}

	public void newItem(Item newItem) {
		setModelProperty(ItemProps.NEW.toString(), newItem);
	}

	public void newItemLink(ItemLink newItemLink) {
		setModelProperty(ItemLinkProps.NEW.toString(), newItemLink);
	}

	public void newLocation(Location newLocation) {
		setModelProperty(LocationProps.NEW.toString(), newLocation);
	}

	private void newMemo(Memo memo) {
		setModelProperty(MemoProps.NEW.toString(), memo);
	}

	public void newPart(Part newPart) {
		setModelProperty(PartProps.NEW.toString(), newPart);
	}

	public void newPerson(Person newPerson) {
		setModelProperty(PersonProps.NEW.toString(), newPerson);
	}

	public void newRelationship(Relationship r) {
		setModelProperty(RelationshipProps.NEW.toString(), r);
	}

	public void newScene(Scene newScene) {
		setModelProperty(SceneProps.NEW.toString(), newScene);
	}

	public void newStrand(Strand newStrand) {
		setModelProperty(StrandProps.NEW.toString(), newStrand);
	}

	public void newTag(Tag newTag) {
		setModelProperty(TagProps.NEW.toString(), newTag);
	}

	public void newTagLink(TagLink newTagLink) {
		setModelProperty(TagLinkProps.NEW.toString(), newTagLink);
	}

	public void newTimeEvent(TimeEvent newChapter) {
		setModelProperty(TimeEventProps.NEW.toString(), newChapter);
	}

	public void orderDownCategory(Category category) {
		setModelProperty(CategoryProps.ORDER_DOWN.toString(), category);
	}

	public void orderDownStrand(Strand strand) {
		setModelProperty(StrandProps.ORDER_DOWN.toString(), strand);
	}

	public void orderUpCategory(Category category) {
		setModelProperty(CategoryProps.ORDER_UP.toString(), category);
	}

	public void orderUpStrand(Strand strand) {
		setModelProperty(StrandProps.ORDER_UP.toString(), strand);
	}

	public void print(SbView view) {
		setModelProperty(CommonProps.PRINT.toString(), view);
	}

	public void readingSetFontSize(Integer val) {
		setModelProperty(ReadingViewProps.FONT_SIZE.toString(), val);
	}

	// reading view
	public void readingSetZoom(Integer val) {
		setModelProperty(ReadingViewProps.ZOOM.toString(), val);
	}

	// common
	public void refresh(SbView view) {
		setModelProperty(CommonProps.REFRESH.toString(), view);
	}

	public void setAttributeToEdit(Attribute entity) {
		setModelProperty(AttributeProps.EDIT.toString(), entity);
	}

	public void setCategoryToEdit(Category categoryToEdit) {
		setModelProperty(CategoryProps.EDIT.toString(), categoryToEdit);
	}

	public void setChapterToEdit(Chapter chapterToEdit) {
		setModelProperty(ChapterProps.EDIT.toString(), chapterToEdit);
	}

	public void setEntityToEdit(AbstractEntity entity) {
		SbApp.trace("BookController.setEntityToEdit(" + entity.getClass().getName() + ")");
		try {
			if (entity instanceof Chapter) {
				setChapterToEdit((Chapter) entity);
			} else if (entity instanceof Part) {
				setPartToEdit((Part) entity);
			} else if (entity instanceof Location) {
				setLocationToEdit((Location) entity);
			} else if (entity instanceof Person) {
				setPersonToEdit((Person) entity);
			} else if (entity instanceof Relationship) {
				setRelationshipToEdit((Relationship) entity);
			} else if (entity instanceof Gender) {
				setGenderToEdit((Gender) entity);
			} else if (entity instanceof Category) {
				setCategoryToEdit((Category) entity);
			} else if (entity instanceof Strand) {
				setStrandToEdit((Strand) entity);
			} else if (entity instanceof Idea) {
				setIdeaToEdit((Idea) entity);
			} else if (entity instanceof Memo) {
				setMemoToEdit((Memo) entity);
			} else if (entity instanceof Tag) {
				setTagToEdit((Tag) entity);
			} else if (entity instanceof TagLink) {
				setTagLinkToEdit((TagLink) entity);
			} else if (entity instanceof Item) {
				setItemToEdit((Item) entity);
			} else if (entity instanceof ItemLink) {
				setItemLinkToEdit((ItemLink) entity);
			} else if (entity instanceof Scene) {
				setSceneToEdit((Scene) entity);
			} else if (entity instanceof Internal) {
				setInternalToEdit((Internal) entity);
			} else if (entity instanceof TimeEvent) {
				setTimeEventToEdit((TimeEvent) entity);
			} else
				throw new Exception("Entity type not found.");
		} catch (Exception e) {
			SbApp.error("BookController.setEntityToEdit(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	public void setGenderToEdit(Gender genderToEdit) {
		setModelProperty(GenderProps.EDIT.toString(), genderToEdit);
	}

	public void setIdeaToEdit(Idea ideaToEdit) {
		setModelProperty(IdeaProps.EDIT.toString(), ideaToEdit);
	}

	public void setInternalToEdit(Internal internalToEdit) {
		setModelProperty(InternalProps.EDIT.toString(), internalToEdit);
	}

	public void setItemLinkToEdit(ItemLink itemLinkToEdit) {
		setModelProperty(ItemLinkProps.EDIT.toString(), itemLinkToEdit);
	}

	public void setItemToEdit(Item itemToEdit) {
		setModelProperty(ItemProps.EDIT.toString(), itemToEdit);
	}

	public void setLocationToEdit(Location locationToEdit) {
		setModelProperty(LocationProps.EDIT.toString(), locationToEdit);
	}

	private void setMemoToEdit(Memo memo) {
		setModelProperty(MemoProps.EDIT.toString(), memo);
	}

	public void setPartToEdit(Part partToEdit) {
		setModelProperty(PartProps.EDIT.toString(), partToEdit);
	}

	public void setPersonToEdit(Person personToEdit) {
		setModelProperty(PersonProps.EDIT.toString(), personToEdit);
	}

	public void setRelationshipToEdit(Relationship r) {
		setModelProperty(RelationshipProps.EDIT.toString(), r);
	}

	public void setSceneToEdit(Scene sceneToEdit) {
		setModelProperty(SceneProps.EDIT.toString(), sceneToEdit);
	}

	public void setStrandToEdit(Strand strandToEdit) {
		setModelProperty(StrandProps.EDIT.toString(), strandToEdit);
	}

	public void setTagLinkToEdit(TagLink tagLinkToEdit) {
		setModelProperty(TagLinkProps.EDIT.toString(), tagLinkToEdit);
	}

	public void setTagToEdit(Tag tagToEdit) {
		setModelProperty(TagProps.EDIT.toString(), tagToEdit);
	}

	public void setTimeEventToEdit(TimeEvent chapterToEdit) {
		setModelProperty(TimeEventProps.EDIT.toString(), chapterToEdit);
	}

	public void showInfo(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_INFO.toString(), entity);
	}

	public void showInfo(DbFile dbFile) {
		setModelProperty(CommonProps.SHOW_INFO.toString(), dbFile);
	}

	public void showInMemoria(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_IN_MEMORIA.toString(), entity);
	}

	public void showMemo(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_MEMO.toString(), entity);
	}

	public void showOptions(SbView view) {
		setModelProperty(CommonProps.SHOW_OPTIONS.toString(), view);
	}

	// tools
	public void showTaskList() {
		SceneStateModel model = new SceneStateModel();
		filterScenes((SceneState) model.findByNumber(6));
	}

	public void unloadEditor() {
		setModelProperty(CommonProps.UNLOAD_EDITOR.toString(), null);
	}

	public void updateAttribute(Attribute entity) {
		setModelProperty(GenderProps.UPDATE.toString(), entity);
	}

	// categories
	public void updateCategory(Category category) {
		setModelProperty(CategoryProps.UPDATE.toString(), category);
	}

	// chapter
	public void updateChapter(Chapter chapter) {
		setModelProperty(ChapterProps.UPDATE.toString(), chapter);
	}

	public void updateEntity(AbstractEntity entity) {
		SbApp.trace("BookController.updateEntity(" + entity.getClass().getName() + ")");
		try {
			if (entity instanceof Chapter) {
				updateChapter((Chapter) entity);
			} else if (entity instanceof Part) {
				updatePart((Part) entity);
			} else if (entity instanceof Location) {
				updateLocation((Location) entity);
			} else if (entity instanceof Person) {
				updatePerson((Person) entity);
			} else if (entity instanceof Relationship) {
				updateRelationship((Relationship) entity);
			} else if (entity instanceof Gender) {
				updateGender((Gender) entity);
			} else if (entity instanceof Category) {
				updateCategory((Category) entity);
			} else if (entity instanceof Strand) {
				updateStrand((Strand) entity);
			} else if (entity instanceof Idea) {
				updateIdea((Idea) entity);
			} else if (entity instanceof Tag) {
				updateTag((Tag) entity);
			} else if (entity instanceof Memo) {
				updateMemo((Memo) entity);
			} else if (entity instanceof TagLink) {
				updateTagLink((TagLink) entity);
			} else if (entity instanceof Item) {
				updateItem((Item) entity);
			} else if (entity instanceof ItemLink) {
				updateItemLink((ItemLink) entity);
			} else if (entity instanceof Scene) {
				updateScene((Scene) entity);
			} else if (entity instanceof Internal) {
				updateInternal((Internal) entity);
			} else if (entity instanceof TimeEvent) {
				updateTimeEvent((TimeEvent) entity);
			} else {
				throw new Exception("Entity type not found.");
			}
		} catch (Exception e) {
			SbApp.error("BookController.updateEntity(" + entity.getAbbr() + ")", e);
		}
	}

	// genders
	public void updateGender(Gender gender) {
		setModelProperty(GenderProps.UPDATE.toString(), gender);
	}

	// ideas
	public void updateIdea(Idea idea) {
		setModelProperty(IdeaProps.UPDATE.toString(), idea);
	}

	// internals
	public void updateInternal(Internal internal) {
		setModelProperty(InternalProps.UPDATE.toString(), internal);
	}

	// items
	public void updateItem(Item item) {
		setModelProperty(ItemProps.UPDATE.toString(), item);
	}

	// item links
	public void updateItemLink(ItemLink itemLink) {
		setModelProperty(ItemLinkProps.UPDATE.toString(), itemLink);
	}

	// location
	public void updateLocation(Location location) {
		setModelProperty(LocationProps.UPDATE.toString(), location);
	}

	private void updateMemo(Memo memo) {
		setModelProperty(MemoProps.UPDATE.toString(), memo);
	}

	// part
	public void updatePart(Part part) {
		setModelProperty(PartProps.UPDATE.toString(), part);
	}

	// person
	public void updatePerson(Person person) {
		setModelProperty(PersonProps.UPDATE.toString(), person);
	}

	// relationship
	public void updateRelationship(Relationship r) {
		setModelProperty(RelationshipProps.UPDATE.toString(), r);
	}

	// scenes
	public void updateScene(Scene scene) {
		setModelProperty(SceneProps.UPDATE.toString(), scene);
	}

	// strands
	public void updateStrand(Strand strand) {
		setModelProperty(StrandProps.UPDATE.toString(), strand);
	}

	// tags
	public void updateTag(Tag tag) {
		setModelProperty(TagProps.UPDATE.toString(), tag);
	}

	// tag links
	public void updateTagLink(TagLink tagLink) {
		setModelProperty(TagLinkProps.UPDATE.toString(), tagLink);
	}

	// TimeEvent
	public void updateTimeEvent(TimeEvent chapter) {
		setModelProperty(TimeEventProps.UPDATE.toString(), chapter);
	}
}
