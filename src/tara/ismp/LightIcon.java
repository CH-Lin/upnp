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
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.upnp.*;


public class LightIcon implements UPnPIcon {

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getMimeType()
	 */
	public String getMimeType() {
		return "image/png";
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getWidth()
	 */
	public int getWidth() {
		return 32;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getHeight()
	 */
	public int getHeight() {
		return 32;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getSize()
	 */
	public int getSize() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getDepth()
	 */
	public int getDepth() {
		return 16;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.upnp.UPnPIcon#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return LightIcon.class.getResourceAsStream("images/PLAY.png");
	}
}
