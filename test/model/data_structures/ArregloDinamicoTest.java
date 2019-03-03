/**
 * 
 */
package model.data_structures;

import java.util.Iterator;


import junit.framework.TestCase;
import model.data_structures.ArregloDinamico;

/**
 * @author cohnan daniya97
 *
 */
public class ArregloDinamicoTest extends TestCase{
	/*
	 * Atributos 
	 */
	private ArregloDinamico<String> arreglo;
	private final int numeroEscenarios = 1000;
	private final int tamanoMax = 100;

	/*
	 * Escenarios
	 */
	// Arreglo con n elementos
	private void setUpEscenario(int n, int max) {
		if (max == 0) 
			arreglo = new ArregloDinamico<String>();
		else
			arreglo = new ArregloDinamico<String>(max);
		for (int i = 0; i < n; i++) {
			arreglo.agregar("Elemento " + i);
		}
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor
	 */
	public void testArregloDinamico() {
		for (int n = 0; n < numeroEscenarios; n++) {
			setUpEscenario(n, 0);
			assertTrue("El arreglo deberia tener tamano " + n, arreglo.darTamano() == n);
			setUpEscenario(n, tamanoMax);
			assertTrue("El arreglo deberia tener tamano " + n, arreglo.darTamano() == n);
		}
	}

	/**
	 * Prueba el metodo iterator()
	 */
	public void testIterator() {
		int n = 1000;
		
		// Probar el iterador para los 2 posibles constructores para un n grande cualquiera
		for (Integer max : new Integer[]{0, tamanoMax}){
			setUpEscenario(n, max);
			
			int i = 0;
			for(String dato: arreglo) {
				assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + i
						+ ", pero se obtuvo " + dato, arreglo.darObjeto(i).equals("Elemento " + i));
				i += 1;
			}
			// Verificar que solo se identifican n elementos
			assertTrue("Escenario: " + n + ". El iterador deberia identificar y devolver " + n + " elementos", i == n);
		}
	}

	/**
	 * Prueba el metodo darTamano()
	 */
	public void testSize() {
		for (int n = 0; n <= numeroEscenarios; n++) {
				setUpEscenario(n, 0);
				assertTrue("Escenario: " + n + ". El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + arreglo.darTamano(), arreglo.darTamano() == n);
		}
	}

	/**
	 * Prueba el metodo agregar
	 */
	public void testAgregar() {
		int nAgregados = 3;
		
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, 0);
			// Agrega nAgreagdos elementos
			for (int i = 0; i < nAgregados; i++) arreglo.agregar("Nuevo elemento " + i);
			assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n + nAgregados) + " elementos."
					+ " Pero tiene " + arreglo.darTamano(), arreglo.darTamano() == (n + nAgregados));
			
			int i = 0;
			for (String dato : arreglo) {
				// Verifica que los primeros n elementos sean los esperados
				if (i < n) {
					assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + i
							+ ", pero se obtuvo " + dato, arreglo.darObjeto(i).equals("Elemento " + i));
					i++;
				}
				// Verifica que los nAgregados elementos siguientes
				else if (n <= i &&  i < n + nAgregados) {
					assertTrue("Escenario: " + n + ". El dato esperado era: " + "Nuevo elemento " + (i-n)
							+ ", pero se obtuvo " + dato, dato.equals("Nuevo elemento " + (i-n)));
					i++;
				}
			}
		}
	}

	/**
	 * Prueba los metodos de eliminacion de elementos
	 */
	public void testEliminarEnPos() {
		int nEscenarios = 100; // Numero de escenarios en los que se probara el metodo
		int nPosicionesEliminar = 20; // Maximo posiciones que se eliminaran del arreglo
		int npe; // Posiciones a eliminar en cada escenario
		
		int[] posicionesEliminar;
		for (int n = 1; n <= nEscenarios; n++) {
			setUpEscenario(n, 0);
			
			// Reducir nPosicionesEliminar si no hay suficientes elementos
			npe = nPosicionesEliminar;
			while (n < npe) {
				//System.out.println(npe);
				npe  = (int)(npe/1.5);
			}
			posicionesEliminar = new int[npe];
			
			// Elegir las posiciones a eliminar
			int i;
			for (int k = 0; k < npe; k++) {
				i = (n-1) - k*(n/npe); // Orden descendente necesario para esta implementacion
				posicionesEliminar[k]= i;
			}
			
			// Eliminar los elementos desde el mas grande
			int eliminados = 0;
			String dato;
			for (int k = 0; k < npe; k++) {
				i = posicionesEliminar[k];
				dato = arreglo.eliminarEnPos(i);
				eliminados += 1;
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato esperado era: " + "Elemento " + (i)
						+ ", pero se obtuvo " + dato, dato.equals("Elemento " + (i)));
				assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n - eliminados) + " elementos."
						+ " Pero tiene " + arreglo.darTamano(), arreglo.darTamano() == (n - eliminados));
				// Verificar que el elemento que ahora se encuentra en la posicon i es el i + 1
				if (i < (arreglo.darTamano()-1) && ((n/npe) != 1)) {
					assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + (i+1)
							+ ", pero se obtuvo " + dato, arreglo.darObjeto(i).equals("Elemento " + (i+1)));
				}
			}
		}
	}
}
