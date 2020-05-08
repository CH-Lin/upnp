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

import java.util.HashMap;

import org.osgi.service.upnp.*;

public class PowerSwitchService implements UPnPService {

	final private String SERVICE_ID = "urn:upnp-org:serviceId:MusicControl:1";

	final private String SERVICE_TYPE = "urn:schemas-upnp-org:service:MusicControl:1";

	final private String VERSION = "1";

	private UPnPStateVariable status, play, music, stop;

	private UPnPStateVariable[] states;

	private HashMap<String, UPnPAction> actions = new HashMap<String, UPnPAction>();

	MusicModel model;

	public PowerSwitchService(MusicModel model) {
		this.model = model;
		status = new Variable_PlayStatus();
		play = new Variable_PlayOrPause();
		music = new Variable_MusicName();
		stop = new Variable_Stop();
		this.states = new UPnPStateVariable[] { status, play, stop, music };

		UPnPAction setPlayOrPause = new Action_SetPlayOrPause(model, play);
		UPnPAction getPlayStatus = new Action_GetPlayStatus(model, status);
		UPnPAction setMusicName = new Action_SetMusicName(model, music);
		UPnPAction getMusicName = new Action_GetMusicName(model, music);
		UPnPAction setStop = new Action_SetStop(model, stop);
		actions.put(setPlayOrPause.getName(), setPlayOrPause);
		actions.put(getPlayStatus.getName(), getPlayStatus);
		actions.put(setMusicName.getName(), setMusicName);
		actions.put(getMusicName.getName(), getMusicName);
		actions.put(setStop.getName(), setStop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getId()
	 */
	public String getId() {
		return SERVICE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getType()
	 */
	public String getType() {
		return SERVICE_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getVersion()
	 */
	public String getVersion() {
		return VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getAction(java.lang.String)
	 */
	public UPnPAction getAction(String name) {
		return (UPnPAction) actions.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getActions()
	 */
	public UPnPAction[] getActions() {
		return (UPnPAction[]) (actions.values()).toArray(new UPnPAction[] {});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getStateVariables()
	 */
	public UPnPStateVariable[] getStateVariables() {
		return states;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.upnp.UPnPService#getStateVariable(java.lang.String)
	 */
	public UPnPStateVariable getStateVariable(String name) {
		if (name.equals("Status"))
			return status;
		else if (name.equals("Play"))
			return play;
		else if (name.equals("Stop"))
			return stop;
		else if (name.equals("music"))
			return music;
		else
			return null;
	}
}
