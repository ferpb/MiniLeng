programa vectores;
entero v[10];
entero j[10];

accion miAccion(ref entero a);
principio
    escribir(a);
    a := 2;
fin

principio
    j[8] := 1;
    v := j;
    miAccion(v[8]);
    escribir(v[8]);
fin
