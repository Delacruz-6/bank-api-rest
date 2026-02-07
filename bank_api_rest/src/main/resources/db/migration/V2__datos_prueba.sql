-- Datos de prueba para desarrollo
-- Compatible con PostgreSQL y H2
INSERT INTO prestamos (nombre_solicitante, importe_solicitado, divisa, documento_identificativo, estado, fecha_creacion) 
VALUES 
    ('Juan Pérez García', 15000.00, 'EUR', '12345678A', 'PENDIENTE', CURRENT_TIMESTAMP),
    ('María López Martínez', 25000.00, 'EUR', '87654321B', 'APROBADA', DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
    ('Carlos Rodríguez Sánchez', 10000.00, 'EUR', 'X1234567C', 'RECHAZADA', DATEADD('DAY', -5, CURRENT_TIMESTAMP)),
    ('Ana González Fernández', 30000.00, 'EUR', '11223344D', 'APROBADA', DATEADD('DAY', -10, CURRENT_TIMESTAMP)),
    ('Pedro Martín Jiménez', 20000.00, 'EUR', 'Y7654321E', 'CANCELADA', DATEADD('DAY', -15, CURRENT_TIMESTAMP));

