package emf.compare.modelio;

import java.io.File;
import java.util.ArrayList;

import emf.compare.modelio.util.PathHelper;

public class BatchComparator {

	public static void main(String[] args) {
		ArrayList<File> files = PathHelper.listAllFiles("model/0/modelio");
		for(File f: files)
		{
			System.out.println(f.getAbsolutePath());
			File counterpart = PathHelper.findCounterPart(f.getName(), "model/1");
			Comparator comparator = new Comparator(f, counterpart);
			comparator.run();
		}
	}
}
