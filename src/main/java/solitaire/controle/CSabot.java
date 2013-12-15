package solitaire.controle;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PSabot;

public class CSabot extends Sabot {
	
	private PSabot p;
	private CTasDeCartes selectedCards;

	// Constructeur du CSabot de nom nom et d'usine u
	public CSabot(String nom, CUsine u) {
		super(nom, u);
		p = new PSabot(this, ((CTasDeCartes) cachees).getPresentation(), ((CTasDeCartes) visibles).getPresentation());
	}
	
	// setReserve recopie un tas de cartes dans le tas de cartes cach�es du CSabot
	public void setReserve(Tas t) {
		super.setReserve(t);
		if (isCarteRetournable()) {
			p.activerRetournerCarte();
		}
	}
	
	// retourner permet de le sabot
	public void retourner() {
		try {
			super.retourner();
			if (!isRetournable()) {
				p.desactiverRetournerCarte();
			}
			if (isCarteRetournable()) {
				p.activerRetournerCarte();
			}
		} catch (Exception e) {
		}
	}
	
	// retournerCarte permet de retourner une carte
	public void retournerCarte() {
		try {
			super.retournerCarte();
			if (isRetournable()) {
				p.desactiverRetournerCarte();
				p.activerRetournerTas();
			}
		} catch (Exception e) {
		}
		
	}
	
	// depiler permet de retirer la carte au sommet de la pile
	public void depiler() {
		try {
			super.depiler();
			if (!isRetournable()) {
				p.desactiverRetournerTas();
			}
		} catch (Exception e) {
		}
	}
	
	// getPresentation permet de r�cup�rer la pr�sentation associ�e au CSabot
	public PSabot getPresentation() {
		return p;
	}

	// p2c_debutDnd r�cup�re la selectedCard s�lectionn� et l'ajoute dans un CTasDeCartes
	public void p2c_debutDnd(CCarte cc) {
		try {
			selectedCards = new CTasDeCartes("tas", new CUsine());
			selectedCards.empiler(cc);
			if (cc == getSommet()){
				depiler();
				p.c2p_debutDnDOK(selectedCards.getPresentation());
			} else {
				p.c2p_debutDnDKO(selectedCards.getPresentation());
			}
		} catch (Exception e) {
		}
	}

	// p2c_dragDropEnd r�empile les cartes s�lectionn�s si il y a une erreur lors du drop
	public void p2c_dragDropEnd(boolean dropSuccess) {
		if (! dropSuccess){
			empiler(selectedCards);
		}
	}
	
}
