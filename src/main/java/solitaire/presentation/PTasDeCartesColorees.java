package solitaire.presentation;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import solitaire.controle.CTasDeCartes;
import solitaire.controle.CTasDeCartesColorees;

public class PTasDeCartesColorees extends PTasDeCartes {

	private static final long serialVersionUID = 1L;
	private DropTargetDropEvent theFinalEvent;
	protected DropTarget dropTarget = null;
	private CTasDeCartesColorees controle;

	public PTasDeCartesColorees(CTasDeCartesColorees c) {
		super(c);
		controle = c;
		setLayout(null);
		setPreferredSize(getSize());
		dropTarget = new DropTarget(this, new MyDropTargetListener());
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
}
