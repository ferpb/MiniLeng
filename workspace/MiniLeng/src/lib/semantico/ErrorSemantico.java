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
		System.err.println("ERROR SEMÁNTICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "'. No se puede redefinir el símbolo");
	}

	public static void deteccion(SimboloNoEncontradoException e, Token t) {
		contadorErrores++;
		System.err.println("ERROR SEMÁNTICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "'. El símbolo no está definido");
	}

	public static void deteccion(InvocacionAccionException e, String mensaje, Token t) {
		contadorErrores++;
		System.err.println("ERROR SEMÁNTICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Error al invocar a: '" + t.image + "'. " + mensaje);
	}

	public static void deteccion(String mensaje, Token t) {
		contadorErrores++;
		System.err.println("ERROR SEMÁNTICO (línea " + t.beginLine + ", columna " + t.beginColumn + ") " +
				"Símbolo: '" + t.image + "'. " + mensaje);
	}

	public static int getContadorErrores() {
		return contadorErrores;
	}


}
