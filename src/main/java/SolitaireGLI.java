import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import solitaire.application.Colonne;
import solitaire.application.Solitaire;
import solitaire.application.TasDeCartesColorees;
import solitaire.application.Usine;
import solitaire.controle.CCarte;
import solitaire.controle.CColonne;
import solitaire.controle.CSabot;
import solitaire.controle.CTasDeCartes;
import solitaire.controle.CTasDeCartesColorees;
import solitaire.controle.CUsine;


public class SolitaireGLI extends Solitaire {
	
	private Set<CCarte> cartes;

	public SolitaireGLI(String arg0, Usine arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void initialiser() {
		super.initialiser();
		
	}

	public static void main(String args[]) {
		JPanel sabot = new JPanel();
		JPanel couleurs = new JPanel();
		JPanel colonnes = new JPanel();
		JPanel haut = new JPanel();
		JFrame f = new JFrame("Solitaire");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout()); // au lieu de BorderLayout par defaut
		f.getContentPane().setBackground(new Color(143, 143, 195)); // violet pele

		// Creation des usines
		CUsine cu = new CUsine();
		
		// Creation du solitaire
		SolitaireGLI solitaire = new SolitaireGLI("Solitaire GLI", cu);
		
		// Initialisation du solitaire
		solitaire.initialiser();
		
		sabot.add(((CSabot) solitaire.sabot).getPresentation());
		
		for (TasDeCartesColorees t : solitaire.pilesColorees) {
			couleurs.add(((CTasDeCartesColorees) t).getPresentation());
		}
		
		for (Colonne c : solitaire.pilesAlternees) {
			colonnes.add(((CColonne) c).getPresentation());
		}
		
		haut.add(sabot, BorderLayout.WEST);
		haut.add(couleurs, BorderLayout.EAST);
		f.getContentPane().add(haut, BorderLayout.NORTH);
		f.getContentPane().add(colonnes, BorderLayout.CENTER);
		f.setMinimumSize(new Dimension(400, 800));
		f.pack(); // dimensionner le cadre
		f.setLocation(200, 100); // le positionner
		f.setVisible(true); // et le rendre visible
	} // main

}
