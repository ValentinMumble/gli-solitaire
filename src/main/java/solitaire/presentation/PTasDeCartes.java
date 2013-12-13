package solitaire.presentation;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controle.ICTasDeCartes;

public class PTasDeCartes extends JPanel implements Transferable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dx;
	private int dy;
	private int x;
	private int y;

	protected ICTasDeCartes controle;

	public PTasDeCartes(ICTasDeCartes c) {
		setLayout(null);
		setSize(PCarte.WIDTH, PCarte.HEIGHT);
		setPreferredSize(getSize());
		setOpaque(false);
		controle = c;
		dx = 0;
		dy = 0;
		x = 0;
		y = 0;
	}

	public void depiler(PCarte pc) {
		remove(pc);
		x -= dx;
		y -= dy;
		setSize(getWidth() - Math.abs(dx), getHeight() - Math.abs(dy));
		setPreferredSize(getSize());
		repaint();
	}

	public void empiler(PCarte pCarte) {
		add(pCarte, 0);
		pCarte.setLocation(x, y);
		if (getWidth() == 0 && getHeight() == 0) {
			setSize(PCarte.WIDTH, PCarte.HEIGHT);
		} else {
			setSize(getWidth() + Math.abs(dx), getHeight() + Math.abs(dy));
		}
		setPreferredSize(getSize());
		x += dx;
		y += dy;
		repaint();
	}

	public void setDxDy(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public ICTasDeCartes getControle() {
		return controle;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		Object result = null;
		if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
			result = this;
		} else {
			result = null;
		}
		return result;
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor data[] = new DataFlavor[1];
		try {
			data[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (java.lang.ClassNotFoundException e) {
		}
		return (data);
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if ((flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType))) {
			return (true);
		} else {
			return (false);
		}
	}
}
