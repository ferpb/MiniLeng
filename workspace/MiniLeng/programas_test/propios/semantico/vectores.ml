programa vectores;
entero i, max, d, v[10], resultado;

accion dato;
principio
    d := 0;
    mq d <= 0
        escribir("Escribe un número: ");
        leer(d);
        si d >= 0 ent
            escribir("El número es positivo.\n");
        si_no
            escribir("El número es negativo.\n");
        fsi
    fmq
fin

accion sumar(ref entero v[10]; ref entero res);
entero i;
principio
    v[0] := 100;
    escribir("Resultado: ", res, "\n");
    escribir("Vector 0: ", v[0], "\n");
    res := 0;
    i := 9;
    mq i >= 0
        escribir("Entrado bucle ", res, " ", v[i], "\n");
        res := res + v[i];
        i := i - 1;
    fmq
fin

principio
    dato;
    i := 0;
    mq i < 10
        v[i] := d;
        escribir("Vector: ", v[i], "\n");
        d := d + 1;
        i := i + 1;
    fmq
    resultado := 1;
    sumar(v, resultado);
    escribir("La suma de los números ", d - 10, " a ", d - 1, " es: ", resultado);
    escribir(entacar(13), entacar(10));
fin
