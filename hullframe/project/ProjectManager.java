package hullframe.project;

import hullframe.util.ExceptionUtil;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ProjectManager
{

	public static boolean createEmptyProjectFile(File fileToCreate)
	{
		try
		{
			Project p = new Project();
			p.setFilename(fileToCreate.getAbsolutePath());
			p.setName(fileToCreate.getName());
			p.setDescription("A Project");
			p.setCreator("Someone");

			storeProject(p);
		} catch (Exception ex)
		{
			System.out.println(ExceptionUtil.getExceptionStackTrace(ex));
			return false;
		}

		return true;
	}
	
	private static void storeProjectValues(Project p, JarOutputStream target) throws Exception
	{
        org.jdom.Document doc = new org.jdom.Document();
        org.jdom.Element rootElement = new org.jdom.Element("HullFrame");
        
        org.jdom.Element projectElement = new Element("Project");
        
        projectElement.setAttribute("Name", p.getName());
        projectElement.setAttribute("Description",p.getDescription());
        projectElement.setAttribute("Creator", p.getCreator());
        
        rootElement.addContent(projectElement);
        doc.addContent(rootElement);
        
		JarEntry entry = new JarEntry("HullFrame.xml");
		entry.setTime(Calendar.getInstance().getTimeInMillis());
		target.putNextEntry(entry);
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(doc, target);
	}
	
	private static void loadProjectValues(InputStream inStream, Project p) throws Exception
	{
		if (inStream == null)
			throw new Exception("Invalid Project file");
		
		SAXBuilder b = new SAXBuilder();
		Document doc = b.build(inStream);

		org.jdom.Element rootElement = doc.getRootElement();
		org.jdom.Element projectElement = rootElement.getChild("Project");
		if (projectElement != null)
		{
			p.setName(projectElement.getAttributeValue("Name"));
			p.setDescription(projectElement.getAttributeValue("Description"));
			p.setCreator(projectElement.getAttributeValue("Creator"));
		}
		else
			throw new Exception("Invalid project file");
	}

	public static boolean storeProject(Project p) throws Exception
	{
		try
		{
			Manifest manifest = new Manifest();
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION,
					"1.0");
			JarOutputStream target = new JarOutputStream(new FileOutputStream(
					p.getFilename()), manifest);

			storeProjectValues(p, target);

			target.close();
		} catch (Exception ex)
		{
			throw ex;
		}

		return true;
	}

	public static Project loadProject(String filename) throws Exception
	{
		return loadProject(new File(filename));
	}

	public static Project loadProject(File file) throws Exception
	{
		JarFile jarFile = null;
		try
		{
			Project p = new Project();
			Path tempDir = Files.createTempDirectory("HullFrame", new FileAttribute<?>[] {});

			jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			
			JarEntry projectXMLEntry = jarFile.getJarEntry("HullFrame.xml");
			if (projectXMLEntry != null)
			{
				loadProjectValues(jarFile.getInputStream(projectXMLEntry), p);
			}
			else
				throw new Exception("Invalid project file");
			
			while (entries.hasMoreElements())
			{
				JarEntry entry = (JarEntry) entries.nextElement();
				System.out.println(entry.getName());
				
			}

			p.setTempLocation(tempDir);
			return p;
		} catch (Exception ex)
		{
			throw ex;
		} finally
		{
			if (jarFile != null)
			{
				jarFile.close();
			}
		}
	}

	/*
	private static void addFileToJar(File source, String fileName, JarOutputStream target)
			throws IOException
	{
		BufferedInputStream in = null;
		try
		{
			if (source.isDirectory())
			{
				String name = source.getPath().replace("\\", "/");
				if (!name.isEmpty())
				{
					if (!name.endsWith("/"))
						name += "/";
					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles())
					addFileToJar(nestedFile, nestedFile.getName(), target);
				return;
			}

			JarEntry entry = new JarEntry(fileName);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true)
			{
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally
		{
			if (in != null)
				in.close();
		}
	}
	*/
}
