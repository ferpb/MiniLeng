/*********************************************************************************
 * Tratamiento de errores semánticos
 *
 * Fichero:    ErrorSemantico.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      04/05/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;
import analizador.Token;

public class ErrorSemantico {
	
	private static int contadorErrores = 0;
	
	public static void deteccion(Exception e, Token token) {
		contadorErrores++;
		
		// Imprime el caracter erroneo, si puede no ser imprimible por la terminal,
		// imprime su equivalente escapado.
		System.err.println("MiniLeng: ERROR LÉXICO (línea " +
			", columna ");
	}
	
	public static int getContadorErrores() {
		return contadorErrores;
	}
	

}
