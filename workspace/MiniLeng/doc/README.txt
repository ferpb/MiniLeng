/*********************************************************************************
 * Documento con las características del compilador de MiniLeng
 *
 * Fichero:    README.txt
 * Autor:      Fernando Peña (NIA: 756012)
 * Fecha:      28/03/2020
 * Versión:    v1.0
 * Asignatura: Procesadores de Lenguajes, curso 2019-2020
 **********************************************************************************/

Uso del compilador
------------------

Compilador de MiniLeng -- v2.2 (abril de 2020)
Autor: Fernando Peña Bes (NIA: 756012)

Uso: minilengcompiler [opciones] [fichero]

Opciones:
  -v, --verbose  Mostrar un resumen de los símbolos utilizados en el programa
  -p, --panic	 Compila con panic mode
  -t, --tokens   Muestra los tokens que se van reconociendo
  -h, --help	 Imprimir ayuda (esta pantalla) y salir
  --version      Imprimir información de la versión y salir


Ejemplos de uso:
    minilengcompiler
    minilengcompiler -v
    minilengcompiler -v fichero.ml
    minlengcompiler -p -v fichero.ml

El fichero con el programa a compilar tiene que tener extensión .ml


Entorno de desarrollo
---------------------

Compilador programado con JavaCC en Eclipse 2019-12
JavaCC Eclipse Plug-in 1.5.33


Gramática del lenguaje
----------------------
Los tokens y la gramática del lenguaje se pueden ver en el fichero 'minilengcompiler.html'


==============================
Características del compilador
==============================

Opciones
--------
  - La opción verbose muestra al final de la compilación una tabla con el número de ocurrencias que
    se han encontrado de cada token en el programa.
      Ejemplo de tabla de ocurrencias:
       +-------------------------------------+
       | Número de ocurrencias de los tokens |
       +-------------------------------------+
       | Token                         num.  |
       +-------------------------------------+
       | Palabras reservadas                 |
       |     PROGRAMA                     1  |
       |     PRINCIPIO                    3  |
       |     FIN                          3  |
       |     SI                           3  |
       |     ENT                          3  |
       |     SI_NO                        2  |
       |     FSI                          3  |
       | Caracteres de agrupación            |
       |     PARENTESIS_IZQ              27  |
       |     PARENTESIS_DER              27  |
       | Operadores aritméticos              |
       |     SUMA                         2  |
       |     RESTA                        1  |
       | Operadores lógicos                  |
       |     AND                          2  |
       |     OR                           1  |
       | Tipos de dato                       |
       |     BOOLEANO                     1  |
       |     CARACTER                     4  |
       +-------------------------------------+

  - La opción panic, activa el modo pánico durante la compilación. Se entra a este modo cada vez que
    se produce un error sintáctico porque falta un punto y coma. El analizador descarta todos los
    token siguientes al error hasta que encuentra un punto y coma o se acaba el fichero, entonces
    sale del modo y sigue analizando la entrada.
    Cuando se entra al modo pánico se informa al usuario con las siguientes líneas:
      MiniLeng: ERROR SINTÁCTICO (línea ..., columna ...): Token incorrecto: '...'. Se esperaba '...'
      PANIC MODE: Iniciado panic mode
    Se va mostrando en una línea diferente cada token rechazado:
        > PANIC MODE: Token descartado: '...'
    Y cuando se sale del modo se muestra:
        > PANIC MODE (línea ..., columna ...): Se ha encontrado ';'
      PANIC MODE: Terminado panic mode

  - La opción tokens que muestra por pantalla los nombre de los tokens que
    se van reconociendo conforme se analiza el programa.
    Ejemplo:
      tPROGRAMA
      tIDENTIFICADOR (Valor: mi_programa)
      tFIN_SENTENCIA
      tENTERO
      tIDENTIFICADOR (Valor: n)
      tFIN_SENTENCIA
      ...
      tFIN


Léxico
------
  - El analizador no es case-sensitive, no distingue entre mayúsculas y minúsculas.

  - Se permite la definición de comentarios de una línea con el carácter '%' y multilínea,
    usando '%%'


Semántico
---------
  - Se permiten acciones anidadas con cualquier nivel de profundidad. Las declaraciones de acciones
    anidadas deben entre la zona de declaración de las variables y la palabra 'principio' de la
    función padre:

      accion accion1;
        % Declaración de variables de accion1
        accion accion2;
          % Declaración de variables de accion2
          accion accion3;
            % Declaración de variables de accion3
          principio
            % Sentencias de accion3
          fin
        principio
          % Sentencias de accion2
        fin
      principio
        % Sentencias de accion1
      fin

  - Se permiten bloques 'seleccion' y 'mientras que' anidados con cualquier nivel de profundidad:

      SI <condicion> ENT
        % Sentencias
        SI <condicion> ENT
          % Sentencias
        SI_NO
          % Sentencias
        FSI
        % Sentencias
      SI_NO
        % Sentencias
      FSI

      MQ <condicion>
        % Sentencias
        MQ <condicion>
          % Sentencias
        FMQ
        % Sentencias
      FMQ

  - Cualquier bloque ('principio/fin', 'seleccion' o 'mientras que') debe contener al menos una
    sentencia.


Errores
-------
  - Errores en el uso del programa:
      Si se utiliza una opción inválida o no se especifican correctamente, se muestra el siguiente
      error:
        MiniLeng: Opción inválida <error>

      Si el fichero a compilar no tiene extensión .ml se muestra el error:
        MiniLeng: El fichero a compilar tiene que tener extensión .ml
                  Fichero introducido: '...'

      Si no se encuentra el fichero a compilar se indica al usuario así:
        MiniLeng: No se ha encontrado el fichero '...'

  - Errores léxicos:
      Tienen el siguiente formato:
        MiniLeng: ERROR LÉXICO (línea ..., columna ...): símbolo no reconocido: '...' (\u...)
      Si el caracter no reconocido no es ASCII, se muestra escapado con su código Unicode.

  - Errores sintácticos:
      Tienen el siguiente formato:
        MiniLeng: ERROR SINTÁCTICO (línea ..., columna ...): Token incorrecto: '...'. Se esperaba '...'

  - Al final de la compilación se muestra un recuento de los errores encontrados:
      Errores léxicos: ...
      Errores sintácticos: ...
      Veces activado panic mode: ...

    Y se muestra:
      No se ha podido compilar el programa.
    si ha habido errores durante la compilación o:
      Compilado sin errores!
    si la compilación ha sido exitosa

  - Si durante la compilación se ha activado el modo pánico se muestra el siguiente mensaje:
      Se ha activado el panic mode durante la compilación. Corrige los errores y vuelve a compilar.
    para indicar al usuario que hace falta volver a compilar.

No se van a usar parámetros ocultos. Como estamos trabajando con objetos Java, las acciones pueden
tener una lista de referencias a los parámetros. Los parámetros se borrarán de la tabla en el
momento que la función deje de ser visible y se borre.
