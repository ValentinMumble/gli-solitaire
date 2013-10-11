package solitaire.presentation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import solitaire.controle.CColonne;

public class PColonne extends JPanel {
	
	private CColonne controle;
	
	private PTasDeCartes cachees;
	private PTasDeCartes visibles;
	
	private RetournerCarteListener rcl;

	public PColonne(CColonne cColonne, PTasDeCartes c,
			PTasDeCartesAlternees v) {
		controle = cColonne;
		cachees = c;
		visibles = v;
		add(cachees);
		add(visibles);
		cachees.setDxDy(0, 15);
		visibles.setDxDy(0, 25);
	}
	
	public void activerRetournerCarte() {
		cachees.addMouseListener(rcl);
	}

	public void desactiverRetournerCarte() {
		cachees.removeMouseListener(rcl);
	}
	
	private class RetournerCarteListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			controle.retournerCarte();
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseReleased(MouseEvent e) {
			
		}
		
	}

}
