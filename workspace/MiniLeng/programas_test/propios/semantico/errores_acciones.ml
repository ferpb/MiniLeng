programa errores_acciones;

accion error1(val entero a, p, p);
principio
    escribir(a, p, p);
fin

accion error2(val entero a, error2);
principio
    escribir(a, error2);
    error2(1, 2);
fin

principio
    error1(1, 2, 3);
    error2(1, 2);
fin
