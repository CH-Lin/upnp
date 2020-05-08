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

import org.osgi.service.upnp.*;


public class Action_SetPlayOrPause implements UPnPAction {

	final private String NAME = "SetPlayOrPause";
	final private String NEW_TARGET_VALUE = "NewPlayValue";
	final private String[] IN_ARG_NAMES = new String[]{NEW_TARGET_VALUE};
	private UPnPStateVariable state;
	private MusicModel model;
	
	
	public Action_SetPlayOrPause(MusicModel model,UPnPStateVariable state){
		this.state = state;
		this.model=model;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#getName()
	 */
	public String getName() {
		return NAME;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#getReturnArgumentName()
	 */
	public String getReturnArgumentName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#getInputArgumentNames()
	 */
	public String[] getInputArgumentNames() {
		return IN_ARG_NAMES;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#getOutputArgumentNames()
	 */
	public String[] getOutputArgumentNames() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#getStateVariable(java.lang.String)
	 */
	public UPnPStateVariable getStateVariable(String argumentName) {
		return state;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPAction#invoke(java.util.Dictionary)
	 */
	public Dictionary invoke(Dictionary args) throws Exception {
		Boolean value = (Boolean) args.get(NEW_TARGET_VALUE);
		model.doSwitch(value.booleanValue());
		return null;
	}
}
