package solitaire.presentation;

import java.awt.Color;

import javax.swing.JPanel;

import solitaire.controle.ICTasDeCartes;

public class PTasDeCartes extends JPanel {
	
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
		repaint();
	}
	
	public void empiler(PCarte pc) {
		add(pc, 0);
		x += dx;
		y += dy;
		pc.setLocation(x, y);
		setSize(getWidth()+Math.abs(dx), getHeight()+Math.abs(dy));
		setPreferredSize(getSize());
		repaint();
	}
	
	public void setDxDy(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
