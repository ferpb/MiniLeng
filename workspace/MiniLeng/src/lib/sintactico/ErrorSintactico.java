/*********************************************************************************
 * Tratamiento de errores sintácticos
 *
 * Fichero:    ErrorSintactico.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v2.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.sintactico;

import analizador.ParseException;

public class ErrorSintactico {
	
	private static int contadorErrores = 0;
	
	
	public static void deteccion(ParseException e, String mensaje) {
		contadorErrores++;
        /*
	  	Token ultimoToken = minilengcompilerTokenManager.getNextToken();
	  	e.currentToken.beginLine;
	  	e.currentToken.beginColumn;
		e.currentToken.next;
         */
		System.err.println("MiniLeng: ERROR SINTÁCTICO (línea " + e.currentToken.next.beginLine +
				", columna " + (e.currentToken.next.beginColumn) + "): Token incorrecto: '" +
                e.currentToken.next + "'. " + mensaje);
	}
	
	public static int getContadorErrores() {
		return contadorErrores;
	}
	

}
