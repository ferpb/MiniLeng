%%
Calcular en un vector el factorial de los números
del 0 al 7 (máximo factorial que cabe en 16 bits)
%%

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
        i := i + 1;
    fmq

    % Recorrer otra vez para imprimir
    i := 0;
    mq i < 8
        escribir("v[", i, "] = ", v[i], "\n");
        i := i + 1;
    fmq
fin
