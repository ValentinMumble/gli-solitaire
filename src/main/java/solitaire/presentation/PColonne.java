package solitaire.presentation;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import solitaire.controle.CCarte;
import solitaire.controle.CColonne;

public class PColonne extends JPanel {
	
	private CColonne controle;
	
	private PTasDeCartes cachees;
	private PTasDeCartes visibles;	
	private RetournerCarteListener rcl;
	private DropTargetDropEvent theFinalEvent;
	protected DropTarget dropTarget = null ;
	private MyDragGestureListener dgl;
	private MyDragSourceListener dsl;
	protected DragGestureEvent theInitialEvent;
	protected DragSource ds = null;
	private PCarte selected;

	public PColonne(CColonne cColonne, PTasDeCartes c,
			PTasDeCartesAlternees v) {
		controle = cColonne;
		cachees = c;
		visibles = v;
		rcl = new RetournerCarteListener();
		setLayout(null);
		setSize(80, 300);
		setPreferredSize(getSize());
		add(cachees);
		add(visibles, 0);
		cachees.setDxDy(0, 15);
		visibles.setDxDy(0, 15);
		visibles.setBorder(BorderFactory.createLineBorder(Color.black));
		dropTarget = new DropTarget(visibles, new MyDropTargetListener()) ;
		ds = new DragSource () ;
		ds.createDefaultDragGestureRecognizer (
				visibles, DnDConstants.ACTION_MOVE,
				new MyDragGestureListener ()) ;
		ds.addDragSourceListener (
				new MyDragSourceListener ()) ;
	}

	protected class MyDropTargetListener implements DropTargetListener {
		PCarte pc;
		public void dragEnter(DropTargetDragEvent event) {
			try {
				pc = (PCarte)event.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			controle.p2c_dragEnter(pc.getControle());
		}

		public void dragExit(DropTargetEvent event) {
			controle.p2c_dragExit(pc.getControle());
		}
		public void dragOver(DropTargetDragEvent event) {

		}

		public void drop(DropTargetDropEvent event) {
			theFinalEvent = event;
			controle.p2c_drop(pc.getControle());

		}

		public void dropActionChanged(DropTargetDragEvent arg0) {

		}

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


	public void c2p_showEmpilable() {
		//TODO
		
	}

	public void c2p_showNonEmpilable() {
		// TODO Auto-generated method stub
		
	}

	public void c2p_showNeutre() {
		// TODO Auto-generated method stub
		
	}

	public void c2p_dropKO() {
		theFinalEvent.rejectDrop();
	}

	public void c2p_dropOK() {
		theFinalEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theFinalEvent.getDropTargetContext().dropComplete(true);
	}


	public void c2p_debutDnDOK(CCarte cc) {
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop,
				cc.getPresentation(), dsl);
	}

	public void c2p_debutDnDKO(CCarte cc) {

	}
	
	public void setCorrectSize() {
		visibles.setLocation(0, cachees.getHeight()-80);
	}
	
	public void activerRetournerCarte() {
		cachees.addMouseListener(rcl);
	}

	public void desactiverRetournerCarte() {
		cachees.removeMouseListener(rcl);
	}
	
	private class RetournerCarteListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			System.out.println("retourner carte");
			try {
				controle.retournerCarte();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

}
