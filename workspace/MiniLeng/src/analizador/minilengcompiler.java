/* Generated By:JavaCC: Do not edit this line. minilengcompiler.java */
package analizador;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;

import analizador.TokenMgrError;
import analizador.minilengcompilerTokenManager;
import analizador.SimpleCharStream;

import lib.lexico.TablaOcurrencias;
import lib.lexico.ErrorLexico;
import lib.sintactico.ErrorSintactico;
import lib.sintactico.PanicMode;
import lib.semantico.Simbolo.*;
import lib.semantico.TablaSimbolos;
import lib.semantico.SimboloYaDeclaradoException;
import lib.semantico.ErrorSemantico;

public class minilengcompiler implements minilengcompilerConstants {

        private static final String version = "2.2";
        private static final String fecha_version = "abril de 2020";
        private static final String fecha_compilado = "10-04-2020";


        protected static Boolean verbose_mode = false;
        protected static Boolean panic_mode = false;
        protected static Boolean show_tokens = false;

        private static Boolean compilado_sin_errores = true;
        private static Boolean entrado_en_panic = false;

        private static TablaSimbolos tabla_simbolos;
        private static int nivel;
        private static int dir;


        private static void help() {
                System.out.println("Uso: minilengcompiler [opciones] fichero\u005cn");
        System.out.println("Opciones:");
        System.out.println("  -v, --verbose  Mostrar un resumen de los s\u00edmbolos utilizados en el programa");
        System.out.println("  -p, --panic\u0009 Compila con panic mode");
        System.out.println("  -t, --tokens   Muestra los tokens que se van reconociendo");
        System.out.println("  -h, --help\u0009 Imprimir ayuda (esta pantalla) y salir");
        System.out.println("  --version      Imprimir informaci\u00f3n de la versi\u00f3n y salir");

        System.exit(0);
    }

    private static void version() {
                System.out.println("  Compilador de MiniLeng Versi\u00f3n " + version + "    Compilado el " + fecha_compilado);
                System.out.println("");
                System.out.println("    Pr\u00e1cticas de la asignatura: Procesadores de Lenguajes");
                System.out.println("      Curso 2019-2020");
                System.out.println("      Universidad de Zaragoza");
                System.out.println("");
                System.out.println("  Programado con JavaCC en Eclipse 2019-12");
                System.out.println("  JavaCC Eclipse Plug-in 1.5.33");

        System.exit(0);
    }

        public static void main(String args []) throws ParseException {
                System.out.println("Compilador de MiniLeng -- v" + version + " (" + fecha_version + ")");
        System.out.println("Autor: Fernando Pe\u00f1a Bes (NIA: 756012)\u005cn");

                // Entrada al programa
        InputStream stream = System.in;
        String fichero_entrada = null;


        if (args.length == 0) {
                // Compilador llamado sin argumentos
        }
        else if (args[0].equals("-h") || args[0].equals("--help")) {
                // Mostrar ayuda y salir
                help();
                }
                else if (args[0].equals("--version")) {
                // Mostrar version y salir
                version();
                }
                else {
                        // Leer los argumentos.
                        // Va leyendo hasta que encuentra uno que no empieza por '-',
                        // ese argumento se considera como fichero de entrada
                        for (int i = 0; i < args.length; i++) {
                            switch(args[i]) {
                                case "-h":
                                case "--help":
                                case "--version":
                                    // Ignorar si aparece ayuda o versión de nuevo
                                        break;

                                case "-v":
                                case "--verbose":
                                        // Activar modo verboso
                                        verbose_mode = true;
                                        break;

                                case "-p":
                                case "--panic":
                                        // Activar modo pánico
                                        panic_mode = true;
                                        break;

                                case "-t":
                                case "--tokens":
                                        // Mostrar tokens conforme se reconocen
                                        show_tokens = true;
                                        break;

                                        default:
                                                if (args[i].charAt(0) == '-') {
                                                        System.err.println("MiniLeng: Opci\u00f3n inv\u00e1lida <" + args[i] + ">\u005cn");
                                                        help();
                                                }
                                                else {
                                                        fichero_entrada = args[i];
                                                }
                                                break;
                            }
                        }
                }


                if (fichero_entrada != null) {
                        // Lectura del fichero del usuario.

                        // Si el fichero no terminal en .ml, error
                        if (!fichero_entrada.endsWith(".ml")) {
                        System.err.println("MiniLeng: El fichero a compilar tiene que tener extensi\u00f3n .ml");
                        System.err.println("          Fichero introducido: '" + fichero_entrada + "'");
                        System.exit(0);
                        }

                        // Ejecutar el compilador con los fichero introducidos
                        System.out.println("MiniLeng: Leyendo el fichero " + fichero_entrada + " ...");
                try {
                        // parser = new minilengcompiler(new FileInputStream(args[0]));
                        stream = new FileInputStream(fichero_entrada);
                }
                catch (FileNotFoundException e) {
                        System.err.println("MiniLeng: No se ha encontrado el fichero '" + fichero_entrada + "'");
                        System.exit(0);
                }
                }
                else {
                        help();
                }


                // Ejecución del compilador
        try {
                minilengcompiler parser = new minilengcompiler(stream);
                tabla_simbolos = new TablaSimbolos();
                minilengcompiler.programa();
        }
        catch (Exception e) {
                        // Detectado error sintáctico
            // System.out.println("NOK.");
            // System.out.println(e.getMessage());
        }
        catch (Error e) {
                        // Detectado error léxico
                        SimpleCharStream entrada = minilengcompilerTokenManager.input_stream;
                        String error;

                        try {
                                error = Character.toString(entrada.readChar());
                        }
                        catch (java.io.IOException fin_fichero) {
                                error = "<EOF>";
                        }

                        ErrorLexico.deteccion(entrada.getEndLine(), entrada.getEndColumn(), error, TokenMgrError.addEscapes(error));
                }

                // Imprimir resultados de la compilación
                resultadosCompilacion();
    }

        static void resultadosCompilacion() {
                System.out.println("");

                // Mostrar contadores de errores
                if (ErrorLexico.getContadorErrores() > 0) {
                        compilado_sin_errores = false;
                        System.out.println("Errores l\u00e9xicos: " + ErrorLexico.getContadorErrores());
                }

                if (ErrorSintactico.getContadorErrores() > 0) {
                        compilado_sin_errores = false;
                        System.out.println("Errores sint\u00e1cticos: " + ErrorSintactico.getContadorErrores());
                }

            if (PanicMode.getContadorErrores() > 0) {
                        entrado_en_panic = true;
                        System.out.println("Veces activado panic mode: " + PanicMode.getContadorErrores());
                }

                // Resultados compilacion
                if (!compilado_sin_errores) {
                        System.out.println("No se ha podido compilar el programa.");
                }
                else if (entrado_en_panic) {
                        System.out.println("Se ha activado el panic mode durante la compilaci\u00f3n. Corrige los errores y vuelve a compilar.");
                }
                else {
                        System.out.println("Compilado sin errores!");
                }
        }

/**** Análisis sintáctico ****/

// Inicio programa

/*
 * programa	::=	<tPROGRAMA> identificador fin_sentencia declaracion_variables declaracion_acciones bloque_sentencias <EOF>
 */
  static final public int programa() throws ParseException {
  int nivel = 0;
  tabla_simbolos.inicializar_tabla();

  Token t;
    try {
      jj_consume_token(tPROGRAMA);
      t = identificador();
                tabla_simbolos.introducir_programa(t.image, 0);
      fin_sentencia();
      declaracion_variables();
      declaracion_acciones();
      bloque_sentencias();
      jj_consume_token(0);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "La declaraci\u00f3n del programa es incorrecta");
    }
    if (verbose_mode) {
      token_source.tabla_ocurrencias.imprimirTabla();
    }
    {if (true) return 0;}
    throw new Error("Missing return statement in function");
  }

// Declaraciones de separadores y limitadores de bloque
  static final public void fin_sentencia() throws ParseException {
    try {
      jj_consume_token(tFIN_SENTENCIA);
    } catch (ParseException e) {
    // Si el modo pánico está activado, descartar entrada hasta el siguiente ;
    if (panic_mode) {
      PanicMode.iniciar(e, "Se esperaba ';'", tFIN_SENTENCIA, ";");
    }
    else {
      ErrorSintactico.deteccion(e, "Se esperaba ';'");
    }
    }
  }

  static final public void sep_variable() throws ParseException {
    try {
      jj_consume_token(tSEP_VARIABLE);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba ','");
    }
  }

  static final public void parentesis_izq() throws ParseException {
    try {
      jj_consume_token(tPARENTESIS_IZQ);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Falta par\u00e9ntesis de cierre: ')'");
    }
  }

  static final public void parentesis_der() throws ParseException {
    try {
      jj_consume_token(tPARENTESIS_DER);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba '('");
    }
  }

  static final public void principio() throws ParseException {
    try {
      jj_consume_token(tPRINCIPIO);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba el delimitador de principio de bloque: 'principio'");
    }
  }

  static final public void fin() throws ParseException {
    try {
      jj_consume_token(tFIN);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba el delimitador fin de bloque: 'fin'");
    }
  }

// Sintaxis del lenguaje
  static final public Token identificador() throws ParseException {
  Token t;
    try {
      t = jj_consume_token(tIDENTIFICADOR);
        {if (true) return t;}
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un identificador");
    }
    throw new Error("Missing return statement in function");
  }

/*
 * declaracion_variables ::= ( declaracion fin_sentencia )*
 */
  static final public void declaracion_variables() throws ParseException {
    try {
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tENTERO:
        case tBOOLEANO:
        case tCARACTER:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        declaracion();
        fin_sentencia();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "");
    }
  }

/*
 * declaracion ::= tipo_variables identificadores
 */
  static final public void declaracion() throws ParseException {
  ArrayList<Token> ids;
  Tipo_variable tipo;
    try {
      tipo = tipo_variables();
      ids = identificadores();
          for (Token id : ids) {
            try {
              tabla_simbolos.introducir_variable(id.image, tipo, nivel, dir);
              dir++;
            }
            catch (SimboloYaDeclaradoException e) {
              ErrorSemantico.deteccion(e, id);
            }
          }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una declaraci\u00f3n de variables");
    }
  }

/*
 * tipo_variables	::=	( <tENTERO> | <tCARACTER> | <tBOOLEANO> )
 */
  static final public Tipo_variable tipo_variables() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tENTERO:
        jj_consume_token(tENTERO);
                    {if (true) return Tipo_variable.ENTERO;}
        break;
      case tCARACTER:
        jj_consume_token(tCARACTER);
                  {if (true) return Tipo_variable.CHAR;}
        break;
      case tBOOLEANO:
        jj_consume_token(tBOOLEANO);
                  {if (true) return Tipo_variable.CADENA;}
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    // Esto creo que es semantico
    // ErrorSintactico.deteccion(e, "Tipo de dato desconocido, se esperaba: 'entero', 'caractero' o 'booleano'");
    ErrorSintactico.deteccion(e, "Se esperaba un tipo de dato");
    }
    throw new Error("Missing return statement in function");
  }

/*
 * identificadores	::=	identificador ( sep_variable identificador )*
 */
  static final public ArrayList<Token> identificadores() throws ParseException {
        ArrayList<Token> tokens = new ArrayList<Token>();
        Token t;
    try {
      t = identificador();
          tokens.add(t);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tSEP_VARIABLE:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
        sep_variable();
        t = identificador();
            tokens.add(t);
      }
          {if (true) return tokens;}
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba uno o varios identificadores");
    }
    throw new Error("Missing return statement in function");
  }

/*
 * declaracion_acciones	::=	( declaracion_accion )*
 */
  static final public void declaracion_acciones() throws ParseException {
    try {
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tACCION:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
        declaracion_accion();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "");
    }
  }

/*
 * declaracion_accion	::=	cabecera_accion fin_sentencia declaracion_variables declaracion_acciones bloque_sentencias
 */
  static final public void declaracion_accion() throws ParseException {
    try {
      cabecera_accion();
      fin_sentencia();
      declaracion_variables();
      declaracion_acciones();
      bloque_sentencias();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una declaraci\u00f3n de acci\u00f3n");
    }
  }

/*
 * cabecera_accion	::=	<tACCION> identificador parametros_formales
 */
  static final public void cabecera_accion() throws ParseException {
    try {
      jj_consume_token(tACCION);
      identificador();
      parametros_formales();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Falta la cabecera de la acci\u00f3n");
    }
  }

/*
 * parametros_formales	::=	( parentesis_izq ( lista_parametros )? parentesis_der )?
 */
  static final public void parametros_formales() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tPARENTESIS_IZQ:
        parentesis_izq();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tVAL:
        case tREF:
          lista_parametros();
          break;
        default:
          jj_la1[4] = jj_gen;
          ;
        }
        parentesis_der();
        break;
      default:
        jj_la1[5] = jj_gen;
        ;
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "");
    }
  }

/*
 * lista_parametros	::=	parametros ( fin_sentencia parametros )*
 */
  static final public void lista_parametros() throws ParseException {
    try {
      parametros();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tFIN_SENTENCIA:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_4;
        }
        fin_sentencia();
        parametros();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaban uno o varios par\u00e1metros");
    }
  }

/*
 * parametros	::=	clase_parametros declaracion
 */
  static final public void parametros() throws ParseException {
    try {
      clase_parametros();
      declaracion();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un par\u00e1metro");
    }
  }

/*
 * clase_parametros	::=	( <tVAL> | <tREF> )
 */
  static final public void clase_parametros() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tVAL:
        jj_consume_token(tVAL);
        break;
      case tREF:
        jj_consume_token(tREF);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    // Esto creo que es semantico
    // ErrorSintactico.deteccion(e, "Tipo de parámetro desconocido. Se esperaba 'val' o 'ref'");
    ErrorSintactico.deteccion(e, "Se esperaba un tipo de parametro");
    }
  }

/*
 * bloque_sentencias	::=	principio lista_sentencias fin
 */
  static final public void bloque_sentencias() throws ParseException {
    try {
      principio();
      lista_sentencias();
      fin();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un bloque se sentencias");
    }
  }

/*
 * lista_sentencias	::=	sentencia ( sentencia )*
 */
  static final public void lista_sentencias() throws ParseException {
    try {
      sentencia();
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tSI:
        case tMQ:
        case tESCRIBIR:
        case tLEER:
        case tIDENTIFICADOR:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_5;
        }
        sentencia();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaban una o m\u00e1s sentencias");
    }
  }

/*
 * sentencia	::=	( leer | escribir | identificacion | seleccion | mientras_que )
 */
  static final public void sentencia() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tLEER:
        leer();
        break;
      case tESCRIBIR:
        escribir();
        break;
      case tIDENTIFICADOR:
        identificacion();
        break;
      case tSI:
        seleccion();
        break;
      case tMQ:
        mientras_que();
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una sentencia.");
    }
  }

/*
 * leer	::=	<tLEER> parentesis_izq lista_asignables parentesis_der fin_sentencia
 */
  static final public void leer() throws ParseException {
    try {
      jj_consume_token(tLEER);
      parentesis_izq();
      lista_asignables();
      parentesis_der();
      fin_sentencia();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba sentencia leer");
    }
  }

/*
 * lista_asignables	::=	identificadores
 */
  static final public void lista_asignables() throws ParseException {
    try {
      identificadores();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba lista de asignables");
    }
  }

/*
 * escribir	::=	<tESCRIBIR> parentesis_izq lista_escribibles parentesis_der fin_sentencia
 */
  static final public void escribir() throws ParseException {
    try {
      jj_consume_token(tESCRIBIR);
      parentesis_izq();
      lista_escribibles();
      parentesis_der();
      fin_sentencia();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba sentencia escribir");
    }
  }

/*
 * lista_escribibles	::=	lista_expresiones
 */
  static final public void lista_escribibles() throws ParseException {
    try {
      lista_expresiones();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba lista de escribibles");
    }
  }

/*
 * asignacion	::=	<tOPAS> expresion fin_sentencia
 */
  static final public void asignacion() throws ParseException {
    try {
      jj_consume_token(tOPAS);
      expresion();
      fin_sentencia();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una asignaci\u00f3n");
    }
  }

/*
 * identificacion	::=	identificador ( ( argumentos )? fin_sentencia | asignacion )
 */
  static final public void identificacion() throws ParseException {
    try {
      identificador();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tPARENTESIS_IZQ:
      case tFIN_SENTENCIA:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tPARENTESIS_IZQ:
          argumentos();
          break;
        default:
          jj_la1[10] = jj_gen;
          ;
        }
        fin_sentencia();
        break;
      case tOPAS:
        asignacion();
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una asignaci\u00f3n o una acci\u00f3n");
    }
  }

/*
 * mientras_que	::=	<tMQ> expresion lista_sentencias <tFMQ>
 */
  static final public void mientras_que() throws ParseException {
    jj_consume_token(tMQ);
    expresion();
    lista_sentencias();
    try {
      jj_consume_token(tFMQ);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba el delimitador de fin de estructura MQ: 'FMQ'");
    }
  }

/*
 * seleccion	::=	<tSI> expresion <tENT> lista_sentencias ( <tSI_NO> lista_sentencias )* <tFSI>
 */
  static final public void seleccion() throws ParseException {
    jj_consume_token(tSI);
    expresion();
    try {
      jj_consume_token(tENT);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba el token 'ENT'");
    }
    lista_sentencias();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tSI_NO:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_6;
      }
      jj_consume_token(tSI_NO);
      lista_sentencias();
    }
    try {
      jj_consume_token(tFSI);
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba el delimitador de fin de estructura SI: 'FSI'");
    }
  }

/*
 * argumentos	::=	parentesis_izq ( lista_expresiones )? parentesis_der
 */
  static final public void argumentos() throws ParseException {
    try {
      parentesis_izq();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tENTACAR:
      case tCARAENT:
      case tPARENTESIS_IZQ:
      case tMAS:
      case tMENOS:
      case tNOT:
      case tTRUE:
      case tFALSE:
      case tIDENTIFICADOR:
      case tCONSTENTERA:
      case tCONSTCHAR:
      case tCONSTCAD:
        lista_expresiones();
        break;
      default:
        jj_la1[13] = jj_gen;
        ;
      }
      parentesis_der();
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una lista de argumentos");
    }
  }

/*
 * lista_expresiones	::=	expresion ( sep_variable expresion )*
 */
  static final public void lista_expresiones() throws ParseException {
    try {
      expresion();
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tSEP_VARIABLE:
          ;
          break;
        default:
          jj_la1[14] = jj_gen;
          break label_7;
        }
        sep_variable();
        expresion();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una lista de expresiones");
    }
  }

/*
 * expresion	::=	expresion_simple ( operador_relacional expresion_simple )?
 */
  static final public void expresion() throws ParseException {
    try {
      expresion_simple();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tMAYOR:
      case tMENOR:
      case tIGUAL:
      case tMAI:
      case tMEI:
      case tNI:
        operador_relacional();
        expresion_simple();
        break;
      default:
        jj_la1[15] = jj_gen;
        ;
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una expresi\u00f3n");
    }
  }

/*
 * operador_relacional	::=	( <tIGUAL> | <tMENOR> | <tMAYOR> | <tMAI> | <tMEI> | <tNI> )
 */
  static final public void operador_relacional() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tIGUAL:
        jj_consume_token(tIGUAL);
        break;
      case tMENOR:
        jj_consume_token(tMENOR);
        break;
      case tMAYOR:
        jj_consume_token(tMAYOR);
        break;
      case tMAI:
        jj_consume_token(tMAI);
        break;
      case tMEI:
        jj_consume_token(tMEI);
        break;
      case tNI:
        jj_consume_token(tNI);
        break;
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
   ErrorSintactico.deteccion(e, "Se esperaba un operador relacional: '=', ' >', '<', '<=', '>=', o '!='");
    }
  }

/*
 * expresion_simple	::=	( <tMAS> | <tMENOS> )? termino ( operador_aditivo termino )*
 */
  static final public void expresion_simple() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tMAS:
      case tMENOS:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tMAS:
          jj_consume_token(tMAS);
          break;
        case tMENOS:
          jj_consume_token(tMENOS);
          break;
        default:
          jj_la1[17] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[18] = jj_gen;
        ;
      }
      termino();
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tMAS:
        case tMENOS:
        case tOR:
          ;
          break;
        default:
          jj_la1[19] = jj_gen;
          break label_8;
        }
        operador_aditivo();
        termino();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba una expresi\u00f3n simple");
    }
  }

/*
 * operador_aditivo	::=	( <tMAS> | <tMENOS> | <tOR> )
 */
  static final public void operador_aditivo() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tMAS:
        jj_consume_token(tMAS);
        break;
      case tMENOS:
        jj_consume_token(tMENOS);
        break;
      case tOR:
        jj_consume_token(tOR);
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un operador aditivo: '+', '-', o 'OR'");
    }
  }

/*
 * termino	::=	factor ( operador_multiplicativo factor )*
 */
  static final public void termino() throws ParseException {
    try {
      factor();
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case tPRODUCTO:
        case tDIVISION:
        case tMOD:
        case tDIV:
        case tAND:
          ;
          break;
        default:
          jj_la1[21] = jj_gen;
          break label_9;
        }
        operador_multiplicativo();
        factor();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un t\u00e9rmino");
    }
  }

/*
 * operador_multiplicativo	::=	( <tPRODUCTO> | <tDIVISION> | <tMOD> | <tAND> )
 */
  static final public void operador_multiplicativo() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tPRODUCTO:
        jj_consume_token(tPRODUCTO);
        break;
      case tDIVISION:
        jj_consume_token(tDIVISION);
        break;
      case tMOD:
        jj_consume_token(tMOD);
        break;
      case tDIV:
        jj_consume_token(tDIV);
        break;
      case tAND:
        jj_consume_token(tAND);
        break;
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un operador multiplicativo: '*', '/', 'MOD', 'AND'");
    }
  }

/*
 * factor	::=	( <tNOT> factor
 *					| parentesis_izq expresion parentesis_der
 *					| <tENTACAR> parentesis_izq expresion parentesis_der
 *					| <tCARAENT> parentesis_izq expresion parentesis_der
 *					| identificador | <tCONSTENTERA> | <tCONSTCHAR>
 *					| <tCONSTCAD> | <tTRUE> | <tFALSE> )
 */
  static final public void factor() throws ParseException {
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tNOT:
        jj_consume_token(tNOT);
        factor();
        break;
      case tPARENTESIS_IZQ:
        parentesis_izq();
        expresion();
        parentesis_der();
        break;
      case tENTACAR:
        jj_consume_token(tENTACAR);
        parentesis_izq();
        expresion();
        parentesis_der();
        break;
      case tCARAENT:
        jj_consume_token(tCARAENT);
        parentesis_izq();
        expresion();
        parentesis_der();
        break;
      case tIDENTIFICADOR:
        identificador();
        break;
      case tCONSTENTERA:
        jj_consume_token(tCONSTENTERA);
        break;
      case tCONSTCHAR:
        jj_consume_token(tCONSTCHAR);
        break;
      case tCONSTCAD:
        jj_consume_token(tCONSTCAD);
        break;
      case tTRUE:
        jj_consume_token(tTRUE);
        break;
      case tFALSE:
        jj_consume_token(tFALSE);
        break;
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (ParseException e) {
    ErrorSintactico.deteccion(e, "Se esperaba un factor");
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public minilengcompilerTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x70000000,0x70000000,0x0,0x2000000,0xc000000,0x0,0x0,0xc000000,0x688000,0x688000,0x0,0x0,0x20000,0x1800000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1800000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x20,0x0,0x0,0x2,0x10,0x0,0x2000000,0x2000000,0x2,0x1a,0x0,0x1f8040c2,0x20,0x1f8000,0x1f8000,0xc0,0xc0,0x20c0,0x20c0,0x1f00,0x1f00,0x1f804002,};
   }

  /** Constructor with InputStream. */
  public minilengcompiler(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public minilengcompiler(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new minilengcompilerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public minilengcompiler(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new minilengcompilerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public minilengcompiler(minilengcompilerTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(minilengcompilerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[61];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 24; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 61; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

         }
