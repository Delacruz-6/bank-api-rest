-- Crear tabla de préstamos
CREATE TABLE prestamos (
    id BIGSERIAL PRIMARY KEY,
    nombre_solicitante VARCHAR(255) NOT NULL,
    importe_solicitado DECIMAL(15, 2) NOT NULL,
    divisa VARCHAR(3) NOT NULL,
    documento_identificativo VARCHAR(20) NOT NULL UNIQUE,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_modificacion TIMESTAMP,
    usuario_ultima_modificacion VARCHAR(255),
    
    CONSTRAINT chk_importe_positivo CHECK (importe_solicitado > 0),
    CONSTRAINT chk_estado CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA', 'CANCELADA'))
);

-- Índices para mejorar rendimiento
CREATE INDEX idx_prestamos_estado ON prestamos(estado);
CREATE INDEX idx_prestamos_fecha_creacion ON prestamos(fecha_creacion DESC);
CREATE INDEX idx_prestamos_documento ON prestamos(documento_identificativo);

-- Comentarios de documentación
COMMENT ON TABLE prestamos IS 'Tabla que almacena las solicitudes de préstamos personales';
COMMENT ON COLUMN prestamos.id IS 'Identificador único del préstamo';
COMMENT ON COLUMN prestamos.nombre_solicitante IS 'Nombre completo del solicitante';
COMMENT ON COLUMN prestamos.importe_solicitado IS 'Importe solicitado del préstamo';
COMMENT ON COLUMN prestamos.divisa IS 'Código de divisa ISO 4217 (ej: EUR, USD)';
COMMENT ON COLUMN prestamos.documento_identificativo IS 'Documento identificativo (DNI/NIE) del solicitante';
COMMENT ON COLUMN prestamos.estado IS 'Estado actual del préstamo: PENDIENTE, APROBADA, RECHAZADA, CANCELADA';
COMMENT ON COLUMN prestamos.fecha_creacion IS 'Fecha y hora de creación de la solicitud';
COMMENT ON COLUMN prestamos.fecha_ultima_modificacion IS 'Fecha y hora de la última modificación';
COMMENT ON COLUMN prestamos.usuario_ultima_modificacion IS 'Usuario que realizó la última modificación';
