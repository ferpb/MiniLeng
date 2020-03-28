%-------------------------------------------------------------------
programa prueba_tokens;
% Prueba todos los tokens del lenguaje
%-------------------------------------------------------------------
entero   _variable_entera, _variable_entera2;
caracter variableCaracter1;
boolean  Booleano;

caracter pri, seg;
entero n, m;

%-------------------------------------------------------------------
accion accion_de_prueba(val entero a, ref entero b)
%-------------------------------------------------------------------
entero uno;
entero dos;

principio
	uno := 1
	dos := 2
	si int1 < int2 ent
		escribir("uno es menor que dos");
		escribir(entacar(10), entacar(13));
	si_no
		escribir("uno no es menor que dos");
		escribir(entacar(10), entacar(13));
	fsi

	b = entero * entero;
fin

%-------------------------------------------------------------------
accion accion_de_prueba
%-------------------------------------------------------------------

%%----------------------------
% Multicomentario
% Otra línea (si, lleva tilde)
%%----------------------------
% Línea fuera del comentario

entero cero;

principio
	cero := 0;

	mq cero = 0
		escribir("bucle infinito");
		escribir(entacar(10), entacar(13));
	fmq
fin

principio
	pri := entacar(49);
	%%%%seg := entacar(50); % No debería haber problemas

	{ }

	leer(pri);
	leer(seg);

	n = caraent(pri);
	m = caraent(seg);

	n + n;
	n - m;
	n * m;
	n / m;
	n mod m;

	n := false;
	n := true;

	n and m;
	n or m;
	not n = n;
  	n > m;
  	n < m;
  	n = m;
  	n >= m;
  	n <= m;
  	n <> m;

  	escribir("Prueba tabuladores\t y fines de línea\n Otra línea\n\tAhora 
           indentada\nY ahora con caracteres éSπeçíá¬éß");

fin

