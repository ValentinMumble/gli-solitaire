package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PTasDeCartesColorees;

public class CTasDeCartesColorees extends TasDeCartesColorees implements ICTasDeCartes{

	private PTasDeCartesColorees p;
	
	
	public CTasDeCartesColorees(String nom, int c, CUsine u) {
		super(nom, c, u);
		p = new PTasDeCartesColorees(this);
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
	
	public PTasDeCartesColorees getPresentation() {
		return p;
	}

	public void p2c_dragEnter(CCarte cc) {
		if (isEmpilable(cc)){
			p.c2p_showEmpilable();
		} else {
			p.c2p_showNonEmpilable();
		}
	}

	public void p2c_dragExit(CCarte cc) {
		p.c2p_showNeutre();
	}

	public void p2c_drop(CCarte cc) {
		if (isEmpilable(cc)){
			empiler(cc);
			p.c2p_dropOK();
		} else {
			p.c2p_dropKO();
		}
		p.c2p_showNeutre();
	}
}
