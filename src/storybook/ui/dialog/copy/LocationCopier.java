package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.LocationEntityHandler;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.entity.Location;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.LocationCbPanelDecorator;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationCopier.
 */
public class LocationCopier extends AbstractCopier<Location> {

	/**
	 * Instantiates a new location copier.
	 *
	 * @param mainFrame the main frame
	 */
	public LocationCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#copySpecialInformation(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Location originElt,
			Location destElt) {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getAllElements(org.hibernate.Session, storybook.ui.MainFrame)
	 */
	@Override
	protected List<Location> getAllElements(Session session, MainFrame origin) {
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> ret = dao.findAll();

		return ret;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getDecorator()
	 */
	@Override
	protected CbPanelDecorator getDecorator() {
		return new LocationCbPanelDecorator();
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#getEntityHandler(storybook.ui.MainFrame)
	 */
	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new LocationEntityHandler(mainFrame);
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.copy.AbstractCopier#prepareTransfer(storybook.ui.MainFrame, storybook.ui.MainFrame, storybook.model.hbn.entity.AbstractEntity, storybook.model.hbn.entity.AbstractEntity)
	 */
	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Location originElt, Location destElt) {

		setLocation(destination, originElt, destElt);
	}

	/**
	 * Sets the location.
	 *
	 * @param destination the destination
	 * @param originElt the origin elt
	 * @param destElt the dest elt
	 */
	private void setLocation(MainFrame destination, Location originElt, Location destElt) {
		Location site = originElt.getSite();
		if (site != null) {
			BookModel destinationModel = destination.getBookModel();
			Session destinationSession = destinationModel.beginTransaction();
			List<Location> sites = new LocationDAOImpl(destinationSession).findAll();
			destinationSession.close();

			boolean found = false;
			for (Location loc : sites) {
				if (loc.getName().equals(site.getName())) {
					found = true;
					destElt.setSite(loc);
					break;
				}
			}

			if (!found) {
				Location destCat = new LocationCopier(getMainFrame()).copy(destination, site);
				destElt.setSite(destCat);
			}
		}
	}

}
