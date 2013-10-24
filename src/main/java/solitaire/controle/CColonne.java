package solitaire.controle;

import java.util.Stack;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PCarte;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	private PColonne p;
	private Stack<PCarte> selectedCards;

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

	public void p2c_dragEnter(CTasDeCartesAlternees top) {
		try {
			if (isEmpilable(top.getBase())){
				p.c2p_showEmpilable();
			} else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
		}
	}

	public void p2c_dragExit(CTasDeCartesAlternees top) {
		p.c2p_showNeutre();
	}

	public void p2c_drop(CTasDeCartes top) {
		try {
			if (isEmpilable(top.getBase())){
				empiler(top);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();
	}

	public void p2c_debutDnd(PCarte selectedCard) {
		try {
			CCarte curCCarte;
			PCarte curPCarte = null;
			selectedCards = new Stack<PCarte>();
			curCCarte = (CCarte)(visibles.getSommet());
			curPCarte = curCCarte.getPresentation();
			selectedCards.push(curPCarte);
			depiler();
			while(curPCarte!=selectedCard && curPCarte!=null)
			{
				curCCarte = (CCarte)(visibles.getSommet());
				curPCarte = curCCarte.getPresentation();
				selectedCards.push(curPCarte);
				depiler();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (selectedCards.size() == 1){
			p.c2p_debutDnDOK(selectedCard);
		}
		else if (selectedCards.size()>1){
			CTasDeCartes ctDragged = new CTasDeCartes("tas", new CUsine());
			while(!selectedCards.isEmpty()){
				ctDragged.empiler(selectedCards.pop().getControle());
			}
			p.c2p_debutDnDOK(ctDragged.getPresentation());
		}
		else {
			p.c2p_debutDnDOK(selectedCard);
		}
	}

	public void p2c_dragDropEnd(boolean dropSuccess, CCarte cc) {
		if (! dropSuccess){
			empiler(cc);
		}
	}
}
