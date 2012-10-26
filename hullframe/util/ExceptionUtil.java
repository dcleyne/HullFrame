package hullframe.util;

import java.io.*;

public class ExceptionUtil
{
	//Provides exception utility functions
    private ExceptionUtil()
    {
        
    }
    
    public static String getExceptionStackTrace(Exception e)
    {     
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
