package Repository;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IRepository<idType,objectType> {


    /**
     * @param id -> the entity's id
     * @return the entity based on its id or null if the object does not exists in database
     */

    objectType findById(idType id);

    /**
     * Deletes the object with the given id
     * @param id-> the object identifier
     * @throws Exception if in database does not exist such an element
     */

    void delete(idType id) throws  Exception;

    /**
     *
     * @return all elements from collection
     */

    List <objectType> getAll();

    /**
     * Saves the object in the database
     * @param newObject the object that we want to insert into database
     * @throws Exception if in database exists such an object
     */

    void save(objectType newObject) throws  Exception;

    /**
     * Updates the fields of the object with the id with the fields of the @param object
     * @param id the object's id
     * @param object
     * @throws Exception if in database does not exits an object
     */

    void update(idType id,objectType object) throws  Exception;

    /**
     * Filter the information
     * @param predicate the predicate used to filter the information from database
     * @return a list with those elements witch respect the condition
     */

    default List <objectType> genericFilter(Predicate<objectType> predicate){
        return getAll().stream().filter(predicate).collect(Collectors.toList());
    }

}
