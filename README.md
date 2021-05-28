# Compilador de MiniLeng - Procesadores de Lenguajes
Implementación de un compilador para el lenguaje MiniLeng con JavaCC.

Autor: Fernando Peña Bes (NIA: 756012)

Fecha: 30 de agosto de 2020

*Universidad de Zaragoza, curso 2019-20*

## Descripción
Este repositorio contiene un compilador que traduce código escrito en el lenguaje MiniLeng a instrucciones ejecutables en la máquina P.

En el [informe](doc/MiniLeng-Memoria/main.pdf) se comentan los dealles del desarrollo de compilador.

## Introducción a MiniLeng
MiniLeng es un lenguaje procedimental, estructurado y fuertemente tipado, cuya sintaxis está inspirada en Pascal y C.

El lenguaje es case-insensitive y soporta comentarios tanto de una sola línea como multilínea. Se pueden  declarar variables tanto globales como locales de tres tipos de dato: `entero`, `caracter` y `booleano`, y permite la declaración de acciones (procedimientos) anidadas con paso de parámetros tanto por valor como por referencia.
Se pueden utilizar las estructuras de control `mientras que` (while) y `seleccion` (if), y se soporta la asignación de valores a variables y la evaluación de expresiones aritméticas y lógicas.

El lenguaje tiene dos procedimientos predefinidos: `escribir`, que permite escribir por pantalla variables simples y cadenas de caracteres constantes y `leer`, que permite leer valores introducidos por el usuario y asignarlos a variables. También tiene dos funciones que permiten transformar un entero a carácter y viceversa: `entacar` y `caraent`.

Por último, se ha añadido la posibilidad de trabajar con vectores unidimensionales. Se permite la declaración de vectores, la asignación de valores a las componentes, la asignación directa entre vectores del mismo tamaño, el uso de componentes en expresiones y el paso de componentes y vectores completos como argumentos al invocar una acción.

A continuación, se muestra un ejemplo de programa de MiniLeng válido semánticamente:

```c
programa factorialVect;
    entero i, r, v[8];

    accion factorial(val entero n; ref entero r);
    principio
        si n < 2 ent
            r := 1;
        si_no
            factorial(n - 1, r);
            r := n * r;
        fsi
    fin

principio
    i := 0;
    mq i < 8
        factorial(i, v[i]);
        escribir("v[", i, "] = ", v[i], "\n");
        i := i + 1;
    fmq
fin
```

## Cómo ejecutar el compilador
Para compilar el proyecto se pueden seguir las siguientes instrucciones:

1. Utilizar JavaCC para generar el analizador:
```
javacc -OUTPUT_DIRECTORY=src/analizador src/analizador/minilengcompiler.jj
```

2. Compilar el compilador
```
javac -d bin $(find src -name "*.java")
```
Una vez hecho esto, se podrá ejecutar con el siguiente comando:

```bash
java -cp bin analizador.minilengcompiler
```

## Uso del compilador
El compilador se invoca de la siguiente manera:
```
minilengcompiler [opciones] fichero
```

El fichero debe tener extensión `.ml` y se ofrecen las siguientes opciones:

* `-v, --verbose`: Al final de la compilación se muestra un resumen de los tokens utilizados en el programa y el número de ocurrencias de cada uno.
* `-p, --panic`: Compila utilizando *panic mode*. El panic mode se activa cada vez que se detecta que falta un token `;` su finalidad es intentar que el compilador se recupere del error para evitar que el error afecte a la compilación de las siguientes partes del código. El compilador descarta la entrada hasta encontrar el siguiente `;` y continúa compilando desde ese punto.
* `-t, --tokens`: Se imprimen los tokens conforme se van encontrando durante el análisis léxico.
* `-d, --debug`: Se muestra la tabla de símbolos antes y después de cerrar cada bloque.
* `-h, --help`: Muestra el uso del programa y las opciones disponibles.
* `--version`: Imprime información sobre la versión del compilador.

A continuación, se muestran algunos ejemplos de uso del compilador:

```bash
minilengcompiler -h
minilengcompiler --version
minilengcompiler -v fichero.ml
minlengcompiler -p -v fichero.ml
minlengcompiler -pv fichero.ml
```

## Comportamiento del compilador
Cada vez que se produce un error de compilación, el compilador muestra un mensaje explicativo junto al token más relevante en el error y su número de fila y columna en el programa. Distingue entre errores léxicos, sintácticos y semánticos.

Cuando el compilador encuentra un error léxico termina la ejecución, pero cuando el error es sintáctico o semántico, intenta seguir compilando hasta el final del fichero para mostrar la máxima información posible sobre los errores que puede haber en el programa. También se incluyen avisos de compilación, como *underflow*/*overflow* y divisiones por cero.

Si no se han producido errores durante la compilación del programa (no se tienen en cuenta los avisos), se genera un fichero con el mismo nombre que el fichero de entrada y con extensión `.code`, que contendrá el código intermedio con las instrucciones para ejecutar el programa en la máquina P.

 La generación de código se realiza utilizando el esquema AST, que da flexibilidad a la generación de código y permite introducir optimizaciones interesantes.
