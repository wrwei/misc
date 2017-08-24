package emf.compare.modelio.util;

import java.io.File;
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
	
	public static ArrayList<String> listEverything(String directoryName)
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
	
	public static ArrayList<File> listAllFiles(String directoryName)
	{
		ArrayList<File> result = new ArrayList<File>();
		
		File directory = new File(directoryName);
		
		File[] fList = directory.listFiles();
		
		for(File file: fList)
		{
			if (file.isFile()) {
				if (file.getName().contains("DS_Store") || file.getName().contains(".project")) {
					
				}
				else {
					result.add(file);	
				}
			}
			else if (file.isDirectory()) {
				result.addAll(listAllFiles(file.getAbsolutePath()));
			}
		}
		return result;
	}
	
	public static File findCounterPart(String fileName, String directoryName)
	{
		ArrayList<File> fList = PathHelper.listAllFiles(directoryName);
		for(File file: fList)
		{
			if (file.getName().equals(fileName)) {
				return file;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ArrayList<String> result = PathHelper.listEverything("model/0");
		for(String str: result)
		{
			System.out.println(str);
		}
	}
}
