package solitaire.controle;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;
import solitaire.presentation.PTasDeCartesAlternees;

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

	public void p2c_dragEnter(ICTasDeCartes top) {
		try {
			if (isEmpilable(((CTasDeCartesAlternees)top).getBase())){
				p.c2p_showEmpilable();
			} else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
		}
	}

	public void p2c_dragExit(ICTasDeCartes top) {
		p.c2p_showNeutre();
	}

	public void p2c_drop(ICTasDeCartes top) {
		try {
			if (isEmpilable(((CTasDeCartesAlternees)top).getBase())){
				empiler((CTasDeCartes)top);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();
	}

	public void p2c_debutDnd(CTasDeCartes ct) {
		try {
			int i;
			CTasDeCartes toMove;
			for (i =0; i < visibles.getNombre(); i++){
				if (ct.getBase() == visibles.getCarte(i)){
					break;
				}
			}
			if (i == visibles.getNombre()){
				p.c2p_debutDnDKO(toMove);
			}
			else {
				for (int j = visibles.getNombre()-1; j >= i; j--){
					visibles.depiler();
					p.c2p_debutDnDOK(toMove);
					}
			}
		}

		catch (Exception e) {}
	}

	public void p2c_dragDropEnd(boolean dropSuccess, CCarte cc) {
		if (! dropSuccess){
			empiler(cc);
		}
	}
}
