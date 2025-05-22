package judamov.demo_jwt.exceptions;

public class AuthExceptions {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String documento) {
            super("Usuario no encontrado con documento: " + documento);
        }
    }

    public static class PasswordErrorException extends RuntimeException {
        public PasswordErrorException() {
            super("La contraseña ingresada es incorrecta");
        }
    }

    public static class TipoDocumentNotFoundException extends RuntimeException {
        public TipoDocumentNotFoundException(Integer idTipoDocumento) {
            super("Tipo de documento no encontrado con id: " + idTipoDocumento);
        }
    }

    public static class RolNotFoundException extends RuntimeException {
        public RolNotFoundException(Integer idRol) {
            super("Rol no encontrado con id: " + idRol);
        }
    }

    public static class UserRegistrationException extends RuntimeException {
        public UserRegistrationException(String detalle) {
            super("Error al registrar el usuario: " + detalle);
        }
    }
}
