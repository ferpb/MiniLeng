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

	public static void deteccion(SimboloYaDeclaradoException e, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "'. No se puede redefinir el símbolo");
	}

	public static void deteccion(SimboloNoEncontradoException e, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "' No se ha encontrado el símbolo");
	}
	
	public static void deteccion(UnderflowException e, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "' La operación produce undeflow");
	}
	
	public static void deteccion(OverflowException e, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "' La operación produce overflow");
	}
	
	public static void deteccion(DivisionPorCeroException e, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "' La operación produce una división por cero");
	}

	public static void deteccion(String mensaje, Token t) {
		contadorErrores++;
		System.err.println("MiniLeng: ERROR LÉXICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "'." + mensaje);
	}

	public static int getContadorErrores() {
		return contadorErrores;
	}


}
