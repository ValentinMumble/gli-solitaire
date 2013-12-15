package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.Colonne;
import solitaire.application.Sabot;
import solitaire.application.TasDeCartes;
import solitaire.application.TasDeCartesAlternees;
import solitaire.application.TasDeCartesColorees;
import solitaire.application.Usine;

public class CUsine extends Usine {

	// Usine des différents éléments du Solitaire.
	
	@Override
	public Carte newCarte(int v, int c) {
		return new CCarte(v, c);
	}

	@Override
	public Colonne newColonne(String nom, Usine usine) {
		return new CColonne(nom, this);
	}

	@Override
	public Sabot newSabot(String nom, Usine usine) {
		return new CSabot(nom, this);
	}

	@Override
	public TasDeCartes newTasDeCartes(String nom, Usine usine) {
		return new CTasDeCartes(nom, this);
	}

	@Override
	public TasDeCartesAlternees newTasDeCartesAlternees(String nom, Usine usine) {
		return new CTasDeCartesAlternees(nom, this);
	}

	@Override
	public TasDeCartesColorees newTasDeCartesColorees(String nom, int couleur,
			Usine usine) {
		return new CTasDeCartesColorees(nom, couleur, this);
	}
}
