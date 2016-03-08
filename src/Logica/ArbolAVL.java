package Logica;

import java.util.Stack;

public class ArbolAVL {
	public Nodo raiz;

	public ArbolAVL() {
		raiz = null;
	}

	private void rotarDerecha(Nodo p, Nodo q) {
		p.bal = 0;
		q.bal = 0;
		p.izq = q.der;
		q.der = p;
	}

	private void rotarIzquierda(Nodo p, Nodo q) {
		p.bal = 0;
		q.bal = 0;
		p.der = q.izq;
		q.izq = p;
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
		return r;
	}

	public void agregarLlave(int n) {
		Nodo nuevo, p, q, s, pivote, pp;
		int llave, altura;

		nuevo = new Nodo(n);
		if (raiz == null) {
			raiz = nuevo;
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
		if (llave < q.info)
			q.izq = nuevo;
		else
			q.der = nuevo;
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
				p = p.izq;
			} else {
				p.bal = -1;
				p = p.der;
			}
		}
		if (pivote.bal == 0)
			pivote.bal = altura;
		else if (pivote.bal + altura == 0)
			pivote.bal = 0;
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
			if (pp == null)
				raiz = s;
			else if (pp.izq == pivote)
				pp.izq = s;
			else
				pp.der = s;
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
				terminar[0] = 1;
				break;
			}
			break;
		case 0:
			q.bal = -1;
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
				terminar[0] = 1;
				break;
			}
			break;
		case 0:
			q.bal = 1;
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
			else
				encontro = true;
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
						t = balanceoDerecha(q, terminar);
						break;
					case 2:
						q.izq = p.der;
						t = balanceoDerecha(q, terminar);
						break;
					}
				} else {
					switch (accion) {
					case 0:
					case 2:
						q.der = p.der;
						t = balanceoIzquierda(q, terminar);
						break;
					case 1:
						q.der = p.izq;
						t = balanceoIzquierda(q, terminar);
						break;
					}
				}
			} else {
				switch (accion) {
				case 0:
					raiz = null;
					terminar[0] = 1;
					break;
				case 1:
					raiz = p.izq;
					break;
				case 2:
					raiz = p.der;
					break;
				}
			}
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
			if (q != null) {
				q.izq = p.der;
				t = balanceoDerecha(q, terminar);
			} else {
				r.der = p.der;
				t = balanceoIzquierda(r, terminar);
			}
			q = (Nodo) pila.pop();
		}
		while (!pila.empty() && terminar[0] == 0) {
			q = (Nodo) pila.pop();
			if (llave < q.info) {
				if (t != null) {
					q.izq = t;
					t = null;
				}
				t = balanceoDerecha(q, terminar);
			} else {
				if (t != null) {
					q.der = t;
					t = null;
				}
				t = balanceoIzquierda(q, terminar);
			}
		}
		if (t != null) {
			if (pila.empty() == true)
				raiz = t;
			else {
				q = (Nodo) pila.pop();
				if (llave < q.info)
					q.izq = t;
				else
					q.der = t;
			}
		}
		return 0;
	}
}