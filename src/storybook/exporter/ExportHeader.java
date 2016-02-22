/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storybook.exporter;

// TODO: Auto-generated Javadoc
/**
 * The Class ExportHeader.
 *
 * @author favdb
 */
public class ExportHeader {
	
	/** The name. */
	private String name;
	
	/** The size. */
	private int size;

	/**
	 * Instantiates a new export header.
	 *
	 * @param n the n
	 * @param s the s
	 */
	ExportHeader(String n, int s) {
		name = n;
		size = s;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
}
