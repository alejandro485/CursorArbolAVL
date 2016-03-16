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
        String file="arbol.dat";
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
                a=null;
		repaint();
	}
        
        public void setArchivo(RandomAccessFile b){
            cabeza=null;
            this.a=b;
            repaint();
        }
	
        public void pintarTabla(){
            try {
                a=new RandomAccessFile(file, "rw");
                int lado=20;
                System.out.println("Longitud: "+a.length());
                int l=(int) (a.length()/4);
                System.out.println("Cositos: "+l);
                String ay="";
                graficas.setColor(Color.red);
                graficas.drawString("N", lado*2, 40);
                graficas.drawString("bal", lado*4, 40);
                graficas.drawString("izq", lado*6, 40);
                graficas.drawString("der", lado*8, 40);
                a.seek(0);
                graficas.setColor(Color.black);
                for(int i=0;i<l/4;i++){
                    ay=a.readInt()+"";
                    graficas.drawString(ay, lado*2, 2*(i+2)*lado);
                    ay=a.readInt()+"";
                    graficas.drawString(ay, lado*4, 2*(i+2)*lado);
                    ay=a.readInt()+"";
                    graficas.drawString(ay, lado*6, 2*(i+2)*lado);
                    ay=a.readInt()+"";
                    graficas.drawString(ay, lado*8, 2*(i+2)*lado);
                    System.out.println("Cosito sub i = "+i);
                }
                a.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
	@Override
	public void update(Graphics g) {
		imagen=createImage(this.getWidth(),this.getHeight());
		graficas=imagen.getGraphics();
		if(cabeza!=null){
                    graficas.setColor(Color.orange);
                    graficas.fillRect(0, 0, this.getWidth(),this.getHeight());
                    pintarNodo(cabeza, this.getWidth()/2, this.getHeight()/10, this.getWidth()/2, this.getHeight()/10, this.getWidth()/2);
		}
                else if(a!=null){
                    graficas.setColor(Color.white);
                    graficas.fillRect(0, 0, this.getWidth(),this.getHeight());
                    pintarTabla();
                }
		g.drawImage(imagen, 0, 0, this);
	}

}
