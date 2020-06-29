/*********************************************************************************
 * Clase que define los símbolos que conforman los programas MiniLeng
 *
 * Fichero:    Simbolo.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      1/5/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

import java.util.ArrayList;

public class Simbolo {

	// Representa el tipo de símbolo
	public enum Tipo_simbolo {
		PROGRAMA, VARIABLE, ACCION, PARAMETRO
	};

	// Representa el tipo de variable
	public enum Tipo_variable {
		DESCONOCIDO, ENTERO, BOOLEANO, CHAR, CADENA
	};

	// Representa la clase de los parámetros en las acciones
	public enum Clase_parametro {
		VAL, REF
	};

	// Atributos
	String nombre;
	int nivel; // Nivel en el que se ha declarado el símbolo (primer nivel = 0)

	Tipo_simbolo tipo;
	Tipo_variable variable;
	Clase_parametro parametro;

	Boolean visible; // Indica si el símbolo es visible o no

	ArrayList<Simbolo> lista_parametros; // Lista de símbolos que representan los parámetros de una acción

	int dir; // Dirección del símbolo

	// Getters y setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Tipo_simbolo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_simbolo tipo) {
		this.tipo = tipo;
	}

	public Tipo_variable getVariable() {
		return variable;
	}

	public void setVariable(Tipo_variable variable) {
		this.variable = variable;
	}

	public Clase_parametro getParametro() {
		return parametro;
	}

	public void setParametro(Clase_parametro parametro) {
		this.parametro = parametro;
	}

	public Boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public ArrayList<Simbolo> getLista_parametros() {
		return lista_parametros;
	}

	public void setLista_parametros(ArrayList<Simbolo> lista_parametros) {
		this.lista_parametros = lista_parametros;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	// Métodos para construir los tipos de símbolos

	// Configura los campos del símbolo correspondientes a un programa
	public void introducir_programa(String nombre, int nivel, int dir) {
		this.tipo = Tipo_simbolo.PROGRAMA;
		this.nivel = nivel;
	}

	// Configura los campos del símbolo correspondiente a una variable
	public void introducir_variable(String nombre, Tipo_variable tipo_var, int nivel, int dir) {
		this.tipo = Tipo_simbolo.VARIABLE;
		this.variable = tipo_var;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a una acción
	public void introducir_accion(String nombre, int nivel, int dir) {
		this.tipo = Tipo_simbolo.VARIABLE;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a un parámetro
	public void introducir_parametro(String nombre, Tipo_variable tipo_var, Clase_parametro clase_param, int nivel,
			int dir) {
		this.tipo = Tipo_simbolo.PARAMETRO;
		this.variable = tipo_var;
		this.parametro = clase_param;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Comprobadores del tipo de símbolo

	public Boolean ES_VARIABLE() {
		return tipo == Tipo_simbolo.VARIABLE;
	}

	public Boolean ES_PARAMETRO() {
		return tipo == Tipo_simbolo.PARAMETRO;
	}

	public Boolean ES_ACCION() {
		return tipo == Tipo_simbolo.ACCION;
	}

	public Boolean ES_VALOR() {
		return (tipo == Tipo_simbolo.PARAMETRO) && (parametro == Clase_parametro.VAL);
	}

	public Boolean ES_REFERENCIA() {
		return (tipo == Tipo_simbolo.PARAMETRO) && (parametro == Clase_parametro.REF);
	}
}
