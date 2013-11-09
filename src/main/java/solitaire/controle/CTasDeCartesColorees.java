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
	
	@Override
	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	public void empiler(CCarte cc) {
		if (isEmpilable(cc)) {
			super.empiler(cc);
			p.empiler(((CCarte) cc).getPresentation());
		}
	}
	
	public PTasDeCartesColorees getPresentation() {
		return p;
	}

	public void p2c_dragEnter(CTasDeCartes ct) {
		Carte c;
		try {
			c = ct.getSommet();
			if (isEmpilable(c) && ct.getNombre() == 1){
				p.c2p_showEmpilable();
			} else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
		}
		
	}

	public void p2c_dragExit(CTasDeCartes ct) {
		p.c2p_showNeutre();
	}

	public void p2c_drop(CTasDeCartes ct) {
		Carte c;
		try {
			c = ct.getSommet();
			if (isEmpilable(c) && ct.getNombre() == 1){
				empiler((CCarte)c);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();
		
	}
		
}
