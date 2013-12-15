package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartes;
import solitaire.presentation.PTasDeCartes;

public class CTasDeCartes extends TasDeCartes implements ICTasDeCartes {
	
	private PTasDeCartes p;

	// Constructeur du CTasDeCartes de nom nom et d'usine u
	public CTasDeCartes(String nom, CUsine u) {
		super(nom, u);
		p = new PTasDeCartes(this);
	}
	
	// depiler permet de retirer la carte au sommet de la pile
	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	// empiler ajoute une presentation de carte à la présentation associée au CTasDeCartes
	public void empiler(Carte c) {
		if (isEmpilable(c)) {
			super.empiler(c);
			p.empiler(((CCarte) c).getPresentation());
		}
	}

	//  getPresentation permet d'obtenir la présentation associée au CTasDeCartes
	public PTasDeCartes getPresentation() {
		return p;
	}
	
	//  setDxDy permet de modifier le Dx et le Dy pour l'affichage des tas draggés
	public void setDxDy(int x, int y){
		p.setDxDy(x, y);
	}

}
