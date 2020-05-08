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

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.osgi.framework.BundleException;

public class MusicUI extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private final static ImageIcon STOP = MusicUI.loadIcon("STOP.png", "Stop");

	private final static ImageIcon PLAY = MusicUI.loadIcon("PLAY.png", "Play");

	private final static ImageIcon PAUSE = MusicUI.loadIcon("PAUSE.png",
			"FAILURE");

	private final JLabel label = new JLabel();

	private final JLabel label2 = new JLabel();

	private JTextField text = new JTextField("C:"
			+ System.getProperty("file.separator") + "test.wav");

	private MusicModel model;

	private Player p;

	public MusicUI(MusicModel model) {
		super("Tara Music Control");
		this.setSize(150, 150);
		this.setResizable(false);
		this.model = model;

		model.setName(text.getText());

		text.setPreferredSize(new Dimension(100, 22));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(setActions(), BorderLayout.WEST);
		panel.add(text, BorderLayout.CENTER);
		getContentPane().add(panel);
		model.addPropertyChangeListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					Activator.context.getBundle().stop();

					if (p != null)
						p.exit();

				} catch (BundleException ex) {
					ex.printStackTrace();
				}
			}
		});

		pack();
		setVisible(true);
	}

	private JPanel setActions() {
		JPanel panel = new JPanel();
		label.setIcon(PLAY);
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						Icon icon = label.getIcon();
						if (icon == PAUSE)
							model.pause();
						else
							model.play();
					}
				}
			}
		});
		label2.setIcon(STOP);
		label2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						model.stop();
						label.setIcon(PLAY);
					}
				}
			}
		});
		panel.add(label);
		panel.add(label2);

		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choseFile();
			}
		});

		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				choseFile();
			}

			public void keyReleased(KeyEvent arg0) {
				choseFile();
			}

			public void keyTyped(KeyEvent arg0) {
				choseFile();
			}
		});

		text.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				choseFile();
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		return panel;
	}

	public static ImageIcon loadIcon(String path, String title) {
		try {
			URL eventIconUrl = MusicUI.class.getResource("images/" + path);
			return new ImageIcon(eventIconUrl, title);
		} catch (Exception ex) {
			System.out.println("Resource:" + path + " not found : "
					+ ex.toString());
			return null;
		}
	}

	private void choseFile() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"WAV file", "wav");
		JFileChooser fileChooser = new JFileChooser(System
				.getProperty("user.dir"));

		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			text.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		model.setName(text.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String property = evt.getPropertyName();

		if (property.equals("Music")) {
			String value = ((String) evt.getNewValue()).toString();
			text.setText(value);
		} else if (property.equals("Status")) {
			// playing = ((Boolean) evt.getNewValue()).booleanValue();
			String value = ((String) evt.getNewValue()).toString();

			if (value.equalsIgnoreCase("PLAY")) {
				if (p == null) {
					p = new Player(model);

					if (p.openAudioFile(text.getText())) {
						p.start();
						label.setIcon(PAUSE);
					}
				} else {
					p.playOrpause(false);
					label.setIcon(PAUSE);
				}

			} else if (value.equalsIgnoreCase("STOP")) {

				if (p != null) {
					p.exit();
					p = null;
					label.setIcon(PLAY);
				}

			} else if (value.equalsIgnoreCase("PAUSE")) {
				if (p != null) {
					label.setIcon(PLAY);
					p.playOrpause(true);
				}
			}

		}
		getContentPane().validate();
		repaint();

	}
}
