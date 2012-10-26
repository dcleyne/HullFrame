package hullframe.ui;

import java.awt.Color;

import java.awt.Dimension;
import java.util.Vector;


import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * <p>Title: Miradan Phedd</p>
 *
 * <p>Description: Library for all Miradan related work</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Miradan Phedd</p>
 *
 * @author Daniel Cleyne
 * @version 0.1
 */
public class InternalFrameManager implements InternalFrameListener
{
    private JFrame _ParentFrame;
    private JDesktopPane _DesktopPane;


    private Vector<JInternalFrame> _ImageFrames = new Vector<JInternalFrame>();
    
    
    public InternalFrameManager(JFrame frame, JDesktopPane DesktopPane)
    {
        _ParentFrame = frame;
        _DesktopPane = DesktopPane;
    }

    public void ShowImageFrame(String imageName)
    {
        try
        {
        	/*
    		Unit u = UnitManager.getInstance().getUnit(unitName);
    		if (u != null)
    		{
    	        boolean found = false;
    	        UnitInternalFrame uif = null;
    	        for (int i = 0; i < _UnitFrames.size(); i++)
    	        {
    	            uif = (UnitInternalFrame)_UnitFrames.elementAt(i);
    	            Unit uif_a = uif.GetUnit();
    	            if (u.equals(uif_a))
    	            {
    	                found = true;
    	                break;
    	            }
    	        }
    	        if (!found)
    	        {
    	            uif = new UnitInternalFrame("Unit : " + u.getName(), u);
    	            _DesktopPane.add(uif);
    	            uif.setBounds(10, 10, 640, 620);
    	            uif.addInternalFrameListener(this);
    	            _UnitFrames.add(uif);
    	        }
    	        else
    	        {
    	            if (uif.isIcon())
    	            {
    	                try
    	                {
    	                    uif.setIcon(false);
    	                }
    	                catch (Exception e)
    	                {
    	                	System.out.println("Exception changing internal frame out of Icon mode");
    	                	e.printStackTrace();
    	                }
    	            }
    	
    	        }
    	        _DesktopPane.getDesktopManager().activateFrame(uif);
    		}
    		*/
        }
        catch (Exception e)
        {
        	System.out.println("Failed to open Unit List Internal Frame");
        	e.printStackTrace();
        }
    }
    
 
    public void internalFrameActivated(InternalFrameEvent e)
    {
    }

    public void internalFrameDeactivated(InternalFrameEvent e)
    {
    }

    public void internalFrameIconified(InternalFrameEvent e)
    {
    }

    public void internalFrameDeiconified(InternalFrameEvent e)
    {
    }

    public void internalFrameClosing(InternalFrameEvent e)
    {
        JInternalFrame jif = e.getInternalFrame();
        
        if (_ImageFrames.contains(jif))
        {
        	/*
            UnitInternalFrame aif = (UnitInternalFrame)jif;
            aif.forceFrameEditCompletion();
            _UnitFrames.remove(jif);
            */
        } 
        
    }

    public void internalFrameClosed(InternalFrameEvent e)
    {
    }

    public void internalFrameOpened(InternalFrameEvent e)
    {
    }

    public void CloseInternalFrames()
    {
        try
        {
            
            for (int i = 0; i < _ImageFrames.size(); i++)
            {
                JInternalFrame uif = (JInternalFrame) _ImageFrames.elementAt(i);
                uif.setClosed(true);
            }
        }
        catch (Exception e)
        {
        	System.out.println("Failed to close an internal frame: ");
        	e.printStackTrace();
        }
    }

    public void showGlassPane(String message)
    {
        JPanel glass = (JPanel)((JFrame)JOptionPane.getFrameForComponent(_ParentFrame)).getGlassPane();
        glass.removeAll();

        glass.setVisible(false);
        glass.removeAll();
        glass.setLayout(null);
        
        if (!message.equalsIgnoreCase(""))
        {
            JPanel sheet = new JPanel();
            sheet.setBorder(new LineBorder(Color.BLACK,1));
            sheet.setBackground(Color.BLUE);
            
            JLabel label = new JLabel(message);
            label.setForeground(Color.WHITE);
            sheet.add(label);
            sheet.validate();
            Dimension d = sheet.getPreferredSize();
            sheet.setSize(d);
            
            int x = (glass.getWidth() - d.width) / 2;
            int y = (glass.getHeight() - d.height)/ 2;
            glass.add(sheet);        
            sheet.setLocation(x,y);    
            sheet.setVisible(true);

        }
        glass.setVisible(true);        
    }

    public void hideGlassPane() 
    {
        JPanel glass = (JPanel)_ParentFrame.getRootPane().getGlassPane();
        glass.setVisible(false);
        glass.removeAll();
        glass.setLayout(null);
    }
}
