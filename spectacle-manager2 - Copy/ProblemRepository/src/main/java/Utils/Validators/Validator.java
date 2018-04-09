package Utils.Validators;

public interface Validator <objectType> {

    /**
     *
     * @param object ->the object which we want to validate
     * @throws Exception if the object does not respect the problem constraints
     */

    void validate(objectType object) throws  Exception;

}
