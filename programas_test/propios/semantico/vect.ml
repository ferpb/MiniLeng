programa vect;
entero v[4];
entero w[4];

accion accion1(ref entero v[4]);
entero j[4];
principio
    escribir("Dentro de accion1\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");
    v[0] := 4;
    v[1] := 3;
    v[2] := 2;
    v[3] := 1;
fin

accion accion2(ref entero v[4]);
entero j[4];
principio
    escribir("Dentro de accion2\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");
    j[0] := 11;
    j[1] := 12;
    j[2] := 13;
    j[3] := 14;
    v := j;
fin

accion accion3(ref entero v[4], w[4]);
principio
    v := w;
fin

principio
    v[0] := 1;
    v[1] := 2;
    v[2] := 3;
    v[3] := 4;
    escribir("Vector inicial\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");

    accion1(v);
    escribir("Después de invocar a accion1\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");

    accion2(v);
    escribir("Después de invocar a accion2\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");

    w[0] := 41;
    w[1] := 42;
    w[2] := 43;
    w[3] := 44;
    accion3(v, w);
    escribir("Después de invocar a acción 3\n");
    escribir(v[0], "\n");
    escribir(v[1], "\n");
    escribir(v[2], "\n");
    escribir(v[3], "\n");
fin
