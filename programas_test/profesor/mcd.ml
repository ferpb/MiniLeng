Programa p;
entero i, j, m, a1;
booleano fallo, semos, hambre;

accion a1 (REF ENTERO a);
Principio
	a:=b;
Fin
accion a2 (val entero b);
	booleano m;
	accion a3(ref entero n,n,pez);
	Principio
		n:=n+1;
	Fin
	accion a1;
	Principio
		a1:=a1+1;
	    caca(j);
	    caca(j);
		fallo:="a"="a";
		fallo:="a">="c";
		fallo:="a">2;
		fallo:=1>3;
	Fin
Principio
	a3(b);
Fin
%-----------------------------------------------------------
accion dato (ref entero d);
%-----------------------------------------------------------
entero num;
Principio
		  caca(j);
		  a3(d);
		  a3(d);
		  d:=num;
        d:=0;
		  d:="a";
		  d:=fallo+3;
		  fallo:=entacar(fallo);
		  d:=hombre;
		  leer(dato);
        Mq d<=0
                escribir("Escribe un numero: ");
                leer(d);
                Si d<=0 ent
                        escribir("El numero debe ser postivo.");
                        escribir (entacar (13), entacar (10));
                FSi
        FMq

Fin

%-----------------------------------------------------------
accion mcd(Val entero  a,b ; ref  entero  m );
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
		 a1:=1;
		  hombre:=1;
		  dato(hombre);
        dato(i);
        dato(j);
        mcd (i,j,m);
		  mcd(i,j,m*i);
		  ccd(i,j,m);
		  mcd;
        escribir ("El MCD es: ", m);
        escribir (entacar (13), entacar (10));
Fin
