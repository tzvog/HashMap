import java.util.LinkedList;

/**
 * helps us implement the open hashing problems
 * * @author Timothy Vogel
 */
public class OpenHashingLink{

    /**
     * the linked list to hold my list
     */
    private LinkedList<String>  linkedList;
    private CollectionFacadeSet set;

    /**
     * the constructor for my OpenHashingLink
     */
    public OpenHashingLink(){
        this.linkedList = new LinkedList<String>();
        this.set = new CollectionFacadeSet(linkedList);
    }

    /**
     * adds a new value to the string
     * @param newValue the new value to add
     * @return if the action was successful
     */
    public boolean add(String newValue)
    {
        return this.set.add(newValue);
    }

    /**
     * checks if the node contains the
     * @param searchVal the value to search for
     * @return if it contains that value
     */
    public boolean contains(String searchVal) {
        return this.set.contains(searchVal);
    }

    /**
     * try's to remove the value from the list
     * @param toDelete the value to delete
     * @return if the action was successful
     */
    public boolean delete(String toDelete){
        return this.set.delete(toDelete);
    }

    /**
     * gets the size of the list currently
     * @return the size of the current list
     */
    public int getSize(){
        return this.set.size();
    }

    /**
     * gets the values currently held by the array
     * @return returns the values of the link list it holds
     */
    public LinkedList<String> getAllValues(){
        return this.linkedList;
    }
}
