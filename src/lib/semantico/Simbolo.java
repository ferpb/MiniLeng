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

	/////////////////////////////
	// Atributos del símbolo //
	/////////////////////////////

	String nombre;
	Integer nivel; // Nivel en el que se ha declarado el símbolo (primer nivel = 0)
	Integer dir; // Dirección del símbolo

	Tipo_simbolo tipo;
	Tipo_variable variable;
	Clase_parametro parametro;

	ArrayList<Simbolo> lista_parametros; // Lista de símbolos que representan los parámetros de una acción

	Boolean vector = false; // Vale true si el símbolo es una variable o parametro vector
	Integer longitud; // Longitud para los vectores

	// Boolean inicializado = false; // Vale true si el símbolo es una variable o parámetro y ha sido inicializado


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

	public ArrayList<Simbolo> getListaParametros() {
		return lista_parametros;
	}

	public void setListaParametros(ArrayList<Simbolo> lista_parametros) {
		this.lista_parametros = lista_parametros;
	}

	public void addParametro(Simbolo parametro) {
		this.lista_parametros.add(parametro);
	}

	public int getDir() {
		return dir;
	}

	public void setDir(Integer dir) {
		this.dir = dir;
	}

	public void setVector(Boolean vector) {
		this.vector = vector;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}

	public Integer getLongitud() {
		return longitud;
	}

	/*
	public void setInicializado(Boolean inicializado) {
		this.inicializado = inicializado;
	}

	public Boolean INICIALIZADO() {
		return inicializado;
	}
	*/

	// Métodos para construir los tipos de símbolos

	// Configura los campos del símbolo correspondientes a un programa
	public void introducir_programa(String nombre, int nivel, int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.PROGRAMA;
		this.nivel = nivel;
	}

	// Configura los campos del símbolo correspondiente a una variable
	public void introducir_variable(String nombre, Tipo_variable tipo_var, int nivel, int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.VARIABLE;
		this.variable = tipo_var;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a una acción
	public void introducir_accion(String nombre, int nivel, int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.ACCION;
		this.lista_parametros = new ArrayList<Simbolo>();
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a un parámetro
	public void introducir_parametro(String nombre, Tipo_variable tipo_var, Clase_parametro clase_param, int nivel,
			int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.PARAMETRO;
		this.variable = tipo_var;
		this.parametro = clase_param;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a una variable vector
	public void introducir_variable_vector(String nombre, Tipo_variable tipo_var, int longitud, int nivel, int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.VARIABLE;
		this.variable = tipo_var;
		this.vector = true;
		this.longitud = longitud;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Configura los campos del símbolo correspondiente a parámetro vector
	public void introducir_parametro_vector(String nombre, Tipo_variable tipo_var, Clase_parametro clase_param,
			int longitud, int nivel, int dir) {
		this.nombre = nombre;
		this.tipo = Tipo_simbolo.PARAMETRO;
		this.variable = tipo_var;
		this.parametro = clase_param;
		this.vector = true;
		this.longitud = longitud;
		this.nivel = nivel;
		this.dir = dir;
	}

	// Comprobadores del tipo de símbolo

	public Boolean ES_PROGRAMA() {
		return tipo == Tipo_simbolo.PROGRAMA;
	}

	public Boolean ES_VARIABLE() {
		return tipo == Tipo_simbolo.VARIABLE;
	}

	public Boolean ES_ACCION() {
		return tipo == Tipo_simbolo.ACCION;
	}

	public Boolean ES_PARAMETRO() {
		return tipo == Tipo_simbolo.PARAMETRO;
	}

	public Boolean ES_VALOR() {
		return (tipo == Tipo_simbolo.PARAMETRO) && (parametro == Clase_parametro.VAL);
	}

	public Boolean ES_REFERENCIA() {
		return (tipo == Tipo_simbolo.PARAMETRO) && (parametro == Clase_parametro.REF);
	}

	public Boolean ES_VECTOR() {
		return vector;
	}

	public Boolean ES_ASIGNABLE() {
		return this.ES_VARIABLE() || this.ES_REFERENCIA();
	}

	// Función toString()
	@Override
	public String toString() {
		String res;

		String nombre = this.nombre;
		if (vector) {
			nombre += "[" + longitud + "]";
		}

		switch (tipo) {
		case PROGRAMA:
			res = String.format("%-25s %s [%d, -]", tipo.name() + ":", nombre, nivel);
			break;

		case VARIABLE:
			res = String.format("%-25s %s [%d, %d]", tipo.name() + " " + variable.name() + ":", nombre, nivel, dir);
			break;

		case ACCION:
			String signatura = nombre + "(";
			boolean primero = true;
			for (Simbolo par : lista_parametros) {
				if (primero) {
					primero = false;
				} else {
					signatura += ", ";
				}
				signatura += par.nombre;
			}
			signatura += ")";
			res = String.format("%-25s %s [%d, %d]", tipo.name() + ":", signatura, nivel, dir);
			break;

		case PARAMETRO:
			res = String.format("%-25s %s [%d, %d]",
					tipo.name() + " " + parametro.name() + " " + variable.name() + ":", nombre, nivel, dir);
			break;

		default:
			res = String.format("%-25s %s [%d, %d]", "SIMBOLO DESCONOCIDO", nombre, nivel, dir);
			break;
		}

		return res;
	}
}
