/*********************************************************************************
 * Implementacion del modo pánico
 *
 * Fichero:    PanicMode.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      2/04/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.sintactico;

import analizador.ParseException;
import analizador.Token;
import analizador.minilengcompiler;
import analizador.minilengcompilerConstants;

public class PanicMode implements minilengcompilerConstants {

	private static int contadorErrores = 0;


	public static void iniciar(ParseException e, String mensaje, int tipoToken, String tokenLiteral) {
		contadorErrores++;

		System.err.println("ERROR SINTÁCTICO (" + e.currentToken.next.beginLine +
				", " + (e.currentToken.next.beginColumn) + "): Token incorrecto: '" +
                e.currentToken.next + "'. " + mensaje);

		System.err.println("PANIC MODE: Iniciado panic mode");


		// Descarta la entrada hasta encontrar un token de tipo tipoToken
		Token t = minilengcompiler.getNextToken();

		while(t.kind != tipoToken && t.kind != EOF) {
			System.out.println("  > PANIC MODE: Token descartado: '" + t.image + "'");
			t = minilengcompiler.getNextToken();
		}


		if (t.kind == EOF) {
			System.out.println("  > PANIC MODE (" + t.beginLine +
					", " + (t.beginColumn) + "): Se ha terminado el fichero");
		}

		else {
			System.out.println("  > PANIC MODE (" + t.beginLine +
					", " + (t.beginColumn) +
					"): Se ha encontrado '" + tokenLiteral + "'");
		}


		System.err.println("PANIC MODE: Terminado panic mode");
	}


	public static int getContadorErrores() {
		return contadorErrores;
	}
}
