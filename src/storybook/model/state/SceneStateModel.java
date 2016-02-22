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

package storybook.model.state;

import storybook.toolkit.I18N;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneStateModel.
 *
 * @author martin
 */
public class SceneStateModel extends AbstractStateModel {

	/**
	 * The Enum State.
	 */
	public enum State {
		
		/** The dummy. */
		DUMMY, 
 /** The outline. */
 OUTLINE, 
 /** The draft. */
 DRAFT, 
 /** The EDI t1. */
 EDIT1, 
 /** The EDI t2. */
 EDIT2, 
 /** The done. */
 DONE, 
 /** The in progress. */
 IN_PROGRESS, 
 /** The all. */
 ALL
	}

	/**
	 * Instantiates a new scene state model.
	 */
	public SceneStateModel() {
		this(true);
	}

	/**
	 * Instantiates a new scene state model.
	 *
	 * @param addPseudoStates the add pseudo states
	 */
	public SceneStateModel(boolean addPseudoStates) {
		super();
		states.add(new SceneState(State.OUTLINE.ordinal(), I18N.getMsg("msg.status.outline"),
				I18N.getIcon("icon.small.status.outline")));
		states.add(new SceneState(State.DRAFT.ordinal(), I18N.getMsg("msg.status.draft"),
				I18N.getIcon("icon.small.status.draft")));
		states.add(new SceneState(State.EDIT1.ordinal(), I18N.getMsg("msg.status.1st.edit"),
				I18N.getIcon("icon.small.status.edit1")));
		states.add(new SceneState(State.EDIT2.ordinal(), I18N.getMsg("msg.status.2nd.edit"),
				I18N.getIcon("icon.small.status.edit2")));
		states.add(new SceneState(State.DONE.ordinal(), I18N.getMsg("msg.status.done"),
				I18N.getIcon("icon.small.status.done")));
		if (addPseudoStates) {
			states.add(new SceneState(State.IN_PROGRESS.ordinal(), I18N.getMsg("msg.status.in.progress"),
					I18N.getIcon("icon.small.status.inprogess")));
			states.add(new SceneState(State.ALL.ordinal(), I18N.getMsg("msg.status.all"),
					I18N.getIcon("icon.small.status.all")));
		}
	}
}
