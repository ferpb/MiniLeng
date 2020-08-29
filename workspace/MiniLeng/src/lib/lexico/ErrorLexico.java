/*********************************************************************************
 * Tratamiento de errores léxicos
 *
 * Fichero:    ErrorLexico.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v2.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.lexico;

public class ErrorLexico {

	private static int contadorErrores = 0;


	public static void deteccion(int linea, int columna, String error, String error_escapado) {
		contadorErrores++;

		// Imprime el caracter erroneo, si puede no ser imprimible por la terminal,
		// imprime su equivalente escapado.
		System.err.println("ERROR LÉXICO (" + linea +
			", " + (columna - 1) + "): " +
			"Caracter no reconocido: '" + error + "'" +
			(!error.equals(error_escapado) ? " (" + error_escapado + ")" : "")
		);
	}

	public static int getContadorErrores() {
		return contadorErrores;
	}


}
