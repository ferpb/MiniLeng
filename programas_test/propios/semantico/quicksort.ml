programa ordenar_quicksort;
entero n, v[10];

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





accion intercambiar(ref entero v[10]; val entero iz, de);
entero aux;
principio
    aux := v[iz];
    v[iz] := v[de];
    v[de] := aux;
fin

accion particion(ref entero v[10], me; val entero iz, de);
entero p, k;
principio
    p := v[iz];
    k := iz;
    me := de + 1;

    k := k + 1;
    mq not ((v[k] > p) or (k >= de))
        k := k + 1;
    fmq

    me := me - 1;
    mq not (v[me] <= p)
        me := me - 1;
    fmq

    mq k < me
        intercambiar(v, k, me);
        k := k + 1;
        mq v[k] <= p
            k := k + 1;
        fmq

        me := me - 1;
        mq v[me] > p
            me := me - 1;
        fmq
    fmq
    intercambiar(v, iz, me);
fin

accion quicksort(ref entero v[10]; val entero iz, de);
entero me;
principio
    si iz < de ent
        particion(v, me, iz, de);
        quicksort(v, iz, me - 1);
        quicksort(v, me + 1, de);
    fsi
fin





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


principio
    n := 10;
    leerVector(n, v);
    imprimirVector(n, v);
    quicksort(v, 0, n - 1);
    imprimirVector(n, v);
fin
