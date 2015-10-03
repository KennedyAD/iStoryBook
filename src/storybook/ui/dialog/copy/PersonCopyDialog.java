package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.PersonEntityHandler;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.entity.Person;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.PersonCbPanelDecorator;

@SuppressWarnings("serial")
public class PersonCopyDialog extends AbstractCopyDialog<Person> {

	public PersonCopyDialog(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Person originElt, Person destElt) {
	}

	@Override
	protected List<Person> getAllElements(Session session, MainFrame origin) {
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return new PersonCbPanelDecorator();
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new PersonEntityHandler(mainFrame);
	}

}
