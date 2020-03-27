/* Generated By:JavaCC: Do not edit this line. minilengcompiler.java */
package analizador;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import lib.lexico.TablaOcurrencias;

public class minilengcompiler implements minilengcompilerConstants {

        static Boolean verbose_mode = false;

        private static void help() {
                System.out.println("Uso: minilengcompiler [opciones] [fichero ...]\u005cn");
        System.out.println("Opciones:");
        System.out.println("    -v, --verbose\u0009Mostrar resumen de los s\u00edmbolos utilizados en el programa");
        System.out.println("    -h, --help\u0009  \u0009Imprimir ayuda (esta pantalla) y salir");
        System.out.println("    --version\u0009\u0009Imprimir informaci\u00f3n de la versi\u00f3n y salir");

        System.exit(0);
    }

    private static void version() {
                System.out.println("  Compilador de MiniLeng Versi\u00f3n 1.0\u0009\u0009Compilado el 27-03-2020");
                System.out.println("\u0009\u0009    ---");
                System.out.println("    Pr\u00e1cticas de la asignatura: Procesadores de Lenguajes");
                System.out.println("        Curso 2019-2020");
                System.out.println("        Universidad de Zaragoza");
                System.out.println("\u0009\u0009    ---");
                System.out.println("    Programado con JavaCC en Eclipse 2019-12");
                System.out.println("    JavaCC Eclipse Plug-in 1.5.33");

        System.exit(0);
    }

        public static void main(String args []) throws ParseException {
                System.out.println("Compilador de MiniLeng -- v1.0 (marzo de 2020)");
        System.out.println("Autor: Fernando Pe\u00f1a Bes (NIA: 756012)\u005cn");

        minilengcompiler parser;
        InputStream stream = System.in;


        String ficheros_entrada[] = null;


        if (args.length == 0) {
                // Compilador llamado sin argumentos
                // System.out.println("MiniLeng: Leyendo de la entrada estándar...");
                // parser = new minilengcompiler(System.in);
                // No se han introducido argumentos
        }
        else if (args[0].equals("-h") || args[0].equals("--help")) {
                // Mostrar ayuda y salir
                help();
                }
                else if (args[0].equals("--version")) {
                // Mostrar ayuda y salir
                version();
                }
                else {
                        // Leer los argumentos.
                        // Va leyendo hasta que encuentra uno que no empieza por '-', todos los
                        // argumentos depués de ese se consideran ficheros de entrada
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

                                        default:
                                                if (args[i].charAt(0) == '-') {
                                                        System.out.println("MiniLeng: Opci\u00f3n inv\u00e1lida <" + args[0] + ">\u005cn");
                                                        help();
                                                }
                                                else {
                                                        ficheros_entrada = Arrays.copyOfRange(args, i, args.length);
                                                }
                                                break;
                            }
                        }
                }

                /*
	   	for (fichero : ficheros_entrada) {
  			System.out.println("MiniLeng: Leyendo el fichero " + fichero + "...");
  			try {
    			// parser = new minilengcompiler(new FileInputStream(args[0]));
    			stream = new FileInputStream(args[0]);
  			}
  			catch (FileNotFoundException e) {
    			System.out.println("MiniLeng: No he encontrado el fichero " + args[0] + ".");
  			}
		}

		if (ficheros_entrada == null) {
		  	// No se ha introducido ningún fichero
			System.out.println("MiniLeng: No se ha introducido ningún fichero\n");
		  	help();
		}
		*/

                if (ficheros_entrada != null) {
                        // Lectura del fichero del usuario.
                        String fichero = ficheros_entrada[0];
                        // Ejecutar el compilador con los fichero introducidos
                        System.out.println("MiniLeng: Leyendo el fichero " + fichero + " ...");
                try {
                        // parser = new minilengcompiler(new FileInputStream(args[0]));
                        stream = new FileInputStream(fichero);
                }
                catch (FileNotFoundException e) {
                        System.out.println("MiniLeng: No he encontrado el fichero " + fichero + " .");
                        System.exit(0);
                }
                }
                else {
                        System.out.println("MiniLeng: Leyendo de la entrada est\u00e1ndar ...");
                }

                try {
                        parser = new minilengcompiler(stream);
                        switch (minilengcompiler.programa()) {
                                case 0 :
                                        System.out.println("OK.");
                                        break;
                                case 1 :
                                        System.out.println("GOODBYE.");
                                        break;
                                default :
                                        break;
                        }
                }
                catch (Exception e) {
                        // error sintáctico                        System.out.println("NOK.");
                        System.out.println(e.getMessage());
                }
                catch (Error e) {
                        // error léxico                        // System.out.println("Error léxico (<linea, columna>): símbolo no reconocido <símbolo>");                        // si no hay errores léxicos no muestra nada en la salida.
                        System.out.println(e.getMessage());
                }
        }

  static final public int programa() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tPROGRAMA:
        jj_consume_token(tPROGRAMA);
        break;
      case tVAR:
        jj_consume_token(tVAR);
        break;
      case tPRINCIPIO:
        jj_consume_token(tPRINCIPIO);
        break;
      case tFIN:
        jj_consume_token(tFIN);
        break;
      case tSI:
        jj_consume_token(tSI);
        break;
      case tENT:
        jj_consume_token(tENT);
        break;
      case tSI_NO:
        jj_consume_token(tSI_NO);
        break;
      case tFSI:
        jj_consume_token(tFSI);
        break;
      case tMQ:
        jj_consume_token(tMQ);
        break;
      case tFMQ:
        jj_consume_token(tFMQ);
        break;
      case tESCRIBIR:
        jj_consume_token(tESCRIBIR);
        break;
      case tLEER:
        jj_consume_token(tLEER);
        break;
      case tENTACAR:
        jj_consume_token(tENTACAR);
        break;
      case tCARAENT:
        jj_consume_token(tCARAENT);
        break;
      case tACCION:
        jj_consume_token(tACCION);
        break;
      case tVAL:
        jj_consume_token(tVAL);
        break;
      case tREF:
        jj_consume_token(tREF);
        break;
      case tLLAVE_IZQ:
        jj_consume_token(tLLAVE_IZQ);
        break;
      case tLLAVE_DER:
        jj_consume_token(tLLAVE_DER);
        break;
      case tPARENTESIS_IZQ:
        jj_consume_token(tPARENTESIS_IZQ);
        break;
      case tPARENTESIS_DER:
        jj_consume_token(tPARENTESIS_DER);
        break;
      case tOPAS:
        jj_consume_token(tOPAS);
        break;
      case tFIN_SENTENCIA:
        jj_consume_token(tFIN_SENTENCIA);
        break;
      case tSEP_VARIABLE:
        jj_consume_token(tSEP_VARIABLE);
        break;
      case tSUMA:
        jj_consume_token(tSUMA);
        break;
      case tRESTA:
        jj_consume_token(tRESTA);
        break;
      case tPRODUCTO:
        jj_consume_token(tPRODUCTO);
        break;
      case tDIVISION:
        jj_consume_token(tDIVISION);
        break;
      case tMOD:
        jj_consume_token(tMOD);
        break;
      case tAND:
        jj_consume_token(tAND);
        break;
      case tOR:
        jj_consume_token(tOR);
        break;
      case tNOT:
        jj_consume_token(tNOT);
        break;
      case tMAYOR:
        jj_consume_token(tMAYOR);
        break;
      case tMENOR:
        jj_consume_token(tMENOR);
        break;
      case tIGUAL:
        jj_consume_token(tIGUAL);
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
      case tENTERO:
        jj_consume_token(tENTERO);
        break;
      case tBOOLEANO:
        jj_consume_token(tBOOLEANO);
        break;
      case tCARACTER:
        jj_consume_token(tCARACTER);
        break;
      case tTRUE:
        jj_consume_token(tTRUE);
        break;
      case tFALSE:
        jj_consume_token(tFALSE);
        break;
      case tIDENTIFICADOR:
        jj_consume_token(tIDENTIFICADOR);
        break;
      case tVALOR_ENTERO:
        jj_consume_token(tVALOR_ENTERO);
        break;
      case tCADENA_CARACTERES:
        jj_consume_token(tCADENA_CARACTERES);
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case tPROGRAMA:
      case tVAR:
      case tPRINCIPIO:
      case tFIN:
      case tSI:
      case tENT:
      case tSI_NO:
      case tFSI:
      case tMQ:
      case tFMQ:
      case tESCRIBIR:
      case tLEER:
      case tENTACAR:
      case tCARAENT:
      case tACCION:
      case tVAL:
      case tREF:
      case tENTERO:
      case tBOOLEANO:
      case tCARACTER:
      case tLLAVE_IZQ:
      case tLLAVE_DER:
      case tPARENTESIS_IZQ:
      case tPARENTESIS_DER:
      case tOPAS:
      case tFIN_SENTENCIA:
      case tSEP_VARIABLE:
      case tSUMA:
      case tRESTA:
      case tPRODUCTO:
      case tDIVISION:
      case tMOD:
      case tAND:
      case tOR:
      case tNOT:
      case tMAYOR:
      case tMENOR:
      case tIGUAL:
      case tMAI:
      case tMEI:
      case tNI:
      case tTRUE:
      case tFALSE:
      case tIDENTIFICADOR:
      case tVALOR_ENTERO:
      case tCADENA_CARACTERES:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
      if (verbose_mode) {
          token_source.tabla.imprimirTabla();
      }
      {if (true) return 0;}
    throw new Error("Missing return statement in function");
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
  static final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xfffff800,0xfffff800,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x7cfffff,0x7cfffff,};
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(minilengcompilerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
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
    boolean[] la1tokens = new boolean[59];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
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
    for (int i = 0; i < 59; i++) {
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
