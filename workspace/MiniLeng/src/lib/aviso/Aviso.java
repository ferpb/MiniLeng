/*********************************************************************************
 * Tratamiento de errores sintácticos
 *
 * Fichero:    ErrorSintactico.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v2.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.aviso;

import analizador.ParseException;
import lib.semantico.Simbolo;

public class Aviso {

	private static int contadorAvisos = 0;


	public static void deteccion(String mensaje) {
		contadorAvisos++;
		System.err.println("MiniLeng: AVISO. " + mensaje);
	}

	public static int getContadorErrores() {
		return contadorAvisos;
	}


}
