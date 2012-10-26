package hullframe.project;

import java.util.Vector;

public class Project 
{
	
	private String _Name;
	private String _Description;
	private String _Creator;

	private Vector<ProjectImage> _Images = new Vector<ProjectImage>();

	public String getName() {
		return _Name;
	}

	public void setName(String name) {
		_Name = name;
	}

	public String getDescription() {
		return _Description;
	}

	public void setDescription(String description) {
		_Description = description;
	}

	public String getCreator() {
		return _Creator;
	}

	public void setCreator(String creator) {
		_Creator = creator;
	}
	
	public void addImage(ProjectImage image)
	{
		_Images.add(image);
	}
}
