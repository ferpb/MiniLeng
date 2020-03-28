/*********************************************************************************
 * Documento con las características del compilador de Minileng
 *
 * Fichero:    README.txt
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      28/03/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/


Uso: minilengcompiler [opciones] [fichero ...]

Opciones:
    -v, --verbose	Mostrar resumen de los símbolos utilizados en el programa
    -h, --help	  	Imprimir ayuda (esta pantalla) y salir
    --version		Imprimir información de la versión y salir

Ejemplos de uso:
    minilengcompiler
    minilengcompiler -v
    minilengcompiler -v fichero.ml
    

Compilador programado con JavaCC en Eclipse 2019-12
JavaCC Eclipse Plug-in 1.5.33


Características:
----------------

  - Gestión de errores en la llamada al comando:
	MiniLeng: Opción inválida <error>

  - Opción verbose para generar una tabla con el número de ocurrencias
    de todos los tokens reconocidos en el programa de entrada. 
    Ejemplo de tabla:
	+-------------------------------------+
	| Número de ocurrencias de los tokens |
	+-------------------------------------+
	| Token                         num.  |
	+-------------------------------------+
	| Palabras reservadas                 |
	|     PROGRAMA                     1  |
	|     PRINCIPIO                    3  |
	|     FIN                          3  |
	|     SI                           3  |
	|     ENT                          3  |
	|     SI_NO                        2  |
	|     FSI                          3  |
	| Caracteres de agrupación            |
	|     PARENTESIS_IZQ              27  |
	|     PARENTESIS_DER              27  |
	| Operadores aritméticos              |
	|     SUMA                         2  |
	|     RESTA                        1  |
	| Operadores lógicos                  |
	|     AND                          2  |
	|     OR                           1  |
	| Tipos de dato                       |
	|     BOOLEANO                     1  |
	|     CARACTER                     4  |
	+-------------------------------------+

  - Opción tokens que muestra por pantalla los nombre de los tokens que
    se van reconociendo conforme se analiza el programa.

  - Permite definir comentarios de una línea con % y de bloque, con %%.

  - Formato de error de los errores léxico y semántico. Muy detallado! Poner ejemplo

  - Contador de errores al final del programa

  - Opción panic mode que se recupera de los errores de ; y no hay fin en 
    una acción o programa.

  - Permite definir funciones anidadas



Gramática de MiniLeng utilizada en EBNF
---------------------------------------

programa ::= <tPROGRAMA> <tIDENTIFICADOR> <tFIN_SENTENCIA>
	declaracion_variables  declaracion_acciones
	bloque_sentencias

declaracion_variables ::= ( declaracion <tFIN_SENTENCIA> )*
declaracion ::= tipo_variables  identificadores
tipo_variables ::= <tENTERO> | <tCARACTER> | <tBOOLEANO>
identificadores ::= <tIDENTIFICADOR> ( <tSEP_VARIABLE> <tIDENTIFICADOR> )*


declaracion_acciones ::= ( declaracion_accion )*
declaracion_accion ::= cabecera_accion <tFIN_SENTENCIA>
	declaracion_variables  declaración_acciones  bloque_sentencias
cabecera_accion ::= <tACCION> <tIDENTIFICADOR> parametros_formales

parametros_formales ::= ( <tPARENTESIS_IZQ> lista_parametros? <tPARENTESIS_DER> )?
lista_parametros ::= parametros ( <tFIN_SENTENCIA> parametros )*
parametros ::= clase_parametros  declaracion
clase_parametros ::= <tVAL> | <tREF>


bloque_sentencias ::= <tPRINCIPIO> lista_sentencias <tFIN>
lista_sentencias ::= sentencia ( sentencia )*
sentencia ::= leer <tFIN_SENTENCIA>
			| escribir <tFIN_SENTENCIA>
			| asignacion
			| invocacion_accion
			| seleccion
			| mientras_que

leer ::= <tLEER> <tPARENTESIS_IZQ> lista_asignables <tPARENTESIS_DER>
lista_asignables ::= identificadores
escribir ::= <tESCRIBIR> <tPARENTESIS_IZQ> lista_escribibles <tPARENTESIS_DER>
lista_escribibles ::= lista_expresiones

asignacion ::= <tIDENTIFICADOR> <tOPAS> expresion <tFIN_SENTENCIA>

invocacion_accion ::= <tIDENTIFICADOR> argumentos <tFIN_SENTENCIA>

mientras_que ::= <tMQ> expresion lista_sentencias <tFMQ>

seleccion ::= <tSI> expresion <tENT> lista_sentencias ( <tSI_NO> lista_sentencias )* <tFSI>

argumentos ::= <tPARENTESIS_IZQ> ( lista_expresiones )? <tPARENTESIS_DER>


lista_expresiones ::= expresion ( <tSEP_VARIABLE> expresion )*

expresion ::= expresion_simple ( operador_relacional  expresion_simple )*
operador_relacional ::= <tIGUAL>
					  | <tMENOR>
					  | <tMAYOR>
					  | <tMAI>
					  | <tMEI>
					  | <tNI>
expresion_simple ::= ( <tMAS> | <tMENOS> )? termino ( operador_aditivo termino )*
operador_aditivo ::= <tMAS>
				   | <tMENOS>
				   | <tOR>
termino ::= factor ( operador_multiplicativo factor )*
operador_multiplicativo ::= <tPRODUCTO>
						  | <tDIVISION>
						  | <tMOD>
						  | <tAND>
factor ::= <tMENOS> factor
		 | <tNOT> factor
		 | <tPARENTESIS_IZQ> expresion <tPARENTESIS_DER>
		 | <tENTACAR> <tPARENTESIS_IZQ> expresion <tPARENTESIS_DER>
		 | <tCARAENT> <tPARENTESIS_IZQ> expresion <tPARENTESIS_DER>
		 | <tIDENTIFICADOR>
		 | <tCONSTENTERA>
		 | <tCONSTCHAR>
		 | <tCONSTCAD
		 | <tTRUE>
		 | <tFALSE>





