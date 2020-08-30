programa errores_invocacion;
entero miEnt, miVecEnt[100], miVecEnt2[10];
booleano miBool, miVecBool[10];

accion miAccion(val booleano b, vb[10]; ref entero e, vn[10]);
principio
    escribir("Bien");
fin

principio
    miAccion(miBool);  % error
    miAccion(miEnt, miVecBool, miEnt, miVecEnt2);  % bien
    miAccion((1 = 2) and (2 <> 2), miVecBool, 1 + 2, miVecEnt2);  % error
    miAccion(true, miVecBool, miEnt, miVecEnt);  % error
    miAccion(miBool, miVecBool, miEnt, miVecEnt2);  % bien
fin
