package solitaire.controle;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	private PColonne p;
	private CTasDeCartes selectedCards;

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
		p.setCorrectLocation();
	}

	@Override
	public void depiler() throws Exception {
		super.depiler();
		if (visibles.isVide()){
			p.setCorrectSize(0, 0);
		}
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

	public void p2c_dragEnter (CTasDeCartes ct) {
		try {
			if (isEmpilable(ct.getBase())){
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
		try {
			if (isEmpilable(ct)){
				empiler(ct);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();
	}

	public void p2c_debutDnd(CCarte selectedCard) {
		try {
			CCarte curCCarte = null;
			selectedCards = new CTasDeCartes("tas", new CUsine());
			curCCarte = (CCarte)(visibles.getSommet());
			selectedCards.empiler(curCCarte);
			depiler();
			while(curCCarte!=selectedCard && curCCarte!=null)
			{
				curCCarte = (CCarte)(visibles.getSommet());
				selectedCards.empiler(curCCarte);
				depiler();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (selectedCards.getNombre()>0){
			p.c2p_debutDnDOK(selectedCards.getPresentation());
		}
		else {
			p.c2p_debutDnDOK(selectedCards.getPresentation());
		}
	}

	public void p2c_dragDropEnd(boolean dropSuccess) {
		if (! dropSuccess){
			empiler(selectedCards);
		}
	}
}
