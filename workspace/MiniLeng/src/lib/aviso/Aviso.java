/*********************************************************************************
 * Tratamiento de avisos
 *
 * Fichero:    Aviso.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.aviso;

import analizador.Token;
import lib.semantico.DivisionPorCeroException;
import lib.semantico.OverflowException;
import lib.semantico.UnderflowException;

public class Aviso {

	private static int contadorAvisos = 0;


	public static void deteccion(String mensaje, Token t) {
		contadorAvisos++;
		System.err.println("AVISO (" + t.beginLine + ", " + t.beginColumn + "): " +
				"Símbolo: '" + t.image + "'. " + mensaje);
	}

	public static void deteccion(UnderflowException e, Token t) {
		contadorAvisos++;
		System.err.println("AVISO (" + t.beginLine + ", " + t.beginColumn + "): " +
				"Símbolo: '" + t.image + "' La operación produce undeflow");
	}

	public static void deteccion(OverflowException e, Token t) {
		contadorAvisos++;
		System.err.println("AVISO (" + t.beginLine + ", " + t.beginColumn + "): " +
				"Símbolo: '" + t.image + "' La operación produce overflow");
	}

	public static void deteccion(DivisionPorCeroException e, Token t) {
		contadorAvisos++;
		System.err.println("AVISO (" + t.beginLine + ", " + t.beginColumn + "): " +
				"Símbolo: '" + t.image + "' La operación produce una división por cero");
	}

	public static int getContadorAvisos() {
		return contadorAvisos;
	}

}
