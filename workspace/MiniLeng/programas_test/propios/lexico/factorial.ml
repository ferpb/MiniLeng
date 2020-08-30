%-------------------------------------------------------------------
programa factorial;
%-------------------------------------------------------------------
entero n, solucion;

%-------------------------------------------------------------------
accion fact (val entero numero; ref entero solucion);
%-------------------------------------------------------------------
entero solRecur;
principio
	si numero <= 1 ent
		solucion := 1;
	si_no
	  	fact(numero - 1, solRecur);

		solucion := solucion * solRecur;
	fsi
fin



%-------------------------------------------------------------------
principio
%-------------------------------------------------------------------
	n := 10;

	fact(n, solucion);

	escribir(n, "! es: ", solucion);

fin
