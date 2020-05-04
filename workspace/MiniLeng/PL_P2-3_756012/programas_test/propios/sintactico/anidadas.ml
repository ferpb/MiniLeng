%-------------------------------------------------------------------
programa anidadas;
%-------------------------------------------------------------------
entero n;

accion prueba;

entero n;
	accion prueba;
    caracter a;
	entero n;
    principio

	  n := 2;

	fin

principio
n := 1;

fin


principio

n := 1;

SI n > 0 ENT
    SI n = 1 ENT
        n := 2;
    SI_NO
        n := 3;
    FSI
SI_NO
    SI n = 2 ENT
        SI n = 3 ENT
            n := 5;
        SI_NO
            n := 10;
        FSI
    FSI
    n := 2;
FSI

MQ n > 0
    n := n + 1;
    MQ n > 1
        n := n + 1;
        MQ n > 2
            n := n + 1;
        FMQ
    FMQ
    n := n + 1;
FMQ



fin
