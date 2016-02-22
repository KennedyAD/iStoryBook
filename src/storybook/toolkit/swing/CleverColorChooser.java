/*
CleverColorChooser: Clever Color Chooser with "no color" and
color palette (both optional).

This class needs MigLayout to compile:
http://www.miglayout.com/

Copyright (C) 2008-2009 Martin Mustun

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

package storybook.toolkit.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import storybook.ui.interfaces.IRefreshable;


// TODO: Auto-generated Javadoc
/**
 * The Class CleverColorChooser.
 */
public class CleverColorChooser extends JPanel implements IRefreshable, MouseListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3215365287209028120L;

	/**
	 * The Class ColorLabel.
	 */
	private class ColorLabel extends JLabel {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 7299513480525524900L;

		// public ColorLabel() {
		// this(null);
		// }

		/**
		 * Instantiates a new color label.
		 *
		 * @param color the color
		 */
		public ColorLabel(Color color) {
			super("", SwingConstants.CENTER);
			Dimension dim = new Dimension(50, 20);
			setPreferredSize(dim);
			setMinimumSize(dim);
			setMaximumSize(dim);
			setOpaque(true);
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBackground(color);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#setBackground(java.awt.Color)
		 */
		@Override
		public void setBackground(Color bg) {
			super.setBackground(bg);
			if (bg == null) {
				setText("X");
			} else {
				setText("");
			}
		}
	}

	/**
	 * The Class ColorsPanel.
	 */
	private class ColorsPanel extends JPanel implements MouseListener {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4673345291752905659L;

		/**
		 * Instantiates a new colors panel.
		 */
		public ColorsPanel() {
			MigLayout layout = new MigLayout("insets 1,wrap 4", "[20]", "[20]");
			setLayout(layout);

			for (Color color : colors) {
				JLabel label = new JLabel();
				label.setOpaque(true);
				label.setBackground(color);
				label.setFocusable(true);
				label.addMouseListener(this);
				label.addMouseListener(getThis());
				add(label, "grow");
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent evt) {
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent evt) {
			JComponent comp = (JComponent) evt.getSource();
			Color color = comp.getBackground();
			Color borderColor;
			if (ColorUtil.isDark(color)) {
				borderColor = Color.white;
			} else {
				borderColor = Color.black;
			}
			comp.setBorder(BorderFactory.createLineBorder(borderColor, 2));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent evt) {
			JComponent comp = (JComponent) evt.getSource();
			comp.setBorder(null);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent evt) {
			JLabel label = (JLabel) evt.getSource();
			Color color = label.getBackground();
			lbShowColor.setBackground(color);
			lbShowColor.setText("");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	/** The Constant COMP_NAME_BT_PALETTE. */
	public static final String COMP_NAME_BT_PALETTE = "bt:palette";
	
	/** The bt palette. */
	private IconButton btPalette;
	
	/** The bt color chooser. */
	private JButton btColorChooser;
	
	/** The bt clear color. */
	private IconButton btClearColor;
	
	/** The lb show color. */
	private ColorLabel lbShowColor;
	
	/** The colors. */
	private Color[] colors;
	
	/** The allow no color. */
	private boolean allowNoColor;

	/** The start color. */
	private Color startColor;

	/** The title. */
	private String title;

	/**
	 * Instantiates a new clever color chooser.
	 */
	public CleverColorChooser() {
		this("", null, null, true);
	}

	/**
	 * Instantiates a new clever color chooser.
	 *
	 * @param title the title
	 * @param startColor the start color
	 * @param colors the colors
	 * @param allowNoColor the allow no color
	 */
	public CleverColorChooser(String title, Color startColor, Color[] colors, boolean allowNoColor) {
		this.title = title;
		this.startColor = startColor;
		this.colors = colors;
		this.allowNoColor = allowNoColor;
		initGUI();
	}

	/**
	 * Creates the palette popup menu.
	 *
	 * @return the j popup menu
	 */
	private JPopupMenu createPalettePopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new ColorsPanel());
		return menu;
	}

	/**
	 * Gets the clear color action.
	 *
	 * @return the clear color action
	 */
	private AbstractAction getClearColorAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3931998229081965151L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				lbShowColor.setBackground(null);
			}
		};
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		if (!lbShowColor.getText().isEmpty()) {
			return null;
		}
		return lbShowColor.getBackground();
	}

	/**
	 * Gets the show color chooser action.
	 *
	 * @return the show color chooser action
	 */
	private AbstractAction getShowColorChooserAction() {
		return new AbstractAction(title) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2428240683262061801L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				Component parent = getThis().getParent();
				if (parent == null) {
					parent = getThis();
				}
				Color color = JColorChooser.showDialog(parent, title, lbShowColor.getBackground());
				if (color != null) {
					lbShowColor.setBackground(color);
				}
			}
		};
	}

	/**
	 * Gets the show palette popup action.
	 *
	 * @return the show palette popup action
	 */
	private AbstractAction getShowPalettePopupAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4512690047082392202L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				JComponent comp = (JComponent) evt.getSource();
				JPopupMenu menu = createPalettePopupMenu();
				menu.show(comp, 10, 10);
			}
		};
	}

	/**
	 * Returns its self for use within anonymous objects that require references
	 * to this object without being able to use <code>this</code> keyword.
	 *
	 * @return the this
	 */
	protected CleverColorChooser getThis() {
		return this;
	}

	/**
	 * Inits the gui.
	 */
	private void initGUI() {
		MigLayout layout = new MigLayout("insets 0");
		setLayout(layout);

		// shows the selected color
		lbShowColor = new ColorLabel(startColor);

		// custom palette
		if (colors != null) {
			btPalette = new IconButton("icon.small.palette", getShowPalettePopupAction());
			btPalette.setSize20x20();
			btPalette.setName(COMP_NAME_BT_PALETTE);
			lbShowColor.setComponentPopupMenu(createPalettePopupMenu());
		}

		// the color chooser
		btColorChooser = new JButton();
		btColorChooser.setAction(getShowColorChooserAction());

		// button to clear the color
		if (allowNoColor) {
			btClearColor = new IconButton("icon.small.delete", getClearColorAction());
			btClearColor.setSize20x20();
		}

		add(lbShowColor);
		if (colors != null) {
			add(btPalette);
		}
		if (allowNoColor) {
			add(btClearColor);
		}
		add(btColorChooser);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mousePressed(MouseEvent evt) {
		Object source = evt.getSource();
		if (source instanceof JLabel) {
			JComponent comp = (JComponent) source;
			JComponent parent1 = (JComponent) comp.getParent();
			JComponent parent2 = (JComponent) parent1.getParent();
			JPopupMenu menu = (JPopupMenu) parent2;
			menu.setVisible(false);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.interfaces.IRefreshable#refresh()
	 */
	@Override
	public void refresh() {
		removeAll();
		initGUI();
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		lbShowColor.setBackground(color);
	}
}
