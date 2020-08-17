/*********************************************************************************
 * Tabla para gestiónar la cuenta de ocureencias de cada palabra reservada
 * en el compilador de MiniLeng.
 *
 * Fichero:    TablaOcurrencias.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      20/02/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.lexico;

import java.util.Arrays;

public class TablaOcurrencias {

	// Definiciones de los tokens del lenguaje

	public enum Reservadas {
		tPROGRAMA,
        tVAR,
        tPRINCIPIO,
        tFIN,
        tSI,
        tENT,
        tSI_NO,
        tFSI,
        tMQ,
        tFMQ,
        tESCRIBIR,
        tLEER,
        tENTACAR,
        tCARAENT,
        tACCION,
        tVAL,
        tREF,
	}
	private int cReservadas[] = new int[Reservadas.values().length];

	public enum Agrupaciones {
		tLLAVE_IZQ,
		tLLAVE_DER,
		tPARENTESIS_IZQ,
		tPARENTESIS_DER
	}
	private int cAgrupaciones[] = new int[Agrupaciones.values().length];

	public enum Vectores {
		tCORCHETE_IZQ,
		tCORCHETE_DER
	}
	private int cVectores[] = new int[Vectores.values().length];

	public enum Operadores {
        tOPAS,
        tFIN_SENTENCIA,
        tSEP_VARIABLE
	}
	private int cOperadores[] = new int[Operadores.values().length];

	public enum OpAritmeticos {
        tMAS,
        tMENOS,
        tPRODUCTO,
        tDIVISION,
        tMOD,
        tDIV
	}
	private int cOpAritmeticos[] = new int[OpAritmeticos.values().length];

	public enum OpLogicos {
        tAND,
        tOR,
        tNOT,
        tMAYOR,
        tMENOR,
        tIGUAL,
        tMAI,
        tMEI,
        tNI
	}
	private int cOpLogicos[] = new int[OpLogicos.values().length];

    public enum Tipos {
        tENTERO,
        tBOOLEANO,
        tCARACTER
    }
    private int cTipos[] = new int[Tipos.values().length];

	public enum Valores {
        tTRUE,
        tFALSE,
        tIDENTIFICADOR,
        tCONSTENTERA,
        tCONSTCHAR,
        tCONSTCAD
	}
	private int cValores[] = new int[Valores.values().length];


	// Mostrar o no los tokens reconocidos
	private Boolean show_tokens;




	public TablaOcurrencias(Boolean show_tokens) {
		this.show_tokens = show_tokens;
	}


	/*
	 * Funciones para incrementar el contador de cada token,
	 * divididas por tipos de token.
	 */
	public void incrementar(Reservadas token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cReservadas[token.ordinal()]++;
	}

	public void incrementar(Agrupaciones token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cAgrupaciones[token.ordinal()]++;
	}

	public void incrementar(Vectores token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cVectores[token.ordinal()]++;
	}

	public void incrementar(Operadores token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cOperadores[token.ordinal()]++;
	}

	public void incrementar(OpAritmeticos token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cOpAritmeticos[token.ordinal()]++;
	}

	public void incrementar(OpLogicos token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cOpLogicos[token.ordinal()]++;
	}

	public void incrementar(Tipos token) {
		if (show_tokens) {
			System.out.println(token.name());
		}
		cTipos[token.ordinal()]++;
	}

	public void incrementar(Valores token, String valor) {
		if (show_tokens) {
			System.out.println(token.name() + " (Valor: " + valor + ")");
		}
		cValores[token.ordinal()]++;
	}



	/*
	 *  Devuelve true si contador tiene algún elemento mayor que 0.
	 */
	private Boolean tieneOcurrencia(int contador[]) {
		for (int cantidad : contador) {
			if (cantidad > 0) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Imprime un contador con formato de tabla
	 */
	private void imprimirContador(int contador[], String tipoToken, String nombres[]) {
		if (tieneOcurrencia(contador)) {
			System.out.format("| %-35s |\n", tipoToken);
			for (int i = 0; i < contador.length; i++) {
				if (contador[i] > 0) {
					System.out.format("|     %-24s  %4d  |\n", nombres[i], contador[i]);
				}
			}
		}
	}

	/*
	 * Devuelve una lista de Strings con los nombres de las entradas
	 * del enum que se pasa como argumento.
	 * Se respeta el orden con el que están declaradas las entradas.
	 */
	public static String[] getNombres(Class<? extends Enum<?>> e) {
	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

	/*
	 * Imprime una tabla con el contenido de todos los contadores
	 */
	public void imprimirTabla() {
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
		System.out.println("| Número de ocurrencias de los tokens |");
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
		System.out.println("| Token                         num.  |");
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");

		imprimirContador(cReservadas, "Palabras reservadas", getNombres(Reservadas.class));
		imprimirContador(cAgrupaciones, "Caracteres de agrupación", getNombres(Agrupaciones.class));
		imprimirContador(cVectores, "Vectores", getNombres(Vectores.class));
		imprimirContador(cOperadores, "Operadores", getNombres(Operadores.class));
		imprimirContador(cOpAritmeticos, "Operadores aritméticos", getNombres(OpAritmeticos.class));
		imprimirContador(cOpLogicos, "Operadores lógicos", getNombres(OpLogicos.class));
		imprimirContador(cTipos, "Tipos de dato", getNombres(Tipos.class));
		imprimirContador(cValores, "Valores", getNombres(Valores.class));

		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
	}

}

