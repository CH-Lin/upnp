/*
 DomoWare OSGi UPnP Sample Light Device is an implementation of UPnP Device service to be used with OSGi Framework
 Copyright (C) 2004  Matteo Demuru, Francesco Furfari, Stefano "Kismet" Lenzi

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 You can contact us at:
 {matte-d, sygent, kismet-sl} [at] users.sourceforge.net
 */

package tara.ismp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPEventListener;
import org.osgi.service.upnp.UPnPService;
import org.osgi.service.upnp.UPnPStateVariable;

public class UPnPEventNotifier implements PropertyChangeListener,
		ServiceListener {
	BundleContext context;

	String deviceId;

	UPnPService service;

	EventSource source;

	Properties UPnPTargetListener;

	String serviceId;

	Vector<ServiceReference> upnpListeners = new Vector<ServiceReference>();

	public UPnPEventNotifier(BundleContext context, String deviceId,
			UPnPService service, EventSource source) {
		this.context = context;
		this.deviceId = deviceId;
		this.service = service;
		this.source = source;
		this.serviceId = service.getId();
		setupUPnPListenerHouseKeeping(deviceId);
	}

	/**
	 * @param deviceId
	 */
	private void setupUPnPListenerHouseKeeping(String deviceId) {
		UPnPTargetListener = new Properties();
		UPnPTargetListener.put(UPnPDevice.ID, deviceId);
		UPnPTargetListener.put(UPnPService.ID, serviceId);
		String ANY_UPnPEventListener = "(" + Constants.OBJECTCLASS + "="
				+ UPnPEventListener.class.getName() + ")";

		ServiceReference[] listeners = null;
		try {
			listeners = context.getServiceReferences(UPnPEventListener.class
					.getName(), null);
			if (listeners != null) {
				for (int i = 0; i < listeners.length; i++) {
					ServiceReference sr = listeners[i];
					Filter filter = (Filter) sr
							.getProperty(UPnPEventListener.UPNP_FILTER);
					if (filter == null)
						upnpListeners.add(sr);
					else {
						if (filter.match(UPnPTargetListener))
							addNewListener(sr);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			// String filter = "(&" + ANY_UPnPEventListener + deviceId_Filter +
			// ")";
			String filter = ANY_UPnPEventListener;
			context.addServiceListener(this, filter);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		if (source != null) {
			UPnPStateVariable[] vars = service.getStateVariables();
			if (vars != null) {
				for (int i = 0; i < vars.length; i++)
					if (vars[i].sendsEvents())
						source.addPropertyChangeListener(vars[i].getName(),
								this);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		Iterator list = upnpListeners.iterator();
		String property = evt.getPropertyName();
		Object value = evt.getNewValue();
		String valueString = value.toString();
		final Properties events = new Properties();
		events.put(property, valueString);
		while (list.hasNext()) {
			final ServiceReference sr = (ServiceReference) list.next();
			String[] props = sr.getPropertyKeys();
			for (int i = 0; i < props.length; i++) {
				//
			}
			new Thread(null, null, "Notifier") {
				public void run() {
					try {
						UPnPEventListener listener = (UPnPEventListener) context
								.getService(sr);
						listener.notifyUPnPEvent(deviceId, serviceId, events);
						context.ungetService(sr);
					} catch (Exception ex) {
						System.out.println("UPnPEventNotifier Err: " + ex);
					}
				}
			}.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.ServiceListener#serviceChanged(org.osgi.framework.ServiceEvent)
	 */
	public void serviceChanged(ServiceEvent e) {
		switch (e.getType()) {
		case ServiceEvent.REGISTERED: {
			System.out.println();
			ServiceReference sr = e.getServiceReference();
			System.out.println(sr);
			Filter filter = (Filter) sr
					.getProperty(UPnPEventListener.UPNP_FILTER);
			System.out.println(filter);
			if (filter == null)
				addNewListener(sr);
			else {
				if (filter.match(UPnPTargetListener))
					addNewListener(sr);
			}
		}
			;
			break;

		case ServiceEvent.MODIFIED: {
		}
			;
			break;

		case ServiceEvent.UNREGISTERING: {
			removeListener(e.getServiceReference());
		}
			;
			break;

		}

	}

	/**
	 * @param reference
	 */
	private void removeListener(ServiceReference reference) {
		upnpListeners.remove(reference);
	}

	/**
	 * @param reference
	 */
	private void addNewListener(ServiceReference reference) {
		upnpListeners.add(reference);
	}

	public void destroy() {
		context.removeServiceListener(this);
		upnpListeners.removeAllElements();
	}
}
