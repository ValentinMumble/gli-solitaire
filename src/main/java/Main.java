import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import solitaire.application.TasDeCartes;
import solitaire.controle.CCarte;
import solitaire.controle.CTasDeCartes;
import solitaire.controle.CUsine;
import solitaire.presentation.PCarte;
import solitaire.presentation.PTasDeCartes;


public class Main {

	public static void main(String args[]) {
		JFrame f = new JFrame("Test PCarte");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(new FlowLayout()); // au lieu de BorderLayout par defaut
		f.getContentPane().setBackground(new Color(143, 143, 195)); // violet
																	// pele

		CUsine u = new CUsine();
		
		CCarte cc = new CCarte(1, 2);
		PCarte pc = new PCarte("4D", cc);
		pc.setFaceVisible(true);
		
		PCarte pc1 = new PCarte("1D", cc);
		pc1.setFaceVisible(false);
		
		
		CTasDeCartes ctas = new CTasDeCartes("ctas", u);
		TasDeCartes tas = new TasDeCartes("tas", u);
		PTasDeCartes ptas = new PTasDeCartes(ctas);
		// une carte visible
		
		
		f.getContentPane().add(ptas);

		// une carte cachee
		
		ptas.empiler(pc);
		ptas.empiler(pc1);

		f.pack(); // dimensionner le cadre
		f.setLocation(200, 100); // le positionner
		f.setVisible(true); // et le rendre visible
	} // main

}
