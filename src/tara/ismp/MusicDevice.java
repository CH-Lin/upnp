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

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.service.upnp.*;


public class MusicDevice implements UPnPDevice {

	final private String DEVICE_ID = "uuid:ISTI-CNR-MusicControl";
	private BundleContext context;
	private MusicModel model;
	private MusicUI ui;
	private PowerSwitchService powerSwitch;
	private UPnPService[] services;
	private Dictionary dictionary;
	private UPnPEventNotifier notifier;
	
	public MusicDevice(BundleContext context) {
		this.context=context;
		model = new MusicModel();
		ui = new MusicUI(model);
		powerSwitch = new PowerSwitchService(model);
		services = new UPnPService[]{powerSwitch};
		setupDeviceProperties();
		buildEventNotifyer();
	}

	/**
	 * 
	 */
	private void buildEventNotifyer() {
		notifier = new UPnPEventNotifier(context,DEVICE_ID,powerSwitch,model);
	}

	@SuppressWarnings("unchecked")
	private void setupDeviceProperties(){
		dictionary = new Properties();
		dictionary.put(UPnPDevice.UPNP_EXPORT,"");
		dictionary.put(
		        org.osgi.service
		        	.device.Constants.DEVICE_CATEGORY,
	        	new String[]{UPnPDevice.DEVICE_CATEGORY}
	        );
		//dictionary.put(UPnPDevice.DEVICE_CATEGORY,new String[]{UPnPDevice.DEVICE_CATEGORY});
		dictionary.put(UPnPDevice.FRIENDLY_NAME,"Tara Music Player");
		dictionary.put(UPnPDevice.MANUFACTURER,"Tara Comp.");
		dictionary.put(UPnPDevice.MANUFACTURER_URL,"http://edwardlin.blog.shinobi.jp/");
		dictionary.put(UPnPDevice.MODEL_DESCRIPTION,"OSGI UPnP HW.");
		dictionary.put(UPnPDevice.MODEL_NAME,"Tara Music Player");
		dictionary.put(UPnPDevice.MODEL_NUMBER,"1.0");
		dictionary.put(UPnPDevice.MODEL_URL,"http://edwardlin.blog.shinobi.jp/");
		dictionary.put(UPnPDevice.PRESENTATION_URL,"http://edwardlin.blog.shinobi.jp/");
		dictionary.put(UPnPDevice.SERIAL_NUMBER,"19840109");
		dictionary.put(UPnPDevice.TYPE,"urn:taea-upnp-org:device:MusicControl:1");
		dictionary.put(UPnPDevice.UDN,DEVICE_ID);
		//dictionary.put(UPnPDevice.ID,dictionary.get(UPnPDevice.UDN));
		dictionary.put(UPnPDevice.UPC,"19840109");
	}
	
	
	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPDevice#getService(java.lang.String)
	 */
	public UPnPService getService(String serviceId) {
		if  (serviceId.equals(powerSwitch.getId())) return powerSwitch;
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPDevice#getServices()
	 */
	public UPnPService[] getServices() {
		return services;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPDevice#getIcons(java.lang.String)
	 */
	public UPnPIcon[] getIcons(String locale) {
		UPnPIcon icon = new LightIcon();
		return new UPnPIcon[]{icon} ;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPDevice#getDescriptions(java.lang.String)
	 */
	public Dictionary getDescriptions(String locale) {
		return dictionary;
	}

	/**
	 * 
	 */
	public void close() {
		ui.dispose();
		notifier.destroy();
	}
	
}
