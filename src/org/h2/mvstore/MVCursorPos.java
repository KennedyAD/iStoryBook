/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.mvstore;

/**
 * A position in a cursor
 */
public class MVCursorPos {

    /**
     * The current page.
     */
    public MVPage page;

    /**
     * The current index.
     */
    public int index;

    /**
     * The position in the parent page, if any.
     */
    public final MVCursorPos parent;

    public MVCursorPos(MVPage page, int index, MVCursorPos parent) {
        this.page = page;
        this.index = index;
        this.parent = parent;
    }

}

