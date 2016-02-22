/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportData.
 *
 * @author favdb
 */
public class ExportData {
	
	/** The report name. */
	private String reportName;
	
	/** The resource key. */
	private String resourceKey;

	/**
	 * Instantiates a new export data.
	 */
	public ExportData() {
	}

	/**
	 * Instantiates a new export data.
	 *
	 * @param reportName the report name
	 * @param key the key
	 */
	public ExportData(String reportName, String key) {
		this.reportName = reportName;
		resourceKey = I18N.getMsg(key);
	}

	/**
	 * Gets the export name.
	 *
	 * @return the export name
	 */
	public String getExportName() {
		return reportName;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return resourceKey;
	}

	/**
	 * Sets the export name.
	 *
	 * @param reportName the new export name
	 */
	public void setExportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		resourceKey = key;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getKey();
	}

}
