/*********************************************************************************
 * Estructura para almacenar listas de instrucciones de código intermedio
 *
 * Fichero:    GeneracionCodigo.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v2.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.generacioncodigo;

import java.util.ArrayList;

import lib.semantico.RegistroOp;

public class ListaInstr {

	// Las instrucciones se almacenan en forma de Strings

	private ArrayList<String> lista = new ArrayList<String>();

	public void addOpBinaria(RegistroOp op) {
		String instr = "";

		switch (op.getOp()) {
		case IGUAL:
			instr = "EQ";
			break;
		case NI:
			instr = "NEQ";
		case MAYOR:
			instr = "GT";
		case MAI:
			instr = "GTE";
		case MENOR:
			instr = "LT";
		case MEI:
			instr = "LTE";

		case  MAS:
			instr = "PLUS";
		case MENOS:
			instr = "SBT";
		case OR:
			instr = "OR";

		case PRODUCTO:
			instr = "TMS";
		case DIVISION:
			instr = "DIV";
		case MOD:
			instr = "MOD";
		case DIV:
			instr = "DIV";
		case AND:
			instr = "AND";
		}
		lista.add(instr);
	}

	public void addMenosUnario() {
		lista.add("NGI");
	}

	public void addNegacionUnaria() {
		lista.add("NGB");
	}

	public void addWrite() {
	}

	public void addRead() {
	}





	public void eliminarUltimaInstr() {
		lista.remove(lista.size() - 1);
	}


}
