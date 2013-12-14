package solitaire.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import solitaire.application.Colonne;
import solitaire.application.Solitaire;
import solitaire.application.TasDeCartesColorees;
import solitaire.application.Usine;
import solitaire.controle.CColonne;
import solitaire.controle.CSabot;
import solitaire.controle.CTasDeCartesColorees;
import solitaire.controle.CUsine;
import solitaire.presentation.PColonne;
import solitaire.presentation.PSabot;
import solitaire.presentation.PTasDeCartes;

public class SolitaireGLI extends Solitaire {

	private JPanel sabotp;
	private JPanel couleurs;
	private JPanel colonnes;
	private JPanel haut;
	private JFrame frame;

	public SolitaireGLI(String name, Usine usine) {
		super(name, usine);
		sabotp = new JPanel();
		couleurs = new JPanel();
		colonnes = new JPanel();
		haut = new JPanel();
		frame = new JFrame(name);
	}

	@Override
	public void initialiser() {
		super.initialiser();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout()); // au lieu de BorderLayout par
												// defaut
		frame.getContentPane().setBackground(new Color(0, 102, 0)); // violet
																	// pele

		colonnes.setOpaque(false);
		haut.setOpaque(false);
		couleurs.setOpaque(false);
		sabotp.setOpaque(false);
		sabotp.add(((CSabot) sabot).getPresentation());

		for (TasDeCartesColorees t : pilesColorees) {
			JPanel uneCouleur = new JPanel();
			uneCouleur.setOpaque(false);
			uneCouleur.setLayout(null);
			uneCouleur.setSize(PColonne.WIDTH, PSabot.HEIGHT);
			uneCouleur.setPreferredSize(uneCouleur.getSize());
			uneCouleur.setBorder(BorderFactory.createLineBorder(Color.black));

			PTasDeCartes presentation = ((CTasDeCartesColorees) t)
					.getPresentation();
			uneCouleur.add(presentation);
			int x = uneCouleur.getWidth() / 2 - presentation.getWidth() / 2;
			int y = uneCouleur.getHeight() / 2 - presentation.getHeight() / 2;
			presentation.setLocation(x, y);

			String fileName = ((CTasDeCartesColorees) t).getNom().trim()
					+ ".png";
			ImageIcon image = new ImageIcon(
					ClassLoader.getSystemResource(fileName));
			JLabel labelImage = new JLabel(image);
			labelImage.setSize(image.getIconWidth(), image.getIconHeight());
			labelImage.setPreferredSize(labelImage.getSize());
			uneCouleur.add(labelImage);
			x = uneCouleur.getWidth() / 2 - labelImage.getWidth() / 2;
			y = uneCouleur.getHeight() / 2 - labelImage.getHeight() / 2;
			labelImage.setLocation(x, y);

			couleurs.add(uneCouleur);
		}

		for (Colonne c : pilesAlternees) {
			colonnes.add(((CColonne) c).getPresentation());
		}

		haut.add(sabotp, BorderLayout.WEST);
		haut.add(couleurs, BorderLayout.EAST);

		frame.getContentPane().add(haut, BorderLayout.NORTH);
		frame.getContentPane().add(colonnes, BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension(950, 600));
		frame.pack(); // dimensionner le cadre
		frame.setLocation(200, 100); // le positionner
		frame.setVisible(true); // et le rendre visible
		frame.setResizable(false);
	}

	public static void main(String args[]) {
		// Creation des usines
		CUsine cu = new CUsine();

		// Creation du solitaire
		SolitaireGLI solitaire = new SolitaireGLI("Solitaire GLI", cu);

		// Initialisation du solitaire
		solitaire.initialiser();
		solitaire.jouer();
	}

}