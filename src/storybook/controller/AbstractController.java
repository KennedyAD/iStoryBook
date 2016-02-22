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
package storybook.controller;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JMenuBar;

import storybook.SbApp;
import storybook.model.AbstractModel;
import storybook.model.EntityUtil;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.ui.panel.AbstractPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractController.
 */
public abstract class AbstractController implements PropertyChangeListener {

	/** The attached views. */
	private final List<Component> attachedViews;
	
	/** The attached models. */
	private final List<AbstractModel> attachedModels;

	/**
	 * Instantiates a new abstract controller.
	 */
	public AbstractController() {
		SbApp.trace("new AbstractController()");
		attachedViews = new CopyOnWriteArrayList<>();
		attachedModels = new ArrayList<>();
	}

	/**
	 * Attach model.
	 *
	 * @param model the model
	 */
	public void attachModel(AbstractModel model) {
		SbApp.trace("AbstractController.attachModel(" + model + ")");
		attachedModels.add(model);
		model.addPropertyChangeListener(this);
		printAttachedModels();
	}

	/**
	 * Attach view.
	 *
	 * @param view the view
	 */
	public void attachView(Component view) {
		SbApp.trace("AbstractController.attachView(" + view + ")");
		synchronized (attachedViews) {
			attachedViews.add(view);
		}
		if (SbApp.getTrace()) {
			printNumberOfAttachedViews();
			printAttachedViews();
		}
	}

	/**
	 * Detach model.
	 *
	 * @param model the model
	 */
	public void detachModel(AbstractModel model) {
		SbApp.trace("AbstractController.detachModel(" + model + ")");
		attachedModels.remove(model);
		model.removePropertyChangeListener(this);
		printAttachedModels();
	}

	/**
	 * Detach view.
	 *
	 * @param view the view
	 */
	public void detachView(Component view) {
		SbApp.trace("AbstractController.detachView(" + view.getName() + ")");
		synchronized (attachedViews) {
			attachedViews.remove(view);
		}
		printAttachedViews();
	}

	/**
	 * Fire again.
	 */
	public synchronized void fireAgain() {
		SbApp.trace("AbstractController.fireAgain()");
		for (AbstractModel model : attachedModels) {
			model.fireAgain();
		}
	}

	/**
	 * Gets the attached models.
	 *
	 * @return the attached models
	 */
	public List<AbstractModel> getAttachedModels() {
		return attachedModels;
	}

	/**
	 * Gets the attached views.
	 *
	 * @return the attached views
	 */
	public ArrayList<Component> getAttachedViews() {
		return (ArrayList<Component>) attachedViews;
	}

	/**
	 * Gets the info attached views.
	 *
	 * @return the info attached views
	 */
	public String getInfoAttachedViews() {
		return getInfoAttachedViews(false);
	}

	/**
	 * Gets the info attached views.
	 *
	 * @param html the html
	 * @return the info attached views
	 */
	public String getInfoAttachedViews(boolean html) {
		StringBuilder buf = new StringBuilder();
		int i = 0;
		int size = attachedViews.size();
		for (Component view : attachedViews) {
			buf.append("attached view ").append(i).append("/").append(size).append(": ")
					.append(view.getClass().getSimpleName());
			if (html) {
				buf.append("\n<br>");
			} else {
				buf.append("\n");
			}
			++i;
		}
		return buf.toString();
	}

	/**
	 * Gets the number of attached views.
	 *
	 * @return the number of attached views
	 */
	public int getNumberOfAttachedViews() {
		return attachedViews.size();
	}

	/**
	 * Prints the attached models.
	 */
	public void printAttachedModels() {
		if (SbApp.getTrace()) {
			SbApp.trace("AbstractController.printAttachedModels()");
			for (AbstractModel model : attachedModels) {
				SbApp.trace("Attached model ->" + model);
			}
		}
	}

	/**
	 * Prints the attached views.
	 */
	public void printAttachedViews() {
		SbApp.trace("AbstractController.printAttachedViews()");
		synchronized (attachedViews) {
			SbApp.trace(getInfoAttachedViews());
		}
	}

	/**
	 * Prints the number of attached views.
	 */
	public void printNumberOfAttachedViews() {
		SbApp.trace(
				"AbstractController.printNumberOfAttachedViews():" + " attached views size: " + attachedViews.size());
	}

	/** (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		SbApp.trace("AbstractController.propertyChange(" + evt + ")");
		synchronized (attachedViews) {
			// must be in synchronized block
			for (Component comp : attachedViews) {
				if (comp instanceof AbstractPanel) {
					SbApp.trace("---> AbstractPanel:" + comp);
					((AbstractPanel) comp).modelPropertyChange(evt);
				} else if (comp instanceof JMenuBar) {
					SbApp.trace("---> JMenuBar");
					JMenuBar mb = (JMenuBar) comp;
					PropertyChangeListener[] pcls = mb.getPropertyChangeListeners();
					for (PropertyChangeListener pcl : pcls) {
						pcl.propertyChange(evt);
					}
				} else if (comp instanceof SbApp) {
					SbApp.trace("---> SbApp");
					((SbApp) comp).modelPropertyChange(evt);
				}
			}
		}
	}

	/**
	 * Sets the model property.
	 *
	 * @param propertyName the new model property
	 */
	protected synchronized void setModelProperty(String propertyName) {
		SbApp.trace("AbstractController.setModelProperty(" + propertyName + ")");
		for (AbstractModel model : attachedModels) {
			try {
				Method method = model.getClass().getMethod("set" + propertyName);
				method.invoke(model);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
			}
		}
	}

	/**
	 * Sets the model property.
	 *
	 * @param propertyName the property name
	 * @param newValue the new value
	 */
	protected synchronized void setModelProperty(String propertyName, Object newValue) {
		if (newValue != null) {
			SbApp.trace("AbstractControler.setModelProperty(" + propertyName + "," + newValue + ")");
			for (AbstractModel model : attachedModels) {
				Method method = null;
				Class<?>[] classes = null;
				try {
					if (newValue instanceof AbstractEntity) {
						SbApp.trace("newValue instanceof AbstractEntity");
						classes = new Class[] { EntityUtil.getEntityClass((AbstractEntity) newValue) };
					} else if (newValue != null) {
						SbApp.trace("newValue != null");
						classes = new Class[] { newValue.getClass() };
					}
					method = model.getClass().getMethod("set" + propertyName, classes);
					SbApp.trace("method : " + "set" + propertyName + classes);
					if (newValue != null) {
						SbApp.trace("newValue != null after method=" + method);
						method.invoke(model, newValue);
					} else {
						SbApp.trace("newValue == null after method");
						method.invoke(model);
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					if (e.getCause() != null) {
						String emsg = e.getCause() == null ? "null" : e.getCause().toString();
						String emethod = method == null ? "null" : method.getName();
						String eclasses = classes.getClass() == null ? "null" : classes.getClass().toString();
						System.err.println("*** : AbstractController.setModelProperty()" + "\nmsg=" + emsg + "\nmethod:"
								+ emethod + "\nclasses:" + eclasses);
					} else {
						System.err.println("*** : AbstractController.setModelProperty()" + e);
					}
				}
			}
		}
	}
}
