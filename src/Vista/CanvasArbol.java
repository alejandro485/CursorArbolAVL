package Vista;

import Logica.Nodo;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.RandomAccessFile;

public class CanvasArbol extends Canvas {

	private static final long serialVersionUID = 1L;
	private RandomAccessFile a;
	private Image imagen;
	private Graphics graficas;
	String file = "arbol.dat";
	private Nodo cabeza;

	private void pintarNodo(Nodo c, int pox, int poy, int anx, int any, int dif) {
		graficas.setColor(Color.black);
		graficas.drawLine(anx, any + 10, pox, poy);
		graficas.setColor(Color.blue);
		graficas.fillRect(pox - 10, poy - 10, ((c.info+"").length()*8)+2 , 20);
		graficas.setColor(Color.white);
		graficas.drawString(c.info + "", pox-8, poy + 2);
		if (c.der != null) {
			pintarNodo(c.der, pox + dif / 2, poy + this.getHeight() / 10, pox,
					poy, dif / 2);
		}
		if (c.izq != null) {
			pintarNodo(c.izq, pox - dif / 2, poy + this.getHeight() / 10, pox,
					poy, dif / 2);
		}
	}

	public void setCabeza(Nodo n) {
		cabeza = n;
		a = null;
		repaint();
	}

	public void setArchivo(RandomAccessFile b) {
		cabeza = null;
		this.a = b;
		repaint();
	}

	public void pintarTabla() {
		try {
			a = new RandomAccessFile(file, "rw");
			int lado = 20;
			long l = a.length()/16;
			String ay = "";
			int pocX=0;
			graficas.setColor(Color.red);
			graficas.drawString("N", lado * 2 , lado);
			graficas.drawString("bal", lado * 4, lado);
			graficas.drawString("izq", lado * 6, lado);
			graficas.drawString("der", lado * 8, lado);
			a.seek(0);
			graficas.setColor(Color.black);
			for (int i = 0; i < l ; i++) {
				if(i%27==0 && i!=0){
					pocX+=lado*11;
				}
				graficas.drawString("->"+i, pocX+3, (((i%27)+2)*lado)-3);
				ay = a.readInt() + "";
				graficas.drawRect(pocX+(2 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay, pocX+(lado * 2)+3, (((i%27) + 2) * lado)-3);
				ay = a.readInt() + "";
				graficas.drawRect(pocX+(4 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay, pocX+(lado * 4)+3, (((i%27) + 2) * lado)-3);
				ay = a.readInt() + "";
				graficas.drawRect(pocX+(6 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay, pocX+(lado * 6)+3, (((i%27) + 2) * lado)-3);
				ay = a.readInt() + "";
				graficas.drawRect(pocX+(8 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay, pocX+(lado * 8)+3, (((i%27) + 2) * lado)-3);
			}
			a.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void update(Graphics g) {
		imagen = createImage(this.getWidth(), this.getHeight());
		graficas = imagen.getGraphics();
		if (cabeza != null) {
			graficas.setColor(Color.lightGray);
			graficas.fillRect(0, 0, this.getWidth(), this.getHeight());
			pintarNodo(cabeza, this.getWidth() / 2, this.getHeight() / 10,
					this.getWidth() / 2, this.getHeight() / 10,
					this.getWidth() / 2);
		} else if (a != null) {
			graficas.setColor(Color.white);
			graficas.fillRect(0, 0, this.getWidth(), this.getHeight());
			pintarTabla();
		}
		g.drawImage(imagen, 0, 0, this);
	}

}
