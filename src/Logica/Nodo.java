package Logica;

public class Nodo {
	public int info;
	public int bal;
	public int num_p;
	public Nodo izq;
	public Nodo der;
	
	public Nodo(int n) {
		info = n;
		bal = 0;
		izq = der = null;
	}
}
