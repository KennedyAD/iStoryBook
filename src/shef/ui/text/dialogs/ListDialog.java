/*
 * Created on Jan 18, 2006
 *
 */
package shef.ui.text.dialogs;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.Icon;

import shef.ui.OptionDialog;
import shef.ui.UIUtils;

import java.util.*;
import storybook.toolkit.I18N;

public class ListDialog extends OptionDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

//    private static final I18n i18n = I18n.getInstance("shef.ui.text.dialogs");
    
    public static final int UNORDERED = ListAttributesPanel.UL_LIST;
    public static final int ORDERED = ListAttributesPanel.OL_LIST;
    
    private static Icon icon = UIUtils.getIcon(UIUtils.X48, "categories.png"); //$NON-NLS-1$
    private static String title = I18N.getMsg("shef.list_properties"); //$NON-NLS-1$
    private static String desc = I18N.getMsg("shef.list_properties_desc"); //$NON-NLS-1$
    
    private ListAttributesPanel listAttrPanel;

    public ListDialog(Frame parent)
    {
        super(parent, title, desc, icon);
        init();
    }

    public ListDialog(Dialog parent)
    {
        super(parent, title, desc, icon);
        init();
    }
    
    private void init()
    {
        listAttrPanel = new ListAttributesPanel();
        setContentPane(listAttrPanel);
        pack();
        setSize(220, getHeight());
        setResizable(false);
    }
    
    public void setListType(int t)
    {
        listAttrPanel.setListType(t);
    }
    
    public int getListType()
    {
        return listAttrPanel.getListType();
    }
    
    public void setListAttributes(Map attr)
    {
        listAttrPanel.setAttributes(attr);
    }
    
    public Map getListAttributes()
    {
        return listAttrPanel.getAttributes();
    }
}
