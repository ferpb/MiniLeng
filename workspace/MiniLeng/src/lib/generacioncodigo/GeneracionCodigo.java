/*********************************************************************************
 * Generación del fichero con código intermedio para la máquina P
 *
 * Fichero:    GeneracionCodigo.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      30/08/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.generacioncodigo;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class GeneracionCodigo {

	// Dirección en la que empiezan las nuevas variables
	public static final int DIR_INICIAL = 3;

	// Valores máximos y mínimos de los enteros 16 bits
	public static final int INT_MAX =  32767;
	public static final int INT_MIN = -32766;


	private String fichero_salida;
	private PrintStream stream = System.out;

	ListaInstr programa;


	// Número de etiqueta actual
	private int etiq;
	// Nivel actual
	private int nivel;
	// Siguiente dirección a asignar en el BA
	private int sig;

	// Guardar sig antes de abrir un bloque para recuperarlo al cerrarlo
	private int sigAnterior;



	public GeneracionCodigo(String fichero_salida) {
		// Abrir fichero de salida
		this.fichero_salida = fichero_salida;

		// Inicializar etiquetas
		etiq = 0;
		sig = DIR_INICIAL;
	}

	public void guardarPrograma(ListaInstr lista) {
		this.programa = lista;
	}

	public void escribirPrograma() {
		if (fichero_salida != null) {
      		try {
        		stream = new PrintStream(fichero_salida);
      		}
      		catch (FileNotFoundException e) {
        		System.err.println("Error: No se ha podido abrir el fichero de salida '" + fichero_salida + "'");
        		System.exit(0);
      		}
		}

		for (String i : programa.getLista()) {
			stream.println(i);
		}
	}




	public int nuevaEtiqueta() {
		return etiq++;
	}

	public void abrirBloque() {
		sigAnterior = sig;
		sig = DIR_INICIAL;
		nivel++;
	}

	public void cerrarBloque() {
		sig = sigAnterior;
		nivel--;
	}

	public int getNivel() {
		return nivel;
	}

	public int getSig() {
		return sig;
	}


	public int nuevaVariable() {
		return sig++;
	}

	public int nuevoVector(int longitud) {
		int devolver = sig;
		sig += longitud + 1;
		return devolver;
	}
}
