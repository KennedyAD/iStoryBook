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

// TODO: Auto-generated Javadoc
/**
 * The Class BookController.
 *
 * @author martin
 */
public class BookController extends AbstractController {

	/**
	 * The Enum AttributeProps.
	 */
	public enum AttributeProps {

		/** The init. */
		INIT("InitAttributes"), /** The edit. */
 EDIT("EditAttribute"), /** The delete. */
 DELETE("DeleteAttribute"), /** The delete multi. */
 DELETE_MULTI(
				"DeleteMultiAttributes"), 
 /** The new. */
 NEW("NewAttribute"), 
 /** The update. */
 UPDATE("UpdateAttribute");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new attribute props.
		 *
		 * @param text the text
		 */
		private AttributeProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum BookViewProps.
	 */
	public enum BookViewProps {

		/** The zoom. */
		ZOOM("BookZoom"), /** The height factor. */
 HEIGHT_FACTOR("BookHeightFactor"), /** The show entity. */
 SHOW_ENTITY("BookShowEntity");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new book view props.
		 *
		 * @param text the text
		 */
		private BookViewProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum CategoryProps.
	 */
	public enum CategoryProps {

		/** The init. */
		INIT("InitCategories"), /** The edit. */
 EDIT("EditCategory"), /** The delete. */
 DELETE("DeleteCategory"), /** The delete multi. */
 DELETE_MULTI(
				"DeleteMultiCategories"), 
 /** The new. */
 NEW("NewCategory"), 
 /** The order up. */
 ORDER_UP("OrderUpCategory"), 
 /** The order down. */
 ORDER_DOWN(
						"OrderDownCategory"), 
 /** The update. */
 UPDATE("UpdateCategory");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new category props.
		 *
		 * @param text the text
		 */
		private CategoryProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum ChapterProps.
	 */
	public enum ChapterProps {

		/** The init. */
		INIT("InitChapters"), /** The edit. */
 EDIT("EditChapter"), /** The delete. */
 DELETE("DeleteChapter"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiChapters"), /** The new. */
 NEW(
				"NewChapter"), 
 /** The update. */
 UPDATE("UpdateChapter");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new chapter props.
		 *
		 * @param text the text
		 */
		private ChapterProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum ChronoViewProps.
	 */
	public enum ChronoViewProps {

		/** The zoom. */
		ZOOM("ChronoZoom"), /** The layout direction. */
 LAYOUT_DIRECTION("ChronoLayoutDirection"), /** The show date difference. */
 SHOW_DATE_DIFFERENCE(
				"ChronoShowDateDifference"), 
 /** The show entity. */
 SHOW_ENTITY("ChronoShowEntity");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new chrono view props.
		 *
		 * @param text the text
		 */
		private ChronoViewProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum CommonProps.
	 */
	public enum CommonProps {

		/** The refresh. */
		REFRESH("Refresh"), /** The show options. */
 SHOW_OPTIONS("ShowOptions"), /** The show info. */
 SHOW_INFO("ShowInfo"), /** The show memo. */
 SHOW_MEMO("ShowMemo"), /** The print. */
 PRINT(
				"Print"), 
 /** The export. */
 EXPORT("Export"), 
 /** The show tasklist. */
 SHOW_TASKLIST("ShowTaskList"), 
 /** The show in memoria. */
 SHOW_IN_MEMORIA(
						"ShowInMemoria"), 
 /** The unload editor. */
 UNLOAD_EDITOR("UnloadEditor");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new common props.
		 *
		 * @param text the text
		 */
		private CommonProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum GenderProps.
	 */
	public enum GenderProps {

		/** The init. */
		INIT("InitGenders"), /** The edit. */
 EDIT("EditGender"), /** The delete. */
 DELETE("DeleteGender"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiGenders"), /** The new. */
 NEW(
				"NewGender"), 
 /** The update. */
 UPDATE("UpdateGender");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new gender props.
		 *
		 * @param text the text
		 */
		private GenderProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum IdeaProps.
	 */
	public enum IdeaProps {

		/** The init. */
		INIT("InitIdeas"), /** The edit. */
 EDIT("EditIdea"), /** The delete. */
 DELETE("DeleteIdea"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiIdeas"), /** The new. */
 NEW(
				"NewIdea"), 
 /** The update. */
 UPDATE("UpdateIdea");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new idea props.
		 *
		 * @param text the text
		 */
		private IdeaProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum InternalProps.
	 */
	public enum InternalProps {

		/** The init. */
		INIT("InitInternal"), /** The edit. */
 EDIT("EditInternal"), /** The delete. */
 DELETE("DeleteInternal"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiInternals"), /** The new. */
 NEW(
				"NewInternal"), 
 /** The update. */
 UPDATE("UpdateInternal");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new internal props.
		 *
		 * @param text the text
		 */
		private InternalProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum ItemLinkProps.
	 */
	public enum ItemLinkProps {

		/** The init. */
		INIT("InitItemLink"), /** The edit. */
 EDIT("EditItemLink"), /** The delete. */
 DELETE("DeleteItemLink"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiItemLinks"), /** The new. */
 NEW(
				"NewItemLink"), 
 /** The update. */
 UPDATE("UpdateItemLink");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new item link props.
		 *
		 * @param text the text
		 */
		private ItemLinkProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum ItemProps.
	 */
	public enum ItemProps {

		/** The init. */
		INIT("InitItem"), /** The edit. */
 EDIT("EditItem"), /** The delete. */
 DELETE("DeleteItem"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiItems"), /** The new. */
 NEW(
				"NewItem"), 
 /** The update. */
 UPDATE("UpdateItem");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new item props.
		 *
		 * @param text the text
		 */
		private ItemProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum LocationProps.
	 */
	public enum LocationProps {

		/** The init. */
		INIT("InitLocations"), /** The edit. */
 EDIT("EditLocation"), /** The delete. */
 DELETE("DeleteLocation"), /** The delete multi. */
 DELETE_MULTI(
				"DeleteMultiLocations"), 
 /** The new. */
 NEW("NewLocation"), 
 /** The update. */
 UPDATE("UpdateLocation");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new location props.
		 *
		 * @param text the text
		 */
		private LocationProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum ManageViewProps.
	 */
	public enum ManageViewProps {

		/** The zoom. */
		ZOOM("ManageZoom"), /** The columns. */
 COLUMNS("ManageColumns"), /** The show entity. */
 SHOW_ENTITY("ManageShowEntity");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new manage view props.
		 *
		 * @param text the text
		 */
		private ManageViewProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum MemoProps.
	 */
	public enum MemoProps {

		/** The init. */
		INIT("InitMemos"), /** The edit. */
 EDIT("EditMemo"), /** The delete. */
 DELETE("DeleteMemo"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiMemos"), /** The new. */
 NEW(
				"NewMemo"), 
 /** The update. */
 UPDATE("UpdateMemo");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new memo props.
		 *
		 * @param text the text
		 */
		private MemoProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * The Enum MemoriaViewProps.
	 */
	public enum MemoriaViewProps {

		/** The balloon. */
		BALLOON("MemoriaBalloon");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new memoria view props.
		 *
		 * @param text the text
		 */
		private MemoriaViewProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum PartProps.
	 */
	public enum PartProps {

		/** The init. */
		INIT("InitParts"), /** The edit. */
 EDIT("EditPart"), /** The delete. */
 DELETE("DeletePart"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiParts"), /** The new. */
 NEW(
				"NewPart"), 
 /** The update. */
 UPDATE("UpdatePart"), 
 /** The change. */
 CHANGE("ChangePart");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new part props.
		 *
		 * @param text the text
		 */
		private PartProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum PersonProps.
	 */
	public enum PersonProps {

		/** The init. */
		INIT("InitPersons"), /** The edit. */
 EDIT("EditPerson"), /** The delete. */
 DELETE("DeletePerson"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiPersons"), /** The new. */
 NEW(
				"NewPerson"), 
 /** The update. */
 UPDATE("UpdatePerson");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new person props.
		 *
		 * @param text the text
		 */
		private PersonProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum ReadingViewProps.
	 */
	public enum ReadingViewProps {

		/** The zoom. */
		ZOOM("ReadingZoom"), /** The font size. */
 FONT_SIZE("ReadingFontSize");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new reading view props.
		 *
		 * @param text the text
		 */
		private ReadingViewProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum RelationshipProps.
	 */
	public enum RelationshipProps {

		/** The init. */
		INIT("InitRelationship"), /** The edit. */
 EDIT("EditRelationship"), /** The delete. */
 DELETE("DeleteRelationship"), /** The delete multi. */
 DELETE_MULTI(
				"DeleteMultiRelationships"), 
 /** The new. */
 NEW("NewRelationship"), 
 /** The update. */
 UPDATE("UpdateRelationship");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new relationship props.
		 *
		 * @param text the text
		 */
		private RelationshipProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum SceneProps.
	 */
	public enum SceneProps {

		/** The init. */
		INIT("InitScene"), /** The edit. */
 EDIT("EditScene"), /** The delete. */
 DELETE("DeleteScene"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiScenes"), /** The new. */
 NEW(
				"NewScene"), 
 /** The update. */
 UPDATE("UpdateScene"), 
 /** The filter. */
 FILTER("FilterScenes"), 
 /** The filterstrand. */
 FILTERSTRAND("FilterStrand"), 
 /** The filterperson. */
 FILTERPERSON(
						"FilterPerson"), 
 /** The filteritem. */
 FILTERITEM("FilterItem"), 
 /** The filterlocation. */
 FILTERLOCATION("FilterLocation");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new scene props.
		 *
		 * @param text the text
		 */
		private SceneProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum StrandProps.
	 */
	public enum StrandProps {

		/** The init. */
		INIT("InitStrands"), /** The edit. */
 EDIT("EditStrand"), /** The delete. */
 DELETE("DeleteStrand"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiStrands"), /** The new. */
 NEW(
				"NewStrand"), 
 /** The order up. */
 ORDER_UP("OrderUpStrand"), 
 /** The order down. */
 ORDER_DOWN("OrderDownStrand"), 
 /** The update. */
 UPDATE("UpdateStrand");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new strand props.
		 *
		 * @param text the text
		 */
		private StrandProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum TagLinkProps.
	 */
	public enum TagLinkProps {

		/** The init. */
		INIT("InitTagLink"), /** The edit. */
 EDIT("EditTagLink"), /** The delete. */
 DELETE("DeleteTagLink"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiTagLinks"), /** The new. */
 NEW(
				"NewTagLink"), 
 /** The update. */
 UPDATE("UpdateTagLink");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new tag link props.
		 *
		 * @param text the text
		 */
		private TagLinkProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum TagProps.
	 */
	public enum TagProps {

		/** The init. */
		INIT("InitTags"), /** The edit. */
 EDIT("EditTag"), /** The delete. */
 DELETE("DeleteTag"), /** The delete multi. */
 DELETE_MULTI("DeleteMultiTags"), /** The new. */
 NEW("NewTag"), /** The update. */
 UPDATE(
				"UpdateTag");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new tag props.
		 *
		 * @param text the text
		 */
		private TagProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/**
	 * The Enum TimeEventProps.
	 */
	public enum TimeEventProps {

		/** The init. */
		INIT("InitTimeEvent"), /** The edit. */
 EDIT("EditTimeEvent"), /** The delete. */
 DELETE("DeleteTimeEvent"), /** The new. */
 NEW("NewTimeEvent"), /** The update. */
 UPDATE(
				"UpdateTimeEvent");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new time event props.
		 *
		 * @param text the text
		 */
		private TimeEventProps(String text) {
			this.text = text;
		}

		/**
		 * Check.
		 *
		 * @param prop the prop
		 * @return true, if successful
		 */
		public boolean check(String prop) {
			return text.equals(prop);
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	};

	/** The main frame. */
	MainFrame mainFrame;;

	/**
	 * Instantiates a new book controller.
	 *
	 * @param m the m
	 */
	public BookController(MainFrame m) {
		super();
		mainFrame = m;
	};

	/**
	 * Instantiates a new book controller.
	 *
	 * @param m the m
	 * @param model the model
	 */
	public BookController(MainFrame m, BlankModel model) {
		super();
		mainFrame = m;
		attachModel(model);
	};

	/**
	 * Instantiates a new book controller.
	 *
	 * @param m the m
	 * @param model the model
	 */
	public BookController(MainFrame m, BookModel model) {
		super();
		mainFrame = m;
		attachModel(model);
	};

	/**
	 * Book set height factor.
	 *
	 * @param val the val
	 */
	public void bookSetHeightFactor(Integer val) {
		setModelProperty(BookViewProps.HEIGHT_FACTOR.toString(), val);
	};

	/**
	 * Book set zoom.
	 *
	 * @param val the val
	 */
	// book view
	public void bookSetZoom(Integer val) {
		setModelProperty(BookViewProps.ZOOM.toString(), val);
	};

	/**
	 * Book show entity.
	 *
	 * @param entity the entity
	 */
	public void bookShowEntity(AbstractEntity entity) {
		setModelProperty(BookViewProps.SHOW_ENTITY.toString(), entity);
	};

	/**
	 * Change part.
	 *
	 * @param part the part
	 */
	public void changePart(Part part) {
		setModelProperty(PartProps.CHANGE.toString(), part);
	};

	/**
	 * Chrono set layout direction.
	 *
	 * @param val the val
	 */
	public void chronoSetLayoutDirection(Boolean val) {
		setModelProperty(ChronoViewProps.LAYOUT_DIRECTION.toString(), val);
	}

	/**
	 * Chrono set show date difference.
	 *
	 * @param val the val
	 */
	public void chronoSetShowDateDifference(Boolean val) {
		setModelProperty(ChronoViewProps.SHOW_DATE_DIFFERENCE.toString(), val);
	}

	/**
	 * Chrono set zoom.
	 *
	 * @param val the val
	 */
	// chrono view
	public void chronoSetZoom(Integer val) {
		setModelProperty(ChronoViewProps.ZOOM.toString(), val);
	}

	/**
	 * Chrono show entity.
	 *
	 * @param entity the entity
	 */
	public void chronoShowEntity(AbstractEntity entity) {
		setModelProperty(ChronoViewProps.SHOW_ENTITY.toString(), entity);
	}

	/**
	 * Delete attribute.
	 *
	 * @param entity the entity
	 */
	public void deleteAttribute(Attribute entity) {
		setModelProperty(GenderProps.DELETE.toString(), entity);
	}

	/**
	 * Delete category.
	 *
	 * @param categoryToDeleted the category to deleted
	 */
	public void deleteCategory(Category categoryToDeleted) {
		setModelProperty(CategoryProps.DELETE.toString(), categoryToDeleted);
	}

	/**
	 * Delete chapter.
	 *
	 * @param chapterToDeleted the chapter to deleted
	 */
	public void deleteChapter(Chapter chapterToDeleted) {
		setModelProperty(ChapterProps.DELETE.toString(), chapterToDeleted);
	}

	/**
	 * Delete entity.
	 *
	 * @param entity the entity
	 */
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
			} else {
				throw new Exception("Entity type not found.");
			}
		} catch (Exception e) {
			SbApp.error("BookController.deleteEntity(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	/**
	 * Delete gender.
	 *
	 * @param genderToDeleted the gender to deleted
	 */
	public void deleteGender(Gender genderToDeleted) {
		setModelProperty(GenderProps.DELETE.toString(), genderToDeleted);
	}

	/**
	 * Delete idea.
	 *
	 * @param ideaToDeleted the idea to deleted
	 */
	public void deleteIdea(Idea ideaToDeleted) {
		setModelProperty(IdeaProps.DELETE.toString(), ideaToDeleted);
	}

	/**
	 * Delete internal.
	 *
	 * @param internalToDeleted the internal to deleted
	 */
	public void deleteInternal(Internal internalToDeleted) {
		setModelProperty(InternalProps.DELETE.toString(), internalToDeleted);
	}

	/**
	 * Delete item.
	 *
	 * @param itemToDeleted the item to deleted
	 */
	public void deleteItem(Item itemToDeleted) {
		setModelProperty(ItemProps.DELETE.toString(), itemToDeleted);
	}

	/**
	 * Delete item link.
	 *
	 * @param itemLinkToDeleted the item link to deleted
	 */
	public void deleteItemLink(ItemLink itemLinkToDeleted) {
		setModelProperty(ItemLinkProps.DELETE.toString(), itemLinkToDeleted);
	}

	/**
	 * Delete location.
	 *
	 * @param locationToDeleted the location to deleted
	 */
	public void deleteLocation(Location locationToDeleted) {
		setModelProperty(LocationProps.DELETE.toString(), locationToDeleted);
	}

	/**
	 * Delete memo.
	 *
	 * @param memo the memo
	 */
	private void deleteMemo(Memo memo) {
		setModelProperty(MemoProps.DELETE.toString(), memo);
	}

	/**
	 * Delete multi attributes.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiAttributes(ArrayList<Long> ids) {
		setModelProperty(GenderProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi categories.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiCategories(ArrayList<Long> ids) {
		setModelProperty(CategoryProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi chapters.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiChapters(ArrayList<Long> ids) {
		setModelProperty(ChapterProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi genders.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiGenders(ArrayList<Long> ids) {
		setModelProperty(GenderProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi ideas.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiIdeas(ArrayList<Long> ids) {
		setModelProperty(IdeaProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi internals.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiInternals(ArrayList<Long> ids) {
		setModelProperty(InternalProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi item links.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiItemLinks(ArrayList<Long> ids) {
		setModelProperty(ItemLinkProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi items.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiItems(ArrayList<Long> ids) {
		setModelProperty(ItemProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi locations.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiLocations(ArrayList<Long> ids) {
		setModelProperty(LocationProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi parts.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiParts(ArrayList<Long> ids) {
		setModelProperty(PartProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi persons.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiPersons(ArrayList<Long> ids) {
		setModelProperty(PersonProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi relationships.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiRelationships(ArrayList<Long> ids) {
		setModelProperty(RelationshipProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi scenes.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiScenes(ArrayList<Long> ids) {
		setModelProperty(SceneProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi strands.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiStrands(ArrayList<Long> ids) {
		setModelProperty(StrandProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi tag links.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiTagLinks(ArrayList<Long> ids) {
		setModelProperty(TagLinkProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete multi tags.
	 *
	 * @param ids the ids
	 */
	public void deleteMultiTags(ArrayList<Long> ids) {
		setModelProperty(TagProps.DELETE_MULTI.toString(), ids);
	}

	/**
	 * Delete part.
	 *
	 * @param partToDeleted the part to deleted
	 */
	public void deletePart(Part partToDeleted) {
		setModelProperty(PartProps.DELETE.toString(), partToDeleted);
	}

	/**
	 * Delete person.
	 *
	 * @param personToDeleted the person to deleted
	 */
	public void deletePerson(Person personToDeleted) {
		setModelProperty(PersonProps.DELETE.toString(), personToDeleted);
	}

	/**
	 * Delete relationship.
	 *
	 * @param r the r
	 */
	public void deleteRelationship(Relationship r) {
		setModelProperty(RelationshipProps.DELETE.toString(), r);
	}

	/**
	 * Delete scene.
	 *
	 * @param sceneToDeleted the scene to deleted
	 */
	public void deleteScene(Scene sceneToDeleted) {
		setModelProperty(SceneProps.DELETE.toString(), sceneToDeleted);
	}

	/**
	 * Delete strand.
	 *
	 * @param strandToDeleted the strand to deleted
	 */
	public void deleteStrand(Strand strandToDeleted) {
		setModelProperty(StrandProps.DELETE.toString(), strandToDeleted);
	}

	/**
	 * Delete tag.
	 *
	 * @param tagToDeleted the tag to deleted
	 */
	public void deleteTag(Tag tagToDeleted) {
		setModelProperty(TagProps.DELETE.toString(), tagToDeleted);
	}

	/**
	 * Delete tag link.
	 *
	 * @param tagLinkToDeleted the tag link to deleted
	 */
	public void deleteTagLink(TagLink tagLinkToDeleted) {
		setModelProperty(TagLinkProps.DELETE.toString(), tagLinkToDeleted);
	}

	/**
	 * Delete time event.
	 *
	 * @param chapterToDeleted the chapter to deleted
	 */
	public void deleteTimeEvent(TimeEvent chapterToDeleted) {
		setModelProperty(TimeEventProps.DELETE.toString(), chapterToDeleted);
	}

	/**
	 * Export.
	 *
	 * @param view the view
	 */
	public void export(SbView view) {
		setModelProperty(CommonProps.EXPORT.toString(), view);
	}

	/**
	 * Filter scenes.
	 *
	 * @param state the state
	 */
	public void filterScenes(SceneState state) {
		setModelProperty(SceneProps.FILTER.toString(), state);
	}

	/**
	 * Filter scenes item.
	 *
	 * @param item the item
	 */
	public void filterScenesItem(String item) {
		setModelProperty(SceneProps.FILTERITEM.toString(), item);
	}

	/**
	 * Filter scenes location.
	 *
	 * @param location the location
	 */
	public void filterScenesLocation(String location) {
		setModelProperty(SceneProps.FILTERLOCATION.toString(), location);
	}

	/**
	 * Filter scenes person.
	 *
	 * @param person the person
	 */
	public void filterScenesPerson(String person) {
		setModelProperty(SceneProps.FILTERPERSON.toString(), person);
	}

	/**
	 * Filter scenes strand.
	 *
	 * @param strand the strand
	 */
	public void filterScenesStrand(String strand) {
		setModelProperty(SceneProps.FILTERSTRAND.toString(), strand);
	}

	/**
	 * Manage set columns.
	 *
	 * @param val the val
	 */
	public void manageSetColumns(Integer val) {
		setModelProperty(ManageViewProps.COLUMNS.toString(), val);
	}

	/**
	 * Manage set zoom.
	 *
	 * @param val the val
	 */
	// manage view
	public void manageSetZoom(Integer val) {
		setModelProperty(ManageViewProps.ZOOM.toString(), val);
	}

	/**
	 * Manage show entity.
	 *
	 * @param entity the entity
	 */
	public void manageShowEntity(AbstractEntity entity) {
		setModelProperty(ManageViewProps.SHOW_ENTITY.toString(), entity);
	}

	/**
	 * Memoria set balloon layout.
	 *
	 * @param val the val
	 */
	// memoria view
	public void memoriaSetBalloonLayout(Boolean val) {
		setModelProperty(MemoriaViewProps.BALLOON.toString(), val);
	}

	/**
	 * New attribute.
	 *
	 * @param entity the entity
	 */
	public void newAttribute(Attribute entity) {
		setModelProperty(GenderProps.NEW.toString(), entity);
	}

	/**
	 * New category.
	 *
	 * @param newCategory the new category
	 */
	public void newCategory(Category newCategory) {
		setModelProperty(CategoryProps.NEW.toString(), newCategory);
	}

	/**
	 * New chapter.
	 *
	 * @param newChapter the new chapter
	 */
	public void newChapter(Chapter newChapter) {
		setModelProperty(ChapterProps.NEW.toString(), newChapter);
	}

	/**
	 * New entity.
	 *
	 * @param entity the entity
	 */
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
			} else {
				throw new Exception("Entity type not found.");
			}
		} catch (Exception e) {
			SbApp.error("BookController.newEntity(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	/**
	 * New gender.
	 *
	 * @param newGender the new gender
	 */
	public void newGender(Gender newGender) {
		setModelProperty(GenderProps.NEW.toString(), newGender);
	}

	/**
	 * New idea.
	 *
	 * @param newIdea the new idea
	 */
	public void newIdea(Idea newIdea) {
		setModelProperty(IdeaProps.NEW.toString(), newIdea);
	}

	/**
	 * New internal.
	 *
	 * @param newInternal the new internal
	 */
	public void newInternal(Internal newInternal) {
		setModelProperty(InternalProps.NEW.toString(), newInternal);
	}

	/**
	 * New item.
	 *
	 * @param newItem the new item
	 */
	public void newItem(Item newItem) {
		setModelProperty(ItemProps.NEW.toString(), newItem);
	}

	/**
	 * New item link.
	 *
	 * @param newItemLink the new item link
	 */
	public void newItemLink(ItemLink newItemLink) {
		setModelProperty(ItemLinkProps.NEW.toString(), newItemLink);
	}

	/**
	 * New location.
	 *
	 * @param newLocation the new location
	 */
	public void newLocation(Location newLocation) {
		setModelProperty(LocationProps.NEW.toString(), newLocation);
	}

	/**
	 * New memo.
	 *
	 * @param memo the memo
	 */
	private void newMemo(Memo memo) {
		setModelProperty(MemoProps.NEW.toString(), memo);
	}

	/**
	 * New part.
	 *
	 * @param newPart the new part
	 */
	public void newPart(Part newPart) {
		setModelProperty(PartProps.NEW.toString(), newPart);
	}

	/**
	 * New person.
	 *
	 * @param newPerson the new person
	 */
	public void newPerson(Person newPerson) {
		setModelProperty(PersonProps.NEW.toString(), newPerson);
	}

	/**
	 * New relationship.
	 *
	 * @param r the r
	 */
	public void newRelationship(Relationship r) {
		setModelProperty(RelationshipProps.NEW.toString(), r);
	}

	/**
	 * New scene.
	 *
	 * @param newScene the new scene
	 */
	public void newScene(Scene newScene) {
		setModelProperty(SceneProps.NEW.toString(), newScene);
	}

	/**
	 * New strand.
	 *
	 * @param newStrand the new strand
	 */
	public void newStrand(Strand newStrand) {
		setModelProperty(StrandProps.NEW.toString(), newStrand);
	}

	/**
	 * New tag.
	 *
	 * @param newTag the new tag
	 */
	public void newTag(Tag newTag) {
		setModelProperty(TagProps.NEW.toString(), newTag);
	}

	/**
	 * New tag link.
	 *
	 * @param newTagLink the new tag link
	 */
	public void newTagLink(TagLink newTagLink) {
		setModelProperty(TagLinkProps.NEW.toString(), newTagLink);
	}

	/**
	 * New time event.
	 *
	 * @param newChapter the new chapter
	 */
	public void newTimeEvent(TimeEvent newChapter) {
		setModelProperty(TimeEventProps.NEW.toString(), newChapter);
	}

	/**
	 * Order down category.
	 *
	 * @param category the category
	 */
	public void orderDownCategory(Category category) {
		setModelProperty(CategoryProps.ORDER_DOWN.toString(), category);
	}

	/**
	 * Order down strand.
	 *
	 * @param strand the strand
	 */
	public void orderDownStrand(Strand strand) {
		setModelProperty(StrandProps.ORDER_DOWN.toString(), strand);
	}

	/**
	 * Order up category.
	 *
	 * @param category the category
	 */
	public void orderUpCategory(Category category) {
		setModelProperty(CategoryProps.ORDER_UP.toString(), category);
	}

	/**
	 * Order up strand.
	 *
	 * @param strand the strand
	 */
	public void orderUpStrand(Strand strand) {
		setModelProperty(StrandProps.ORDER_UP.toString(), strand);
	}

	/**
	 * Prints the.
	 *
	 * @param view the view
	 */
	public void print(SbView view) {
		setModelProperty(CommonProps.PRINT.toString(), view);
	}

	/**
	 * Reading set font size.
	 *
	 * @param val the val
	 */
	public void readingSetFontSize(Integer val) {
		setModelProperty(ReadingViewProps.FONT_SIZE.toString(), val);
	}

	/**
	 * Reading set zoom.
	 *
	 * @param val the val
	 */
	// reading view
	public void readingSetZoom(Integer val) {
		setModelProperty(ReadingViewProps.ZOOM.toString(), val);
	}

	/**
	 * Refresh.
	 *
	 * @param view the view
	 */
	// common
	public void refresh(SbView view) {
		setModelProperty(CommonProps.REFRESH.toString(), view);
	}

	/**
	 * Sets the attribute to edit.
	 *
	 * @param entity the new attribute to edit
	 */
	public void setAttributeToEdit(Attribute entity) {
		setModelProperty(AttributeProps.EDIT.toString(), entity);
	}

	/**
	 * Sets the category to edit.
	 *
	 * @param categoryToEdit the new category to edit
	 */
	public void setCategoryToEdit(Category categoryToEdit) {
		setModelProperty(CategoryProps.EDIT.toString(), categoryToEdit);
	}

	/**
	 * Sets the chapter to edit.
	 *
	 * @param chapterToEdit the new chapter to edit
	 */
	public void setChapterToEdit(Chapter chapterToEdit) {
		setModelProperty(ChapterProps.EDIT.toString(), chapterToEdit);
	}

	/**
	 * Sets the entity to edit.
	 *
	 * @param entity the new entity to edit
	 */
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
			} else {
				throw new Exception("Entity type not found.");
			}
		} catch (Exception e) {
			SbApp.error("BookController.setEntityToEdit(" + entity.getClass().getName() + ") Exception:", e);
		}
	}

	/**
	 * Sets the gender to edit.
	 *
	 * @param genderToEdit the new gender to edit
	 */
	public void setGenderToEdit(Gender genderToEdit) {
		setModelProperty(GenderProps.EDIT.toString(), genderToEdit);
	}

	/**
	 * Sets the idea to edit.
	 *
	 * @param ideaToEdit the new idea to edit
	 */
	public void setIdeaToEdit(Idea ideaToEdit) {
		setModelProperty(IdeaProps.EDIT.toString(), ideaToEdit);
	}

	/**
	 * Sets the internal to edit.
	 *
	 * @param internalToEdit the new internal to edit
	 */
	public void setInternalToEdit(Internal internalToEdit) {
		setModelProperty(InternalProps.EDIT.toString(), internalToEdit);
	}

	/**
	 * Sets the item link to edit.
	 *
	 * @param itemLinkToEdit the new item link to edit
	 */
	public void setItemLinkToEdit(ItemLink itemLinkToEdit) {
		setModelProperty(ItemLinkProps.EDIT.toString(), itemLinkToEdit);
	}

	/**
	 * Sets the item to edit.
	 *
	 * @param itemToEdit the new item to edit
	 */
	public void setItemToEdit(Item itemToEdit) {
		setModelProperty(ItemProps.EDIT.toString(), itemToEdit);
	}

	/**
	 * Sets the location to edit.
	 *
	 * @param locationToEdit the new location to edit
	 */
	public void setLocationToEdit(Location locationToEdit) {
		setModelProperty(LocationProps.EDIT.toString(), locationToEdit);
	}

	/**
	 * Sets the memo to edit.
	 *
	 * @param memo the new memo to edit
	 */
	private void setMemoToEdit(Memo memo) {
		setModelProperty(MemoProps.EDIT.toString(), memo);
	}

	/**
	 * Sets the part to edit.
	 *
	 * @param partToEdit the new part to edit
	 */
	public void setPartToEdit(Part partToEdit) {
		setModelProperty(PartProps.EDIT.toString(), partToEdit);
	}

	/**
	 * Sets the person to edit.
	 *
	 * @param personToEdit the new person to edit
	 */
	public void setPersonToEdit(Person personToEdit) {
		setModelProperty(PersonProps.EDIT.toString(), personToEdit);
	}

	/**
	 * Sets the relationship to edit.
	 *
	 * @param r the new relationship to edit
	 */
	public void setRelationshipToEdit(Relationship r) {
		setModelProperty(RelationshipProps.EDIT.toString(), r);
	}

	/**
	 * Sets the scene to edit.
	 *
	 * @param sceneToEdit the new scene to edit
	 */
	public void setSceneToEdit(Scene sceneToEdit) {
		setModelProperty(SceneProps.EDIT.toString(), sceneToEdit);
	}

	/**
	 * Sets the strand to edit.
	 *
	 * @param strandToEdit the new strand to edit
	 */
	public void setStrandToEdit(Strand strandToEdit) {
		setModelProperty(StrandProps.EDIT.toString(), strandToEdit);
	}

	/**
	 * Sets the tag link to edit.
	 *
	 * @param tagLinkToEdit the new tag link to edit
	 */
	public void setTagLinkToEdit(TagLink tagLinkToEdit) {
		setModelProperty(TagLinkProps.EDIT.toString(), tagLinkToEdit);
	}

	/**
	 * Sets the tag to edit.
	 *
	 * @param tagToEdit the new tag to edit
	 */
	public void setTagToEdit(Tag tagToEdit) {
		setModelProperty(TagProps.EDIT.toString(), tagToEdit);
	}

	/**
	 * Sets the time event to edit.
	 *
	 * @param chapterToEdit the new time event to edit
	 */
	public void setTimeEventToEdit(TimeEvent chapterToEdit) {
		setModelProperty(TimeEventProps.EDIT.toString(), chapterToEdit);
	}

	/**
	 * Show info.
	 *
	 * @param entity the entity
	 */
	public void showInfo(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_INFO.toString(), entity);
	}

	/**
	 * Show info.
	 *
	 * @param dbFile the db file
	 */
	public void showInfo(DbFile dbFile) {
		setModelProperty(CommonProps.SHOW_INFO.toString(), dbFile);
	}

	/**
	 * Show in memoria.
	 *
	 * @param entity the entity
	 */
	public void showInMemoria(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_IN_MEMORIA.toString(), entity);
	}

	/**
	 * Show memo.
	 *
	 * @param entity the entity
	 */
	public void showMemo(AbstractEntity entity) {
		setModelProperty(CommonProps.SHOW_MEMO.toString(), entity);
	}

	/**
	 * Show options.
	 *
	 * @param view the view
	 */
	public void showOptions(SbView view) {
		setModelProperty(CommonProps.SHOW_OPTIONS.toString(), view);
	}

	/**
	 * Show task list.
	 */
	// tools
	public void showTaskList() {
		SceneStateModel model = new SceneStateModel();
		filterScenes((SceneState) model.findByNumber(6));
	}

	/**
	 * Unload editor.
	 */
	public void unloadEditor() {
		setModelProperty(CommonProps.UNLOAD_EDITOR.toString(), null);
	}

	/**
	 * Update attribute.
	 *
	 * @param entity the entity
	 */
	public void updateAttribute(Attribute entity) {
		setModelProperty(GenderProps.UPDATE.toString(), entity);
	}

	/**
	 * Update category.
	 *
	 * @param category the category
	 */
	// categories
	public void updateCategory(Category category) {
		setModelProperty(CategoryProps.UPDATE.toString(), category);
	}

	/**
	 * Update chapter.
	 *
	 * @param chapter the chapter
	 */
	// chapter
	public void updateChapter(Chapter chapter) {
		setModelProperty(ChapterProps.UPDATE.toString(), chapter);
	}

	/**
	 * Update entity.
	 *
	 * @param entity the entity
	 */
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

	/**
	 * Update gender.
	 *
	 * @param gender the gender
	 */
	// genders
	public void updateGender(Gender gender) {
		setModelProperty(GenderProps.UPDATE.toString(), gender);
	}

	/**
	 * Update idea.
	 *
	 * @param idea the idea
	 */
	// ideas
	public void updateIdea(Idea idea) {
		setModelProperty(IdeaProps.UPDATE.toString(), idea);
	}

	/**
	 * Update internal.
	 *
	 * @param internal the internal
	 */
	// internals
	public void updateInternal(Internal internal) {
		setModelProperty(InternalProps.UPDATE.toString(), internal);
	}

	/**
	 * Update item.
	 *
	 * @param item the item
	 */
	// items
	public void updateItem(Item item) {
		setModelProperty(ItemProps.UPDATE.toString(), item);
	}

	/**
	 * Update item link.
	 *
	 * @param itemLink the item link
	 */
	// item links
	public void updateItemLink(ItemLink itemLink) {
		setModelProperty(ItemLinkProps.UPDATE.toString(), itemLink);
	}

	/**
	 * Update location.
	 *
	 * @param location the location
	 */
	// location
	public void updateLocation(Location location) {
		setModelProperty(LocationProps.UPDATE.toString(), location);
	}

	/**
	 * Update memo.
	 *
	 * @param memo the memo
	 */
	private void updateMemo(Memo memo) {
		setModelProperty(MemoProps.UPDATE.toString(), memo);
	}

	/**
	 * Update part.
	 *
	 * @param part the part
	 */
	// part
	public void updatePart(Part part) {
		setModelProperty(PartProps.UPDATE.toString(), part);
	}

	/**
	 * Update person.
	 *
	 * @param person the person
	 */
	// person
	public void updatePerson(Person person) {
		setModelProperty(PersonProps.UPDATE.toString(), person);
	}

	/**
	 * Update relationship.
	 *
	 * @param r the r
	 */
	// relationship
	public void updateRelationship(Relationship r) {
		setModelProperty(RelationshipProps.UPDATE.toString(), r);
	}

	/**
	 * Update scene.
	 *
	 * @param scene the scene
	 */
	// scenes
	public void updateScene(Scene scene) {
		setModelProperty(SceneProps.UPDATE.toString(), scene);
	}

	/**
	 * Update strand.
	 *
	 * @param strand the strand
	 */
	// strands
	public void updateStrand(Strand strand) {
		setModelProperty(StrandProps.UPDATE.toString(), strand);
	}

	/**
	 * Update tag.
	 *
	 * @param tag the tag
	 */
	// tags
	public void updateTag(Tag tag) {
		setModelProperty(TagProps.UPDATE.toString(), tag);
	}

	/**
	 * Update tag link.
	 *
	 * @param tagLink the tag link
	 */
	// tag links
	public void updateTagLink(TagLink tagLink) {
		setModelProperty(TagLinkProps.UPDATE.toString(), tagLink);
	}

	/**
	 * Update time event.
	 *
	 * @param chapter the chapter
	 */
	// TimeEvent
	public void updateTimeEvent(TimeEvent chapter) {
		setModelProperty(TimeEventProps.UPDATE.toString(), chapter);
	}
}
