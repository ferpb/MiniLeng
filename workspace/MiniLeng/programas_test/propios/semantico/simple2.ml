programa vectores;
entero a;
entero b;

accion miAccion(val entero par);
principio
    escribir(par, "\n");
    escribir("accion\n");
fin

accion miAccion2(ref entero par);

    accion miAccion3(val entero par);
    principio
        escribir(par);
    fin

principio
    par := 1;
    escribir("accion 2\n");
fin

principio
    a := 0;
    b := 100;
    escribir("El valor inicial es: ", a, "\n");
    miAccion(1);
    miAccion2(a);
    escribir("El valor es: ", a, "\n");
    escribir("El valor de b es: ", b, "\n");
    escribir("Hi");
    escribir(entacar(13), entacar(10));
fin
