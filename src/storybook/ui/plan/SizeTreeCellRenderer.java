package storybook.ui.plan;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import storybook.model.EntityUtil;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Scene;


// TODO: Auto-generated Javadoc
/**
 * The Class SizeTreeCellRenderer.
 */
public class SizeTreeCellRenderer extends JPanel implements TreeCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9088112521225314888L;
	
	/** The Constant BARSIZE. */
	private static final int BARSIZE = 200;
	
	/** The Constant DELTA. */
	private static final int DELTA = 20;

	/** The maxval. */
	private int maxval;
	
	/** The currentval. */
	private int currentval;
	
	/** The text label. */
	private JLabel textLabel;
	
	/** The percent label. */
	private JLabel percentLabel;

	/**
	 * Instantiates a new size tree cell renderer.
	 */
	public SizeTreeCellRenderer() {
		setLayout(null);
		setBackground(UIManager.getColor("Tree.textBackground"));
		textLabel = new JLabel("Test");
		textLabel.setFont(UIManager.getFont("Tree.font"));
		add(textLabel);
		percentLabel = new JLabel("Test");
		percentLabel.setFont(UIManager.getFont("Tree.font"));
		add(percentLabel);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension size = textLabel.getPreferredSize();
		if (maxval > 0) {
			size.width += BARSIZE + 2 * DELTA + percentLabel.getPreferredSize().width;
		}
		return size;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object userObject = node.getUserObject();
		textLabel.setText(userObject.toString());

		maxval = -1;
		int percent = 0;
		if (userObject instanceof SizedElement) {
			currentval = ((SizedElement) userObject).getSize();
			maxval = -1;
			Object obj = ((SizedElement) userObject).getElement();
			percentLabel.setVisible(false);
			if (obj instanceof Scene) {
				Scene scene = (Scene) obj;
				textLabel.setIcon(scene.getSceneState().getIcon());
			} else if ((leaf) && (obj instanceof AbstractEntity)) {
				Icon icon = EntityUtil.getEntityIcon((AbstractEntity) obj);
				textLabel.setIcon(icon);
			} else if (obj instanceof String) {
				// default icon for title
				textLabel.setIcon(UIManager.getIcon("Tree.closedIcon"));
				maxval = ((SizedElement) userObject).getMaxSize();
				percent = (currentval * 100) / ((maxval == 0) ? 100 : maxval);
				percentLabel.setVisible(true);
				percentLabel.setText("" + percent + " %");
			}
			if (!leaf && obj instanceof AbstractEntity) {
				Icon icon = EntityUtil.getEntityIcon((AbstractEntity) obj);
				textLabel.setIcon(icon);
				if (obj instanceof Part) {
					maxval = Math.max(currentval, ((Part) obj).getObjectiveChars());
				} else if (obj instanceof Chapter) {
					maxval = Math.max(currentval, ((Chapter) obj).getObjectiveChars());
				}
				if (maxval == 0) {
					percent = 0;
				} else {
					percent = (currentval * 100) / maxval;
				}
				percentLabel.setVisible(true);
				percentLabel.setText("" + percent + " %");
			}

		}
		return this;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (maxval > 0) {
			Rectangle dimension = getBounds();
			g.setColor(Color.BLUE);
			g.fillRect(dimension.width - BARSIZE - DELTA - percentLabel.getPreferredSize().width, 1, BARSIZE,
					dimension.height - 2);
			g.setColor(Color.GREEN);
			int width = (BARSIZE * currentval) / maxval;
			g.fillRect(dimension.width - BARSIZE - DELTA - percentLabel.getPreferredSize().width + 1, 2, width - 2,
					dimension.height - 4);
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		textLabel.setBounds(0, 0, textLabel.getPreferredSize().width, textLabel.getPreferredSize().height);
		percentLabel.setBounds(w - percentLabel.getPreferredSize().width, 0, percentLabel.getPreferredSize().width,
				percentLabel.getPreferredSize().height);
	}
}