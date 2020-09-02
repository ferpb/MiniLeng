programa generacion_codigo;
entero v[10];

accion miAccion(ref entero v[10]);
principio
    escribir(v[1]);
fin

principio
    v[1] := 100;
    miAccion(v);
fin
