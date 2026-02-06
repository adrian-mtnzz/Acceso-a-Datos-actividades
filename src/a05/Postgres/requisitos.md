## Actividad: Personal de un centro educativo con PostgreSQL (BDOR)
Vamos a modelar un caso típico de herencia en un centro educativo usando características objeto-relaciones de PostgreSQL.


### Datos comunes de cualquier Empleado-Centro
De cada empleado se almacenerá:

- nombreCompleto 
- ubicación (tipo de vía: calle/avenida /plaza, nombre d vía y número) 
- teléfonos (lista de teléfonos, 0...N) 
- supervisorDirecto, que será otro EmpleadoCentro (auto-referencia)


### Tipos de empleado
Los empleados pueden ser: 

1) Docente - especialidad: programación / bases_datos / sistemas - horasSemanales: entero (por ejemplo 1-40)

2) Administrativo - plusProductividad: número (doble /numeric) - area: secretaría / administración / dirección


### Requisitos técnicos (usar todos los conceptos aprendidos del tema)
Debes utilizar obligatoriamente:

- ENUM 
- Tipo estructurado (composite type) 
- Arrays (colecciones) 
- Herencia con INHERITS 
- Auto-referencia (supervisor) 
- Consultas (3 distintas):
  - Una consulta que muestre solo los docentes y su supervisor (con JOIN). 
  - Una consulta que muestre los Administrativos filtrando por área y ordenando por plus. 
  - Una consulta que muestre cada teléfono en una fila.


### --- ENTREGABLES --- 
1. Creación de tipos y tablas 
2. Inserción de datos de prueba (mínimo: 2 docentes, 2 administrativos, 1 supervisor) 
3. Las tres consultas pedidas.