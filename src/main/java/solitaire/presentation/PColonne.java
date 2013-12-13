package solitaire.presentation;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int COL_WIDTH = 90;

	public static final int DY = 15;

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
	protected MyDragSourceMotionListener myDragSourceMotionListener = null;

	public PColonne(CColonne cColonne, PTasDeCartes c, PTasDeCartesAlternees v) {
		controle = cColonne;
		cachees = c;
		visibles = v;
		rcl = new RetournerCarteListener();
		setLayout(null);
		setSize(90, 500);
		setPreferredSize(getSize());
		add(cachees);
		add(visibles, 0);
		cachees.setDxDy(0, DY);
		visibles.setDxDy(0, DY);
		
		//visibles.setBorder(BorderFactory.createLineBorder(Color.black));
		
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
		public void dragMouseMoved(DragSourceDragEvent event) {
			selectedCard.setLocation(1 + event.getX(), 1 + event.getY());
		}
	}

	protected class MyDragGestureListener implements DragGestureListener {
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			selectedCard = null;
			try {
				selectedCard = (PCarte) visibles.getComponentAt(dge
						.getDragOrigin());
			} catch (Exception e) {
				e.printStackTrace();
			}

			controle.p2c_debutDnd(selectedCard.getControle());
		}
	}

	public void c2p_debutDnDOK(PTasDeCartes pt) {
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, pt, dsl);
	}

	public void c2p_debutDnDKO(PTasDeCartes pt) {

	}

	protected class MyDragSourceListener implements DragSourceListener {
		public void dragDropEnd(DragSourceDropEvent event) {
			controle.p2c_dragDropEnd(event.getDropSuccess());
		}

		public void dragEnter(DragSourceDragEvent event) {
		}

		public void dragExit(DragSourceEvent event) {
		}

		public void dragOver(DragSourceDragEvent event) {
		}

		public void dropActionChanged(DragSourceDragEvent event) {
		}
	}

	protected class MyDropTargetListener implements DropTargetListener {
		PTasDeCartes pc;

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

		public void drop(DropTargetDropEvent event) {
			theFinalEvent = event;
			controle.p2c_drop((CTasDeCartes) pc.getControle());

		}

		public void dropActionChanged(DropTargetDragEvent arg0) {

		}

	}

	public void c2p_showEmpilable() {

	}

	public void c2p_showNonEmpilable() {

	}

	public void c2p_showNeutre() {

	}

	public void c2p_dropKO() {
		theFinalEvent.rejectDrop();
	}

	public void c2p_dropOK() {
		theFinalEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theFinalEvent.getDropTargetContext().dropComplete(true);
	}

	public void setCorrectLocation() {
		visibles.setLocation(0, cachees.getHeight() - (99 + DY));
	}

	public void setCorrectSize(int i, int j) {
		visibles.setSize(i, j);
	}

}
