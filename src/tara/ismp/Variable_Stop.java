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
import org.osgi.service.upnp.*;

public class Variable_Stop implements UPnPStateVariable{
	
	final private String NAME = "Stop";
	final private Boolean DEFAULT_VALUE = Boolean.TRUE;
	
	
	public Variable_Stop(){
	}
	
	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getName()
	 */
	public String getName() {
		return NAME;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getJavaDataType()
	 */
	public Class getJavaDataType() {
		return Boolean.class;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getUPnPDataType()
	 */
	public String getUPnPDataType() {
		return TYPE_BOOLEAN;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getDefaultValue()
	 */
	public Object getDefaultValue() {
		return DEFAULT_VALUE;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getAllowedValues()
	 */
	public String[] getAllowedValues() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getMinimum()
	 */
	public Number getMinimum() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getMaximum()
	 */
	public Number getMaximum() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#getStep()
	 */
	public Number getStep() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPStateVariable#sendsEvents()
	 */
	public boolean sendsEvents() {
		return false;
	}
}
