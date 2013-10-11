package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PTasDeCartesAlternees;

public class CTasDeCartesAlternees extends TasDeCartesColorees implements
		ICTasDeCartes {

	private PTasDeCartesAlternees p;

	public CTasDeCartesAlternees(String nom, int c, CUsine u) {
		super(nom, c, u);
		p = new PTasDeCartesAlternees(this);
	}

	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}

	public void empiler(Carte c) {
		super.empiler(c);
		try {
			if (c == getSommet()) {
				p.empiler(((CCarte) c).getPresentation());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PTasDeCartesAlternees getPresentation() {
		return p;
	}
}
