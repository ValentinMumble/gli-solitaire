package solitaire.presentation;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.BorderFactory;

import solitaire.controle.CTasDeCartesColorees;
import solitaire.controle.ICTasDeCartes;

public class PTasDeCartesColorees extends PTasDeCartes {

	private DropTargetDropEvent theFinalEvent;
	protected DropTarget dropTarget = null ;

	public PTasDeCartesColorees(ICTasDeCartes c) {
		super(c);
		setSize(80, 100);
		setBorder(BorderFactory.createLineBorder(Color.black));
		dropTarget = new DropTarget(this, new MyDropTargetListener()) ;
	}

	protected class MyDropTargetListener implements DropTargetListener {
		PCarte pc;
		public void dragEnter(DropTargetDragEvent event) {
			try {
				pc = (PCarte)event.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
			} catch (UnsupportedFlavorException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
			((CTasDeCartesColorees)controle).p2c_dragEnter(pc.getControle());
		}

		public void dragExit(DropTargetEvent event) {
			((CTasDeCartesColorees)controle).p2c_dragExit(pc.getControle());
		}
		public void dragOver(DropTargetDragEvent event) {

		}

		public void drop(DropTargetDropEvent event) {
			theFinalEvent = event;
			((CTasDeCartesColorees)controle).p2c_drop(pc.getControle());

		}

		public void dropActionChanged(DropTargetDragEvent arg0) {

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
}
