package emf.compare.modelio;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.FeatureMapChange;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;

public class ChangeContainer {

	ArrayList<FileChange> fileChanges = new ArrayList<FileChange>();
	
	public ChangeContainer()
	{
		
	}
	
	public void addMatch(File file, Comparison comp)
	{
		if (!containsFileChange(file)) {
			FileChange change = new FileChange(file, comp);
			fileChanges.add(change);
		}
	}
	
	public boolean containsFileChange(File file)
	{
		for(FileChange fc: fileChanges)
		{
			if (fc.getPath().equals(file.getAbsoluteFile())) {
				return true;
			}
		}
		return false;
	}
	
	public FileChange getFileChange(File f)
	{
		for(FileChange fc: fileChanges)
		{
			if (fc.getPath().equals(f.getAbsoluteFile())) {
				return fc;
			}
		}
		return null;
	}
	
	public ArrayList<Diff> getAllDiffs()
	{
		ArrayList<Diff> result = new ArrayList<Diff>();
		for(FileChange fc: fileChanges)
		{
			result.addAll(fc.getDiffs());
		}
		return result;
	}
	
	public ArrayList<ResourceAttachmentChange> getAllResourceChanges()
	{
		ArrayList<ResourceAttachmentChange> result = new ArrayList<ResourceAttachmentChange>();
		for(FileChange fc: fileChanges)
		{
			result.addAll(fc.getResourceChanges());
		}
		return result;
	}
	
	public ArrayList<AttributeChange> getAllAttributeChanges()
	{
		ArrayList<AttributeChange> result = new ArrayList<AttributeChange>();
		for(FileChange fc: fileChanges)
		{
			result.addAll(fc.getAttributeChanges());
		}
		return result;
	}
	
	public ArrayList<ReferenceChange> getAllReferenceChanges()
	{
		ArrayList<ReferenceChange> result = new ArrayList<ReferenceChange>();
		for(FileChange fc: fileChanges)
		{
			result.addAll(fc.getReferenceChanges());
		}
		return result;
	}
	
	public ArrayList<FeatureMapChange> getAllFeatureMapChanges()
	{
		ArrayList<FeatureMapChange> result = new ArrayList<FeatureMapChange>();
		for(FileChange fc: fileChanges)
		{
			result.addAll(fc.getFeatureMapChanges());
		}
		return result;
	}
}
