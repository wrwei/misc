package emf.compare.modelio;

import java.util.ArrayList;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.FeatureMapChange;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;

public class FileChange {

	protected String path;
	
	protected Match match;
	
	protected boolean counterpartExists = true;
	
	public FileChange(String path, boolean counterpartExists)
	{
		this.path = path;
		this.counterpartExists = counterpartExists;
	}
	
	public boolean counterpartExists()
	{
		return counterpartExists;
	}
	
	public ArrayList<AttributeChange> getAttributeChanges()
	{
		ArrayList<AttributeChange> changes = new ArrayList<AttributeChange>();
		for(Diff diff: match.getDifferences())
		{
			if (diff instanceof AttributeChange) {
				changes.add((AttributeChange) diff);
			}
		}
		
		return changes;
	}
	
	public ArrayList<FeatureMapChange> getFeatureMapChanges()
	{
		ArrayList<FeatureMapChange> changes = new ArrayList<FeatureMapChange>();
		for(Diff diff: match.getDifferences())
		{
			if (diff instanceof FeatureMapChange) {
				changes.add((FeatureMapChange) diff);
			}
		}
		
		return changes;
	}
	
	public ArrayList<ReferenceChange> getReferenceChanges()
	{
		ArrayList<ReferenceChange> changes = new ArrayList<ReferenceChange>();
		for(Diff diff: match.getDifferences())
		{
			if (diff instanceof ReferenceChange) {
				changes.add((ReferenceChange) diff);
			}
		}
		return changes;
	}
	
	public ArrayList<ResourceAttachmentChange> getResourceChanges()
	{
		ArrayList<ResourceAttachmentChange> changes = new ArrayList<ResourceAttachmentChange>();
		for(Diff diff: match.getDifferences())
		{
			if (diff instanceof ResourceAttachmentChange) {
				changes.add((ResourceAttachmentChange) diff);
			}
		}
		return changes;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Diff> getDiffs()
	{
		return (ArrayList<Diff>) match.getDifferences();
	}
}
