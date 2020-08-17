/*********************************************************************************
 * Estructura para almacenar valores el valor de una expresión junto a su tipo.
 * También incluye operaciones para operar con estos registros.
 *
 * Fichero:    RegistroExpr.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      8/8/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

import lib.semantico.Simbolo.*;
import lib.aviso.Aviso;
import lib.generacioncodigo.ListaInstr;
import lib.semantico.RegistroOp;
import lib.semantico.RegistroOp.Operador;

import java.math.BigInteger;

public class RegistroExpr {

	private Integer valorEnt;
	private Boolean valorBool;
	private Character valorChar;
	private String valorCad;

	private Tipo_variable tipo;

	private Clase_parametro parametro;

	private Boolean asignable = false;

	private Boolean vector = false;
	private Integer longitud;

	// Generacion codigo
	// Contiene la lista de instrucciones necesaria para calcular la
	// expresión que representa el registro
	private ListaInstr instrucciones = new ListaInstr();


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

	public boolean esParVal() {
		return parametro == Clase_parametro.VAL;
	}

	public boolean esParRef() {
		return parametro == Clase_parametro.REF;
	}

	public boolean esAsignable() {
		return asignable;
	}

	public boolean esVector() {
		return vector;
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

	public void setAsignable(boolean asignable) {
		this.asignable = asignable;
	}

	public void setParVal() {
		this.parametro = Clase_parametro.VAL;
	}

	public void setParRef() {
		this.parametro = Clase_parametro.REF;
	}

	public void setVector(boolean vector) {
		this.vector = true;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}

	public Integer getLongitud() {
		return longitud;
	}

	public ListaInstr getInstr() {
		return instrucciones;
	}

	// Operar con registros

	public static RegistroExpr operar(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		// Para operar los registros, ambos deben ser del mismo tipo

		// No se puede operar con vectores
		if (reg1.esVector() || reg2.esVector()) {
			ErrorSemantico.deteccion("No se pueden realizar operaciones con vectores", op.getToken());
			RegistroExpr res = new RegistroExpr();
			res.setTipoDesc();
			return res;
		}

		// Si uno de los dos operandos es desconocido, no se realiza la operación y
		// se propaga desconocido
		if (reg1.esDesc() || reg2.esDesc()) {
			RegistroExpr res = new RegistroExpr();
			res.setTipoDesc();
			return res;
		}

		switch (op.getOp()) {
		// Operadores enteros
		case MAS:
		case MENOS:
		case PRODUCTO:
		case DIV:
		case DIVISION:
		case MOD:
			return operarEntero(op, reg1, reg2);

		// Operadores booleanos
		case OR:
		case AND:
			return operarBooleano(op, reg1, reg2);

		// Operadores de comparación
		case IGUAL:
		case MAI:
		case MAYOR:
		case MEI:
		case MENOR:
		case NI:
			return operarComparacion(op, reg1, reg2);

		default:
			RegistroExpr res = new RegistroExpr();
			res.setTipoDesc();
			return res;
		}

	}

	private static RegistroExpr operarEntero(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		RegistroExpr res = new RegistroExpr();
		res.setTipoEnt();

		if (!reg1.esEnt()) {
			ErrorSemantico.deteccion("El operando 1 debe ser entero", op.getToken());
		} else if (!reg2.esEnt()) {
			ErrorSemantico.deteccion("El operador 2 debe ser entero", op.getToken());
		} else if (reg1.getValorEnt() != null && reg2.getValorEnt() != null) {
			if (!hayDivisionPorCero(op, reg1.getValorEnt(), reg2.getValorEnt())
					&& !hayUnderflowOverflow(op, reg1.getValorEnt(), reg2.getValorEnt())) {
				switch (op.getOp()) {
				// Operadores enteros
				case MAS:
					res.setValorEnt(reg1.getValorEnt() + reg2.getValorEnt());
					break;
				case MENOS:
					res.setValorEnt(reg1.getValorEnt() - reg2.getValorEnt());
					break;
				case PRODUCTO:
					res.setValorEnt(reg1.getValorEnt() * reg2.getValorEnt());
					break;
				case DIV:
				case DIVISION:
					if (reg2.getValorEnt() == 0) {
						Aviso.deteccion(new DivisionPorCeroException(), op.getToken());
					} else {
						res.setValorEnt(reg1.getValorEnt() / reg2.getValorEnt());
					}
					break;
				case MOD:
					if (reg2.getValorEnt() == 0) {
						Aviso.deteccion(new DivisionPorCeroException(), op.getToken());
					} else {
						res.setValorEnt(reg1.getValorEnt() % reg2.getValorEnt());
					}
					break;
				default:
					break;
				}
			}
		}
		return res;
	}

	private static RegistroExpr operarBooleano(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		RegistroExpr res = new RegistroExpr();
		res.setTipoBool();

		if (!reg1.esBool()) {
			ErrorSemantico.deteccion("El operando 1 debe ser booleano", op.getToken());
		} else if (!reg2.esBool()) {
			ErrorSemantico.deteccion("El operando 2 debe ser booleano", op.getToken());
		} else if (reg1.getValorBool() != null && reg2.getValorBool() != null) {
			switch (op.getOp()) {
			// Operadores booleanos
			case OR:
				res.setValorBool(reg1.getValorBool() || reg2.getValorBool());
				break;
			case AND:
				res.setValorBool(reg1.getValorBool() && reg2.getValorBool());
				break;
			default:
				break;
			}
		}
		return res;
	}

	private static RegistroExpr operarComparacion(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		RegistroExpr res = new RegistroExpr();
		res.setTipoBool();

		if (reg1.getTipo() != reg2.getTipo()) {
			ErrorSemantico.deteccion("Los operandos deben ser del mismo tipo", op.getToken());
		} else {
			if (reg1.esEnt() && (reg1.getValorEnt() != null && reg2.getValorEnt() != null)) {
				res.setValorBool(compararEnt(op, reg1.getValorEnt(), reg2.getValorEnt()));
			} else if (reg1.esBool() && (reg1.getValorBool() != null && reg2.getValorBool() != null)) {
				res.setValorBool(compararBool(op, reg1.getValorBool(), reg2.getValorBool()));
			} else if (reg1.esChar() && (reg1.getValorChar() != null && reg2.getValorChar() != null)) {
				res.setValorBool(compararChar(op, reg1.getValorChar(), reg2.getValorChar()));
			} else if (reg1.esCad() && (reg1.getValorCad() != null && reg2.getValorCad() != null)) {
				res.setValorBool(compararCad(op, reg1.getValorCad(), reg2.getValorCad()));
			}
		}
		return res;
	}

	private static boolean compararEnt(RegistroOp op, Integer ent1, Integer ent2) {
		switch (op.getOp()) {
		case IGUAL:
			return ent1 == ent2;
		case MAI:
			return ent1 >= ent2;
		case MAYOR:
			return ent1 > ent2;
		case MEI:
			return ent1 <= ent2;
		case MENOR:
			return ent1 < ent2;
		case NI:
			return ent1 != ent2;
		default:
			return false;
		}
	}

	private static boolean compararBool(RegistroOp op, Boolean bool1, Boolean bool2) {
		switch (op.getOp()) {
		case IGUAL:
			return bool1 == bool2;
		case MAI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para booleanos", op.getToken());
			return false;
		case MAYOR:
			ErrorSemantico.deteccion("El operador '>' no esta definido para booleanos", op.getToken());
			return false;
		case MEI:
			ErrorSemantico.deteccion("El operador '<=' no esta definido para booleanos", op.getToken());
			return false;
		case MENOR:
			ErrorSemantico.deteccion("El operador '<' no esta definido para booleanos", op.getToken());
			return false;
		case NI:
			return bool1 != bool2;
		default:
			return false;
		}
	}

	private static boolean compararChar(RegistroOp op, Character char1, Character char2) {
		switch (op.getOp()) {
		case IGUAL:
			return char1 == char2;
		case MAI:
			return char1 >= char2;
		case MAYOR:
			return char1 > char2;
		case MEI:
			return char1 <= char2;
		case MENOR:
			return char1 < char2;
		case NI:
			return char1 != char2;
		default:
			return false;
		}
	}

	private static boolean compararCad(RegistroOp op, String cad1, String cad2) {
		switch (op.getOp()) {
		case IGUAL:
			return cad1.equals(cad2);
		case MAI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para cadenas", op.getToken());
			return false;
		case MAYOR:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para cadenas", op.getToken());
			return false;
		case MEI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para cadenas", op.getToken());
			return false;
		case MENOR:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para cadenas", op.getToken());
			return false;
		case NI:
			return !cad1.equals(cad2);
		default:
			return false;
		}
	}

	private static boolean hayDivisionPorCero(RegistroOp op, Integer ent1, Integer ent2) {
		if ((op.getOp() == Operador.DIV || op.getOp() == Operador.DIVISION || op.getOp() == Operador.MOD)
				&& ent2 == 0) {
			Aviso.deteccion(new DivisionPorCeroException(), op.getToken());
			return true;
		} else {
			return false;
		}
	}

	private static boolean hayUnderflowOverflow(RegistroOp op, Integer ent1, Integer ent2) {
		BigInteger bi1 = BigInteger.valueOf(ent1);
		BigInteger bi2 = BigInteger.valueOf(ent2);

		System.out.println(bi1);
		System.out.println(bi2);

		BigInteger res;
		boolean detectado = false;

		switch (op.getOp()) {
		case MAS:
			res = bi1.add(bi2);
			break;
		case MENOS:
		case PRODUCTO:
			res = bi1.multiply(bi2);
			break;
		case DIV:
		case DIVISION:
			res = bi1.divide(bi2);
			break;
		case MOD:
			res = bi1.remainder(bi2);
			break;
		default:
			res = BigInteger.ZERO;
			break;
		}

		if (res.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
			detectado = true;
			Aviso.deteccion(new OverflowException(), op.getToken());
		}

		if (res.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1) {
			detectado = true;
			Aviso.deteccion(new UnderflowException(), op.getToken());
		}

		return detectado;
	}

}
