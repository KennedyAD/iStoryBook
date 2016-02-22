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

package storybook.ui.panel.navigation;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import storybook.SbConstants.ViewName;
import storybook.controller.BookController;
import storybook.model.EntityUtil;
import storybook.model.handler.ChapterEntityHandler;
import storybook.model.hbn.entity.Chapter;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.IconButton;
import storybook.toolkit.swing.SwingUtil;
import storybook.toolkit.swing.panel.ViewsRadioButtonPanel;
import storybook.ui.MainFrame;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class FindChapterPanel.
 *
 * @author martin
 */

public class FindChapterPanel extends AbstractPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7205902018716861163L;
	
	/** The chapter combo. */
	private JComboBox chapterCombo;
	
	/** The views rb panel. */
	private ViewsRadioButtonPanel viewsRbPanel;

	/**
	 * Instantiates a new find chapter panel.
	 *
	 * @param mainFrame the main frame
	 */
	public FindChapterPanel(MainFrame mainFrame) {
		super(mainFrame);
		initAll();
	}

	/**
	 * Gets the find action.
	 *
	 * @return the find action
	 */
	private AbstractAction getFindAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6683469154055891212L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				scrollToChapter();
			}
		};
	}

	/**
	 * Gets the next action.
	 *
	 * @return the next action
	 */
	private AbstractAction getNextAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3154236054992990137L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				int index = chapterCombo.getSelectedIndex();
				++index;
				if (index == chapterCombo.getItemCount()) {
					index = chapterCombo.getItemCount() - 1;
				}
				chapterCombo.setSelectedIndex(index);
				scrollToChapter();
			}
		};
	}

	/**
	 * Gets the previous action.
	 *
	 * @return the previous action
	 */
	private AbstractAction getPreviousAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1667305502207086039L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				int index = chapterCombo.getSelectedIndex();
				--index;
				if (index == -1) {
					index = 0;
				}
				chapterCombo.setSelectedIndex(index);
				scrollToChapter();
			}
		};
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#initUi()
	 */
	@Override
	public void initUi() {
		setLayout(new MigLayout("fillx,wrap 2", "[]10[grow]", "[]10[]10[]"));

		JLabel lbChapter = new JLabel(I18N.getMsgColon("msg.common.chapter"));

		chapterCombo = new JComboBox();
		ChapterEntityHandler handler = new ChapterEntityHandler(mainFrame);
		Chapter chapter = new Chapter();
		EntityUtil.fillEntityCombo(mainFrame, chapterCombo, handler, chapter, false, false);
		SwingUtil.setMaxWidth(chapterCombo, 200);

		IconButton btPrev = new IconButton("icon.small.previous", getPreviousAction());
		btPrev.setSize20x20();

		IconButton btNext = new IconButton("icon.small.next", getNextAction());
		btNext.setSize20x20();

		JLabel lbShow = new JLabel(I18N.getMsgColon("msg.navigation.show.in"));
		viewsRbPanel = new ViewsRadioButtonPanel(mainFrame);

		JButton btFind = new JButton();
		btFind.setAction(getFindAction());
		btFind.setText(I18N.getMsg("msg.common.find"));
		btFind.setIcon(I18N.getIcon("icon.small.search"));
		SwingUtil.addEnterAction(btFind, getFindAction());

		// layout
		add(lbChapter);
		add(chapterCombo, "growx,span 2,split 3");
		add(btPrev);
		add(btNext);
		add(lbShow, "top");
		add(viewsRbPanel);
		add(btFind, "span,right");
	}

	/* (non-Javadoc)
	 * @see storybook.ui.panel.AbstractPanel#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

	/**
	 * Scroll to chapter.
	 */
	private void scrollToChapter() {
		Chapter chapter = (Chapter) chapterCombo.getSelectedItem();
		BookController ctrl = mainFrame.getBookController();
		if (viewsRbPanel.isChronoSelected()) {
			mainFrame.showView(ViewName.CHRONO);
			ctrl.chronoShowEntity(chapter);
			return;
		}
		if (viewsRbPanel.isBookSelected()) {
			mainFrame.showView(ViewName.BOOK);
			ctrl.bookShowEntity(chapter);
			return;
		}
		if (viewsRbPanel.isManageSelected()) {
			mainFrame.showView(ViewName.MANAGE);
			ctrl.manageShowEntity(chapter);
			return;
		}
	}
}
