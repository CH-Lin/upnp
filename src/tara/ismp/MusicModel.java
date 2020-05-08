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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MusicModel implements EventSource {

	private String name = "";

	private String playstatus = "STOP";

	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(
			this);

	public MusicModel() {
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(propertyName, listener);
	}

	public void setName(String n) {
		String oldStatus = name;
		name = n;
		propertySupport.firePropertyChange("Music", oldStatus, name);

	}

	public void doSwitch(boolean value) {
		if (value)
			play();
		else
			pause();
	}

	public void play() {
		String oldStatus = playstatus;
		playstatus = "PLAY";
		propertySupport.firePropertyChange("Status", oldStatus, playstatus);
	}

	public void pause() {
		String oldStatus = playstatus;
		playstatus = "PAUSE";
		propertySupport.firePropertyChange("Status", oldStatus, playstatus);
	}

	public void stop() {
		String oldStatus = playstatus;
		playstatus = "STOP";
		propertySupport.firePropertyChange("Status", oldStatus, playstatus);
	}

	/**
	 * @return
	 */
	public String getPlayStatus() {
		return playstatus;
	}

	/**
	 * @return
	 */
	public String getMusicName() {
		return name;
	}

}
