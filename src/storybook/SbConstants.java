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
package storybook;

import java.awt.Dimension;
import java.util.Locale;

import net.infonode.docking.View;
import storybook.SbConstants.PreferenceKey;
import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class SbConstants.
 *
 * @author martin
 * @update favdb
 */
public class SbConstants {
	/* default values */

	/**
	 * The Enum ActionCommand.
	 */
	public enum ActionCommand {
		
		/** The edit. */
EDIT("Edit"), 
 /** The new. */
 NEW("New"), 
 /** The copy. */
 COPY("Copy"), 
 /** The delete. */
 DELETE("Delete");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new action command.
		 *
		 * @param text the text
		 */
		private ActionCommand(String text) {
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
	 * The Enum ActionKey.
	 */
	public enum ActionKey {
		
		/** The category. */
		CATEGORY("Category");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new action key.
		 *
		 * @param text the text
		 */
		private ActionKey(String text) {
			this.text = text;
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
	 * The Enum BookKey.
	 */
public enum BookKey {
		
 /** The chrono zoom. */
 CHRONO_ZOOM("ChronoZoom"), 
 /** The chrono layout direction. */
 CHRONO_LAYOUT_DIRECTION("ChornoLayoutDirection"), 
 /** The chrono show date difference. */
 CHRONO_SHOW_DATE_DIFFERENCE("ChornoShowDateDiff"), 
 /** The book zoom. */
 BOOK_ZOOM("BookZoom"), 
 /** The book height factor. */
 BOOK_HEIGHT_FACTOR("BookHeightFactor"), 
 /** The manage zoom. */
 MANAGE_ZOOM("ManageZoom"), 
 /** The manage columns. */
 MANAGE_COLUMNS("ManageColumns"), 
 /** The reading zoom. */
 READING_ZOOM("ReadingZoom"), 
 /** The reading font size. */
 READING_FONT_SIZE("ReadingFontSize"), 
 /** The leave editor open. */
 LEAVE_EDITOR_OPEN("LeaveEditorOpen"), 
 /** The last used part. */
 LAST_USED_PART("LastUsedPart"), 
 /** The use libreoffice. */
 USE_LIBREOFFICE("UseLibreOffice"), 
 /** The use html scenes. */
 USE_HTML_SCENES("UseHtmlScenes"), 
 /** The use html descr. */
 USE_HTML_DESCR("UseHtmlDescr"), 
 /** The memoria balloon. */
 MEMORIA_BALLOON("MemoriaBalloon"), 
 /** The export chapter numbers. */
 EXPORT_CHAPTER_NUMBERS("ExportChapterNumbers"), 
 /** The export Roman numerals. */
 EXPORT_ROMAN_NUMERALS("ExportRomanNumerals"), 
 /** The export chapter titles. */
 EXPORT_CHAPTER_TITLES("ExportChapterTitles"), 
 /** The export chapter dates locations. */
 EXPORT_CHAPTER_DATES_LOCATIONS("ExportChapterDatesLocations"), 
 /** The export scene titles. */
 EXPORT_SCENE_TITLES("ExportSceneTitles"), 
 /** The export scene separator. */
 EXPORT_SCENE_SEPARATOR("ExportSceneSeparator"), 
 /** The export part titles. */
 EXPORT_PART_TITLES("ExportPartTitles"), 
 /** The editor modless. */
 EDITOR_MODLESS(
																														"EditorModless"), 
 /** The editor full toolbar. */
 EDITOR_FULL_TOOLBAR(
																																"EditorFullToolbar"), 
 /** The export directory. */
 EXPORT_DIRECTORY(
																																		"ExportDirectory"), 
 /** The title. */
 TITLE(
																																				"Title"), 
 /** The subtitle. */
 SUBTITLE(
																																						"SubTitle"), 
 /** The author. */
 AUTHOR(
																																								"Author"), 
 /** The copyright. */
 COPYRIGHT(
																																										"Copyright"), 
 /** The blurb. */
 BLURB(
																																												"Blurb"), 
 /** The notes. */
 NOTES(
																																														"Notes"), 
 /** The last used layout. */
 LAST_USED_LAYOUT(
																																																"_internal_last_used_layout_"), 
 /** The csv quotes. */
 CSV_QUOTES(
																																																		"CSVQuotes"), 
 /** The csv comma. */
 CSV_COMMA(
																																																				"CSVCommaSeparator"), 
 /** The txt tab. */
 TXT_TAB(
																																																						"TXTTabSeparator"), 
 /** The txt other. */
 TXT_OTHER(
																																																								"TXTOtherSeparator"), 
 /** The html use css. */
 HTML_USE_CSS(
																																																										"HTMLUseCSS"), 
 /** The html css file. */
 HTML_CSS_FILE(
																																																												"HTLMCssFile"), 
 /** The html book multi. */
 HTML_BOOK_MULTI(
																																																														"HTMLBookMultifile"), 
 /** The pdf page size. */
 PDF_PAGE_SIZE(
																																																																"PDFPageSize"), 
 /** The pdf landscape. */
 PDF_LANDSCAPE(
																																																																		"PDFLandscape"), 
 /** The calendar. */
 CALENDAR(
																																																																				"Calendar"), 
 /** The book creation date. */
 BOOK_CREATION_DATE(
																																																																						"BookCreationDate"), 
 /** The use simple template. */
 USE_SIMPLE_TEMPLATE(
																																																																								"UseSimpleTemplate"), 
 /** The use personnal template. */
 USE_PERSONNAL_TEMPLATE(
																																																																										"UsePersonnalTemplate");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new book key.
		 *
		 * @param text the text
		 */
		private BookKey(String text) {
			this.text = text;
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
	 * The Enum ClientPropertyName.
	 */
	public enum ClientPropertyName {
		
 /** The document model. */
 DOCUMENT_MODEL("DocumentModel"), 
 /** The dao class. */
 DAO_CLASS("DAOClass"), 
 /** The entity. */
 ENTITY("Entity"), 
 /** The dao. */
 DAO("DAO"), 
 /** The is new entity. */
 IS_NEW_ENTITY("IsNewEntity"), 
 /** The component title. */
 COMPONENT_TITLE("ComponentTitle"), 
 /** The main frame. */
 MAIN_FRAME("MainFrame");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new client property name.
		 *
		 * @param text the text
		 */
		private ClientPropertyName(String text) {
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
	 * The Enum ComponentName.
	 */
	public enum ComponentName {
		
 /** The bt edit. */
 BT_EDIT("BtEdit"), 
 /** The bt new. */
 BT_NEW("BtNew"), 
 /** The bt copy. */
 BT_COPY("BtCopy"), 
 /** The bt delete. */
 BT_DELETE("BtDelete"), 
 /** The bt add or update. */
 BT_ADD_OR_UPDATE("BtUpdate"), 
 /** The bt ok. */
 BT_OK("BtOk"), 
 /** The bt cancel. */
 BT_CANCEL("BtCancel"), 
 /** The bt close. */
 BT_CLOSE("BtClose"), 
 /** The bt order up. */
 BT_ORDER_UP("BtOrderUp"), 
 /** The bt order down. */
 BT_ORDER_DOWN("BtOrderDown"), 
 /** The bt print. */
 BT_PRINT("BtPrint"), 
 /** The bt next. */
 BT_NEXT("BtNext"), 
 /** The bt previous. */
 BT_PREVIOUS("BtPrevious"), 
 /** The bt first. */
 BT_FIRST("BtFirst"), 
 /** The bt last. */
 BT_LAST("BtLast"), 
 /** The bt odt. */
 BT_ODT("BtODT"), 
 /** The tb main. */
 TB_MAIN("MainToolbar"), 
 /** The cb category. */
 CB_CATEGORY("CbCategory"), 
 /** The cb person. */
 CB_PERSON("CbPerson"), 
 /** The cb item. */
 CB_ITEM("CbItem"), 
 /** The combo entity types. */
 COMBO_ENTITY_TYPES("ComboEntityType"), 
 /** The combo entities. */
 COMBO_ENTITIES("ComboEntities"), 
 /** The combo dates. */
 COMBO_DATES("ComboDates"), 
 /** The combo scene states. */
 COMBO_SCENE_STATES("ComboSceneStates"), 
 /** The combo scene strand. */
 COMBO_SCENE_STRAND("ComboSceneStrand"), 
 /** The combo scene person. */
 COMBO_SCENE_PERSON("ComboScenePerson"), 
 /** The combo scene location. */
 COMBO_SCENE_LOCATION("ComboSceneLocation"), 
 /** The combo scene item. */
 COMBO_SCENE_ITEM(
																								"ComboSceneItem");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new component name.
		 *
		 * @param text the text
		 */
		private ComponentName(String text) {
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
	 * The Enum ComponentState.
	 */
	public enum ComponentState {
		
		/** The enabled. */
		ENABLED("Enabled"), 
 /** The disabled. */
 DISABLED("Disabled");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new component state.
		 *
		 * @param text the text
		 */
		private ComponentState(String text) {
			this.text = text;
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
	 * The Enum Directory.
	 */
	public enum Directory {
		
		/** The dictionaries. */
		DICTIONARIES("dicts/"), 
 /** The user dicts. */
 USER_DICTS("dicts");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new directory.
		 *
		 * @param text the text
		 */
		private Directory(String text) {
			this.text = text;
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
	 * The Enum IconSize.
	 */
	public enum IconSize {
		
		/** The small. */
		SMALL(new Dimension(16, 16)), 
 /** The medium. */
 MEDIUM(new Dimension(32, 32)), 
 /** The medium wide. */
 MEDIUM_WIDE(new Dimension(32, 16)), 
 /** The large. */
 LARGE(
				new Dimension(32, 32));
		
		/** The dim. */
		private final Dimension dim;

		/**
		 * Instantiates a new icon size.
		 *
		 * @param text the text
		 */
		private IconSize(Dimension text) {
			this.dim = text;
		}

		/**
		 * Gets the dimension.
		 *
		 * @return the dimension
		 */
		public Dimension getDimension() {
			return dim;
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return dim.toString();
		}
	}
	
	/**
	 * The Enum Language.
	 */
	public static enum Language {
		
		/** The en_ us. */
		en_US, 
 /** The de_ de. */
 // USA english
		de_DE, 
 /** The es_ es. */
 // German
		es_ES, 
 /** The da_ dk. */
 // Spanish
		da_DK, 
 /** The pt_ br. */
 // Danish
		pt_BR, 
 /** The it_ it. */
 // Brazilian Portuguese
		it_IT, 
 /** The fr_ fr. */
 // Italian
		fr_FR, 
 /** The nl_ nl. */
 // French
		nl_NL, 
 /** The hu_ hu. */
 // Ducth
		hu_HU, 
 /** The iw_ il. */
 // Hungarian
		iw_IL, 
 /** The fi_ fi. */
 // Hebrew
		fi_FI, 
 /** The sv_ se. */
 // Finnish
		sv_SE, 
 /** The el_ gr. */
 // Swedish
		el_GR, 
 /** The ja_ jp. */
 // Greek
		ja_JP, 
 /** The zh_ hk. */
 // Japanese
		zh_HK, 
 /** The ru_ ru. */
 // Traditional Chinese (Hong Kong)
		ru_RU, 
 /** The cs_ cz. */
 // Russian
		cs_CZ, 
 /** The zh_ cn. */
 // Czech
		zh_CN, 
 /** The pl_ pl. */
 // Simplified Chinese
		pl_PL; 
 /**
  * Gets the i18 n.
  *
  * @return the i18 n
  */
 // Polish
		public String getI18N() {
			return I18N.getMsg("msg.common.language." + name());
		}

		/**
		 * Gets the locale.
		 *
		 * @return the locale
		 */
		public Locale getLocale() {
			Locale locale;
			switch (this) {
			case en_US:
				locale = Locale.US;
				break;
			case de_DE:
				locale = Locale.GERMANY;
				break;
			case es_ES:
				locale = new Locale("es", "ES");
				break;
			case da_DK:
				locale = new Locale("da", "DK");
				break;
			case pt_BR:
				locale = new Locale("pt", "BR");
				break;
			case it_IT:
				locale = new Locale("it", "IT");
				break;
			case fr_FR:
				locale = new Locale("fr", "FR");
				break;
			case nl_NL:
				locale = new Locale("nl", "NL");
				break;
			case iw_IL:
				locale = new Locale("iw", "IL");
				break;
			case hu_HU:
				locale = new Locale("hu", "HU");
				break;
			case fi_FI:
				locale = new Locale("fi", "FI");
				break;
			case sv_SE:
				locale = new Locale("sv", "SE");
				break;
			case el_GR:
				locale = new Locale("el", "GR");
				break;
			case ja_JP:
				locale = new Locale("ja", "JP");
				break;
			case zh_HK:
				locale = new Locale("zh", "HK");
				break;
			case ru_RU:
				locale = new Locale("ru", "RU");
				break;
			case cs_CZ:
				locale = new Locale("cs", "CZ");
				break;
			case zh_CN:
				locale = new Locale("zh", "CN");
				break;
			case pl_PL:
				locale = new Locale("pl", "PL");
				break;
			default:
				locale = Locale.US;
			}
			return locale;
		}
	}
	
	/**
	 * The Enum LookAndFeel.
	 */
	public static enum LookAndFeel {
		
		/** The cross. */
		cross
/**
 * Gets the i18 n.
 *
 * @return the i18 n
 */
/* , system */;
		public String getI18N() {
			return I18N.getMsg("msg.pref.laf." + name());
		}
	}
	
	/**
	 * The Enum PreferenceKey.
	 */
	public enum PreferenceKey {
		
/** The storybook version. */
 STORYBOOK_VERSION("StorybookVersion"), 
 /** The last open dir. */
 LAST_OPEN_DIR("LastOpenDir"), 
 /** The last open file. */
 LAST_OPEN_FILE("LastOpenFile"), 
 DEFAULT_DIR("DefaultDir"), 
 /** The last open file. */
 DEFAULT_FILE("DefaultFile"), 
 /** The open last file. */
 OPEN_LAST_FILE("OpenLastFile"), 
 /** The recent files. */
 RECENT_FILES("RecentFiles"), 
 /** The confirm exit. */
 CONFIRM_EXIT("ConfirmExit"), 
 /** The google maps url. */
 GOOGLE_MAPS_URL("GoogleMapsURL"), 
 /** The size width. */
 SIZE_WIDTH("SizeWidth"), 
 /** The size height. */
 SIZE_HEIGHT("SizeHeight"), 
 /** The pos x. */
 POS_X("PosX"), 
 /** The pos y. */
 POS_Y("PosY"), 
 /** The maximized. */
 MAXIMIZED("Maximized"), 
 /** The lang. */
 LANG("language"), 
 /** The dateformat. */
 DATEFORMAT("dateFormat"), 
 /** The spelling. */
 SPELLING("Spelling"), 
 /** The laf. */
 LAF("LookAndFeel"), 
 /** The FIRS t_ star t_4. */
 FIRST_START_4("FirstStart4"), 
 /** The docking layout. */
 DOCKING_LAYOUT("DockingLayout"), 
 /** The default font name. */
 DEFAULT_FONT_NAME("DefaultFontName"), 
 /** The default font size. */
 DEFAULT_FONT_SIZE("DefaultFontSize"), 
 /** The default font style. */
 DEFAULT_FONT_STYLE("DefaultFontStyle"), 
 /** The translator mode. */
 TRANSLATOR_MODE("TranslatorMode"), 
 /** The email. */
 EMAIL("Email"), 
 /** The password. */
 PASSWORD("Password");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new preference key.
		 *
		 * @param text the text
		 */
		private PreferenceKey(String text) {
			this.text = text;
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
	 * The Enum Spelling.
	 */
	public static enum Spelling {
		
		/** The none. */
		none, 
 /** The en_ us. */
 en_US, 
 /** The de_ de. */
 de_DE, 
 /** The es_ es. */
 es_ES, 
 /** The it_ it. */
 it_IT, 
 /** The fr_ fr. */
 fr_FR, 
 /** The ru_ ru. */
 ru_RU, 
 /** The nl_ nl. */
 nl_NL, 
 /** The pl_ pl. */
 pl_PL;
		
		/**
		 * Gets the i18 n.
		 *
		 * @return the i18 n
		 */
		public String getI18N() {
			if (this == none) {
				return I18N.getMsg("msg.pref.spelling.no");
			}
			return I18N.getMsg("msg.common.language." + name());
		}
	}
	
	/**
	 * The Enum SpellingToGet.
	 */
	public static enum SpellingToGet {
		
		/** The en_ us. */
		en_US, 
 /** The de_ de. */
 de_DE, 
 /** The es_ es. */
 es_ES, 
 /** The it_ it. */
 it_IT, 
 /** The fr_ fr. */
 fr_FR, 
 /** The ru_ ru. */
 ru_RU, 
 /** The nl_ nl. */
 nl_NL, 
 /** The pl_ pl. */
 pl_PL;
		
		/**
		 * Gets the i18 n.
		 *
		 * @return the i18 n
		 */
		public String getI18N() {
			// if (this == none)
			// return I18N.getMsg("msg.pref.spelling.no");
			return I18N.getMsg("msg.common.language." + name());
		}
	}
	
	/**
	 * The Enum Storybook.
	 */
	public enum Storybook {
		
		/** The product name. */
		PRODUCT_NAME("oStorybook"), 
 /** The product version. */
 PRODUCT_VERSION("4.10.2"), 
 /** The product full name. */
 PRODUCT_FULL_NAME(PRODUCT_NAME + " " + PRODUCT_VERSION), 
 /** The product release date. */
 PRODUCT_RELEASE_DATE("2016-04-01"), 
 /** The copyright year. */
 COPYRIGHT_YEAR("2012-2013-2014-2015"), 
 /** The copyright company. */
 COPYRIGHT_COMPANY("The Storybook Team"), 
 /** The preference db name. */
 PREFERENCE_DB_NAME("preference"), 
 /** The db version. */
 DB_VERSION("4.0"), 
 /** The db file ext. */
 DB_FILE_EXT(".h2.db"), 
 /** The D b_ fil e_ ex t2. */
 DB_FILE_EXT2(".mv.db"), 
 /** The db config ext. */
 DB_CONFIG_EXT(".cfg.xml"), 
 /** The is beta version. */
 IS_BETA_VERSION("false"), 
 /** The user home dir. */
 USER_HOME_DIR("storybook5");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new storybook.
		 *
		 * @param text the text
		 */
		private Storybook(String text) {
			this.text = text;
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
	 * The Enum URL.
	 */
	public enum URL {
		
		/** The homepage. */
HOMEPAGE("http://ostorybook.tuxfamily.org/"), 
 /** The contact. */
 CONTACT("http://ostorybook.tuxfamily.org/fortopic.php"), 
 /** The doc. */
 DOC(HOMEPAGE + "articles.php?pg=41&mnuid=2&tconfig=2"), 
 /** The faq. */
 FAQ(HOMEPAGE + "faq.php"), 
 /** The reportbug. */
 REPORTBUG(HOMEPAGE + "support"), 
 /** The web. */
 WEB("http://ostorybook.tuxfamily.org/"), 
 /** The version. */
 VERSION(WEB + "Versions.txt"), 
 /** The update. */
 UPDATE(WEB + "Update.zip"), 
 /** The do update. */
 DO_UPDATE("false");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new url.
		 *
		 * @param text the text
		 */
		private URL(String text) {
			this.text = text;
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
	 * The Enum ViewName.
	 */
	public enum ViewName {
		
		/** The scenes. */
 SCENES("Scenes"), 
 /** The chapters. */
 CHAPTERS("Chapters"), 
 /** The parts. */
 PARTS("Parts"), 
 /** The locations. */
 LOCATIONS("Locations"), 
 /** The persons. */
 PERSONS("Persons"), 
 /** The relationships. */
 RELATIONSHIPS("Relationships"), 
 /** The genders. */
 GENDERS("Genders"), 
 /** The categories. */
 CATEGORIES("Categories"), 
 /** The attributes. */
 ATTRIBUTES("Attributes"), 
 /** The strands. */
 STRANDS("Strands"), 
 /** The ideas. */
 IDEAS("Ideas"), 
 /** The tags. */
 TAGS("Tags"), 
 /** The taglinks. */
 TAGLINKS("TagLinks"), 
 /** The items. */
 ITEMS("Items"), 
 /** The itemlinks. */
 ITEMLINKS("ItemLinks"), 
 /** The internals. */
 INTERNALS("Internals"),
								
								/** The chrono. */
								// EDITOR("Editor"),
								CHRONO("Chrono"), 
 /** The book. */
 BOOK("Book"), 
 /** The manage. */
 MANAGE("Manage"), 
 /** The reading. */
 READING("Reading"), 
 /** The memoria. */
 MEMORIA("Memoria"), 
 /** The navigation. */
 NAVIGATION("Navigation"), 
 /** The chart persons by date. */
 CHART_PERSONS_BY_DATE("ChartPersonsByDate"), 
 /** The chart persons by scene. */
 CHART_PERSONS_BY_SCENE("ChartPersonsByScene"), 
 /** The CHAR t_ wi ww. */
 CHART_WiWW("ChartWiWW"), 
 /** The chart strands by date. */
 CHART_STRANDS_BY_DATE("ChartStrandsByDate"), 
 /** The chart occurrence of persons. */
 CHART_OCCURRENCE_OF_PERSONS("ChartOccurrenceOfPersons"), 
 /** The chart occurrence of locations. */
 CHART_OCCURRENCE_OF_LOCATIONS("ChartOccurrenceOfLocations"), 
 /** The chart gantt. */
 CHART_GANTT("ChartGantt"), 
 /** The tree. */
 TREE("Tree"), 
 /** The info. */
 INFO("Info"), 
 /** The memos. */
 MEMOS("Memos"), 
 /** The plan. */
 PLAN("Plan"), 
 /** The timeevent. */
 TIMEEVENT("TimeEvent");
		
		/** The text. */
		private final String text;

		/**
		 * Instantiates a new view name.
		 *
		 * @param text the text
		 */
		private ViewName(String text) {
			this.text = text;
		}

		/**
		 * Compare.
		 *
		 * @param view the view
		 * @return true, if successful
		 */
		public boolean compare(View view) {
			return text.equals(view.getName());
		}

		/** (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return text;
		}
	}
	
	/** The Constant DEFAULT_LANG. */
	public static final String DEFAULT_LANG = "en_US";
	
	/** The Constant DEFAULT_FONT_NAME. */
	public static final String DEFAULT_FONT_NAME = "Dialog";
	
	/** The Constant DEFAULT_FONT_SIZE. */
	public static final int DEFAULT_FONT_SIZE = 12;
	
	/** The Constant DEFAULT_FONT_STYLE. */
	public static final int DEFAULT_FONT_STYLE = 0;
	
	/** The Constant DEFAULT_CHRONO_ZOOM. */
	public static final int DEFAULT_CHRONO_ZOOM = 40;
	
	/** The Constant MIN_CHRONO_ZOOM. */
	public static final int MIN_CHRONO_ZOOM = 20;
	
	/** The Constant MAX_CHRONO_ZOOM. */
	public static final int MAX_CHRONO_ZOOM = 100;
	
	/** The Constant DEFAULT_CHRONO_LAYOUT_DIRECTION. */
	public static final boolean DEFAULT_CHRONO_LAYOUT_DIRECTION = true;
	
	/** The Constant DEFAULT_CHRONO_SHOW_DATE_DIFFERENCE. */
	public static final boolean DEFAULT_CHRONO_SHOW_DATE_DIFFERENCE = false;
	
	/** The Constant DEFAULT_BOOK_ZOOM. */
	public static final int DEFAULT_BOOK_ZOOM = 40;
	
	/** The Constant MIN_BOOK_ZOOM. */
	public static final int MIN_BOOK_ZOOM = 10;
	
	/** The Constant MAX_BOOK_ZOOM. */
	public static final int MAX_BOOK_ZOOM = 200;
	
	/** The Constant DEFAULT_BOOK_HEIGHT_FACTOR. */
	public static final int DEFAULT_BOOK_HEIGHT_FACTOR = 10;
	
	/** The Constant DEFAULT_MANAGE_ZOOM. */
	public static final int DEFAULT_MANAGE_ZOOM = 10;
	
	/** The Constant MIN_MANAGE_ZOOM. */
	public static final int MIN_MANAGE_ZOOM = 1;
	
	/** The Constant MAX_MANAGE_ZOOM. */
	public static final int MAX_MANAGE_ZOOM = 30;
	
	/** The Constant DEFAULT_MANAGE_COLUMNS. */
	public static final int DEFAULT_MANAGE_COLUMNS = 5;
	
	/** The Constant DEFAULT_READING_ZOOM. */
	public static final int DEFAULT_READING_ZOOM = 60;
	
	/** The Constant DEFAULT_READING_FONT_SIZE. */
	public static final int DEFAULT_READING_FONT_SIZE = 11;
	
	/** The Constant DEFAULT_LEAVE_EDITOR_OPEN. */
	public static final boolean DEFAULT_LEAVE_EDITOR_OPEN = false;
	
	/** The Constant DEFAULT_USE_LIBREOFFICE. */
	public static final boolean DEFAULT_USE_LIBREOFFICE = false;
	
	/** The Constant DEFAULT_USE_SIMPLE_TEMPLATE. */
	public static final boolean DEFAULT_USE_SIMPLE_TEMPLATE = false;
	
	/** The Constant DEFAULT_USE_HTML_SCENES. */
	public static final boolean DEFAULT_USE_HTML_SCENES = true;

	/** The Constant DEFAULT_USE_HTML_DESCR. */
	public static final boolean DEFAULT_USE_HTML_DESCR = true;

	/** The Constant DEFAULT_MEMORIA_BALLOON. */
	public static final boolean DEFAULT_MEMORIA_BALLOON = true;

	/** The Constant DEFAULT_EXPORT_CHAPTER_NUMBERS. */
	public static final boolean DEFAULT_EXPORT_CHAPTER_NUMBERS = true;

	/** The Constant DEFAULT_EXPORT_ROMAN_NUMERALS. */
	public static final boolean DEFAULT_EXPORT_ROMAN_NUMERALS = false;;

	/** The Constant DEFAULT_EXPORT_CHAPTER_TITLES. */
	public static final boolean DEFAULT_EXPORT_CHAPTER_TITLES = false;

	/** The Constant DEFAULT_EXPORT_CHAPTER_DATES_LOCATIONS. */
	public static final boolean DEFAULT_EXPORT_CHAPTER_DATES_LOCATIONS = false;

	/** The Constant DEFAULT_EXPORT_SCENE_TITLES. */
	public static final boolean DEFAULT_EXPORT_SCENE_TITLES = false;

	/** The Constant DEFAULT_EXPORT_SCENE_SEPARATOR. */
	public static final boolean DEFAULT_EXPORT_SCENE_SEPARATOR = false;

	/** The Constant DEFAULT_EXPORT_PART_TITLES. */
	public static final boolean DEFAULT_EXPORT_PART_TITLES = false;

	/** The Constant DEFAULT_SIZE_WIDTH. */
	public static final int DEFAULT_SIZE_WIDTH = 1000;

	/** The Constant DEFAULT_SIZE_HEIGHT. */
	public static final int DEFAULT_SIZE_HEIGHT = 700;

	/** The Constant DEFAULT_POS_X. */
	public static final int DEFAULT_POS_X = 100;

	/** The Constant DEFAULT_POS_Y. */
	public static final int DEFAULT_POS_Y = 100;

	/** The Constant DEFAULT_EDITOR_FULL_TOOLBAR. */
	public static final boolean DEFAULT_EDITOR_FULL_TOOLBAR = false;

	/** The Constant DEFAULT_EDITOR_MODLESS. */
	public static final boolean DEFAULT_EDITOR_MODLESS = false;

	/** The Constant DEFAULT_GOOGLE_MAPS_URL. */
	public static final String DEFAULT_GOOGLE_MAPS_URL = "http://maps.google.com";
}
