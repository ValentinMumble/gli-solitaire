package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartes;
import solitaire.presentation.PTasDeCartes;

public class CTasDeCartes extends TasDeCartes implements ICTasDeCartes {
	
	private PTasDeCartes p;

	public CTasDeCartes(String nom, CUsine u) {
		super(nom, u);
		p = new PTasDeCartes(this);
	}
	
	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	public void empiler(Carte c) {
		if (isEmpilable(c)) {
			super.empiler(c);
			p.empiler(((CCarte) c).getPresentation());
		}
	}

	public PTasDeCartes getPresentation() {
		return p;
	}

}
