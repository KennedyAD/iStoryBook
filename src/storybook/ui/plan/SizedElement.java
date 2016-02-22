package storybook.ui.plan;

import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Scene;

// TODO: Auto-generated Javadoc
/**
 * The Class SizedElement.
 */
public class SizedElement {
	
	/** The element. */
	Object element;
	
	/** The size. */
	int size;
	
	/** The max size. */
	int maxSize;

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public Object getElement() {
		return element;
	}

	/**
	 * Gets the max size.
	 *
	 * @return the max size
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the element.
	 *
	 * @param element the new element
	 */
	public void setElement(Object element) {
		this.element = element;
		if (element instanceof Part) {
			Part part = (Part) element;
			maxSize = part.getObjectiveChars();
		} else if (element instanceof Chapter) {
			Chapter chapter = (Chapter) element;
			maxSize = chapter.getObjectiveChars();
		}
	}

	/**
	 * Sets the max size.
	 *
	 * @param size the new max size
	 */
	public void setMaxSize(int size) {
		this.maxSize = size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (element instanceof Part) {
			Part part = (Part) element;
			maxSize = part.getObjectiveChars();
			return (part.getName() + "    (" + size + "/" + maxSize + ")");
		} else if (element instanceof Chapter) {
			Chapter chapter = (Chapter) element;
			maxSize = chapter.getObjectiveChars();
			return chapter.getTitle() + "    (" + size + "/" + maxSize + ")";
		} else if (element instanceof Scene) {
			Scene scene = (Scene) element;
			return scene.getTitle() + "    (" + size + ")";
		} else if (element instanceof String) {
			return (String) element + "    (" + size + "/" + maxSize + ")";
		}
		return "";
	}
}
