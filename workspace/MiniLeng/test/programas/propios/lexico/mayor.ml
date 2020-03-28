%-------------------------------------------------------------------
programa decidemayor;
%-------------------------------------------------------------------
entero primero, segundo, solución;

accion mayor (VAL entero pri; val entero SEG; REF entero sol);
principio
 si pri > seg ent
  sol := pri;
 si_no
  sol := seg;
 fsi
fin

%-------------------------------------------------------------------
principio
%-------------------------------------------------------------------
 escribir("Introduce el primer número: ");
 leer(primero);
 escribir (entacar (13), entacar (10));
 escribir("Introduce el segundo número: ");
 leer(segundo);
 
 % Error, tIDENTIFICADOR no puede contener letras con tildes.
 mayor(primero, segundo, solución);
 escribir("El número mayor es: ", solución)

fin
