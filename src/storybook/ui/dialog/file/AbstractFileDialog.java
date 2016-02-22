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

package storybook.ui.dialog.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import net.miginfocom.swing.MigLayout;
import storybook.SbConstants;
import storybook.toolkit.I18N;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.dialog.AbstractDialog;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractFileDialog.
 */
public abstract class AbstractFileDialog extends AbstractDialog implements CaretListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3841682455900267198L;
	
	/** The lb warning. */
	protected JLabel lbWarning;
	
	/** The bt ok. */
	protected JButton btOk;
	
	/** The tf dir. */
	protected JTextField tfDir;
	
	/** The tf name. */
	protected JTextField tfName;
	
	/** The bt choose dir. */
	protected JButton btChooseDir;
	
	/** The options panel. */
	protected JPanel optionsPanel;
	
	/** The file. */
	protected File file;
	
	/** The hide dir. */
	private boolean hideDir = false;
	
	/** The force db ext. */
	private boolean forceDbExt = true;
	
	/** The default db ext. */
	private String defaultDBExt = "mv.db";
	
	/** The ask for overwrite. */
	private boolean askForOverwrite = false;

	/**
	 * Instantiates a new abstract file dialog.
	 *
	 * @param mainFrame the main frame
	 */
	public AbstractFileDialog(MainFrame mainFrame) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		init();
		initUi();
	}

	/**
	 * Add information (label / widget) to the main panel.
	 */
	protected void addInformationLines() {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() instanceof JTextField) {
			if (tfName.getText().isEmpty() || tfDir.getText().isEmpty()) {
				btOk.setEnabled(false);
				return;
			}
			btOk.setEnabled(true);
			lbWarning.setText(" ");
		}
	}

	/**
	 * Gets the choose folder action.
	 *
	 * @return the choose folder action
	 */
	private AbstractAction getChooseFolderAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3638390475240763293L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				final JFileChooser fc = new JFileChooser(tfDir.getText());
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = fc.showOpenDialog(mainFrame);
				if (ret != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File dir = fc.getSelectedFile();
				tfDir.setText(dir.getAbsolutePath());
				lbWarning.setText(" ");
			}
		};
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Gets the main frame.
	 *
	 * @return the main frame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#getOkAction()
	 */
	@Override
	protected AbstractAction getOkAction() {
		return new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3413349766401704105L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				if (tfName.getText().isEmpty() || tfDir.getText().isEmpty()) {
					btOk.setEnabled(false);
					return;
				}
				File dir = new File(tfDir.getText());
				if (!dir.isDirectory() || !dir.canWrite() || !dir.canExecute()) {
					lbWarning.setText(I18N.getMsg("msg.new_file.not.writable"));
					return;
				}
				String name = tfName.getText();
				if (forceDbExt) {
					String fileExtOld = SbConstants.Storybook.DB_FILE_EXT.toString();
					String fileExt = SbConstants.Storybook.DB_FILE_EXT2.toString();
					if ((!name.endsWith(fileExtOld)) && (!name.endsWith(fileExt))) {
						name += fileExt;
					}
				} else {
					name += defaultDBExt;
				}
				file = new File(tfDir.getText() + File.separator + name);
				if ((file.exists()) && (askForOverwrite)) {
					int ret = JOptionPane.showConfirmDialog(mainFrame,
							I18N.getMsg("msg.save_file.overwrite.text", file.getName()),
							I18N.getMsg("msg.save_file.overwrite.title"), JOptionPane.YES_NO_OPTION);
					if (ret == JOptionPane.NO_OPTION) {
						lbWarning.setText(I18N.getMsg("msg.new_file.file.exists"));
						return;
					}
				} else if (file.exists()) {
					lbWarning.setText(I18N.getMsg("msg.new_file.file.exists"));
					return;
				}
				getThis().canceled = false;
				getThis().dispose();
			}
		};
	}

	/**
	 * Gets the tf dir.
	 *
	 * @return the tf dir
	 */
	public JTextField getTfDir() {
		return tfDir;
	}

	/**
	 * Gets the tf name.
	 *
	 * @return the tf name
	 */
	public JTextField getTfName() {
		return tfName;
	}

	/**
	 * Gets the this.
	 *
	 * @return the this
	 */
	protected AbstractFileDialog getThis() {
		return this;
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#init()
	 */
	@Override
	public void init() {
	}

	/**
	 * Inits the options panel.
	 */
	protected void initOptionsPanel() {
	}

	/* (non-Javadoc)
	 * @see storybook.ui.dialog.AbstractDialog#initUi()
	 */
	@Override
	public void initUi() {
		MigLayout layout = new MigLayout("wrap 2", "[]", "[]");
		setLayout(layout);

		JLabel lbName = new JLabel(I18N.getMsgColon("msg.dlg.mng.prjs.project.name"));
		tfName = new JTextField(30);
		tfName.setName("name");
		tfName.addCaretListener(this);

		JLabel lbDir = new JLabel(I18N.getMsgColon("msg.common.folder"));
		tfDir = new JTextField(30);
		tfDir.setName("folder");
		tfDir.addCaretListener(this);

		btChooseDir = new JButton();
		btChooseDir.setAction(getChooseFolderAction());
		btChooseDir.setText(I18N.getMsg("msg.common.choose.folder"));

		optionsPanel = new JPanel();
		initOptionsPanel();

		lbWarning = new JLabel(" ");

		// OK button
		btOk = new JButton();
		btOk.setAction(getOkAction());
		SwingUtil.addEnterAction(btOk, getOkAction());
		btOk.setText(I18N.getMsg("msg.common.ok"));
		btOk.setIcon(I18N.getIcon("icon.small.ok"));
		btOk.setEnabled(false);

		// cancel button
		JButton btCancel = new JButton();
		btCancel.setAction(getCancelAction());
		SwingUtil.addEscAction(btCancel, getCancelAction());
		btCancel.setText(I18N.getMsg("msg.common.cancel"));
		btCancel.setIcon(I18N.getIcon("icon.small.close"));

		// layout
		add(lbName);
		add(tfName);
		if (!hideDir) {
			add(lbDir);
			add(tfDir, "split 2");
			add(btChooseDir);
		}

		addInformationLines();

		add(optionsPanel, "span");
		add(lbWarning, "span,gapy 10");
		add(btOk, "sg,span,split 2,right,gapy 10");
		add(btCancel, "sg");
	}

	/**
	 * Sets the ask for overwrite.
	 *
	 * @param ask the new ask for overwrite
	 */
	public void setAskForOverwrite(boolean ask) {
		askForOverwrite = ask;
	}

	/**
	 * Sets the default db ext.
	 *
	 * @param ext the new default db ext
	 */
	public void setDefaultDBExt(String ext) {
		defaultDBExt = ext;
	}

	/**
	 * Sets the dir.
	 *
	 * @param dir the new dir
	 */
	protected void setDir(String dir) {
		tfDir.setText(dir);
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	protected void setFilename(String filename) {
		tfName.setText(filename);
		tfName.selectAll();
	}

	/**
	 * Sets the force db extension.
	 *
	 * @param forced the new force db extension
	 */
	public void setForceDbExtension(boolean forced) {
		forceDbExt = forced;
	}

	/**
	 * Sets the hide dir.
	 *
	 * @param dirOnly the new hide dir
	 */
	public void setHideDir(boolean dirOnly) {
		hideDir = dirOnly;
	}

	/**
	 * Sets the tf name.
	 *
	 * @param name the new tf name
	 */
	public void setTfName(String name) {
		tfName.setText(name);
	}
}