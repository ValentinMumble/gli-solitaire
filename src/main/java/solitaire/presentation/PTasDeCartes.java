package solitaire.presentation;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controle.ICTasDeCartes;

public class PTasDeCartes extends JPanel implements Transferable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Le decalage a appliquer horizontalement
	 */
	private int dx;
	
	/**
	 * Le decalage a appliquer verticalement
	 */
	private int dy;
	
	/**
	 * La position du tas de cartes
	 */
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

	/**
	 * Supprime la carte du tas et met a jour la position du tas ainsi que sa taille
	 * @param pc
	 */
	public void depiler(PCarte pc) {
		remove(pc);
		x -= dx;
		y -= dy;
		setSize(getWidth() - Math.abs(dx), getHeight() - Math.abs(dy));
		setPreferredSize(getSize());
		repaint();
	}

	/**
	 * Ajoute la carte au tas et met a jour sa position ainsi que sa taille
	 * @param pCarte
	 */
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

	/**
	 * Retourne this si la flavor demandee correspond au type objet
	 */
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		Object result = null;
		if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
			result = this;
		}
		return result;
	}

	/**
	 * Retourne les types de flavor supportes
	 */
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor data[] = new DataFlavor[2];
		try {
			data[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (java.lang.ClassNotFoundException e) {
		}
		return (data);
	}

	/**
	 * Retourne vrai si la flavor passee en parametre est supportee (Local Object ici)
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType));
	}
}
