package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.CategoryEntityHandler;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.entity.Category;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryCopier.
 */
public class CategoryCopier extends AbstractCopier<Category> {

	/**
	 * Instantiates a new category copier.
	 *
	 * @param mainFrame the main frame
	 */
	public CategoryCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#copySpecialInformation(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Category originElt,
			Category destElt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getAllElements(org.hibernate.Session, storybook.ui.MainFrame)
	 */
	@Override
	protected List<Category> getAllElements(Session session, MainFrame origin) {
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		List<Category> ret = dao.findAll();

		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getDecorator()
	 */
	@Override
	protected CbPanelDecorator getDecorator() {
		return null;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getEntityHandler(storybook.ui.MainFrame)
	 */
	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new CategoryEntityHandler(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#prepareTransfer(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Category originElt, Category destElt) {

		Category sup = originElt.getSup();
		if (sup != null) {
			BookModel destinationModel = destination.getBookModel();
			Session destinationSession = destinationModel.beginTransaction();
			List<Category> cats = new CategoryDAOImpl(destinationSession).findAll();
			boolean found = false;
			for (Category cat : cats) {
				if (cat.getName().equals(sup.getName())) {
					found = true;
					destElt.setSup(cat);
					break;
				}
			}

			if (!found) {
				Category destSup = copy(destination, sup);
				destElt.setSup(destSup);
			}
		}
	}

}
