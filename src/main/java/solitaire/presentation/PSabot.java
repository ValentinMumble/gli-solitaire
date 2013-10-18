package solitaire.presentation;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import solitaire.controle.CCarte;
import solitaire.controle.CSabot;

public class PSabot extends JPanel {

	private CSabot controle;

	private PTasDeCartes cachees;
	private PTasDeCartes visibles;

	private RetournerTasListener rtl;
	private RetournerCarteListener rcl;
	private MyDragGestureListener dgl;
	private MyDragSourceListener dsl;
	protected DragGestureEvent theInitialEvent;
	protected DragSource ds = null;
	private PCarte selected;

	public PSabot(CSabot cSabot, PTasDeCartes c, PTasDeCartes v) {
		controle = cSabot;
		cachees = c;
		visibles = v;
		add(cachees);
		add(visibles);
		cachees.setBorder(BorderFactory.createLineBorder(Color.black));
		cachees.setDxDy(0, 0);
		visibles.setDxDy(15, 0);
		rtl = new RetournerTasListener();
		rcl = new RetournerCarteListener();
		ds = new DragSource () ;
		ds.createDefaultDragGestureRecognizer (
				visibles, DnDConstants.ACTION_MOVE,
				new MyDragGestureListener ()) ;
		ds.addDragSourceListener (
				new MyDragSourceListener ()) ;
	}

	public void activerRetournerCarte() {
		cachees.addMouseListener(rcl);
	}

	public void activerRetournerTas() {
		cachees.addMouseListener(rtl);
	}

	public void desactiverRetournerCarte() {
		cachees.removeMouseListener(rcl);
	}

	public void desactiverRetournerTas() {
		cachees.removeMouseListener(rtl);
	}

	protected class MyDragGestureListener implements DragGestureListener {
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			selected = null;
			CCarte cc = null;
			try {
				selected = (PCarte) visibles.getComponentAt(dge.getDragOrigin());
				cc = selected.getControle();
			} catch (Exception e) {
			}
			controle.p2c_debutDnd(cc);
		}
	}

	protected class MyDragSourceListener implements DragSourceListener {
		public void dragDropEnd(DragSourceDropEvent event) {
			controle.p2c_dragDropEnd(event.getDropSuccess(), selected.getControle());
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

	private class RetournerTasListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			controle.retourner();
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

	private class RetournerCarteListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			controle.retournerCarte();
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

	public void c2p_debutDnDOK(CCarte cc) {
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop,
				cc.getPresentation(), dsl);
	}

	public void c2p_debutDnDKO(CCarte cc) {

	}

}
