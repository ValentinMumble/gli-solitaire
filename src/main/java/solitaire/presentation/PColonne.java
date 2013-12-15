package solitaire.presentation;

import java.awt.Color;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import solitaire.controle.CColonne;
import solitaire.controle.CTasDeCartes;

public class PColonne extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * La largeur d'une colonne
	 */
	public static final int WIDTH = 90;

	/**
	 * Le decalage vertical entre chaque carte de la colonne
	 */
	public static final int DY = 15;
	
	/**
	 * Couleur correspondant aux differents statuts de la colonne
	 */
	public static final Color NEUTRE = new Color(0, 90, 0);
	public static final Color EMPILABLE = new Color(0, 51, 102);
	public static final Color NON_EMPILABLE = new Color(102, 0, 0);

	private CColonne controle;

	private PTasDeCartes cachees;
	private PTasDeCartes visibles;
	private RetournerCarteListener rcl;
	private DropTargetDropEvent theFinalEvent;
	protected DropTarget dropTarget = null;
	private MyDragSourceListener dsl;
	protected DragGestureEvent theInitialEvent;
	protected DragSource ds = null;
	private PCarte selectedCard;
	private PTasDeCartes selecCards;
	protected MyDragSourceMotionListener myDragSourceMotionListener = null;

	private int offsetx;

	public PColonne(CColonne cColonne, PTasDeCartes c, PTasDeCartesAlternees v) {
		controle = cColonne;
		cachees = c;
		visibles = v;
		rcl = new RetournerCarteListener();
		setLayout(null);
		setBackground(NEUTRE);
		setSize(WIDTH, 350);
		setPreferredSize(getSize());
		add(cachees);
		add(visibles, 0);
		offsetx = (WIDTH - PCarte.WIDTH) / 2;
		cachees.setLocation(offsetx, offsetx);
		cachees.setDxDy(0, DY);
		visibles.setDxDy(0, DY);

		// Initialisation et liaison des listeners pour le Drag n drop
		dropTarget = new DropTarget(visibles, new MyDropTargetListener());
		ds = new DragSource();
		ds.addDragSourceListener(new MyDragSourceListener());
		ds.createDefaultDragGestureRecognizer(visibles,
				DnDConstants.ACTION_MOVE, new MyDragGestureListener());
		myDragSourceMotionListener = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDragSourceMotionListener);
	}

	public void activerRetournerCarte() {
		cachees.addMouseListener(rcl);
	}

	public void desactiverRetournerCarte() {
		cachees.removeMouseListener(rcl);
	}

	private class RetournerCarteListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			try {
				controle.retournerCarte();
			} catch (Exception e1) {
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	class MyDragSourceMotionListener implements DragSourceMotionListener {
		/**
		 * Deplace et redessine le tas de carte en cours de drag
		 */
		public void dragMouseMoved(DragSourceDragEvent event) {
			Point p = getParent().getParent().getLocationOnScreen();
			selecCards.setLocation(event.getX() - p.x - PCarte.WIDTH / 2, 
			event.getY() - p.y - PCarte.HEIGHT / 2);
			repaint();
		}
	}

	protected class MyDragGestureListener implements DragGestureListener {
		
		/**
		 * Memorise la carte selectionnee pour le DnD et signale a son controle que le DnD commence
		 */
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			selectedCard = null;
			try {
				selectedCard = (PCarte) visibles.getComponentAt(dge
						.getDragOrigin());
			} catch (Exception e) {
			}

			controle.p2c_debutDnd(selectedCard.getControle());
		}
	}

	/**
	 * Le controleur appelle cette methode quand le DnD est demarre.
	 * Stocke le tas de cartes en cours de Drag, l'ajoute au panel principal et le dessine
	 * @param pt
	 */
	public void c2p_debutDnDOK(PTasDeCartes pt) {
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, pt, dsl);
		selecCards = pt;
		getRootPane().add(selecCards, 0);
		repaint();
	}

	public void c2p_debutDnDKO(PTasDeCartes pt) {

	}

	protected class MyDragSourceListener implements DragSourceListener {
		public void dragDropEnd(DragSourceDropEvent event) {
			controle.p2c_dragDropEnd(event.getDropSuccess());
			getRootPane().repaint();
		}

		public void dragEnter(DragSourceDragEvent event) {
		}

		public void dragExit(DragSourceEvent event) {
			repaint();
		}

		public void dragOver(DragSourceDragEvent event) {
		}

		public void dropActionChanged(DragSourceDragEvent event) {
		}
	}

	protected class MyDropTargetListener implements DropTargetListener {
		PTasDeCartes pc;

		/**
		 * Signale au controleur qu'un tas de carte lui passe au dessus
		 */
		public void dragEnter(DropTargetDragEvent event) {
			try {
				pc = (PTasDeCartes) event.getTransferable().getTransferData(
						new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
				controle.p2c_dragEnter((CTasDeCartes) pc.getControle());
			} catch (Exception e) {
			}
		}

		public void dragExit(DropTargetEvent event) {
			if (pc != null) {
				controle.p2c_dragExit((CTasDeCartes) pc.getControle());
			}
		}

		public void dragOver(DropTargetDragEvent event) {

		}

		/**
		 * Signale au controleur qu'un tas de carte a ete pose
		 */
		public void drop(DropTargetDropEvent event) {
			theFinalEvent = event;
			controle.p2c_drop((CTasDeCartes) pc.getControle());
		}

		public void dropActionChanged(DropTargetDragEvent arg0) {

		}

	}

	/**
	 * Appelee par le controleur pour montrer que cette colonne peut recevoir un tas de cartes
	 */
	public void c2p_showEmpilable() {
		setBackground(EMPILABLE);
	}

	/**
	 * Appelee par le controleur pour montrer que cette colonne ne peut pas recevoir un tas de cartes
	 */
	public void c2p_showNonEmpilable() {
		setBackground(NON_EMPILABLE);
	}

	/**
	 * Appelee par le controleur pour montrer que la colonne est neutre
	 */
	public void c2p_showNeutre() {
		setBackground(NEUTRE);
	}

	public void c2p_dropKO() {
		theFinalEvent.rejectDrop();
	}

	public void c2p_dropOK() {
		theFinalEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theFinalEvent.getDropTargetContext().dropComplete(true);
	}

	/**
	 * Deplace le tas de cartes visibles au bon endroit quand des cartes sont retournees
	 */
	public void setCorrectLocation() {
		visibles.setLocation(offsetx, offsetx + cachees.getHeight() - (PCarte.HEIGHT));
	}

	/**
	 * Modifie la taille du tas de cartes visibles
	 * @param i
	 * @param j
	 */
	public void setCorrectSize(int i, int j) {
		visibles.setSize(i, j);
	}

}
