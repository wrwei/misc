package emf.compare.modelio.util;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class PathHelper {

	public ArrayList<String> ListFilesAndFolders(String directoryName)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		for(File file: fList)
		{
			result.add(file.getAbsolutePath());
		}
		return result;
	}
	
	public ArrayList<String> listFiles(String directoryName)
	{
		ArrayList<String> result = new ArrayList<String>();

		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		
		for(File file: fList)
		{
			if (file.isFile()) {
				result.add(file.getAbsolutePath());
			}
		}
		return result;
	}
	
	public ArrayList<String> listFolders(String directoryName)
	{
		ArrayList<String> result = new ArrayList<String>();

		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		
		for(File file: fList)
		{
			if (file.isDirectory()) {
				result.add(file.getAbsolutePath());
			}
		}
		return result;
	}
	
	public ArrayList<String> listEverything(String directoryName)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		
		for(File file: fList)
		{
			if (file.isFile()) {
				if (file.getName().contains("DS_Store") || file.getName().contains(".project")) {
					
				}
				else {
					result.add(file.getAbsolutePath());	
				}
			}
			else if (file.isDirectory()) {
				result.addAll(listEverything(file.getAbsolutePath()));
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		PathHelper pathHelper = new PathHelper();
		ArrayList<String> result = pathHelper.listEverything("model/0");
		for(String str: result)
		{
			System.out.println(str);
		}
	}
}
