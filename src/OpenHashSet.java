/**
 * a class representing an open hash set
 * @author Timothy Vogel
 */
public class OpenHashSet extends SimpleHashSet{

    private OpenHashingLink [] openHashingLinks;
    private int capacity;
    private final static int EXPAND_FACTOR = 2;
    private int currentAmount;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.finishConstructor();
    }

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16)
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.finishConstructor();
    }

    /**
     * builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        super();
        this.finishConstructor();

        // goes through all the strings
        for (String current: data){
            this.add(current);
        }
    }

    /**
     * gets the capacity
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        // gets the location
        int location = this.clamp(newValue.hashCode());

        // checks if that location is not empty
        if(this.openHashingLinks[location] != null){

            // checks if it in that location
            if(this.openHashingLinks[location].contains(newValue)){
                return false;
            }
        }

        // checks if we need to increase the capacity of the table
        if(this.additionNeedRehashing()){

            // increases the capacity of the table
            this.resizeTable(this.capacity *  OpenHashSet.EXPAND_FACTOR);

            // gets a new location to put the value in
            location = this.clamp(newValue.hashCode());
        }

        // checks if the location we wanna add to is currently null
        if(this.openHashingLinks[location] == null){
            this.openHashingLinks[location] = new OpenHashingLink();
        }

        this.currentAmount ++;
        this.openHashingLinks[location].add(newValue);

        // adds the data to the hardlink
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {

        // gets the location for the value
        int location = this.clamp(searchVal.hashCode());

        // checks if that location is empty
        if(this.openHashingLinks[location] == null){
            return false;
        }
        // returns if it succeed
        return this.openHashingLinks[location].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {

        // gets the location where it should be
        int location = this.clamp(toDelete.hashCode());

        // checks if that location is currently null
        if(openHashingLinks[location] == null){
            return false;
        }

        // checks if we have removed the value
        if(openHashingLinks[location].delete(toDelete)){

            // checks if we need to resize
            if(this.removeNeedRehashing()){

                // resize the table based on need
                this.resizeTable(this.capacity / OpenHashSet.EXPAND_FACTOR);
            }

            // reducess the current amount
            this.currentAmount --;

            return true;
        }

        return false;
    }

    /**
     * gets the current amount in the array
     * @return the current amount
     */
    @Override
    public int size() {
        return currentAmount;
    }

    /**
     * finishes the constructor
     */
    private void finishConstructor(){

        // sets the current amount to zero
        currentAmount = 0;

        // sets the new current size
        this.capacity = SimpleHashSet.INITIAL_CAPACITY;

        // creates a hashlink array
        this.openHashingLinks = new OpenHashingLink[capacity];
    }

    /**
     *  resizes the capacity of the table
     */
    private void resizeTable(int newSize){

        // checks if the new size is not zero
        if(newSize == 0){
            return;
        }

        // gets the new capacity for the new array
        this.capacity = newSize;

        // creates a new list
        OpenHashingLink [] newList = new OpenHashingLink[this.capacity];

        // goes through all our lists
        for(OpenHashingLink link: this.openHashingLinks){

            if(link != null){

                // goes through every value to rehash it
                for(String value: link.getAllValues()){

                    int newPlace = this.clamp(value.hashCode());

                    if (newList[newPlace] == null){
                        newList[newPlace] = new OpenHashingLink();
                    }

                    // adds the value to the new array
                    newList[newPlace].add(value);
                }
            }
        }

        // sets the array to the new array
        this.openHashingLinks = newList;
    }
}
