//@author A0088617R
package logAndException;
import global.Clock;

import java.io.File;
import java.util.logging.*;

import org.joda.time.DateTime;
  
public class Log {
	private static final Logger messageLogger=Logger.getLogger("Message");
	private static final Logger errorLogger=Logger.getLogger("Error");
	private static FileHandler fh=null;
	private static FileHandler fh2=null;
	public static void setup() throws Exception
	{	File dir = new File("Log Files");  
		dir.mkdir();
		String fileName="Log Files/MessageLog -" + Clock.toString(DateTime.now()).replace(":", "").replace("/", "") + ".txt";
		fh = new FileHandler(fileName, true);
		String fileName2="Log Files/ErrorLog -" + Clock.toString(DateTime.now()).replace(":", "").replace("/", "") + ".txt";
		fh2 = new FileHandler(fileName2, true);	
		fh.setFormatter(new SimpleFormatter());
		fh2.setFormatter(new SimpleFormatter());
		messageLogger.addHandler(fh);
		messageLogger.setLevel(java.util.logging.Level.ALL);
		errorLogger.addHandler(fh2);
		errorLogger.setLevel(java.util.logging.Level.SEVERE);
 	}
	public static void toLog(int Level,String message) throws Exception
	{
		if(fh!=null)
		{
		if(Level==0)
			messageLogger.info(message);
		else if(Level==1)
			messageLogger.warning(message);
		else
			errorLogger.severe(message);
		}
	}
	
}
