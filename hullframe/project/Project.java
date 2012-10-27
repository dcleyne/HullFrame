package hullframe.project;

import java.nio.file.Path;
import java.util.Vector;

public class Project
{
	//Values we want to store
	private String _Name;
	private String _Description;
	private String _Creator;
	
	// Values we don't want to store
	private String _Filename;
	private Path _TempLocation;

	private Vector<ProjectImage> _Images = new Vector<ProjectImage>();

	public String getFilename()
	{
		return _Filename;
	}

	public void setFilename(String filename)
	{
		_Filename = filename;
	}

	public String getName()
	{
		return _Name;
	}

	public void setName(String name)
	{
		_Name = name;
	}

	public String getDescription()
	{
		return _Description;
	}

	public void setDescription(String description)
	{
		_Description = description;
	}

	public String getCreator()
	{
		return _Creator;
	}

	public void setCreator(String creator)
	{
		_Creator = creator;
	}

	public Path getTempLocation()
	{
		return _TempLocation;
	}

	public void setTempLocation(Path tempLocation)
	{
		_TempLocation = tempLocation;
	}

	public void addImage(ProjectImage image)
	{
		_Images.add(image);
	}
}
