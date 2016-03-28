package Logica;

import java.io.RandomAccessFile;

public class Archivo {
	public RandomAccessFile a;
	public boolean exists;
	String file = "arbol.dat";
	final int desp = 4;

	public Archivo() {
		try {
			a = new RandomAccessFile(file, "rw");
			if (a.length() > 0) {
				int b = a.readInt();
				System.out.println("Primera casilla");
				if (b > 0)
					exists = true;
				else
					exists = false;
			} else {
				for (int i = 1; i < 3; i++) {
					a.writeInt(0);
					a.writeInt(0);
					a.writeInt(0);
					a.writeInt(i);
				}
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(0);
				exists = false;
			}
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int aumentarArchivo() {
		int b = 0;
		try {
			b = (int) (a.length()/16);
			a.seek(b * 16);
			for (int i = 1; i < 5; i++) {
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(b + i);
			}
			a.writeInt(0);
			a.writeInt(0);
			a.writeInt(0);
			a.writeInt(0);
			a.seek(3 * desp);
			a.writeInt(b);
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		return b;
	}

	public int siguienteLibre() {
		int i, j, b;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(3 * desp);
			i = a.readInt();
			if (i == 0) {
				i = aumentarArchivo();
			}
			System.out.println("siguiente libre: " + i + " p: "
					+ a.getFilePointer());
			b = (i * 4 * desp) + (3 * desp);
			a.seek(b);
			j = a.readInt();
			System.out.println("siguiente del siguiente libre: " + j + " p: "
					+ a.getFilePointer());
			a.seek(3 * desp);
			a.writeInt(j);
			a.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			i = 0;
		}
		return i;
	}

	public void modificarNodo(Nodo n) {
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(n.num_p * 4 * desp);
			a.writeInt(n.info);
			a.writeInt(n.bal);
			if (n.izq != null)
				a.writeInt(n.izq.num_p);
			else
				a.writeInt(0);
			if (n.der != null)
				a.writeInt(n.der.num_p);
			else
				a.writeInt(0);
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarNodo(int n) {
		int i = 0;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(3 * desp);
			i = a.readInt();
			int b = (4 * desp * n) + (3 * desp);
			a.seek(b);
			a.writeInt(i);
			a.seek(3 * desp);
			a.writeInt(n);
			System.out.println("Siguiente vacio " + i + " en "
					+ a.getFilePointer());
			System.out.println("Siguiente del siguiente " + n + " en " + b);
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cambiarRaiz(int r) {
		try {
			a = new RandomAccessFile(file, "rw");
			a.writeInt(r);
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Nodo recon() throws Exception {
		a = new RandomAccessFile(file, "rw");
		int b = a.readInt();
		Nodo n = reconstruir(b);
		a.close();
		return n;
	}

	public Nodo reconstruir(int poc) {
		int i = 0, j = 0;
		Nodo n = null;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(4 * desp * poc);
			i = a.readInt();
			n = new Nodo(i);
			i = a.readInt();
			n.bal = i;
			n.num_p = poc;
			i = a.readInt();
			j = a.readInt();
			a.close();
			if (i > 0) {
				n.izq = reconstruir(i);
			}
			if (j > 0) {
				n.der = reconstruir(j);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

}
