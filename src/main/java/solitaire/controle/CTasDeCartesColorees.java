package solitaire.controle;

import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PTasDeCartesColorees;

public class CTasDeCartesColorees extends TasDeCartesColorees implements
		ICTasDeCartes {

	private PTasDeCartesColorees p;
	private CTasDeCartes selectedCards;

	// Constructeur du CTasDeCartesColorees de nom nom, de couleur c et d'usine u.
	public CTasDeCartesColorees(String nom, int c, CUsine u) {
		super(nom, c, u);
		p = new PTasDeCartesColorees(this);
	}

	// depiler permet de retirer la carte au sommet de la pile
	@Override
	public void depiler() throws Exception {
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}

	// empiler ajoute une presentation de carte à la présentation associée au CTasDeCartesColorees
	public void empiler(CCarte cc) {
		if (isEmpilable(cc)) {
			super.empiler(cc);
			p.empiler(((CCarte) cc).getPresentation());
		}
	}

	//  getPresentation permet d'obtenir la présentation associée au CTasDeCartesColorees
	public PTasDeCartesColorees getPresentation() {
		return p;
	}

	//  p2c_dragEnter permet d'appeler la méthode d'affichage lié à la possibilité de drag ou non. (Bleu/Rouge)
	public void p2c_dragEnter(CTasDeCartes ct) {
		Carte c;
		try {
			c = ct.getSommet();
			if (isEmpilable(c) && ct.getNombre() == 1) {
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
		Carte c;
		try {
			c = ct.getSommet();
			if (isEmpilable(c) && ct.getNombre() == 1) {
				empiler((CCarte) c);
				p.c2p_dropOK();
			} else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
		}
		p.c2p_showNeutre();

	}
	
	// p2c_debutDnd récupére la selectedCard sélectionné et l'ajoute dans un CTasDeCartes
		public void p2c_debutDnd(CCarte cc) {
			try {
				selectedCards = new CTasDeCartes("tas", new CUsine());
				selectedCards.empiler(cc);
				if (cc == getSommet()){
					depiler();
					p.c2p_debutDnDOK(selectedCards.getPresentation());
				} else {
					p.c2p_debutDnDKO(selectedCards.getPresentation());
				}
			} catch (Exception e) {
			}
		}

		// p2c_dragDropEnd réempile les cartes sélectionnés si il y a une erreur lors du drop
		public void p2c_dragDropEnd(boolean dropSuccess) {
			if (! dropSuccess){
				empiler(selectedCards);
			}
		}

}
