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
	private static final String[] ReservadasNombres = {
		"PROGRAMA",
        "VAR",
        "PRINCIPIO",
        "FIN",
        "SI",
        "ENT",
        "SI_NO",
        "FSI",
        "MQ",
        "FMQ",
        "ESCRIBIR",
        "LEER",
        "ENTACAR",
        "CARAENT",
        "ACCION",
        "VAL",
        "REF"
	};
	private final int nReservadas = 17;

	public enum Agrupaciones {
		tLLAVE_IZQ,
		tLLAVE_DER,
		tPARENTESIS_IZQ,
		tPARENTESIS_DER
	}
	private static final String[] AgrupacionesNombres = {
		"LLAVE_IZQ",
		"LLAVE_DER",
		"PARENTESIS_IZQ",
		"PARENTESIS_DER"
	};
	private final int nAgrupaciones = 4;

	public enum Operadores {
        tOPAS,
        tFIN_SENTENCIA,
        tSEP_VARIABLE
	}
	private static final String[] OperadoresNombres = {
        "OPAS",
        "FIN_SENTENCIA",
        "SEP_VARIABLE"
	};
	private final int nOperadores = 3;

	public enum OpAritmeticos {
        tMAS,
        tMENOS,
        tPRODUCTO,
        tDIVISION,
        tMOD,
        tDIV
	}
	private static final String[] OpAritmeticosNombres = {
        "MAS",
        "MENOS",
        "PRODUCTO",
        "DIVISION",
        "MOD",
        "DIV"
	};
	private final int nOpAritmeticos = 6;

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
	private static final String[] OpLogicosNombres = {
        "AND",
        "OR",
        "NOT",
        "MAYOR",
        "MENOR",
        "IGUAL",
        "MAI",
        "MEI",
        "NI"
	};
	private final int nOpLogicos = 9;

    public enum Tipos {
        tENTERO,
        tBOOLEANO,
        tCARACTER
    }
    private static final String[] TiposNombres = {
        "ENTERO",
        "BOOLEANO",
        "CARACTER"
    };
    private final int nTipos = 3;

	public enum Valores {
        tTRUE,
        tFALSE,
        tIDENTIFICADOR,
        tCONSTENTERA,
        tCONSTCHAR,
        tCONSTCAD
	}
    private static final String[] ValoresNombres = {
        "TRUE",
        "FALSE",
        "IDENTIFICADOR",
        "CONSTENTERA",
        "CONSTCHAR",
        "CONSTCAD"
	};
	private final int nValores = 6;

	// Definición de los contadores, uno por cada tipo de token.
	int cReservadas[];
	int cAgrupaciones[];
	int cOperadores[];
	int cOpAritmeticos[];
	int cOpLogicos[];
	int cTipos[];
	int cValores[];
	
	
	// Mostrar o no los tokens reconocidos
	private Boolean show_tokens;


	/*
	 * Constructor de la clase que inicializa todos
	 * los contadores a 0
	 */
	public TablaOcurrencias(Boolean show_tokens) {
		// Se puede utilizar también Arrays.fills(arr, 0)
		
		this.show_tokens = show_tokens;

		cReservadas = new int[nReservadas];
		cAgrupaciones = new int[nAgrupaciones];
		cOperadores = new int[nOperadores];
		cOpAritmeticos = new int[nOpAritmeticos];
		cOpLogicos = new int[nOpLogicos];
		cTipos = new int[nTipos];
		cValores = new int[nValores];
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
	 * Imprime una tabla con el contenido de todos los contadores
	 */
	public void imprimirTabla() {
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
		System.out.println("| Número de ocurrencias de los tokens |");
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
		System.out.println("| Token                         num.  |");
		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");

		imprimirContador(cReservadas, "Palabras reservadas", ReservadasNombres);
		imprimirContador(cAgrupaciones, "Caracteres de agrupación", AgrupacionesNombres);
		imprimirContador(cOperadores, "Operadores", OperadoresNombres);
		imprimirContador(cOpAritmeticos, "Operadores aritméticos", OpAritmeticosNombres);
		imprimirContador(cOpLogicos, "Operadores lógicos", OpLogicosNombres);
		imprimirContador(cTipos, "Tipos de dato", TiposNombres);
		imprimirContador(cValores, "Valores", ValoresNombres);

		System.out.println("+" + new String(new char[37]).replace("\0", "-") + "+");
	}

}
