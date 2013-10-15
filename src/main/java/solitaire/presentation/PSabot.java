package solitaire.presentation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import solitaire.controle.CSabot;

public class PSabot extends JPanel {
	
	private CSabot controle;
	
	private PTasDeCartes cachees;
	private PTasDeCartes visibles;
	
	private RetournerTasListener rtl;
	private RetournerCarteListener rcl;

	public PSabot(CSabot cSabot, PTasDeCartes c,
			PTasDeCartes v) {
		controle = cSabot;
		cachees = c;
		visibles = v;
		add(cachees);
		add(visibles);
		cachees.setDxDy(0, 0);
		visibles.setDxDy(-15, 0);
		rtl = new RetournerTasListener();
		rcl = new RetournerCarteListener();
	}

	public void activerRetournerCarte() {
		cachees.addMouseListener(rcl);
	}
	
	public void activerRetournerTas() {
		cachees.addMouseListener(rtl);
	}
	
	public void desactiverRetournerCarte() {
		cachees.removeMouseListener(rcl);
	}
	
	public void desactiverRetournerTas() {
		cachees.removeMouseListener(rtl);
	}
	
	private class RetournerTasListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			controle.retourner();
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
