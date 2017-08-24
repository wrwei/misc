package emf.compare.modelio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import emf.compare.modelio.util.PathHelper;

public class Comparator {

	protected File file1;
	protected File file2;
	
	PathHelper pathHelper = new PathHelper();
	ArrayList<FileChange> fileChanges = new ArrayList<FileChange>();

	public Comparator(String path1, String path2) {
		file1 = new File(path1);
		file2 = new File(path2);
	}
	
	public Comparator(File f1, File f2) {
		file1 = f1;
		file2 = f2;
	}
	
	public void run()
	{
		if (file1.exists()) {
			if (!file2.exists()) {
				FileChange change = new FileChange(file1.getAbsolutePath(), false);
				fileChanges.add(change);
			}
			else {
				ResourceSet rs1 = new ResourceSetImpl();
				ResourceSet rs2 = new ResourceSetImpl();
				load(file1.getAbsolutePath(), rs1);
				load(file2.getAbsolutePath(), rs2);
				Comparison comparison = compare(rs1, rs2);
				for(Match match: comparison.getMatches())
				{
					System.out.println(match);
					for(Diff diff: match.getDifferences())
					{
						System.out.println("\t" + diff);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ArrayList<File> files = PathHelper.listAllFiles("model/0/modelio");
		File firstFile = files.get(0);
		System.out.println(firstFile.getAbsolutePath());
		File counterpart = PathHelper.findCounterPart(firstFile.getName(), "model/1");
		System.out.println(counterpart.getAbsolutePath());
		Comparator comparator = new Comparator(firstFile, counterpart);
		comparator.run();
	}
	
	public Comparison compare(ResourceSet rs1, ResourceSet rs2) {

		// Configure EMF Compare
		IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
		IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		IMatchEngine.Factory matchEngineFactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
	        matchEngineFactory.setRanking(20);
	        IMatchEngine.Factory.Registry matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
	        matchEngineRegistry.add(matchEngineFactory);
		EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(matchEngineRegistry).build();

		// Compare the two models
		IComparisonScope scope = EMFCompare.createDefaultScope(rs1, rs2);
		return comparator.compare(scope);
	}
	
	private void load(String absolutePath, ResourceSet resourceSet)
	{
		//create ecore resource set
		ResourceSet ecoreResourceSet = new ResourceSetImpl();
		ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("*", new XMIResourceFactoryImpl());
		Resource ecoreResource = ecoreResourceSet.
			createResource(URI.createFileURI(new File("model/modelio-v3.ecore").getAbsolutePath()));
		try {
			ecoreResource.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (EObject o : ecoreResource.getContents()) {
			EPackage ePackage = (EPackage) o;
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		
		//resource factory for modelio models
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
		URI uri = URI.createFileURI(absolutePath);
		resourceSet.getResource(uri, true);
		EcoreUtil.resolveAll(resourceSet);
	}
}
