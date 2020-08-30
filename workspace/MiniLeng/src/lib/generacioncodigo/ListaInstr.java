/*********************************************************************************
 * Estructura para almacenar listas de instrucciones de código intermedio
 *
 * Fichero:    GeneracionCodigo.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      30/08/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.generacioncodigo;

import java.util.ArrayList;
import java.util.Arrays;

import lib.semantico.RegistroExpr;
import lib.semantico.RegistroOp;
import lib.semantico.Simbolo;

public class ListaInstr {

	// Las instrucciones se almacenan en forma de Strings
	private ArrayList<String> lista = new ArrayList<String>();


	// Programa

	public void addPrograma(String nombre, Integer etiq, ListaInstr listaAcciones, ListaInstr listaSentencias) {
		addComentario("Programa: " + nombre);
		lista.add("\tENP L" + etiq);
	 	concatenarLista(listaAcciones);
	 	addComentario("Comienzo de programa: " + nombre);
	 	addEtiqueta(etiq);
	 	concatenarLista(listaSentencias);
	 	addComentario("Fin de " + nombre);
		lista.add("\tLVP");
	}


	// Acciones

	// Declaración

	public void addAccion(String nombre, Integer etiq, ListaInstr listaCabecera, ListaInstr listaAcciones, ListaInstr listaSentencias) {
		addComentario("Acción: " + nombre);
		concatenarLista(listaCabecera);

		// Si hay acciones locales hace falta saltarlas al ejecutar la acción
		if (listaAcciones.getLista().size() != 0) {
			addSaltoIncod(etiq);
			concatenarLista(listaAcciones);
			addEtiqueta(etiq);
		}

		addComentario("Comienzo de acción: " + nombre);
		concatenarLista(listaSentencias);
		lista.add("\tCSF");
	}

	// Invocación

	// El símbolo param debe contener un parámetro
	// La lista de instrucciones arg debe contener las instrucciones para acceder al valor
	// del argumento que se quiere apilar
	public void addApilarArgumento(Simbolo param, Integer nparam, ListaInstr arg) {
		// IMPORTANTE! En los parámetro por referencia hay que eliminar la última
	    // 			   instrucción (que será DRF) para apilar su DIRECCIÓN
        addComentario("Apilar argumento " + (nparam + 1) + ": " + param);
        concatenarLista(arg);
        if (!param.ES_VECTOR() && param.ES_REFERENCIA()) {
          eliminarUltimaInstr();
        }
	}

	// Apila todos los valores del vector v
	public void addApilarValoresVector(Simbolo v, Integer nivelAct) {
        addComentario("Apilar valores vector: " + v);
        for (int i = 0; i < v.getLongitud(); i++) {
			lista.add("\tSRF " + (nivelAct - v.getNivel()) + " " + (v.getDir() + i));
			lista.add("\tDRF");
        }
	}

	// El símbolo acción debe ser una acción
	public void addInvocacionAccion(Simbolo accion, Integer size, Integer nivelAct) {
		addComentario("Invocar a " + accion);
		// size es el número de espacios que hay que respetar del bloque anterior

		lista.add("\tOSF " + size + " " + (nivelAct - accion.getNivel()) + " L" + accion.getDir());
	}

	public void addRecuperarPar(Simbolo s, Integer nparam, Integer nivelAct) {
		addComentario("Recuperar parametro " + (nparam + 1) + ": " + s);
		if (s.ES_VECTOR() && s.ES_VALOR()) {
			// Recuperar componentes del vector en orden inverso
	        for (int i = (s.getLongitud() - 1); i >= 0; i--) {
				lista.add("\tSRF " + (nivelAct - s.getNivel()) + " " + (s.getDir() + i));
				lista.add("\tASGI");
	        }
		}
		else {
		  lista.add("\tSRF " + (nivelAct - s.getNivel()) + " " + s.getDir());
		  lista.add("\tASGI");
		}
	}


	// Expresiones

	public void addOpBinaria(RegistroOp op) {
		String instr = "";

		switch (op.getOp()) {
		case IGUAL:
			instr = "\tEQ";
			break;
		case NI:
			instr = "\tNEQ";
			break;
		case MAYOR:
			instr = "\tGT";
			break;
		case MAI:
			instr = "\tGTE";
			break;
		case MENOR:
			instr = "\tLT";
			break;
		case MEI:
			instr = "\tLTE";
			break;

		case  MAS:
			instr = "\tPLUS";
			break;
		case MENOS:
			instr = "\tSBT";
			break;
		case OR:
			instr = "\tOR";
			break;

		case PRODUCTO:
			instr = "\tTMS";
			break;
		case DIVISION:
			instr = "\tDIV";
			break;
		case MOD:
			instr = "\tMOD";
			break;
		case DIV:
			instr = "\tDIV";
			break;
		case AND:
			instr = "\tAND";
			break;
		}
		lista.add(instr);
	}

	public void addMenosUnario() {
		lista.add("\tNGI");
	}

	public void addNegacionUnaria() {
		lista.add("\tNGB");
	}

	public void addConstEnt(Integer c) {
		lista.add("\tSTC " + c);
	}

	public void addConstChar(Character c) {
		lista.add("\tSTC " + (int) c);
	}

	public void addConstBool(Boolean c) {
		if (c) {
			lista.add("\tSTC 1");
		}
		else {
			lista.add("\tSTC 0");
		}
	}



	// Acceso a variables y parametros

	// El símbolo s es una variable o parametro
	public void addGetValor(Simbolo s, Integer nivelAct, RegistroExpr indice) {
		addComentario("Obtener valor de " + s);

		Integer direccion = s.getDir();
		// Si se ha pasado un índice constante, sumar sobre la dirección
		if (indice != null && indice.getValorEnt() != null && !s.ES_REFERENCIA()) {
			addComentario("Indice: " + indice.getValorEnt());
			direccion += indice.getValorEnt();
		}

		lista.add("\tSRF " + (nivelAct - s.getNivel()) + " " + direccion);

		// Si el símbolo es un parametro por referencia hace falta un DRF adicional
		// para obtener la dirección de la variable referenciada
		if (s.ES_REFERENCIA()) {
			lista.add("\tDRF");
		}

		// Si se ha pasado un índice y no es constante, añadir las instrucciones
		// y sumar sobre la dirección base
		if (indice != null && (indice.getValorEnt() == null || s.ES_REFERENCIA())) {
			addComentario("Indice");
			concatenarLista(indice.getListaInstr());
			lista.add("\tPLUS");
		}

		// Obtener valor
		lista.add("\tDRF");
	}

	// El símbolo s es una variable o parámetro
	public void addGetDireccion(Simbolo s, Integer nivelAct, RegistroExpr indice) {
		addComentario("Obtener dirección de " + s);

		Integer direccion = s.getDir();
		// Si se ha pasado un índice constante, sumar sobre la dirección
		if (indice != null && indice.getValorEnt() != null && !s.ES_REFERENCIA()) {
			addComentario("Indice: " + indice.getValorEnt());
			direccion += indice.getValorEnt();
		}

		lista.add("\tSRF " + (nivelAct - s.getNivel()) + " " + direccion);

		// Si el símbolo es un parametro por referencia hace falta un DRF adicional
		// para obtener la dirección de la variable referenciada
		if (s.ES_REFERENCIA()) {
			lista.add("\tDRF");
		}

		// Si se ha pasado un índice y no es constante, añadir las instrucciones
		// y sumar sobre la dirección base
		if (indice != null && (indice.getValorEnt() == null || s.ES_REFERENCIA())) {
			addComentario("Indice");
			concatenarLista(indice.getListaInstr());
			lista.add("\tPLUS");
		}
	}

	// Asignación

	public void addAsigVariable(Simbolo dest, Integer nivelAct, RegistroExpr indice, ListaInstr expr) {
		addComentario("Asignación a " + dest.getNombre());

		// Añadir código para calcular la dirección de la variable o parámetro
		addGetDireccion(dest, nivelAct, indice);

		// Añadir código para calcular el valor de la expresión
		concatenarLista(expr);

		// Asignación
		lista.add("\tASG");
	}


	// La lista de instrucciones debe contener el código para obtener la dirección
	// del primer elemento del vector
	// El símbolo dest debe contener un vector
	public void addAsigVectores(Simbolo dest, Simbolo orig, Integer nivelAct) {
		addComentario("Asignación directa de vectores");
		addComentario("Dest: " + dest);
		addComentario("Orig: " + orig);


		// Copiar resto de elementos
		for (int i = 0; i < dest.getLongitud(); i++) {
			RegistroExpr indice = new RegistroExpr();
			indice.setTipoEnt();
			indice.setValorEnt(i);
			indice.getListaInstr().addConstEnt(i);
			addGetDireccion(dest, nivelAct, indice);
			addGetValor(orig, nivelAct, indice);
			lista.add("\tASG");
		}


	}


	// Etiquetas
	public void addEtiqueta(Integer etiq) {
		lista.add("L" + etiq + ":");
	}



	// Saltos
	public void addSaltoIncod(Integer etiq) {
		lista.add("\tJMP L" + etiq);
	}

	public void addSaltoTrue(Integer etiq) {
		lista.add("\tJMT L" + etiq);
	}

	public void addSaltoFalse(Integer etiq) {
		lista.add("\tJMF L" + etiq);
	}



	// Sentencias

	public void addEscribirEnt(ListaInstr expr) {
		addComentario("Escribir entero");
		concatenarLista(expr);
		lista.add("\tWRT 1");
	}

	public void addEscribirChar(ListaInstr expr) {
		addComentario("Escribir char");
		concatenarLista(expr);
		lista.add("\tWRT 0");
	}

	// Se genera un if para escribir "True" o "False" dependiendo del valor
	// de la expresión
	public void addEscribirBool(ListaInstr expr, Integer etiq1, Integer etiq2) {
		addComentario("Escribir bool");

		ListaInstr si = new ListaInstr();
		si.addEscribirCad("True");
		ListaInstr sino = new ListaInstr();
		sino.addEscribirCad("False");

		addSeleccion(expr, si, sino, etiq1, etiq2);
	}

	public void addEscribirCad(String cad) {
		addComentario("Escribir cadena: " + cad);

		// Reemplazar '\r'
		cad = cad.replaceAll("\\\\r", "\r");
		// Reemplazar '\n'
		cad = cad.replaceAll("\\\\n", "\n");
		// Reemplazar '\t'
		cad = cad.replaceAll("\\\\t", "\t");

		char cadchar[] = cad.toCharArray();
		for (int i = 0; i < cadchar.length; i++) {
			addConstChar(cadchar[i]);
			lista.add("\tWRT 0");
		}
	}


	// Las instrucciones de expr obtienen el valor de una variable, si se
	// elimina la última instrucción queda la dirección sobre hay que leer
	public void addLeerChar(ListaInstr expr) {
		addComentario("Leer como char");
		concatenarLista(expr);
		eliminarUltimaInstr();
		lista.add("\tRD 0");
	}

	public void addLeerEnt(ListaInstr expr) {
		addComentario("Leer como entero");
		concatenarLista(expr);
		eliminarUltimaInstr();
		lista.add("\tRD 1");
	}

	public void addMientrasQue(ListaInstr expresion, ListaInstr sentencias, Integer etiq1, Integer etiq2) {
		// El código generado tiene la siguiente forma:
		//
		// 			JMF ETIQ1
		// ETIQ2:
		//    		<sentencias>
		// ETIQ1:
		//			<expresion>
		//			JMP ETIQ2
		addComentario("Mientras que");
		addSaltoIncod(etiq1);
		addEtiqueta(etiq2);
		concatenarLista(sentencias);
		addEtiqueta(etiq1);
		addComentario("Condicion mientras que");
		concatenarLista(expresion);
		addSaltoTrue(etiq2);
	}

	// Seleccion
	public void addSeleccionSimple(ListaInstr expresion, ListaInstr si, Integer etiq) {
		// El código generado tiene la siguiente forma:
		//
		//			<expresion>
		//			JMF ETIQ
		//			<si>
		// ETIQ:
		addComentario("Seleccion simple");
		concatenarLista(expresion);
		addSaltoFalse(etiq);
		addComentario("Si");
		concatenarLista(si);
		addEtiqueta(etiq);
	}

	public void addSeleccion(ListaInstr expresion, ListaInstr si, ListaInstr sino, Integer etiq1, Integer etiq2) {
		// El código generado tiene la siguiente forma:
		//
		//			<expresion>
		//			JMF ETIQ1
		//			<si>
		//			JMP ETIQ2
		// ETIQ1:
		//			<sino>
		// ETIQ2:
		addComentario("Seleccion");
		concatenarLista(expresion);
		addSaltoFalse(etiq1);
		addComentario("Si");
		concatenarLista(si);
		addSaltoIncod(etiq2);
		addEtiqueta(etiq1);
		addComentario("Si no");
		concatenarLista(sino);
		addEtiqueta(etiq2);
	}


	// Utilidades
	public ArrayList<String> getLista() {
		return lista;
	}

	public void addComentario(String comentario) {
		lista.add("; " + comentario);
	}

	public void concatenarLista(ListaInstr lista) {
		this.lista.addAll(lista.getLista());
	}

	public void eliminarUltimaInstr() {
		String instr = lista.get(lista.size() - 1);
		lista.remove(lista.size() - 1);
		addComentario("Instrucción eliminada (" + instr.trim() + ")");
	}
}
