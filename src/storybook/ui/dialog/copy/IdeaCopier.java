package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.IdeaEntityHandler;
import storybook.model.hbn.dao.IdeaDAOImpl;
import storybook.model.hbn.entity.Idea;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.IdeaCbPanelDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class IdeaCopier.
 */
public class IdeaCopier extends AbstractCopier<Idea> {

	/**
	 * Instantiates a new idea copier.
	 *
	 * @param mainFrame the main frame
	 */
	public IdeaCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#copySpecialInformation(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Idea originElt, Idea destElt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getAllElements(org.hibernate.Session, storybook.ui.MainFrame)
	 */
	@Override
	protected List<Idea> getAllElements(Session session, MainFrame origin) {
		IdeaDAOImpl dao = new IdeaDAOImpl(session);
		List<Idea> ret = dao.findAll();

		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getDecorator()
	 */
	@Override
	protected CbPanelDecorator getDecorator() {
		return new IdeaCbPanelDecorator();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getEntityHandler(storybook.ui.MainFrame)
	 */
	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new IdeaEntityHandler(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#prepareTransfer(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Idea originElt, Idea destElt) {
	}

}
