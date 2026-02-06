## Solucion Actividad 5

### 1. Creación de tipos y tablas

#### Creacion de Tipos Enum

TIPO DE VIA para la ubicacion de los empleados 
```sql
CREATE TYPE tipo_via AS ENUM ('calle', 'avenida', 'plaza');
```

TIPO DE ESPECIALIDADES del Docente
```sql
CREATE TYPE especialidad_docente AS ENUM ('programacion', 'bases_datos', 'sistemas'); 
```

TIPO DE AREA del Administrativo
```sql
CREATE TYPE area_administrativo AS ENUM ('secretaria', 'administracion', 'direccion');
```


#### Creacion de Composite Type

TIPO ESTRUCTURADO UBICACION (Composite Type)
```sql
CREATE TYPE Ubicacion AS (
    via tipo_via,
    nombre_via VARCHAR,
    numero INTEGER
);
```


#### Creacion de Tablas

TABLA BASE: EMPLEADO-CENTRO
```sql
CREATE TABLE EmpleadoCentro (
    id_empleado SERIAL PRIMARY KEY,
    nombre_completo VARCHAR NOT NULL,
    
    -- Tipo compuesto ubicacion
    ubicacion Ubicacion,
    
    -- Coleccion de telefonos (0...N)
    telefonos VARCHAR[],
    
    -- Auto-referencia: supervisorDirecto es otro empleado
    supervisorDirecto INTEGER,
    CONSTRAINT fk_supervisor
        FOREIGN KEY (supervisorDirecto)
        REFERENCES EmpleadoCentro(id_empleado)
);
```


TABLAS HIJAS CON HERENCIA (INHERITS)

TABLA HIJA: DOCENTE
```sql
CREATE TABLE Docente (
    especialidad especialidad_docente,
    horas_semanales INTEGER CHECK (horas_semanales BETWEEN 1 AND 40)
) INHERITS (EmpleadoCentro); 
```

TABLA HIJA: ADMINISTRATIVO
```sql
CREATE TABLE Administrativo (
    plus_productividad NUMERIC(8,2),
    area area_administrativo
) INHERITS (EmpleadoCentro);
```



### 2. INSERCION DE DATOS: 3 Docentes, 3 administrativos y 2 supervisores

SUPERVISORES (EmpleadoCentro)
```sql
INSERT INTO EmpleadoCentro (nombre_completo, ubicacion, telefonos)
VALUES 
    ('Antonio Abenza', ('calle', 'Alcantara', 23), ARRAY['123456789']),
    ('Beatriz Beltran', ('avenida', 'Andalucia', 34), ARRAY['987654321','129834765']);
```

DOCENTES (Docente) con supervisor Antonio (id_empleado = 1) y Beatriz (id_empleado = 2)
```sql
INSERT INTO Docente (nombre_completo, ubicacion, telefonos, especialidad, horas_semanales, supervisorDirecto)
VALUES
    ('Carlos Cutillas', ('plaza', 'Constitucion', 56), ARRAY['123987456'], 'programacion', 35, 1),
    ('Dario Diaz', ('calle', 'Dali', 78), ARRAY['543216789'], 'bases_datos', 40, 2),
    ('Elena Escobar', ('avenida', 'Estepa', 9), ARRAY['564738291'], 'sistemas', 20, 1);
```

ADMINISTRATIVOS (Administrativo) con supervisor Antonio (id_empleado = 1) y Beatriz (id_empleado = 2)
```sql
INSERT INTO Administrativo (nombre_completo, ubicacion, telefonos, plus_productividad, area, supervisorDirecto)
VALUES
    ('Fabio Fernandez', ('plaza', 'Flores', 19), ARRAY['110022995'], 24.99, 'secretaria', 1),
    ('Gabriela Garcia', ('calle', 'Galindo', 28), ARRAY['337744566'], 463.50, 'direccion', 2),
    ('Hector Huertas', ('avenida', 'Heroes del Cenepa', 37), ARRAY['109238746'], 221.82, 'administracion', 2);
```



### 3. CONSULTAS

Una consulta que muestre solo los docentes y su supervisor (con JOIN). 
Muestro todos los datos de los docentes y el nombre del supervisor.
```sql
SELECT d.*, s.nombre_completo AS supervisor
FROM ONLY Docente d
LEFT JOIN EmpleadoCentro s 
ON d.supervisorDirecto = s.id_empleado
;
```

Una consulta que muestre los Administrativos filtrando por área y ordenando por plus.
Muestro todos los datos de los administrativos filtrados por area y en orden descendente por plus de productividad.
```sql
-- El ONLY ahora mismo no es necesario, pero en caso de futuras herencias mostraria las tablas hijas si no se pone
SELECT * FROM ONLY Administrativo 
WHERE area = 'administracion'
ORDER BY plus_productividad DESC
;
```

Una consulta que muestre cada teléfono en una fila. 
La realizo utilizando la tabla base para obtener los telefonos de todos los trabajadores en una fila y
ya que estoy pongo el nombre del empleado para identificar de quien es cada telefono. 
```sql
SELECT nombre_completo, UNNEST(telefonos) AS telefonos
FROM EmpleadoCentro
;
```