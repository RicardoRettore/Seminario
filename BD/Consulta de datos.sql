# consulta de datos de los materiales asociados al mantenimiento 1
select mat.IDMat as "Código", mat.Descripcion as "Descripción", count(*) as cantidad
from materiales_mantenimiento as matmto, mantenimiento as mto, materiales as mat
where mto.ID = 1
and mto.id = matmto.ID_Mto
and matmto.ID_Mat = mat.IDMat
group by mat.IDMat, mat.Descripcion;

