package exmp.napodev.firebasecrashexample.exceptions;

/**
 * Created by opannapo on 12/4/17.
 */

public class EntityNullException extends Throwable {
    public EntityNullException(Class entity) {
        super(String.valueOf(entity));
    }
}