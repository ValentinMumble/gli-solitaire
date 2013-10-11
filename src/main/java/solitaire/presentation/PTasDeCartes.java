package solitaire.presentation;

import javax.swing.JPanel;

import solitaire.controle.ICTasDeCartes;

public class PTasDeCartes extends JPanel {
	
	private int dx;
	private int dy;
	private int x;
	private int y;
	
	private ICTasDeCartes controle;
	
	public PTasDeCartes(ICTasDeCartes c) {
		setLayout(null);
		setSize(200, 200);
		setPreferredSize(getSize());
		controle = c;
		dx = 0;
		dy = 20;
		x = y = 50;
	}

	public void depiler(PCarte pc) {
		remove(pc);
		x += dx;
		y += dy;
	}
	
	public void empiler(PCarte pc) {
		add(pc);
		x -= dx;
		y -= dy;
		pc.setLocation(x, y);
	}
	
	public void setDxDy(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
