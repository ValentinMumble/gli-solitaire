package solitaire.controle;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {
	
	private PColonne p;

	public CColonne(String nom, CUsine u) {
		super(nom, u);
		p = new PColonne(this, ((CTasDeCartes) cachees).getPresentation(), ((CTasDeCartesAlternees) visibles).getPresentation());
	}
	
	public void setReserve(Tas t) {
		super.setReserve(t);
		if (isCarteRetournable()) {
			p.desactiverRetournerCarte();
		}
		p.setCorrectSize();
	}
	
	public void retournerCarte() {
		try {
			super.retournerCarte();
			if (isCarteRetournable()) {
				p.activerRetournerCarte();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public PColonne getPresentation() {
		return p;
	}
}
