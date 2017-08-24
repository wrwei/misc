package emf.compare.modelio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.FeatureMapChange;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;

public class FileChange {

	protected File file;
	
	protected Comparison comparison;
	
	protected boolean counterpartExists = true;
	
	public FileChange(File f, boolean counterpartExists)
	{
		file = f;
		this.counterpartExists = counterpartExists;
	}
	
	public FileChange(File f, Comparison comp)
	{
		file = f;
		comparison = comp;
	}
	
	public String getPath() {
		return file.getAbsolutePath();
	}
	
	public File getFile() {
		return file;
	}
	
	
	public boolean counterpartExists()
	{
		return counterpartExists;
	}
	
	public ArrayList<AttributeChange> getAttributeChanges()
	{
		ArrayList<AttributeChange> changes = new ArrayList<AttributeChange>();
		for(Diff diff: comparison.getDifferences())
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
		for(Diff diff: comparison.getDifferences())
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
		for(Diff diff: comparison.getDifferences())
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
		for(Diff diff: comparison.getDifferences())
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
		return (ArrayList<Diff>) comparison.getDifferences();
	}
	
	public ArrayList<Match> getMatchs()
	{
		return (ArrayList<Match>) comparison.getMatches();
	}
	
	public void writeToFile()
	{
		List<Diff> list = comparison.getDifferences();
		try {
			String filename = "data/"+file.getName()+".txt";
			PrintWriter writer = new PrintWriter(filename);
			List<MatchResource> resources = comparison.getMatchedResources();
			for (MatchResource resource: resources) {
				writer.println(resource);
			}
			for(Diff diff: list)
			{
				String remark = "";
				if (diff instanceof ResourceAttachmentChange) {
					ResourceAttachmentChange change = (ResourceAttachmentChange) diff;
					remark += "ResourceAttachmentChange " + change.getResourceURI();
				}
				if (diff instanceof AttributeChange) {
					AttributeChange change = (AttributeChange) diff;
					remark += "AttributeChange " + change.getAttribute() + " - " + change.getValue();
				}
				if (diff instanceof ReferenceChange) {
					
					ReferenceChange change = (ReferenceChange) diff;
					remark += "ReferenceChange" + change.getReference() + " - " + change.getValue();
				}
				if (diff instanceof FeatureMapChange) {
					FeatureMapChange change = (FeatureMapChange) diff;
					remark += "FeatureMapChange " + change.getAttribute() + " - " + change.getValue();
				}
				remark += "\n" + diff.getSource() + ": " + diff.getKind() + " " + diff.getMatch().getLeft() + " --- " + diff.getMatch().getRight();
				writer.println(remark);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
