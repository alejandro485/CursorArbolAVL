package Vista;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import Logica.Nodo;

public class CanvasArbol extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private Image imagen;
	private Graphics graficas;
	private Nodo cabeza;
	
	private void pintarNodo(Nodo c, int pox, int poy, int anx, int any,int dif){
		graficas.setColor(Color.black);
		graficas.drawLine(anx, any+10, pox, poy);
		graficas.setColor(Color.white);
		graficas.fillOval(pox-10,poy-10, 20, 20);
		graficas.setColor(Color.black);
		graficas.drawString(c.info+"", pox-3, poy+2);
		if(c.der!=null){
			pintarNodo(c.der,pox+dif/2, poy+this.getHeight()/10, pox, poy, dif/2);
		}
		if(c.izq!=null){
			pintarNodo(c.izq,pox-dif/2, poy+this.getHeight()/10, pox, poy, dif/2);
		}
	}
	
	public void setCabeza(Nodo n){
		cabeza=n;
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		imagen=createImage(this.getWidth(),this.getHeight());
		graficas=imagen.getGraphics();
		graficas.setColor(Color.orange);
		graficas.fillRect(0, 0, this.getWidth(),this.getHeight());
		if(cabeza!=null){
			pintarNodo(cabeza, this.getWidth()/2, this.getHeight()/10, this.getWidth()/2, this.getHeight()/10, this.getWidth()/2);
		}
		g.drawImage(imagen, 0, 0, this);
	}

}
