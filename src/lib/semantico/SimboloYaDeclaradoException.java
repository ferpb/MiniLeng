/*********************************************************************************
 * Excepción utilizada al intentar declarar un símbolo con el mismo nombre
 * que alguno de los existentes en el nivel actual
 *
 * Fichero:    SimboloYaDeclaradoException.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      29/03/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

public class SimboloYaDeclaradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public SimboloYaDeclaradoException() {

	}
}
