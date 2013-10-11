package solitaire.presentation;

//import solitaire.controle.* ;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import solitaire.application.Carte;
import solitaire.controle.CCarte;

/**
 * Composant Presentation d'une carte
 */
public class PCarte extends JPanel {

	protected CCarte controle ; // controleur associe
	protected JLabel face, dos;
	protected ImageIcon icone; // image de la face
	protected static ImageIcon iconeDos; // image du dos
	public static int largeur, hauteur;

	/**
	 * initialiser une carte
	 * 
	 * @param chaine
	 *            : nom de la carte (exemple "3H" = 3 Heart)
	 */
	public PCarte (final String chaine, final CCarte controle) {
		this.controle = controle ;

		// image de la face
		icone = new ImageIcon(ClassLoader.getSystemResource("cartesCSHD/"
				+ chaine + ".gif"));
		face = new JLabel(icone);
		add(face);
		face.setLocation(0, 0);
		face.setSize(largeur, hauteur);

		// image du dos
		dos = new JLabel(iconeDos);
		add(dos);
		dos.setLocation(0, 0);
		dos.setSize(largeur, hauteur);

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

	// public final CCarte getControle () {
	// return (controle) ;
	// }

	public ImageIcon getIcone() {
		return icone;
	}

	/**
	 * initialiser l'image du dos et les dimensions d'une PCarte
	 */
	static {
		iconeDos = new ImageIcon(
				ClassLoader.getSystemResource("cartesCSHD/dos.jpg"));
		largeur = iconeDos.getIconWidth() +2;
		hauteur = iconeDos.getIconHeight() +2;
	}

	
} // PCarte
