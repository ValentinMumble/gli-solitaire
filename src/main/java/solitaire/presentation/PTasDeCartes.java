package solitaire.presentation;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controle.CCarte;
import solitaire.controle.ICTasDeCartes;

public class PTasDeCartes extends JPanel implements Transferable{

	private int dx;
	private int dy;
	private int x;
	private int y;

	protected ICTasDeCartes controle;

	public PTasDeCartes(ICTasDeCartes c) {
		setLayout(null);
		setSize(72, 96);
		setPreferredSize(getSize());
		setOpaque(false);
		controle = c;
		dx = 0;
		dy = 0;
		x = 2;
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

	public void empiler(PCarte pc) {
		add(pc, 0);
		pc.setLocation(x, y);
		if (getWidth() == 0 && getHeight()==0){
			setSize(72, 96);
		}
		else { 
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
		// TODO Auto-generated method stub
		return null;
	}

	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return false;
	}
}
