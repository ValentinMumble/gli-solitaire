package solitaire.controle;

import solitaire.application.Carte;
import solitaire.presentation.PCarte;

public class CCarte extends Carte {
	
	private PCarte p;

	public CCarte(int v, int c) {
		super(Math.min(Math.max(1, v), Carte.NbCartesParCouleur), Math.min(Math.max(1, c), Carte.NbCouleurs));
		p = new PCarte(Carte.valeurs[getValeur()-1] + Carte.couleurs[getCouleur()-1], this);
		p.setFaceVisible(isFaceVisible());
	}
	
	public void setFaceVisible(boolean v) {
		super.setFaceVisible(v);
		p.setFaceVisible(isFaceVisible());
	}
	
	public PCarte getPresentation() {
		return p;
	}

}
