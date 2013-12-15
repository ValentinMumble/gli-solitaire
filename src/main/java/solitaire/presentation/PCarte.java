package solitaire.presentation;

//import solitaire.controle.* ;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import solitaire.controle.CCarte;

/**
 * Composant Presentation d'une carte
 */
public class PCarte extends JPanel implements Transferable {

	private static final long serialVersionUID = 1L;
	protected CCarte controle; // controleur associe
	protected JLabel face, dos;
	protected ImageIcon icone; // image de la face
	protected static ImageIcon iconeDos; // image du dos
	public static int WIDTH, HEIGHT;

	/**
	 * initialiser une carte
	 * 
	 * @param chaine
	 *            : nom de la carte (exemple "3H" = 3 Heart)
	 */
	public PCarte(final String chaine, final CCarte controle) {
		this.controle = controle;

		// image de la face
		icone = new ImageIcon(ClassLoader.getSystemResource(chaine + ".png"));
		face = new JLabel(icone);
		add(face);
		face.setLocation(0, 0);
		face.setSize(WIDTH, HEIGHT);

		// image du dos
		dos = new JLabel(iconeDos);
		add(dos);
		dos.setLocation(0, 0);
		dos.setSize(WIDTH, HEIGHT);

		// le JPanel
		setLayout(null);
		setBackground(Color.white);
		setOpaque(true);
		setSize(face.getSize());
		setPreferredSize(getSize());
		setFaceVisible(false);
	} // constructeur

	/**
	 * changer la visibilite de la carte
	 * 
	 * @param faceVisible
	 *            : vrai si la face est visible, faux sinon
	 */
	public void setFaceVisible(boolean faceVisible) {
		face.setVisible(faceVisible);
		dos.setVisible(!faceVisible);
	}

	public ImageIcon getIcone() {
		return icone;
	}

	/**
	 * initialiser l'image du dos et les dimensions d'une PCarte
	 */
	static {
		iconeDos = new ImageIcon(ClassLoader.getSystemResource("dos.png"));
		WIDTH = iconeDos.getIconWidth();
		HEIGHT = iconeDos.getIconHeight();
	}

	/**
	 * 
	 * @return le controleur correspondant a cette PCarte
	 */
	public CCarte getControle() {
		return controle;
	}

	/**
	 * Retourne this si la flavor demandee correspond au type objet
	 */
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		Object result = null;
		if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
			result = this;
		}
		return result;
	}

	/**
	 * Retourne les types de flavor supportes
	 */
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor data[] = new DataFlavor[2];
		try {
			data[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (java.lang.ClassNotFoundException e) {
		}
		return (data);
	}

	/**
	 * Retourne vrai si la flavor passee en parametre est supportee (Local Object ici)
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType));
	}

}
