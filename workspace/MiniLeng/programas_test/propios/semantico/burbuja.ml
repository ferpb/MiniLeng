programa ordenar_burbuja;
entero n, v[10];

accion imprimirVector(val entero n, v[10]);
entero i;
booleano primero;
principio
    i := 0;
    primero := true;
    mq i < n
        si primero ent
            escribir("[", v[i]);
            primero := false;
        si_no
            escribir(", ", v[i]);
        fsi
        i := i + 1;
    fmq
    escribir("]\n");
fin

accion leerVector(val entero n; ref entero v[10]);
entero i;
principio
    i := 0;
    mq i < n
        escribir("Número ", i + 1, ": ");
        leer(v[i]);
        i := i + 1;
    fmq
fin

accion burbuja(val entero n; ref entero v[10]);
entero i, j, aux;
principio
    i := 0;
    mq i < n
        j := 0;
        mq j < n - i - 1
            si v[j] > v[j + 1] ent
                aux := v[j];
                v[j] := v[j + 1];
                v[j + 1] := aux;
            fsi
            j := j + 1;
        fmq
        i := i + 1;
    fmq
fin



principio
    n := 10;
    leerVector(n, v);
    imprimirVector(n, v);
    burbuja(n, v);
    imprimirVector(n, v);
fin
