programa vectores;
entero a;

accion miAccion(val entero par);
principio
    escribir(par, "\n");
    escribir("accion\n");
fin

accion miAccion2(ref entero par);
principio
    par := 1;
    escribir("accion 2\n");
fin

principio
    a := 0;
    escribir("El valor inicial es: ", a, "\n");
    miAccion(1);
    miAccion2(a);
    escribir("El valor es: ", a, "\n");
    escribir("Hi");
    escribir(entacar(13), entacar(10));
fin
