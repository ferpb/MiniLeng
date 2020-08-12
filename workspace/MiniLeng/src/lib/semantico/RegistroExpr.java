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

import java.math.BigInteger;

import lib.semantico.RegistroOp.*;

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
	
	
	
	
	
	
	
	// Operar con registros
	public static RegistroExpr operar(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		// Para operar los registros, ambos deben ser del mismo tipo
		
		// Si uno de los dos operandos es desconocido, no se realiza la operación y
	    // se propaga desconocido
		if (reg1.esDesc() || reg2.esDesc()) {
			RegistroExpr res = new RegistroExpr();
			res.setTipoDesc();
			return res;
		}
		
		switch(op.getOp()) {
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

		if (!reg1.esEnt()) {
			ErrorSemantico.deteccion("El operando 1 debe ser entero", op.getToken());
			res.setTipoDesc();
		}
		else if (!reg2.esEnt()) {
			ErrorSemantico.deteccion("El operador 2 debe ser entero", op.getToken());
			res.setTipoDesc();
		}
		else {
			if (hayUnderflowOverflow(op, reg1.getValorEnt(), reg2.getValorEnt())) {
				res.setTipoDesc();
			}
			else {
				res.setTipoEnt();
				switch(op.getOp()) {
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
					res.setValorEnt(reg1.getValorEnt() / reg2.getValorEnt());
					break;
				case MOD:
					res.setValorEnt(reg1.getValorEnt() % reg2.getValorEnt());
					break;
				default:
					res.setTipoDesc();
					break;
				}
			}
		}
		return res;
	}
	
	
	private static RegistroExpr operarBooleano(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		RegistroExpr res = new RegistroExpr();

		if (!reg1.esEnt()) {
			ErrorSemantico.deteccion("El operando 1 debe ser booleano", op.getToken());
			res.setTipoDesc();
		}
		else if (!reg2.esEnt()) {
			ErrorSemantico.deteccion("El operando 2 debe ser booleano", op.getToken());
			res.setTipoDesc();
		}
		else {
			if (hayUnderflowOverflow(op, reg1.getValorEnt(), reg2.getValorEnt())) {
				res.setTipoDesc();
			}
			else {
				res.setTipoEnt();
				switch(op.getOp()) {
				// Operadores booleanos
				case OR:
					res.setValorBool(reg1.getValorBool() || reg2.getValorBool());
					break;
				case AND:
					res.setValorBool(reg1.getValorBool() && reg2.getValorBool());
					break;
				default:
					res.setTipoDesc();
					break;
				}
			}
		}
		return res;
	}
	
	
	private static RegistroExpr operarComparacion(RegistroOp op, RegistroExpr reg1, RegistroExpr reg2) {
		RegistroExpr res = new RegistroExpr();

		if (reg1.getTipo() != reg2.getTipo()) {
			ErrorSemantico.deteccion("Los operandos deben ser del mismo tipo", op.getToken());
			res.setTipoDesc();
		}
		else {
			res.setTipoBool();
			if (reg1.esEnt()) {
				res.setValorBool(compararEnt(op, reg1.getValorEnt(), reg2.getValorEnt()));
			}
			else if (reg1.esBool()) {
				res.setValorBool(compararBool(op, reg1.getValorBool(), reg2.getValorBool()));
			}
			else if (reg1.esChar()) {
				res.setValorBool(compararChar(op, reg1.getValorChar(), reg2.getValorChar()));
			}
			else if (reg1.esCad()) {
				res.setValorBool(compararCad(op, reg1.getValorCad(), reg2.getValorCad()));
			}
			else {
				res.setTipoDesc();
			}
		}	
		return res;
	}
	
	
	private static boolean compararEnt(RegistroOp op, Integer ent1, Integer ent2) {
		switch(op.getOp()) {
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
		switch(op.getOp()) {
		case IGUAL:
			return bool1 == bool2;
		case MAI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para operadores booleanos", op.getToken());
			return false;
		case MAYOR:
			ErrorSemantico.deteccion("El operador '>' no esta definido para operadores booleanos", op.getToken());
			return false;
		case MEI:
			ErrorSemantico.deteccion("El operador '<=' no esta definido para operadores booleanos", op.getToken());
			return false;
		case MENOR:
			ErrorSemantico.deteccion("El operador '<' no esta definido para operadores booleanos", op.getToken());
			return false;
		case NI:
			return bool1 != bool2;
		default:
			return false;
		}
	}
	
	private static boolean compararChar(RegistroOp op, Character char1, Character char2) {
		switch(op.getOp()) {
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
		switch(op.getOp()) {
		case IGUAL:
			return cad1.equals(cad2); 
		case MAI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para operadores cadenas", op.getToken());
			return false;
		case MAYOR:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para operadores cadenas", op.getToken());
			return false;
		case MEI:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para operadores cadenas", op.getToken());
			return false;
		case MENOR:
			ErrorSemantico.deteccion("El operador '>=' no esta definido para operadores cadenas", op.getToken());
			return false;
		case NI:
			return !cad1.equals(cad2);
		default:
			return false;
		}
	}
	
	
	private static boolean hayUnderflowOverflow(RegistroOp op, Integer ent1, Integer ent2) {
		BigInteger bi1 = BigInteger.valueOf(ent1);
		BigInteger bi2 = BigInteger.valueOf(ent2);
		
		BigInteger res;
		boolean detectado = false;
		
		switch(op.getOp()) {
		case MAS:
			res = bi1.add(bi2);
		case MENOS:
		case PRODUCTO:
			res = bi1.multiply(bi2);
		case DIV:
		case DIVISION:
			res = bi1.divide(bi2);
		case MOD:
			res = bi1.mod(bi2);
		default:
			res = BigInteger.ZERO;
		}
		
		if (res.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
			detectado = true;
			ErrorSemantico.deteccion(new OverflowException(), op.getToken());
		}
		
		if (res.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1) {
			detectado = true;
			ErrorSemantico.deteccion(new UnderflowException(), op.getToken());
		}
		
		return detectado;
	}
	
}
