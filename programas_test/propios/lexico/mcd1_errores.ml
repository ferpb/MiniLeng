%-----------------------------------------------------------
Programa máximo_comun_divisor;
%-----------------------------------------------------------

int m, d, a, b;

%-----------------------------------------------------------
accion dato;
%-----------------------------------------------------------
Principio
        d:=0;
        Mq d<=0
                éscribir("Escribe un número: ");
                leer(d);
                Si d<=0 ent
                        escribir("El numero debe ser postivo.");
                        escribir (entacar (13), entacar (10));
                FSi
        FMq
Fin

%-----------------------------------------------------------
accion mcd;
%-----------------------------------------------------------
entero r;
Principio
        r:=a mod b;
        Mq r<>0
                a:=b;
                b:=r;
                r:=a mod b;
        FMq
		m := b;
Fin

%-----------------------------------------------------------
Principio
        dato;
        a := d;
        dato;
        b := d;
        mcd;
        escribir("El MCD es: ", m);
        escribir (entacar (13), entacar (10));
Fin
