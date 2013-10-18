package solitaire.controle;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	private PColonne p;

	public CColonne(String nom, CUsine u) {
		super(nom, u);
		p = new PColonne(this, ((CTasDeCartes) cachees).getPresentation(),
				((CTasDeCartesAlternees) visibles).getPresentation());
	}

	public void setReserve(Tas t) {
		super.setReserve(t);
		if (isCarteRetournable()) {
			p.desactiverRetournerCarte();
		}
		p.setCorrectSize();
	}

	@Override
	public void depiler() throws Exception {
		super.depiler();
		if (isCarteRetournable()) {
			p.activerRetournerCarte();
		}
	}

	public void retournerCarte() throws Exception {
		super.retournerCarte();
		p.desactiverRetournerCarte();
	}

	public PColonne getPresentation() {
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
	
	public void p2c_debutDnd(CCarte cc) {
		try {
			if (cc == getSommet()){
				depiler();
				p.c2p_debutDnDOK(cc);
			} else {
				p.c2p_debutDnDKO(cc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void p2c_dragDropEnd(boolean dropSuccess, CCarte cc) {
		if (! dropSuccess){
			empiler(cc);
		}
	}
}
