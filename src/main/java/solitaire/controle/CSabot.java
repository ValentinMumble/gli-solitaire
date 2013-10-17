package solitaire.controle;

import javax.print.attribute.standard.PresentationDirection;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PCarte;
import solitaire.presentation.PSabot;

public class CSabot extends Sabot {
	
	private PSabot p;

	public CSabot(String nom, CUsine u) {
		super(nom, u);
		p = new PSabot(this, ((CTasDeCartes) cachees).getPresentation(), ((CTasDeCartes) visibles).getPresentation());
	}
	
	public void setReserve(Tas t) {
		super.setReserve(t);
		if (isCarteRetournable()) {
			p.activerRetournerCarte();
		}
	}
	
	public void retourner() {
		try {
			super.retourner();
			if (!isRetournable()) {
				p.desactiverRetournerCarte();
			}
			if (isCarteRetournable()) {
				p.activerRetournerCarte();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retournerCarte() {
		try {
			super.retournerCarte();
			if (isRetournable()) {
				p.desactiverRetournerCarte();
				p.activerRetournerTas();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void depiler() {
		try {
			super.depiler();
			if (!isRetournable()) {
				p.desactiverRetournerTas();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PSabot getPresentation() {
		return p;
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
