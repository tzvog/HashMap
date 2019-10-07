/**
 * a facade class that will implement all the hard work for us
 * * @author Timothy Vogel
 */
public class CollectionFacadeSet implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){

        // sets that as our collection
        this.collection =  collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        if (this.contains(newValue)){
            return false;
        }

        this.collection.add(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        return this.collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {

        return this.collection.remove(toDelete);

    }


    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.collection.size();
    }

}
