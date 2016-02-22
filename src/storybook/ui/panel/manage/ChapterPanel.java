/*
Storybook: Scene-based software for novelists and authors.
Copyright (C) 2008 - 2011 Martin Mustun

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

package storybook.ui.panel.manage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.hibernate.Session;

import net.miginfocom.swing.MigLayout;
import storybook.SbApp;
import storybook.SbConstants;
import storybook.SbConstants.BookKey;
import storybook.action.EditEntityAction;
import storybook.controller.BookController;
import storybook.model.BookModel;
import storybook.model.EntityUtil;
import storybook.model.hbn.dao.ChapterDAOImpl;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Internal;
import storybook.model.hbn.entity.Scene;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.label.VerticalLabelUI;
import storybook.ui.MainFrame;
import storybook.ui.interfaces.IRefreshable;
import storybook.ui.panel.AbstractPanel;
import storybook.ui.panel.manage.dnd.DTScenePanel;
import storybook.ui.panel.manage.dnd.ScenePanel;
import storybook.ui.panel.manage.dnd.SceneTransferHandler;

// TODO: Auto-generated Javadoc
/*
Ajout de l'ouverture de l'éditeur sur double click sur le titre du chapitre
*/


/**
 * The Class ChapterPanel.
 */
public class ChapterPanel extends AbstractPanel implements MouseListener, IRefreshable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9124772447240362592L;
	
	/** The chapter. */
	private Chapter chapter;
	
	/** The scene transfer handler. */
	private SceneTransferHandler sceneTransferHandler;
	
	/** The pref width. */
	private int prefWidth;

	/**
	 * Instantiates a new chapter panel.
	 *
	 * @param mainFrame the main frame
	 */
	public ChapterPanel(MainFrame mainFrame) {
		this(mainFrame, null);
	}

	/**
	 * Instantiates a new chapter panel.
	 *
	 * @param mainFrame the main frame
	 * @param chapter the chapter
	 */
	public ChapterPanel(MainFrame mainFrame, Chapter chapter) {
		super(mainFrame);
		this.chapter = chapter;
		initAll();
	}

	/**
	 * Gets the chapter.
	 *
	 * @return the chapter
	 */
	public Chapter getChapter() {
		return chapter;
	}

	/**
	 * Gets all {@link DTScenePanel} that have a scene assigned.
	 *
	 * @return a list of all {@link DTScenePanel}
	 * @see DTScenePanel
	 */
	public List<DTScenePanel> getDTScenePanels() {
		List<DTScenePanel> list = new ArrayList<DTScenePanel>();
		for (Component comp : getComponents()) {
			if (comp instanceof DTScenePanel && ((DTScenePanel) comp).getScene() != null) {
				list.add((DTScenePanel) comp);
			}
		}
		return list;
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	protected ChapterPanel getThis() {
		return this;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
		SbApp.trace("ChapterPanel.init()");
		try {
			Internal internal = BookUtil.get(mainFrame, BookKey.MANAGE_ZOOM, SbConstants.DEFAULT_MANAGE_ZOOM);
			setZoomedSize(internal.getIntegerValue());
		} catch (Exception e) {
			e.printStackTrace();
			setZoomedSize(SbConstants.DEFAULT_MANAGE_ZOOM);
		}
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		SbApp.trace("ChapterPanel.initUI()");
		MigLayout layout;
		if (isForUnassignedScene()) {
			layout = new MigLayout("flowx", "[]", // columns
					"[fill]" // rows
			);
		} else {
			layout = new MigLayout("flowy", "[]", "[]4[]0[]");
		}
		setLayout(layout);
		setBorder(SwingUtil.getBorderDefault());
		if (!isForUnassignedScene()) {
			setPreferredSize(new Dimension(prefWidth, 80));
		}
		setComponentPopupMenu(EntityUtil.createPopupMenu(mainFrame, chapter));

		JLabel lbChapter = new JLabel();
		StringBuilder buf = new StringBuilder();
		if (chapter == null) {
			buf.append(I18N.getMsg("msg.unassigned.scenes"));
			lbChapter.setUI(new VerticalLabelUI(false));
		} else {
			buf.append(chapter.getChapternoStr());
			buf.append(" ");
			buf.append(chapter.getTitle());
		}
		lbChapter.setVerticalAlignment(SwingConstants.TOP);
		lbChapter.setText(buf.toString());
		lbChapter.addMouseListener(this);
		add(lbChapter);

		sceneTransferHandler = new SceneTransferHandler(mainFrame);

		BookModel model = mainFrame.getBookModel();
		Session session = model.beginTransaction();
		ChapterDAOImpl dao = new ChapterDAOImpl(session);
		List<Scene> scenes = dao.findScenes(chapter);
		model.commit();
		if (chapter == null) {
			// show all unassigned scenes
			for (Scene scene : scenes) {
				DTScenePanel dtScene = new DTScenePanel(mainFrame, scene, ScenePanel.TYPE_UNASSIGNED);
				dtScene.setTransferHandler(sceneTransferHandler);
				SwingUtil.setForcedSize(dtScene, new Dimension(prefWidth - 10, 140));
				add(dtScene, "growy");
			}
			// to make a scene unassigned
			DTScenePanel makeUnassigned = new DTScenePanel(mainFrame, ScenePanel.TYPE_MAKE_UNASSIGNED);
			makeUnassigned.setTransferHandler(sceneTransferHandler);
			makeUnassigned.setPreferredSize(new Dimension(280, 140));
			add(makeUnassigned, "grow");
		} else {
			DTScenePanel begin = new DTScenePanel(mainFrame, ScenePanel.TYPE_BEGIN);
			begin.setTransferHandler(sceneTransferHandler);
			if (scenes.isEmpty()) {
				SwingUtil.setMaxPreferredSize(begin);
			} else {
				begin.setPreferredSize(new Dimension(Short.MAX_VALUE, 15));
			}
			add(begin);

			int i = 0;
			for (Scene scene : scenes) {
				// scene
				DTScenePanel dtScene = new DTScenePanel(mainFrame, scene);
				dtScene.setTransferHandler(sceneTransferHandler);
				add(dtScene, "growx");

				// move next
				DTScenePanel next = new DTScenePanel(mainFrame, ScenePanel.TYPE_NEXT);
				if (scene.getSceneno() != null) {
					next.setPreviousNumber(scene.getSceneno());
				}
				next.setTransferHandler(sceneTransferHandler);
				if (i < scenes.size() - 1) {
					next.setPreferredSize(new Dimension(Short.MAX_VALUE, 15));
				} else {
					SwingUtil.setMaxPreferredSize(next);
				}
				add(next);
				++i;
			}
		}
	}

	/**
	 * Checks if is for unassigned scene.
	 *
	 * @return true, if is for unassigned scene
	 */
	public boolean isForUnassignedScene() {
		return chapter == null;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		Object newValue = evt.getNewValue();
		Object oldValue = evt.getOldValue();
		String propName = evt.getPropertyName();

		if (BookController.ManageViewProps.ZOOM.check(propName)) {
			refresh();
			return;
		}

		if (BookController.StrandProps.UPDATE.check(propName)) {
			refresh();
			return;
		}

		if (BookController.ChapterProps.UPDATE.check(propName)) {
			if (chapter == null) {
				return;
			}
			Chapter newChapter = (Chapter) newValue;
			if (!newChapter.getId().equals(chapter.getId())) {
				return;
			}
			chapter = newChapter;
			refresh();
			return;
		}

		if (BookController.SceneProps.UPDATE.check(propName)) {
			Chapter newSceneChapter = ((Scene) newValue).getChapter();
			Chapter oldSceneChapter = ((Scene) oldValue).getChapter();
			if (newSceneChapter == null && chapter == null) {
				refresh();
				return;
			}
			if (chapter == null || newSceneChapter == null || oldSceneChapter == null) {
				refresh();
				return;
			}
			if (!newSceneChapter.getId().equals(chapter.getId()) && !oldSceneChapter.getId().equals(chapter.getId())) {
				return;
			}
			refresh();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (getChapter() == null) {
			return;
		}
		requestFocusInWindow();
		if (e.getClickCount() == 2) {
			EditEntityAction act = new EditEntityAction(mainFrame, getChapter(), false);
			act.actionPerformed(null);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Sets the zoomed size.
	 *
	 * @param zoomValue the new zoomed size
	 */
	private void setZoomedSize(int zoomValue) {
		prefWidth = 50 + zoomValue * 10;
	}
}
