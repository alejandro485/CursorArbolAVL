package Logica;

import java.util.Stack;

public class ArbolAVL {
	public Nodo raiz;
	public Archivo archivo;

	public ArbolAVL() {
		archivo=new Archivo();
		if(archivo.exists){
			System.out.println("El arbol existe :v");
			try {
				raiz=archivo.recon();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			raiz=null;
			System.out.println("El arbol no existe");
		}
	}

	private void rotarDerecha(Nodo p, Nodo q) {
		p.bal = 0;
		q.bal = 0;
		p.izq = q.der;
		q.der = p;
		archivo.modificarNodo(p);
		archivo.modificarNodo(q);
	}

	private void rotarIzquierda(Nodo p, Nodo q) {
		p.bal = 0;
		q.bal = 0;
		p.der = q.izq;
		q.izq = p;
		archivo.modificarNodo(p);
		archivo.modificarNodo(q);
	}

	private Nodo dobleRotacionDerecha(Nodo p, Nodo q) {
		Nodo r= q.der;
		q.der = r.izq;
		r.izq = q;
		p.izq = r.der;
		r.der = p;
		switch (r.bal) {
		case -1:
			q.bal = 1;
			p.bal = 0;
			break;
		case 0:
			q.bal = p.bal = 0;
			break;
		case 1:
			q.bal = 0;
			p.bal = -1;
			break;
		}
		r.bal = 0;
		archivo.modificarNodo(p);
		archivo.modificarNodo(q);
		archivo.modificarNodo(r);
		return r;
	}

	private Nodo dobleRotacionIzquierda(Nodo p, Nodo q) {
		Nodo r = q.izq;
		q.izq = r.der;
		r.der = q;
		p.der = r.izq;
		r.izq = p;
		switch (r.bal) {
		case -1:
			q.bal = 0;
			p.bal = 1;
			break;
		case 1:
			q.bal = -1;
			p.bal = 0;
			break;
		case 0:
			q.bal = p.bal = 0;
			break;

		}
		r.bal = 0;
		archivo.modificarNodo(p);
		archivo.modificarNodo(q);
		archivo.modificarNodo(r);
		return r;
	}

	public void agregarLlave(int n) {
		Nodo nuevo, p, q, s, pivote, pp,a;
		int llave, altura;

		nuevo = new Nodo(n);
		if (raiz == null) {
			nuevo.num_p=archivo.siguienteLibre();
			archivo.cambiarRaiz(nuevo.num_p);
			archivo.modificarNodo(nuevo);
			raiz = nuevo;
			System.out.println("Agregando cabeza arbol");
			return;
		}
		pp = q = null;
		pivote = p = raiz;
		llave = nuevo.info;
		while (p != null) {
			if (p.bal != 0) {
				pp = q;
				pivote = p;
			}
			if (llave == p.info) {
				return;
			} else {
				q = p;
				if (llave < p.info)
					p = p.izq;
				else
					p = p.der;
			}
		}
		nuevo.num_p=archivo.siguienteLibre();
		if (llave < q.info){
			q.izq = nuevo;
		}
		else{
			q.der = nuevo;
		}
		archivo.modificarNodo(nuevo);// modificando el nodo recien agregado
		archivo.modificarNodo(q);// modificando el padre del nodo recien agregado
		if (llave < pivote.info) {
			s = pivote.izq;
			altura = 1;
		} else {
			s = pivote.der;
			altura = -1;
		}
		p = s;
		while (p != nuevo) {
			if (llave < p.info) {
				p.bal = 1;
				a=p;
				p = p.izq;
			} else {
				p.bal = -1;
				a=p;
				p = p.der;
			}
			archivo.modificarNodo(a);// modificando balanceo del recorrido
		}
		if (pivote.bal == 0){
			pivote.bal = altura;
			archivo.modificarNodo(pivote);//cambiando privote
		}
		else if (pivote.bal + altura == 0){
			pivote.bal = 0;
			archivo.modificarNodo(pivote);//cambiando privote
		}
		else {
			if (altura == 1) {
				if (s.bal == 1)
					rotarDerecha(pivote, s);
				else
					s = dobleRotacionDerecha(pivote, s);
			} else {
				if (s.bal == -1)
					rotarIzquierda(pivote, s);
				else
					s = dobleRotacionIzquierda(pivote, s);
			}
			if (pp == null){
				raiz = s;
				archivo.cambiarRaiz(s.num_p);
			}
			else {
				if (pp.izq == pivote){
					pp.izq = s;
				}
				else{
					pp.der = s;
				}
				archivo.modificarNodo(pp);
			}
		}
	}
	
	public String inorden(Nodo p) {
		String cadena = "";
		if (p != null) {
			cadena += inorden(p.izq);
			cadena += p.info;
			cadena += inorden(p.der);
		}
		return cadena;
	}

	public String preorden(Nodo p) {
		String cadena = "";
		if (p != null) {
			cadena += p.info;
			cadena += preorden(p.izq);
			cadena += preorden(p.der);
		}
		return cadena;
	}

	public String posorden(Nodo p) {
		String cadena = "";
		if (p != null) {
			posorden(p.izq);
			posorden(p.der);
			cadena += p.info;
		}
		return cadena;
	}

	private Nodo balanceoDerecha(Nodo q, int[] terminar) {
		Nodo t = null;
		switch (q.bal) {
		case 1:
			q.bal = 0;
			archivo.modificarNodo(q);
			break;
		case -1:
			t = q.der;
			switch (t.bal) {
			case 1:
				t = dobleRotacionIzquierda(q, t);
				break;
			case -1:
				rotarIzquierda(q, t);
				break;
			case 0:
				q.der = t.izq;
				t.izq = q;
				t.bal = 1;
				archivo.modificarNodo(q);
				archivo.modificarNodo(t);
				terminar[0] = 1;
				break;
			}
			break;
		case 0:
			q.bal = -1;
			archivo.modificarNodo(q);
			terminar[0] = 1;
			break;
		}
		return t;
	}

	private Nodo balanceoIzquierda(Nodo q, int[] terminar) {
		Nodo t = null;
		switch (q.bal) {
		case -1:
			q.bal = 0;
			archivo.modificarNodo(q);
			break;
		case 1:
			t = q.izq;
			switch (t.bal) {
			case 1:
				rotarDerecha(q, t);
				break;
			case -1:
				t = dobleRotacionDerecha(q, t);
				break;
			case 0:
				q.izq = t.der;
				t.der = q;
				t.bal = -1;
				archivo.modificarNodo(q);
				archivo.modificarNodo(t);
				terminar[0] = 1;
				break;
			}
			break;
		case 0:
			q.bal = 1;
			archivo.modificarNodo(q);
			terminar[0] = 1;
			break;
		}
		return t;
	}

	public int retirarLlave(int n) {
		Stack<Nodo> pila = new Stack<Nodo>();
		Nodo p, q, t, r;
		int llave, accion;

		int[] terminar = new int[1];

		boolean encontro = false;

		if (raiz == null) {
			return (1);
		}
		terminar[0] = 0;
		p = raiz;
		while (!encontro && p != null) {
			pila.push(p);
			if (n < p.info)
				p = p.izq;
			else if (n > p.info)
				p = p.der;
			else{
				encontro = true;
			}
		}
		if (!encontro) {
			return (2);
		}
		t = null;
		p = (Nodo) pila.pop();
		llave = p.info;
		if (p.izq == null && p.der == null)
			accion = 0;
		else if (p.der == null)
			accion = 1;
		else if (p.izq == null)
			accion = 2;
		else
			accion = 3;
		if (accion == 0 || accion == 1 || accion == 2) {
			if (!pila.empty()) {
				q = (Nodo) pila.pop();
				if (llave < q.info) {
					switch (accion) {
					case 0:
					case 1:
						q.izq = p.izq;
						archivo.modificarNodo(q);
						t = balanceoDerecha(q, terminar);
						break;
					case 2:
						q.izq = p.der;
						archivo.modificarNodo(q);
						t = balanceoDerecha(q, terminar);
						break;
					}
				} else {
					switch (accion) {
					case 0:
					case 2:
						q.der = p.der;
						archivo.modificarNodo(q);
						t = balanceoIzquierda(q, terminar);
						break;
					case 1:
						q.der = p.izq;
						archivo.modificarNodo(q);
						t = balanceoIzquierda(q, terminar);
						break;
					}
				}
			} else {
				switch (accion) {
				case 0:
					raiz = null;
					archivo.cambiarRaiz(0);
					terminar[0] = 1;
					break;
				case 1:
					raiz = p.izq;
					archivo.cambiarRaiz(raiz.num_p);
					break;
				case 2:
					raiz = p.der;
					archivo.cambiarRaiz(raiz.num_p);
					break;
				}
			}
			archivo.eliminarNodo(p.num_p);
		} else {
			pila.push(p);
			r = p;
			p = r.der;
			q = null;
			while (p.izq != null) {
				pila.push(p);
				q = p;
				p = p.izq;
			}
			llave = r.info = p.info;
			archivo.modificarNodo(r);
			archivo.eliminarNodo(p.num_p);
			if (q != null) {
				q.izq = p.der;
				archivo.modificarNodo(q);
				t = balanceoDerecha(q, terminar);
			} else {
				r.der = p.der;
				archivo.modificarNodo(r);
				t = balanceoIzquierda(r, terminar);
			}
			q = (Nodo) pila.pop();
		}
		while (!pila.empty() && terminar[0] == 0) {
			q = (Nodo) pila.pop();
			if (llave < q.info) {
				if (t != null) {
					q.izq = t;
					archivo.modificarNodo(q);
					t = null;
				}
				t = balanceoDerecha(q, terminar);
			}
			else {
				if (t != null) {
					q.der = t;
					archivo.modificarNodo(q);
					t = null;
				}
				t = balanceoIzquierda(q, terminar);
			}
		}
		if (t != null) {
			if (pila.empty() == true){
				raiz = t;
				archivo.cambiarRaiz(t.num_p);
				archivo.modificarNodo(t);
			}
			else {
				q = (Nodo) pila.pop();
				if (llave < q.info)
					q.izq = t;
				else
					q.der = t;
				archivo.modificarNodo(q);
			}
		}
		return 0;
	}
}