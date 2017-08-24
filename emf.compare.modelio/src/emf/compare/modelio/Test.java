package emf.compare.modelio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.FeatureMapChange;
import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import emf.compare.modelio.util.ComparisonUtil;
import emf.compare.modelio.util.PathHelper;

public class Test {
	
	PathHelper pathHelper = new PathHelper();

	public static void main(String[] args) {
		Test test = new Test();
		File file1 = new File("model_unfragmented/0");
		File file2 = new File("model_unfragmented/1");
		ResourceSet rs1 = new ResourceSetImpl();
		ResourceSet rs2 = new ResourceSetImpl();
		test.load(file1.getAbsolutePath(), rs1);
		test.load(file2.getAbsolutePath(), rs2);
		Comparison comparison = ComparisonUtil.compare(rs1, rs2);
		List<Diff> list = comparison.getDifferences();
		try {
			PrintWriter writer = new PrintWriter("data/result.txt");
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
	
	public void load(String absolutePath, ResourceSet resourceSet)
	{
		ResourceSet ecoreResourceSet = new ResourceSetImpl();
		ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("*", new XMIResourceFactoryImpl());
		Resource ecoreResource = ecoreResourceSet.
			createResource(URI.createFileURI(new File("model/modelio-v3.ecore").getAbsolutePath()));
		try {
			ecoreResource.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (EObject o : ecoreResource.getContents()) {
			EPackage ePackage = (EPackage) o;
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
//		resourceSet.eAdapters().add(new EventAdapter());
		
		System.out.println("started loading models...");

		ArrayList<String> paths = PathHelper.listEverything(absolutePath);
		for(int i=0; i < paths.size(); i++)
		{
			URI uri = URI.createFileURI(paths.get(i));
			resourceSet.getResource(uri, true);
//			if (contains(path, pathsToIgnore)) {
//				continue L1;
//			}
//			for(Resource resource: resourceSet.getResources())
//			{
//				if (uri.lastSegment().equals(resource.getURI().lastSegment())) {
//					System.out.println(uri.lastSegment() + "-" + resource.getURI().lastSegment());
//					pathsToIgnore.add(path);
//					continue L1;
//				}
//			}
//			resourceSet.getResource(uri, true);
//			EcoreUtil.resolveAll(resourceSet);
		}
		System.out.println("finished loading models...");
	}
	
	public boolean contains(String s, ArrayList<String> list)
	{
		for(String str: list)
		{
			if (str.equals(s)) {
				return true;
			}
		}
		return false;
	}
}
