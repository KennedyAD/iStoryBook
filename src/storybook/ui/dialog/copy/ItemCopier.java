package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.ItemEntityHandler;
import storybook.model.hbn.dao.ItemDAOImpl;
import storybook.model.hbn.entity.Item;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.ItemCbPanelDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemCopier.
 */
public class ItemCopier extends AbstractCopier<Item> {

	/**
	 * Instantiates a new item copier.
	 *
	 * @param mainFrame the main frame
	 */
	public ItemCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#copySpecialInformation(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Item originElt, Item destElt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getAllElements(org.hibernate.Session, storybook.ui.MainFrame)
	 */
	@Override
	protected List<Item> getAllElements(Session session, MainFrame origin) {
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> ret = dao.findAll();

		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getDecorator()
	 */
	@Override
	protected CbPanelDecorator getDecorator() {
		return new ItemCbPanelDecorator();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getEntityHandler(storybook.ui.MainFrame)
	 */
	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new ItemEntityHandler(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#prepareTransfer(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Item originElt, Item destElt) {
	}

}
