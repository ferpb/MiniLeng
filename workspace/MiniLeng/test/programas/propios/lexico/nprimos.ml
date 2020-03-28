%-------------------------------------------------------------------
programa nprimos;
%-------------------------------------------------------------------
entero n, i;
booleano loEs;

%-------------------------------------
accion esPrimo (val entero num; ref booleano loEs);

entero i;

principio
	si num <= 3 ent
		loEs := n > 1;
	si_no
		si ((n mod 2) = 0) or ((n mod 3) = 0) ent
			loEs := false;
		si_no

			i := 5;
			loEs := true;

			mq (i * i <= num) and (loEs = true)
				si (n mod i = 0) or (n mod (i + 2) = 0) ent
					loEs := false;
				fsi
				i := i + 6;
			fmq

		fsi
	fsi
fin

%-------------------------------------------------------------------
principio
%-------------------------------------------------------------------
	n := 100;
	i := 0;

	mq i < n
		loEs := false;
	 	esPrimo(i, loEs);

	 	si loEs ent
	 		escribir(i);
	 		escribir(entacar(10), entacar(13));
	 	fsi
	 fmq
fin
