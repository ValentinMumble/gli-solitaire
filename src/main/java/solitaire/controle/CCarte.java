package solitaire.controle;

import solitaire.application.Carte;
import solitaire.presentation.PCarte;

public class CCarte extends Carte {
	
	private PCarte p;

	// Constructeur de la CCarte de valeur v et de couleur c
	public CCarte(int v, int c) {
		super(Math.min(Math.max(1, v), Carte.NbCartesParCouleur), Math.min(Math.max(1, c), Carte.NbCouleurs));
		p = new PCarte(Carte.valeurs[getValeur()-1] + Carte.couleurs[getCouleur()-1], this);
		p.setFaceVisible(isFaceVisible());
	}
	
	// setFaceVisible met une carte face visible
	public void setFaceVisible(boolean b) {
		super.setFaceVisible(b);
		p.setFaceVisible(isFaceVisible());
	}
	
	// getPresentation retourne la présentation associée à la CCarte
	public PCarte getPresentation() {
		return p;
	}

}
