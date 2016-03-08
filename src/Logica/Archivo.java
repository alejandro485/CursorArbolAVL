package Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Archivo {
	public RandomAccessFile a;
	
	public Archivo() {
		try {
			File fichero = new File("arbol.txt");
			if(fichero.exists()){
				a=new RandomAccessFile("arbol.txt", "rw");
			}
			a=new RandomAccessFile("arbol.txt", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
