/*********************************************************************************
 * Clase que define la tabla de símbolos del compilador de MiniLeng.
 * Se implementa mediante una tabla de dispersión abierta.
 *
 * Fichero:    Tabla_simbolos.java
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      1/5/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

package lib.semantico;

import lib.semantico.Simbolo.*;

import lib.aviso.Aviso;

import java.util.Random;

import analizador.Token;
import analizador.minilengcompiler;

import java.util.LinkedList;
import java.util.Iterator;

public class TablaSimbolos {
	private final int M = 256;

	private int T[];

	/*
	 * La tabla de hash es una tabla abierta, las colisiones se resuelven encadenándolas
	 * en una lista enlazada
     *
	 * Los símbolos que colisionan se almacenan en las listas por nivel descendente,
	 * de esta forma el primer símbolo de la lista siempre tiene nivel mayor o igual que los
	 * demás.
	 */

	private LinkedList<Simbolo> tabla_hash[];

	@SuppressWarnings("unchecked")
	public TablaSimbolos() {
		// Crea la tabla hash
		tabla_hash = new LinkedList[M];

		T = new int[M];
	}

	/*
	 * Crea una tabla de símbolos vacía. Este procedimiento debe invocarse antes de
	 * empezar a utilizar la tabla de símbolos.
	 */
	public void inicializar_tabla() {
		for (int i = 0; i < M; i++) {
			tabla_hash[i] = new LinkedList<Simbolo>();
		}

		// Generar vector T
		for (int i = 0; i < M; i++) {
			T[i] = i;
		}

		mezclaVector(T);
	}

	/*
	 * Algoritmo de Fisher–Yates para permutar aleatoriamente los elementos de un
	 * vector
	 */
	private void mezclaVector(int[] a) {
		Random rnd = new Random();
		for (int i = a.length - 1; i > 0; i--) {
			// Genera un número aleatorio j tal que 0 <= j <= i
			int j = rnd.nextInt(i + 1);

			// Intercambia a[j] y a[i]
			int aux = a[j];
			a[j] = a[i];
			a[i] = aux;
		}
	}

	/*
	 * Función de hash utilizando el algoritmo de Pearson
	 */
	private int h(String cadena) {
		int h = 0;
		for (int i = 0; i < cadena.length(); i++) {
			h = T[h ^ cadena.charAt(i)];
		}
		return h;
	}

	/*
	 * Busca en la tabla el símbolo de mayor nivel cuyo nombre coincida con el del
	 * parámetro (se distinguen minúsculas y mayúsculas). Si existe, devuelve un
	 * puntero como resultado, de lo contrario lanza una excepción.
	 */
	public Simbolo buscar_simbolo(String nombre) throws SimboloNoEncontradoException {
		int clave = h(nombre);
		for (Simbolo s : tabla_hash[clave]) {
			if (s.nombre.equals(nombre)) {
				return s;
			}
		}
		// Si no se ha encontrado
		throw new SimboloNoEncontradoException();
	}

	/*
	 * Introduce en la tabla un símbolo PROGRAMA, con el nombre del parámetro, de
	 * nivel 0, con la dirección del parámetro. Dado que debe ser el primer símbolo
	 * a introducir, NO SE VERIFICA QUE EL SÍMBOLO YA EXISTA.
	 */
	public Simbolo introducir_programa(String nombre, int dir) {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_programa(nombre, 0, dir);
		simbolo.setDir(dir);

		int clave = h(nombre);
		tabla_hash[clave].addFirst(simbolo);

		return simbolo;
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo, nombre,
	 * lanza una excepción. De lo contrario, introduce el símbolo pasado como
	 * parámetro.
	 */
	private Simbolo introducir_simbolo(Simbolo simbolo) throws SimboloYaDeclaradoException {
		int clave = h(simbolo.getNombre());

		for (Simbolo s : tabla_hash[clave]) {
			// Si el símbolo ya está declarado en ese mismo nivel, lanzar una excepción
			if (s.getNombre().equals(simbolo.getNombre()) && s.getNivel() == simbolo.getNivel()) {
				throw new SimboloYaDeclaradoException();
			}
			// Si hay un símbolo ya declarado con ese nombre en otro nivel, mostrar un aviso
			else if (s.getNombre().equals(simbolo.getNombre())) {
				Token t = minilengcompiler.token;
				if (simbolo.ES_VECTOR()) {
					// Evitar que el token sea ']'
					t.image = simbolo.nombre;
				}
				Aviso.deteccion("Este símbolo, definido en el nivel " + simbolo.nivel +
						", va a ocultar a otro definido con el mismo nombre en el nivel " + s.nivel + "",
						t);
			}
		}

		// Si no se ha lanzado la excepción, se añade
		tabla_hash[clave].addFirst(simbolo);

		return simbolo;
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo nombre, lanza
	 * una excepción. De lo contrario, introduce un símbolo VARIABLE (simple) con
	 * los datos de los argumentos.
	 */
	public Simbolo introducir_variable(String nombre, Tipo_variable variable, int nivel, int dir)
			throws SimboloYaDeclaradoException {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_variable(nombre, variable, nivel, dir);
		return introducir_simbolo(simbolo);
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo nombre, lanza
	 * una excepción. De lo contrario, introduce un símbolo ACCIÓN con los datos de
	 * los argumentos.
	 */
	public Simbolo introducir_accion(String nombre, int nivel, int dir) throws SimboloYaDeclaradoException {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_accion(nombre, nivel, dir);
		return introducir_simbolo(simbolo);
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo nombre, lanza
	 * una excepción. De lo contrario, introduce un símbolo PARÁMETRO con los datos
	 * de los argumentos.
	 */
	public Simbolo introducir_parametro(String nombre, Tipo_variable variable, Clase_parametro parametro, int nivel,
			int dir) throws SimboloYaDeclaradoException {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_parametro(nombre, variable, parametro, nivel, dir);

		return introducir_simbolo(simbolo);
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo nombre, lanza
	 * una excepción. De lo contrario, introduce un símbolo VARIABLE VECTOR con los datos
	 * de los argumentos.
	 */
	public Simbolo introducir_variable_vector(String nombre, Tipo_variable variable, int longitud, int nivel,
			int dir) throws SimboloYaDeclaradoException {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_variable_vector(nombre, variable, longitud, nivel, dir);

		return introducir_simbolo(simbolo);
	}

	/*
	 * Si existe un símbolo en la tabla del mismo nivel y con el mismo nombre, lanza
	 * una excepción. De lo contrario, introduce un símbolo PARÁMETRO VECTOR con los datos
	 * de los argumentos.
	 */
	public Simbolo introducir_parametro_vector(String nombre, Tipo_variable variable, Clase_parametro clase, int longitud, int nivel,
			int dir) throws SimboloYaDeclaradoException {
		Simbolo simbolo = new Simbolo();
		simbolo.introducir_parametro_vector(nombre, variable, clase, longitud, nivel, dir);

		return introducir_simbolo(simbolo);
	}

	/*
	 * Elimina de la tabla todos los símbolos del nivel y tipo pasados como
	 * parámetros
	 */
	private void eliminar_tipo_en_nivel(int nivel, Tipo_simbolo tipo) {
		for (int i = 0; i < M; i++) {
			Iterator<Simbolo> iter = tabla_hash[i].iterator();
			while (iter.hasNext()) {
				Simbolo s = iter.next();
				if (s.nivel == nivel && s.tipo == tipo) {
					iter.remove();
				}
			}
		}
	}

	/*
	 * Elimina el símbolo con nombre y nivel iguales a los pasados como parámetros
	 */
	private void eliminar_simbolo(String nombre, int nivel) {
		int clave = h(nombre);
		Iterator<Simbolo> iter = tabla_hash[clave].iterator();

		Boolean borrado = false;
		while (iter.hasNext() && borrado == false) {
			Simbolo s = iter.next();
			if (s.nombre == nombre && s.nivel == nivel) {
				iter.remove();
				borrado = true;
			}
		}
	}

	/*
	 * Elimina de la tabla todos los símbolos PROGRAMA de nivel 0 (debería haber uno
	 * solo).
	 */
	public void eliminar_programa() {
		eliminar_tipo_en_nivel(0, Tipo_simbolo.PROGRAMA);
	}

	/*
	 * Elimina de la tabla todas las variables que sean del nivel del argumento. NO
	 * ELIMINA PARÁMETROS.
	 */
	public void eliminar_variables(int nivel) {
		eliminar_tipo_en_nivel(nivel, Tipo_simbolo.VARIABLE);
	}

	/*
	 * Elimina de la tabla todos los parametros de un nivel.
	 */
	public void eliminar_parametros(int nivel) {
		eliminar_tipo_en_nivel(nivel, Tipo_simbolo.PARAMETRO);
	}

	/*
	 * Elimina de la tabla todas las acciones de un nivel. LOS PARAMETROS DE ESTAS
	 * ACCIONES DEBEN SER ELIMINADOS TAMBIÉN PARA MANTENER LA COHERENCIA DE LA
	 * TABLA.
	 */
	public void eliminar_acciones(int nivel) {
		eliminar_tipo_en_nivel(nivel, Tipo_simbolo.ACCION);
	}

	/*
	 * Imprime por pantalla el contenido de la tabla de símbolos
	 */
	public void imprimirTabla() {
		System.out.println("+" + new String(new char[59]).replace("\0", "-") + "+");
		System.out.println("| Tabla de símbolos                                         |");
		System.out.println("+" + new String(new char[59]).replace("\0", "-") + "+");

		for (int i = 0; i < tabla_hash.length; i++) {
			if (!tabla_hash[i].isEmpty()) {
				Simbolo s = tabla_hash[i].getFirst();
				System.out.format("| %5d  %-50s |\n", i, s);

				if (tabla_hash[i].size() > 1) {
					for (Simbolo resto : tabla_hash[i].subList(1, tabla_hash[i].size())) {
						System.out.format("|        %-50s |\n", resto);
					}
				}
			}
		}
		System.out.println("+" + new String(new char[59]).replace("\0", "-") + "+");
	}
}
