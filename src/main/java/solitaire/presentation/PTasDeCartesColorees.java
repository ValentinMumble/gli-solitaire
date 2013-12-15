package solitaire.presentation;

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

import solitaire.controle.CCarte;
import solitaire.controle.CTasDeCartes;
import solitaire.controle.CTasDeCartesColorees;

public class PTasDeCartesColorees extends PTasDeCartes {

	private static final long serialVersionUID = 1L;
	private DropTargetDropEvent theFinalEvent;
	protected DropTarget dropTarget = null;
	private CTasDeCartesColorees controle;
	protected DragGestureEvent theInitialEvent;
	protected DragSource ds = null;
	private MyDragSourceListener dsl;
	private PCarte selected;
	protected MyDragSourceMotionListener myDragSourceMotionListener = null;

	public PTasDeCartesColorees(CTasDeCartesColorees c) {
		super(c);
		controle = c;
		setLayout(null);
		setPreferredSize(getSize());
		dropTarget = new DropTarget(this, new MyDropTargetListener());
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_MOVE, new MyDragGestureListener());
		ds.addDragSourceListener(new MyDragSourceListener());
		myDragSourceMotionListener = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDragSourceMotionListener);
	}
	
	class MyDragSourceMotionListener implements DragSourceMotionListener {
		/**
		 * Deplace et redessine le tas de carte en cours de drag
		 */
		public void dragMouseMoved(DragSourceDragEvent event) {
			Point p = getParent().getParent().getParent().getLocationOnScreen();
			selected.setLocation(event.getX() - p.x - PCarte.WIDTH / 2,
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
			CCarte cc;
			try {
				cc = (CCarte) controle.getSommet();
				selected = cc.getPresentation();
				controle.p2c_debutDnd(cc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	 * Appelee par le controleur pour montrer que ce tas peut recevoir un tas de cartes
	 */
	public void c2p_showEmpilable() {
		getParent().setBackground(PColonne.EMPILABLE);
	}

	/**
	 * Appelee par le controleur pour montrer que ce tas ne peut pas recevoir un tas de cartes
	 */
	public void c2p_showNonEmpilable() {
		getParent().setBackground(PColonne.NON_EMPILABLE);
	}

	/**
	 * Appelee par le controleur pour montrer que le tas est neutre
	 */
	public void c2p_showNeutre() {
		getParent().setBackground(PColonne.NEUTRE);
	}

	public void c2p_dropKO() {
		theFinalEvent.rejectDrop();
	}

	public void c2p_dropOK() {
		theFinalEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theFinalEvent.getDropTargetContext().dropComplete(true);
	}
	

	/**
	 * Le controleur appelle cette methode quand le DnD est demarre.
	 * Stocke le tas de cartes en cours de Drag, l'ajoute au panel principal et le dessine
	 * @param pt
	 */
	public void c2p_debutDnDOK(PTasDeCartes pt) {
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, pt, dsl);
		CTasDeCartes ct = (CTasDeCartes) pt.getControle();
		try {
			CCarte cc = (CCarte) ct.getSommet();
			selected = cc.getPresentation();
			getRootPane().add(selected, 0);
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void c2p_debutDnDKO(PTasDeCartes pt) {

	}

}
