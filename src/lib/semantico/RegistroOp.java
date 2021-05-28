/*********************************************************************************
 * Estructura para almacenar un operador junto a su token
 *
 * Fichero:    RegistroOp.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      8/8/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

import analizador.Token;

public class RegistroOp {

	public enum Operador {
		// Operadores relacionales (expresion())
		IGUAL,
		NI,
		MAYOR,
		MAI,
		MENOR,
		MEI,

		// Operadores aditivos (expresion_simple())
		MAS,
		MENOS,
		OR,

		// Operadores multiplicativos (termino())
		PRODUCTO,
		DIVISION,
		MOD,
		DIV,
		AND
	}

	private Token token;
	private Operador op;


	public RegistroOp(Token token, Operador op) {
		this.token = token;
		this.op = op;
	}

	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public Operador getOp() {
		return op;
	}
	public void setOp(Operador op) {
		this.op = op;
	}
}
