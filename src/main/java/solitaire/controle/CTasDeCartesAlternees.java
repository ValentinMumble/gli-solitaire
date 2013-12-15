package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartesAlternees;
import solitaire.presentation.PTasDeCartesAlternees;

public class CTasDeCartesAlternees extends TasDeCartesAlternees implements
		ICTasDeCartes {

	private PTasDeCartesAlternees p;

	// Constructeur du CTasDeCartesAlternees de nom nom et d'usine u
	public CTasDeCartesAlternees(String nom, CUsine u) {
		super(nom, u);
		p = new PTasDeCartesAlternees(this);
	}

	// depiler permet de retirer la carte au sommet de la pile
	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}

	// empiler ajoute une presentation de carte à la présentation associée au CTasDeCartesAlternees
	public void empiler(Carte c) {
		super.empiler(c);
		try {
			if (c == getSommet()) {
				p.empiler(((CCarte) c).getPresentation());
			}
		} catch (Exception e) {
		}
	}

	//  getPresentation permet d'obtenir la présentation associée au CTasDeCartesAlternees
	public PTasDeCartesAlternees getPresentation() {
		return p;
	}

	
}
