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

public class SbColumn {

	public enum InputType {
		TEXTFIELD, TEXTAREA, COMBOBOX, CHECKBOX, DATE, COLOR, LIST, ICON, ATTRIBUTES, NONE, SEPARATOR
	}

	private final int colId;
	private final InputType inputType;
	private final String methodName;
	private final String resourceKey;
	private int width = 100;
	private boolean readOnly = false;
	private AbstractInputVerifier verifier = null;
	private boolean showInSeparateTab = false;
	private AbstractCompleter completer = null;
	private boolean hideOnStart = false;
	private boolean hideOnInfo = false;
	private boolean allowNoColor = true;
	private ComboBoxModel comboModel = null;
	private boolean emptyComboItem = false;
	private TableCellRenderer tableCellRenderer = null;
	private Comparator<?> comparator = null;
	private ListCellRenderer listCellRenderer = null;
	private Search search = null;
	private CbPanelDecorator decorator;
	private RadioButtonGroup radioButtonGroup;
	private int radioButtonIndex;
	private boolean growX;
	private boolean autoComplete;
	private String autoCompleteDaoMethod;
	private int maxLength = -1;
	private boolean showDateTime = false;
	private boolean defaultSort = false;
	private int maxChars = 4;

	public SbColumn(int colId, String methodName, InputType inputType, String resourceKey) {
		this.colId = colId;
		this.methodName = methodName;
		this.inputType = inputType;
		this.resourceKey = resourceKey;
	}

	public SbColumn(int colId, String methodName, String resourceKey) {
		this(colId, methodName, InputType.TEXTFIELD, resourceKey);
	}

	public String getAutoCompleteDaoMethod() {
		return autoCompleteDaoMethod;
	}

	public int getColId() {
		return colId;
	}

	public ComboBoxModel getComboModel() {
		return comboModel;
	}

	public Comparator<?> getComparator() {
		return comparator;
	}

	public AbstractCompleter getCompleter() {
		return completer;
	}

	public CbPanelDecorator getDecorator() {
		return decorator;
	}

	public InputType getInputType() {
		return inputType;
	}

	public ListCellRenderer getListCellRenderer() {
		return listCellRenderer;
	}

	public int getMaxChars() {
		return maxChars;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getMethodName() {
		return methodName;
	}

	public RadioButtonGroup getRadioButtonGroup() {
		return radioButtonGroup;
	}

	public int getRadioButtonIndex() {
		return radioButtonIndex;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public Search getSearch() {
		return search;
	}

	public TableCellRenderer getTableCellRenderer() {
		return tableCellRenderer;
	}

	public AbstractInputVerifier getVerifier() {
		return verifier;
	}

	public int getWidth() {
		return width;
	}

	public boolean hasComboModel() {
		return comboModel != null;
	}

	public boolean hasComparator() {
		return comparator != null;
	}

	public boolean hasCompleter() {
		return completer != null;
	}

	public boolean hasDateTime() {
		return showDateTime;
	}

	public boolean hasDecorator() {
		return decorator != null;
	}

	public boolean hasListCellRenderer() {
		return listCellRenderer != null;
	}

	public boolean hasMaxLength() {
		return maxLength > 0;
	}

	public boolean hasRadioButtonGroup() {
		return radioButtonGroup != null;
	}

	public boolean hasSearch() {
		return search != null;
	}

	public boolean hasTableCellRenderer() {
		return tableCellRenderer != null;
	}

	public boolean hasVerifier() {
		return verifier != null;
	}

	public boolean isAllowNoColor() {
		return allowNoColor;
	}

	public boolean isAutoComplete() {
		return autoComplete;
	}

	public boolean isDefaultSort() {
		return defaultSort;
	}

	public boolean isEmptyComboItem() {
		return emptyComboItem;
	}

	public boolean isGrowX() {
		return growX;
	}

	public boolean isHideOnInfo() {
		return hideOnInfo;
	}

	public boolean isHideOnStart() {
		return hideOnStart;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isShowInSeparateTab() {
		return showInSeparateTab;
	}

	public void setAllowNoColor(boolean allowNoColor) {
		this.allowNoColor = allowNoColor;
	}

	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}

	public void setAutoCompleteDaoMethod(String autoCompleteDaoMethod) {
		this.autoCompleteDaoMethod = autoCompleteDaoMethod;
	}

	public void setCbDecorator(CbPanelDecorator decorator) {
		this.decorator = decorator;
	}

	public void setComboModel(ComboBoxModel comboModel) {
		this.comboModel = comboModel;
	}

	public void setComparator(Comparator<?> comparator) {
		this.comparator = comparator;
	}

	public void setCompleter(AbstractCompleter completer) {
		this.completer = completer;
	}

	public void setDefaultSort(boolean defaultSort) {
		this.defaultSort = defaultSort;
	}

	public void setEmptyComboItem(boolean emptyComboItem) {
		this.emptyComboItem = emptyComboItem;
	}

	public void setGrowX(boolean growX) {
		this.growX = growX;
	}

	public void setHideOnInfo(boolean hideOnInfo) {
		this.hideOnInfo = hideOnInfo;
	}

	public void setHideOnStart(boolean hideOnStart) {
		this.hideOnStart = hideOnStart;
	}

	public void setListCellRenderer(DefaultListCellRenderer listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}

	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setRadioButtonGroup(RadioButtonGroup radioButtonGroup) {
		this.radioButtonGroup = radioButtonGroup;
	}

	public void setRadioButtonIndex(int radioButtonIndex) {
		this.radioButtonIndex = radioButtonIndex;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public void setShowDateTime(boolean showDateTime) {
		this.showDateTime = showDateTime;
	}

	public void setShowInSeparateTab(boolean showInSeparateTab) {
		this.showInSeparateTab = showInSeparateTab;
	}

	public void setTableCellRenderer(TableCellRenderer renderer) {
		this.tableCellRenderer = renderer;
	}

	public void setVerifier(AbstractInputVerifier verifier) {
		this.verifier = verifier;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		if ((resourceKey != null) && (!resourceKey.isEmpty())) {
			return I18N.getMsg(resourceKey);
		} else {
			return "";
		}
	}
}
