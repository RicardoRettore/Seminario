#--------------------------------------------------------------------------
# Manipulación Tabla Mantenimiento

insert into mantenimiento values (1, "Distribuidor Las Tunas", "Cambio de 8 postes de madera y 5 crucetas en derivación estancia Los Teros","Pendiente", null);
insert into mantenimiento values (2, "Distribuidor Parque Industrial", "Cambiar aisladores en Subestación Transformadora Sector 4","Pendiente", null);
insert into mantenimiento values (3, "Distribuidor Costanera", "Limpieza de línea y poda. Cambio aislador Baja Tensión", "Pendiente", null);

#--------------------------------------------------------------------------
# Manipulación Tabla Mantenimiento

insert into materiales values ("MS08", "Poste Madera 8 Metros", "Estructura", "Unidad", 50);
insert into materiales values ("MS11", "Poste Madera 11 Metros", "Estructura", "Unidad", 50);
insert into materiales values ("HS10", "Columna Hormigón 10 Metros", "Estructura", "Unidad", 50);
insert into materiales values ("HS13", "Columna Hormigón 13 Metros", "Estructura", "Unidad", 50);
insert into materiales values ("AI13", "Aislador 13,2 kV", "Aislador", "Unidad", 50);
insert into materiales values ("AI33", "Aislador 33 kV", "Aislador", "Unidad", 50);
insert into materiales values ("P095", "Cable preensamblado 95/50 Al/Al", "Cable", "Metros", 50);
insert into materiales values ("P120", "Cable preensamblado 120/50 Al/Al", "Cable", "Metros", 50);
#--------------------------------------------------------------------------

#--------------------------------------------------------------------------
# Manipulación Tabla Materiales_Mantenimiento

insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 1, "MS08");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 1, "MS08");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 1, "CRM");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 1, "CRM");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 2, "A13");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 2, "A13");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 2, "A13");
insert into materiales_mantenimiento (ID_Mto, ID_Mat)  values ( 3, "ABT");

#--------------------------------------------------------------------------


#--------------------------------------------------------------------------
# Manipulación Tabla Operarios

insert into operarios values (28676538, "Rettore Ricardo", "Encargado");
insert into operarios values (26589145, "López José", "Encargado");
insert into operarios values (36569458, "Landra Pablo","Oficial");
insert into operarios values (40256895, "Ríos Héctor", "Oficial");
insert into operarios values (42571023, "Hunz Leandro", "Ayudante");
insert into operarios values (46125784, "Vega Ignacio", "Ayudante");

#--------------------------------------------------------------------------

#--------------------------------------------------------------------------
# Manipulación Tabla Cuadrillas

insert into cuadrillas values ( "CH01", "Cuadrilla con Hidro H100");
insert into cuadrillas values ( "CP01", "Cuadrilla poda con Hidro H10");

#--------------------------------------------------------------------------


#--------------------------------------------------------------------------
# Manipulación Tabla Operarios_Cuadrillas

insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CH01", 28676538);
insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CH01", 36569458);
insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CH01", 42571023);
insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CP01", 26589145);
insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CP01", 40256895);
insert into operarios_cuadrillas (ID_Cuad, ID_Op)  values ( "CP01", 46125784);

#--------------------------------------------------------------------------


#--------------------------------------------------------------------------
# Manipulación Tabla Mantenimiento_Cuadrillas

insert into mantenimiento_cuadrillas (ID_Mant, ID_Cuad)  values (1, "CH01");
insert into mantenimiento_cuadrillas (ID_Mant, ID_Cuad)  values (2, "CH01");

#--------------------------------------------------------------------------

select * from mantenimiento_cuadrillas;
select * from materiales_cuadrillas;
select * from operarios_cuasrillas;
select * from operarios;
select * from cuadrillas;
select * from materiales;
select * from mantenimiento;

