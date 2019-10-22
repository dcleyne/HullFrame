package hullframe;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import hullframe.project.Project;
import hullframe.project.ProjectManager;
import hullframe.ui.InternalFrameManager;
import hullframe.util.ExceptionUtil;
import hullframe.util.PropertyUtil;

public class HullFrame extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 1;
	private static final String _Caption = "Model Boat Frame Helper";
	
	private static final String ITEM_ABOUT = "About...";
	private static final String ITEM_NEW_PROJECT = "NewProject";
	private static final String ITEM_SAVE_PROJECT = "SaveProject";
	private static final String ITEM_SAVE_PROJECT_AS = "SaveProjectAs";
	private static final String ITEM_OPEN_PROJECT = "OpenProject";
	private static final String ITEM_CLOSE_PROJECT = "CloseProject";
	private static final String ITEM_EXIT = "Exit";
	
	private static final String ITEM_PROJECT_ADD_IMAGE = "ProjectAddImage";
	private static final String ITEM_PROJECT_PROPERTIES = "ProjectProperties";
	
	
    private JDesktopPane _MainPane = new JDesktopPane();
    private InternalFrameManager _FrameManager = new InternalFrameManager(this, _MainPane);

    private JPanel _MainPanel;
    private JMenuBar _MainMenu;
    private JToolBar _MainToolbar;

    private HashMap<String, ImageIcon> _ApplicationIcons = new HashMap<String, ImageIcon>();
    private HashMap<String, JMenuItem> _ApplicationMenuItems = new HashMap<String, JMenuItem>();
    private HashMap<String, JComponent> _ApplicationComponents = new HashMap<String, JComponent>();

    private ApplicationState _ApplicationState = new ApplicationState();

    public HullFrame()
    {
        this(_Caption);
        

    }

    public HullFrame(String title)
    {
        // Initialize
    	try
    	{
	        PropertyUtil.loadSystemProperties("hullframe/system.properties");
    	}
    	catch (Exception ex)
    	{
    		System.out.println(ExceptionUtil.getExceptionStackTrace(ex));
    		System.exit(1);
    	}
        
        //
        // Common images for menubar and toolbar
        LoadCommonImages();
        // Frame
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0,0));

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width-100,screenSize.height-200);
        setLocation((screenSize.width - getWidth() - 100) / 2, (screenSize.height - getHeight() - 200) / 2);

        setVisible(false);
        // Menu
        _MainMenu = new JMenuBar();
        setJMenuBar(_MainMenu);
        menuAddItems(_MainMenu);
        // Panels

        //m_MainPane.setOpaque(false);

        _MainPane.setDesktopManager(new DefaultDesktopManager());
        getContentPane().add(_MainPane,BorderLayout.CENTER);


        _MainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        getContentPane().add(BorderLayout.NORTH, _MainPanel);
        _MainPanel.setBounds(0,0,475,30);


        // Toolbar
        
        _MainToolbar = new JToolBar();
        _MainPanel.add(_MainToolbar);
        _MainToolbar.setBounds(0,0,375,30);
        addToolbarButtons(_MainToolbar);


        // Register listeners
        //
        // Window listener
        addWindowListener(this);
    }

    protected void LoadCommonImages()
    {
    	_ApplicationIcons.put("iconAbout", new ImageIcon("images/about.gif"));
    }

    protected void addToolbarButtons(JToolBar toolbar) 
    {
        Rectangle bounds = new Rectangle();
        // Bounds for each button
        bounds.x=2;
        bounds.y=2;
        bounds.width=25;
        bounds.height=25;
        // Toolbar separator
        // Button size
        Dimension buttonsize = new Dimension(bounds.width,bounds.height);

        // About
        JButton buttonAbout = new JButton(_ApplicationIcons.get("iconAbout"));
        buttonAbout.setDefaultCapable(false);
        buttonAbout.setToolTipText("Display program information");
        buttonAbout.setMnemonic((int)'A');
        toolbar.add(buttonAbout);
        bounds.x += bounds.width;
        buttonAbout.setBounds(bounds);
        buttonAbout.setMinimumSize(buttonsize);
        buttonAbout.setMaximumSize(buttonsize);
        buttonAbout.setPreferredSize(buttonsize);
        buttonAbout.setActionCommand("About...");
        buttonAbout.addActionListener(this);
        _ApplicationComponents.put(ITEM_ABOUT, buttonAbout);
    }
    
    private void addMenuItem(JMenu menu, String caption, String command, char Mnemonic)
    {
        JMenuItem menuitem = new JMenuItem(caption);
        menuitem.setHorizontalTextPosition(SwingConstants.RIGHT);
        menuitem.setActionCommand(command);
        menuitem.setBorderPainted(false);
        menuitem.setMnemonic((int)Mnemonic);
        menuitem.addActionListener(this);
        menu.add(menuitem);
        _ApplicationMenuItems.put(command, menuitem);
    }

    private void addFileMenu(JMenuBar mainMenu)
    {
        JMenu menu = new JMenu("File");
        menu.setActionCommand("File");
        menu.setBorderPainted(false);
        menu.setMnemonic( (int)'F');
        mainMenu.add(menu);
        
        addMenuItem(menu, "New Project", ITEM_NEW_PROJECT, 'N');
        addMenuItem(menu, "Open Project", ITEM_OPEN_PROJECT, 'O');
        menu.addSeparator();
        addMenuItem(menu, "Save Project", ITEM_SAVE_PROJECT, 'S');
        addMenuItem(menu, "Save Project As", ITEM_SAVE_PROJECT_AS, 'A');
        menu.addSeparator();
        addMenuItem(menu, "Close Project", ITEM_CLOSE_PROJECT, 'C');
        menu.addSeparator();
        addMenuItem(menu, "Exit", ITEM_EXIT, 'X');        
    }
    
    private void addToolsMenu(JMenuBar mainMenu)
    {
        //"Tools" menu
    	JMenu menu = new JMenu("Tools");
        menu.setActionCommand("Tools");
        menu.setBorderPainted(false);
        menu.setMnemonic( (int)'T');
        mainMenu.add(menu);
    }
    
    private void addProjectMenu(JMenuBar mainMenu)
    {   //"Setup" menu
    	JMenu menu = new JMenu("Project");
        menu.setActionCommand("Project");
        menu.setBorderPainted(false);
        menu.setMnemonic( (int)'P');
        mainMenu.add(menu);
        
        addMenuItem(menu, "Add Image", ITEM_PROJECT_ADD_IMAGE, 'I');
        menu.addSeparator();
        addMenuItem(menu, "Properties", ITEM_PROJECT_PROPERTIES, 'P');
        
    }
    
    private void menuAddItems(JMenuBar hmenu) 
    {
    	addFileMenu(hmenu);
    	addToolsMenu(hmenu);
    	addProjectMenu(hmenu);
        //
        // "Help" menu
        //
    	JMenu menu = new JMenu("Help");
        menu.setActionCommand("Help");
        menu.setBorderPainted(false);
        menu.setMnemonic((int)'H');
        hmenu.add(menu);
        
        addMenuItem(menu, "About...", ITEM_ABOUT, 'A');
    }

    static public void main(String args[])
    {
        /**
         * The entry point for this application.
         * @param args
         */
    	System.out.println("Starting " + _Caption);

        try {
            // Set the "look and feel" to the native system
            try {
                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            } catch (Exception e) {
            }
            // Create a new instance of the application frame and make it visible
            (new HullFrame()).setVisible(true);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            // Exit with error condition
            System.exit(1);
        }
    }

    // Used by addNotify
    boolean addNotify_done=false;

    /**
     * Makes this Container displayable by connecting it to
     * a native screen resource.  Making a container displayable will
     * cause any of its children to be made displayable.
     * This method is called internally by the toolkit and should
     * not be called directly by programs.
     * <p>
     * Overridden here to adjust the size of the frame if needed.
     * </p>
     * @see java.awt.Component#isDisplayable
     * @see java.awt.Container#removeNotify
     */
    public void addNotify() {
        Dimension d=getSize();

        super.addNotify();

        if (addNotify_done) return;

        // Adjust size according to the insets so that entire component
        // areas are renderable.
        int menubarheight=0;
        JMenuBar menubar = getRootPane().getJMenuBar();
        if (menubar!=null) {
            menubarheight = menubar.getPreferredSize().height;
        }
        Insets insets=getInsets();
        setSize(insets.left + insets.right + d.width, insets.top + insets.bottom + d.height + menubarheight);
        addNotify_done=true;
    }

    protected void aboutApplication() {
        try {
            JOptionPane.showMessageDialog(this, _Caption, "About" ,JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
        }
    }

    protected void exitApplication()
    {
        try {
            Toolkit.getDefaultToolkit().beep();
            // Show an Exit confirmation dialog
            int reply = JOptionPane.showConfirmDialog(this,
                                                      "Do you really want to exit?",
                                                      "Exit" ,
                                                      JOptionPane.YES_NO_OPTION,
                                                      JOptionPane.QUESTION_MESSAGE);
            if (reply==JOptionPane.YES_OPTION) {
                // User answered "Yes", so cleanup and exit
                //
                //Close all open Internal Frames
                _FrameManager.CloseInternalFrames();

                // Hide the frame
                this.setVisible(false);
                // Free system resources
                this.dispose();
                // Exit the application
                System.exit(0);
            }
            System.exit(0); //Just exit for now. This should display a dialog if the document is dirty
        } catch (Exception e) {
        }
    }

	@Override
	public void actionPerformed(ActionEvent event) 
	{
        String command = event.getActionCommand();
        if (command==ITEM_ABOUT) 
        {
            aboutApplication();
        }
        if (command==ITEM_EXIT) 
        {
            exitApplication();
        }
        if (command==ITEM_NEW_PROJECT) 
        {
        	newProject();
        }
        if (command==ITEM_OPEN_PROJECT) 
        {
        	openProject();
        }
        if (command==ITEM_CLOSE_PROJECT) 
        {
        	closeProject();
        }
        if (command==ITEM_SAVE_PROJECT) 
        {
        	saveProject();
        }
        if (command==ITEM_SAVE_PROJECT_AS) 
        {
        	saveProjectAs();
        }
		
        setApplicationState();
	}
	
	private boolean saveAndCloseProject()
	{
		if (_ApplicationState.isProjectOpen())
		{
			if (_ApplicationState.isProjectDirty())
			{
				//Ask the user if they want to save and close
				int result = JOptionPane.showConfirmDialog(this, "The current project has changes. Do you want to save the changes?", "Save and Close Project", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
				if (result == JOptionPane.YES_OPTION)
				{
					saveProject();
				}
				else if (result == JOptionPane.CANCEL_OPTION)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private void setCaption()
	{
		if (_ApplicationState.isProjectOpen())
		{
			if (_ApplicationState.isProjectDirty())
			{
				setTitle(_Caption + " : " + _ApplicationState.getCurrentProject().getName() + "*");
			}
			else
			{
				setTitle(_Caption + " : " + _ApplicationState.getCurrentProject().getName());				
			}
		}
		else
		{
			setTitle(_Caption);
		}
	}
	
	private void setProjectOpen(Project p)
	{
		_ApplicationState.setProjectOpen(p);
		
		setCaption();
	}
	
	private void setProjectClosed()
	{
		_ApplicationState.setProjectClosed();
		
		setCaption();		
	}
	
	@SuppressWarnings("unused")
	private void setProjectDirty()
	{
		_ApplicationState.setProjectDirty();
		
		setCaption();
	}
	
	private void setProjectClean()
	{
		_ApplicationState.setProjectClean();
		
		setCaption();
	}
	
	private void newProject()
	{
		// Do something with any open projects
		if (!saveAndCloseProject())
			return;
		
		try
		{
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);		
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File selectedFile = fc.getSelectedFile();
				if (ProjectManager.createEmptyProjectFile(selectedFile))
				{
					Project newProject = ProjectManager.loadProject(selectedFile);
					if (newProject != null)
					{
						setProjectOpen(newProject);
					}
					else
					{
						JOptionPane.showConfirmDialog(this, "Unable to create New Project");
					}
				}
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showConfirmDialog(this, ExceptionUtil.getExceptionStackTrace(ex), "Unable to create New Project", JOptionPane.OK_OPTION);			
		}
	}
	
	private void openProject()
	{
		// Do something with any open projects
		if (!saveAndCloseProject())
			return;
		
		try
		{
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File selectedFile = fc.getSelectedFile();
				if (!selectedFile.exists())
				{
					ProjectManager.createEmptyProjectFile(selectedFile);
				}
				openProject(selectedFile);
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showConfirmDialog(this, ExceptionUtil.getExceptionStackTrace(ex), "Unable to open Project", JOptionPane.OK_OPTION);						
		}
	}
	
	private void openProject(File fileToOpen) throws Exception
	{
		
		Project newProject = ProjectManager.loadProject(fileToOpen);
		if (newProject != null)
		{
			setProjectOpen(newProject);
		}
		else
		{
			JOptionPane.showConfirmDialog(this, "Unable to open Project");									
		}
	}
	
	private void closeProject()
	{
		if (saveAndCloseProject())
		{
			setProjectClosed();
		}
	}
	
	private void saveProject()
	{
		try
		{
			ProjectManager.storeProject(_ApplicationState.getCurrentProject());
			setProjectClean();
		}
		catch (Exception ex)
		{
			JOptionPane.showConfirmDialog(this, ExceptionUtil.getExceptionStackTrace(ex), "Unable to save Project", JOptionPane.OK_OPTION);									
		}
	}
	
	private void saveProjectAs()
	{
		
	}

	@Override
	public void windowActivated(WindowEvent event) 
	{
	}

	@Override
	public void windowClosed(WindowEvent event) 
	{
	}

	@Override
	public void windowClosing(WindowEvent event) 
	{
		this.exitApplication();
	}

	@Override
	public void windowDeactivated(WindowEvent event) 
	{
	}

	@Override
	public void windowDeiconified(WindowEvent event) 
	{
	}

	@Override
	public void windowIconified(WindowEvent event) 
	{
	}

	@Override
	public void windowOpened(WindowEvent arg0) 
	{
	}
	
	private void setApplicationState()
	{
		setControlState(ITEM_NEW_PROJECT, true, true);
		setControlState(ITEM_OPEN_PROJECT, true, true);
		setControlState(ITEM_ABOUT, true, true);

		setControlState(ITEM_SAVE_PROJECT_AS, _ApplicationState.isProjectOpen(), true);
		setControlState(ITEM_CLOSE_PROJECT, _ApplicationState.isProjectOpen(), true);
		
		if (_ApplicationState.isProjectOpen())
		{
			setControlState(ITEM_SAVE_PROJECT, _ApplicationState.isProjectDirty(), true);
		}
		else
		{
			setControlState(ITEM_SAVE_PROJECT, false, true);
		}

		setControlState(ITEM_PROJECT_ADD_IMAGE, _ApplicationState.isProjectOpen(), true);
		setControlState(ITEM_PROJECT_PROPERTIES, _ApplicationState.isProjectOpen(), true);

	}
	
	private void setControlState(String command, boolean enabled, boolean visible)
	{
		if (_ApplicationMenuItems.containsKey(command))
		{
			JMenuItem menuItem = _ApplicationMenuItems.get(command);
			menuItem.setEnabled(enabled);
			menuItem.setVisible(visible);
		}
		if (_ApplicationComponents.containsKey(command))
		{
			JComponent component = _ApplicationComponents.get(command);
			component.setEnabled(enabled);
			component.setVisible(visible);
		}
	}

	private class ApplicationState
	{
		private boolean _ProjectOpen = false;
		private boolean _ProjectDirty = false;
		private Project _CurrentProject = null;
		
		public boolean isProjectOpen()
		{
			return _ProjectOpen;
		}
		
		public void setProjectOpen(Project openProject)
		{
			_ProjectOpen = true;
			_CurrentProject = openProject;
			_ProjectDirty = false;
		}
		
		public void setProjectClosed()
		{
			_ProjectOpen = false;
			_CurrentProject = null;
			_ProjectDirty = false;
		}
		
		public boolean isProjectDirty()
		{
			return _ProjectDirty;
		}
		
		public void setProjectDirty()
		{
			_ProjectDirty = true;
		}
		
		public void setProjectClean()
		{
			_ProjectDirty = false;
		}
		
		public Project getCurrentProject()
		{
			return _CurrentProject;
		}
	}
}
