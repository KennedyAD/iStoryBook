package storybook.ui.table;

import java.util.Comparator;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.googlecode.genericdao.search.Search;

import storybook.toolkit.I18N;
import storybook.toolkit.completer.AbstractCompleter;
import storybook.toolkit.swing.verifier.AbstractInputVerifier;
import storybook.ui.RadioButtonGroup;
import storybook.ui.edit.CbPanelDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class SbColumn.
 */
public class SbColumn {

	/**
	 * The Enum InputType.
	 */
	public enum InputType {
		
		/** The textfield. */
		TEXTFIELD, 
 /** The textarea. */
 TEXTAREA, 
 /** The combobox. */
 COMBOBOX, 
 /** The checkbox. */
 CHECKBOX, 
 /** The date. */
 DATE, 
 /** The color. */
 COLOR, 
 /** The list. */
 LIST, 
 /** The icon. */
 ICON, 
 /** The attributes. */
 ATTRIBUTES, 
 /** The none. */
 NONE, 
 /** The separator. */
 SEPARATOR
	}

	/** The col id. */
	private final int colId;
	
	/** The input type. */
	private final InputType inputType;
	
	/** The method name. */
	private final String methodName;
	
	/** The resource key. */
	private final String resourceKey;
	
	/** The width. */
	private int width = 100;
	
	/** The read only. */
	private boolean readOnly = false;
	
	/** The verifier. */
	private AbstractInputVerifier verifier = null;
	
	/** The show in separate tab. */
	private boolean showInSeparateTab = false;
	
	/** The completer. */
	private AbstractCompleter completer = null;
	
	/** The hide on start. */
	private boolean hideOnStart = false;
	
	/** The hide on info. */
	private boolean hideOnInfo = false;
	
	/** The allow no color. */
	private boolean allowNoColor = true;
	
	/** The combo model. */
	private ComboBoxModel comboModel = null;
	
	/** The empty combo item. */
	private boolean emptyComboItem = false;
	
	/** The table cell renderer. */
	private TableCellRenderer tableCellRenderer = null;
	
	/** The comparator. */
	private Comparator<?> comparator = null;
	
	/** The list cell renderer. */
	private ListCellRenderer listCellRenderer = null;
	
	/** The search. */
	private Search search = null;
	
	/** The decorator. */
	private CbPanelDecorator decorator;
	
	/** The radio button group. */
	private RadioButtonGroup radioButtonGroup;
	
	/** The radio button index. */
	private int radioButtonIndex;
	
	/** The grow x. */
	private boolean growX;
	
	/** The auto complete. */
	private boolean autoComplete;
	
	/** The auto complete dao method. */
	private String autoCompleteDaoMethod;
	
	/** The max length. */
	private int maxLength = -1;
	
	/** The show date time. */
	private boolean showDateTime = false;
	
	/** The default sort. */
	private boolean defaultSort = false;
	
	/** The max chars. */
	private int maxChars = 4;

	/**
	 * Instantiates a new sb column.
	 *
	 * @param colId the col id
	 * @param methodName the method name
	 * @param inputType the input type
	 * @param resourceKey the resource key
	 */
	public SbColumn(int colId, String methodName, InputType inputType, String resourceKey) {
		this.colId = colId;
		this.methodName = methodName;
		this.inputType = inputType;
		this.resourceKey = resourceKey;
	}

	/**
	 * Instantiates a new sb column.
	 *
	 * @param colId the col id
	 * @param methodName the method name
	 * @param resourceKey the resource key
	 */
	public SbColumn(int colId, String methodName, String resourceKey) {
		this(colId, methodName, InputType.TEXTFIELD, resourceKey);
	}

	/**
	 * Gets the auto complete dao method.
	 *
	 * @return the auto complete dao method
	 */
	public String getAutoCompleteDaoMethod() {
		return autoCompleteDaoMethod;
	}

	/**
	 * Gets the col id.
	 *
	 * @return the col id
	 */
	public int getColId() {
		return colId;
	}

	/**
	 * Gets the combo model.
	 *
	 * @return the combo model
	 */
	public ComboBoxModel getComboModel() {
		return comboModel;
	}

	/**
	 * Gets the comparator.
	 *
	 * @return the comparator
	 */
	public Comparator<?> getComparator() {
		return comparator;
	}

	/**
	 * Gets the completer.
	 *
	 * @return the completer
	 */
	public AbstractCompleter getCompleter() {
		return completer;
	}

	/**
	 * Gets the decorator.
	 *
	 * @return the decorator
	 */
	public CbPanelDecorator getDecorator() {
		return decorator;
	}

	/**
	 * Gets the input type.
	 *
	 * @return the input type
	 */
	public InputType getInputType() {
		return inputType;
	}

	/**
	 * Gets the list cell renderer.
	 *
	 * @return the list cell renderer
	 */
	public ListCellRenderer getListCellRenderer() {
		return listCellRenderer;
	}

	/**
	 * Gets the max chars.
	 *
	 * @return the max chars
	 */
	public int getMaxChars() {
		return maxChars;
	}

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Gets the radio button group.
	 *
	 * @return the radio button group
	 */
	public RadioButtonGroup getRadioButtonGroup() {
		return radioButtonGroup;
	}

	/**
	 * Gets the radio button index.
	 *
	 * @return the radio button index
	 */
	public int getRadioButtonIndex() {
		return radioButtonIndex;
	}

	/**
	 * Gets the resource key.
	 *
	 * @return the resource key
	 */
	public String getResourceKey() {
		return resourceKey;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public Search getSearch() {
		return search;
	}

	/**
	 * Gets the table cell renderer.
	 *
	 * @return the table cell renderer
	 */
	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

	/**
	 * Gets the verifier.
	 *
	 * @return the verifier
	 */
	public AbstractInputVerifier getVerifier() {
		return verifier;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Checks for combo model.
	 *
	 * @return true, if successful
	 */
	public boolean hasComboModel() {
		return comboModel != null;
	}

	/**
	 * Checks for comparator.
	 *
	 * @return true, if successful
	 */
	public boolean hasComparator() {
		return comparator != null;
	}

	/**
	 * Checks for completer.
	 *
	 * @return true, if successful
	 */
	public boolean hasCompleter() {
		return completer != null;
	}

	/**
	 * Checks for date time.
	 *
	 * @return true, if successful
	 */
	public boolean hasDateTime() {
		return showDateTime;
	}

	/**
	 * Checks for decorator.
	 *
	 * @return true, if successful
	 */
	public boolean hasDecorator() {
		return decorator != null;
	}

	/**
	 * Checks for list cell renderer.
	 *
	 * @return true, if successful
	 */
	public boolean hasListCellRenderer() {
		return listCellRenderer != null;
	}

	/**
	 * Checks for max length.
	 *
	 * @return true, if successful
	 */
	public boolean hasMaxLength() {
		return maxLength > 0;
	}

	/**
	 * Checks for radio button group.
	 *
	 * @return true, if successful
	 */
	public boolean hasRadioButtonGroup() {
		return radioButtonGroup != null;
	}

	/**
	 * Checks for search.
	 *
	 * @return true, if successful
	 */
	public boolean hasSearch() {
		return search != null;
	}

	/**
	 * Checks for table cell renderer.
	 *
	 * @return true, if successful
	 */
	public boolean hasTableCellRenderer() {
		return tableCellRenderer != null;
	}

	/**
	 * Checks for verifier.
	 *
	 * @return true, if successful
	 */
	public boolean hasVerifier() {
		return verifier != null;
	}

	/**
	 * Checks if is allow no color.
	 *
	 * @return true, if is allow no color
	 */
	public boolean isAllowNoColor() {
		return allowNoColor;
	}

	/**
	 * Checks if is auto complete.
	 *
	 * @return true, if is auto complete
	 */
	public boolean isAutoComplete() {
		return autoComplete;
	}

	/**
	 * Checks if is default sort.
	 *
	 * @return true, if is default sort
	 */
	public boolean isDefaultSort() {
		return defaultSort;
	}

	/**
	 * Checks if is empty combo item.
	 *
	 * @return true, if is empty combo item
	 */
	public boolean isEmptyComboItem() {
		return emptyComboItem;
	}

	/**
	 * Checks if is grow x.
	 *
	 * @return true, if is grow x
	 */
	public boolean isGrowX() {
		return growX;
	}

	/**
	 * Checks if is hide on info.
	 *
	 * @return true, if is hide on info
	 */
	public boolean isHideOnInfo() {
		return hideOnInfo;
	}

	/**
	 * Checks if is hide on start.
	 *
	 * @return true, if is hide on start
	 */
	public boolean isHideOnStart() {
		return hideOnStart;
	}

	/**
	 * Checks if is read only.
	 *
	 * @return true, if is read only
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Checks if is show in separate tab.
	 *
	 * @return true, if is show in separate tab
	 */
	public boolean isShowInSeparateTab() {
		return showInSeparateTab;
	}

	/**
	 * Sets the allow no color.
	 *
	 * @param allowNoColor the new allow no color
	 */
	public void setAllowNoColor(boolean allowNoColor) {
		this.allowNoColor = allowNoColor;
	}

	/**
	 * Sets the auto complete.
	 *
	 * @param autoComplete the new auto complete
	 */
	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}

	/**
	 * Sets the auto complete dao method.
	 *
	 * @param autoCompleteDaoMethod the new auto complete dao method
	 */
	public void setAutoCompleteDaoMethod(String autoCompleteDaoMethod) {
		this.autoCompleteDaoMethod = autoCompleteDaoMethod;
	}

	/**
	 * Sets the cb decorator.
	 *
	 * @param decorator the new cb decorator
	 */
	public void setCbDecorator(CbPanelDecorator decorator) {
		this.decorator = decorator;
	}

	/**
	 * Sets the combo model.
	 *
	 * @param comboModel the new combo model
	 */
	public void setComboModel(ComboBoxModel comboModel) {
		this.comboModel = comboModel;
	}

	/**
	 * Sets the comparator.
	 *
	 * @param comparator the new comparator
	 */
	public void setComparator(Comparator<?> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Sets the completer.
	 *
	 * @param completer the new completer
	 */
	public void setCompleter(AbstractCompleter completer) {
		this.completer = completer;
	}

	/**
	 * Sets the default sort.
	 *
	 * @param defaultSort the new default sort
	 */
	public void setDefaultSort(boolean defaultSort) {
		this.defaultSort = defaultSort;
	}

	/**
	 * Sets the empty combo item.
	 *
	 * @param emptyComboItem the new empty combo item
	 */
	public void setEmptyComboItem(boolean emptyComboItem) {
		this.emptyComboItem = emptyComboItem;
	}

	/**
	 * Sets the grow x.
	 *
	 * @param growX the new grow x
	 */
	public void setGrowX(boolean growX) {
		this.growX = growX;
	}

	/**
	 * Sets the hide on info.
	 *
	 * @param hideOnInfo the new hide on info
	 */
	public void setHideOnInfo(boolean hideOnInfo) {
		this.hideOnInfo = hideOnInfo;
	}

	/**
	 * Sets the hide on start.
	 *
	 * @param hideOnStart the new hide on start
	 */
	public void setHideOnStart(boolean hideOnStart) {
		this.hideOnStart = hideOnStart;
	}

	/**
	 * Sets the list cell renderer.
	 *
	 * @param listCellRenderer the new list cell renderer
	 */
	public void setListCellRenderer(DefaultListCellRenderer listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}

	/**
	 * Sets the max chars.
	 *
	 * @param maxChars the new max chars
	 */
	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}

	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Sets the radio button group.
	 *
	 * @param radioButtonGroup the new radio button group
	 */
	public void setRadioButtonGroup(RadioButtonGroup radioButtonGroup) {
		this.radioButtonGroup = radioButtonGroup;
	}

	/**
	 * Sets the radio button index.
	 *
	 * @param radioButtonIndex the new radio button index
	 */
	public void setRadioButtonIndex(int radioButtonIndex) {
		this.radioButtonIndex = radioButtonIndex;
	}

	/**
	 * Sets the read only.
	 *
	 * @param readOnly the new read only
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the new search
	 */
	public void setSearch(Search search) {
		this.search = search;
	}

	/**
	 * Sets the show date time.
	 *
	 * @param showDateTime the new show date time
	 */
	public void setShowDateTime(boolean showDateTime) {
		this.showDateTime = showDateTime;
	}

	/**
	 * Sets the show in separate tab.
	 *
	 * @param showInSeparateTab the new show in separate tab
	 */
	public void setShowInSeparateTab(boolean showInSeparateTab) {
		this.showInSeparateTab = showInSeparateTab;
	}

	/**
	 * Sets the table cell renderer.
	 *
	 * @param renderer the new table cell renderer
	 */
	public void setTableCellRenderer(TableCellRenderer renderer) {
		this.tableCellRenderer = renderer;
	}

	/**
	 * Sets the verifier.
	 *
	 * @param verifier the new verifier
	 */
	public void setVerifier(AbstractInputVerifier verifier) {
		this.verifier = verifier;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if ((resourceKey != null) && (!resourceKey.isEmpty())) {
			return I18N.getMsg(resourceKey);
		} else {
			return "";
		}
	}
}
