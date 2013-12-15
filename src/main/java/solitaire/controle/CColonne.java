package solitaire.controle;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	private PColonne p;
	private CTasDeCartes selectedCards;
	private CTasDeCartes selectedCardsbis;

	// Constructeur de la CColonne de nom nom et d'usine u
	public CColonne(String nom, CUsine u) {
		super(nom, u);
		p = new PColonne(this, ((CTasDeCartes) cachees).getPresentation(),
				((CTasDeCartesAlternees) visibles).getPresentation());
	}

	// setReserve recopie un tas de cartes dans le tas de cartes cachées de la CColonne
	public void setReserve(Tas t) {
		super.setReserve(t);
		if (isCarteRetournable()) {
			p.desactiverRetournerCarte();
		}
		p.setCorrectLocation();
	}

	// depiler permet de retirer la carte au sommet de la pile et d'activer le retournement d'une carte cachée
	@Override
	public void depiler() throws Exception {
		super.depiler();
		if (visibles.isVide() && !cachees.isVide()) {
			p.setCorrectSize(0, 0);
		}
		if (isCarteRetournable()) {
			p.activerRetournerCarte();
		}
	}

	// retournerCarte permet de retourner une carte
	public void retournerCarte() throws Exception {
		super.retournerCarte();
		p.setCorrectLocation();
		p.desactiverRetournerCarte();
	}

	// getPresentation permet de récupérer la présentation associée à la CColonne
	public PColonne getPresentation() {
		return p;
	}

	//  p2c_dragEnter permet d'appeler la méthode d'affichage lié à la possibilité de drag ou non. (Bleu/Rouge)
	public void p2c_dragEnter(CTasDeCartes ct) {
		try {
			if (isEmpilable(ct.getBase())) {
				p.c2p_showEmpilable();
			} else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
		}
	}

	// p2c_dragExit permet d'appeler la méthode d'affichage du background neutre lié au drag
	public void p2c_dragExit(CTasDeCartes ct) {
		p.c2p_showNeutre();
	}

	// p2c_drop empile la carte si c'est possible et le Drop s'est bien passé (OK) sinon si la carte n'est pas empilable le drop est KO.
	public void p2c_drop(CTasDeCartes ct) {
		try {
			if (isEmpilable(ct)) {
				empiler(ct);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();
	}

	// p2c_debutDnd récupére la selectedCard sélectionné ou le tas au dessus de la selectedCard si elle n'est en premier plan
	public void p2c_debutDnd(CCarte selectedCard) {
		try {
			CCarte curCCarte = null;
			selectedCards = new CTasDeCartes("tas", new CUsine());
			selectedCards.setDxDy(0, 15);
			curCCarte = (CCarte) (visibles.getSommet());
			selectedCards.empiler(curCCarte);
			depiler();
			while (curCCarte != selectedCard && curCCarte != null) {
				curCCarte = (CCarte) (visibles.getSommet());
				selectedCards.empiler(curCCarte);
				depiler();
			}
			// On inverse le tas de cartes pour l'empiler dans le bon ordre.
			selectedCardsbis = new CTasDeCartes("tasbis", new CUsine());
			selectedCardsbis.setDxDy(0, 15);
			while (selectedCards.getNombre() != 0) {
				CCarte curCCartebis = (CCarte) (selectedCards.getSommet());
				selectedCardsbis.empiler(curCCartebis);
				selectedCards.depiler();
			}
			selectedCards = selectedCardsbis;

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (selectedCards.getNombre() > 0) {
			p.c2p_debutDnDOK(selectedCards.getPresentation());
		} else {
			p.c2p_debutDnDKO(selectedCards.getPresentation());
		}
	}

	// p2c_dragDropEnd réempile les cartes sélectionnés si il y a une erreur lors du drop
	public void p2c_dragDropEnd(boolean dropSuccess) {
		if (!dropSuccess) {
			empiler(selectedCards);
		}
	}
}
