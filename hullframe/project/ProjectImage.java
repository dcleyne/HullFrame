package hullframe.project;

public class ProjectImage 
{
	private String _Name;
	private String _Type;
	private long _Width;
	private long _Height;
	private double _Resolution; //in dpi
	
	public String getName() {
		return _Name;
	}
	public void setName(String name) {
		_Name = name;
	}
	public String getType() {
		return _Type;
	}
	public void setType(String type) {
		_Type = type;
	}
	public long getWidth() {
		return _Width;
	}
	public void setWidth(long width) {
		_Width = width;
	}
	public long getHeight() {
		return _Height;
	}
	public void setHeight(long height) {
		_Height = height;
	}
	public double getResolution() {
		return _Resolution;
	}
	public void setResolution(double resolution) {
		_Resolution = resolution;
	}
	

}
