/*********************************************************************************
 * Estructura para almacenar valores el valor de una expresión junto a su tipo.
 *
 * Fichero:    RegistroExpr.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      8/8/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

import lib.semantico.Simbolo.*;

public class RegistroExpr {

	private Integer valorEnt;
	private Boolean valorBool;
	private Character valorChar;
	private String valorCad;

	private Tipo_variable tipo;


	public boolean esDesc() {
		return tipo == Tipo_variable.DESCONOCIDO;
	}

	public boolean esEnt() {
		return tipo == Tipo_variable.ENTERO;
	}

	public boolean esBool() {
		return tipo == Tipo_variable.BOOLEANO;
	}

	public boolean esChar() {
		return tipo == Tipo_variable.CHAR;
	}

	public boolean esCad() {
		return tipo == Tipo_variable.CADENA;
	}


	// Getters y setters

	public Integer getValorEnt() {
		return valorEnt;
	}

	public void setValorEnt(Integer valorEnt) {
		this.valorEnt = valorEnt;
	}

	public Boolean getValorBool() {
		return valorBool;
	}

	public void setValorBool(Boolean valorBool) {
		this.valorBool = valorBool;
	}

	public Character getValorChar() {
		return valorChar;
	}

	public void setValorChar(Character valorChar) {
		this.valorChar = valorChar;
	}

	public String getValorCad() {
		return valorCad;
	}

	public void setValorCad(String valorCad) {
		this.valorCad = valorCad;
	}

	public Tipo_variable getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_variable tipo) {
		this.tipo = tipo;
	}

	public void setTipoDesc() {
		this.tipo = Tipo_variable.DESCONOCIDO;
	}
	public void setTipoEnt() {
		this.tipo = Tipo_variable.ENTERO;
	}
	public void setTipoBool() {
		this.tipo = Tipo_variable.BOOLEANO;
	}
	public void setTipoChar() {
		this.tipo = Tipo_variable.CHAR;
	}
	public void setTipoCad() {
		this.tipo = Tipo_variable.CADENA;
	}
}
