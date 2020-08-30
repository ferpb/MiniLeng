programa vect;
entero n;
entero v[100];
entero i;

accion miAccion(ref entero a);
entero b;

    accion miAccion2(val entero x);
    entero y;
    principio
        escribir(b, "\n");
        escribir(y, "\n");
        escribir(x, "\n");
    fin

principio
    escribir(a, "\n");
    escribir(b, "\n");
    miAccion2(b);
fin

principio
%    n := 1;
    i := 0;
    mq i < 100
        escribir(v[i], "\n");
        i := i + 1;
    fmq
    escribir(n, "\n");
    miAccion(n);
fin
