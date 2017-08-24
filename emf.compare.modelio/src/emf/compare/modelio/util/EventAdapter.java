package emf.compare.modelio.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.XMIResource;

public class EventAdapter extends EContentAdapter {

	
	@Override
	public void notifyChanged(Notification notification) {
		XMIResource obj = (XMIResource) notification.getNewValue();
		URI uri = obj.getURI();
		if (!uri.hasAbsolutePath()) {
			System.out.println(uri.path());
		}
		else {
			System.out.println(uri.path());
		}
	}
}
