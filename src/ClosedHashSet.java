/**
 * implements the class of a closed hash set.
 * @author Timothy Vogel
 */
public class ClosedHashSet extends SimpleHashSet{

    private int capacity;
    private String [] currentList;
    private final static String DELETED_INDICATOR = new String();
    private final static int BAD_LOCATION = -1;
    private final static int RESIZE_FACTOR = 2;
    private int currentAmount;

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table
     */
    public ClosedHashSet(float upperLoadFactor,
                         float lowerLoadFactor){

        super(upperLoadFactor, lowerLoadFactor);
        this.finishConstructor();
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){

        super();
        this.finishConstructor();
    }

    /**
     * builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){

        super();
        this.finishConstructor();
        for(String current: data){
            this.add(current);
        }
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return this.capacity;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        // checks if the new value has
        if(this.contains(newValue)){
            return false;
        }

        // checks if a rehashing needs to be done
        if(this.additionNeedRehashing()){
            this.rehashTable(this.capacity() * ClosedHashSet.RESIZE_FACTOR);
        }

        this.currentAmount++;
        // puts the value in  a new location
        this.currentList[this.findLocationForNewValue(newValue, this.currentList)] = newValue;
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {

        // gets the location of the word
        int wordLocation = this.findWordsLocation(searchVal);

        // checks that the location is not bad location
        return wordLocation != ClosedHashSet.BAD_LOCATION;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {

        // checks if we have the value
        if (!this.contains(toDelete)){
            return false;
        }

        // implements the new location with a pointer to deleted location
        this.currentList[this.findWordsLocation(toDelete)] = ClosedHashSet.DELETED_INDICATOR;


        // checks if we need to rehash
        if(this.removeNeedRehashing()){
            this.rehashTable(this.capacity() / ClosedHashSet.RESIZE_FACTOR);
        }

        this.currentAmount --;

        return true;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {

        return this.currentAmount;
    }

    /**
     * finishes the constructor job
     */
    private void finishConstructor(){
        this.capacity = SimpleHashSet.INITIAL_CAPACITY;
        this.currentList = new String[this.capacity];
    }

    /**
     * finds a free location for the new word
     * @param newWord the new word
     * @param arrayWithValues the arrays with the values
     * @return a location to put a new value
     */
    private int findLocationForNewValue(String newWord, String [] arrayWithValues) {

        int currentGuess = ClosedHashSet.BAD_LOCATION;

        for (int i = 0; i < arrayWithValues.length; i++){

            // gets the current guess
            currentGuess = this.getEstimatedLocation(i, newWord);

            // checks if the current guess location is null or deleted location
            if (arrayWithValues[currentGuess] == null || arrayWithValues[currentGuess] ==
                    ClosedHashSet.DELETED_INDICATOR){
                return currentGuess;
            }
        }

        return currentGuess;
    }

    /**
     * gets the estimated location of a string
     * @param index our attempt number
     * @param word our word
     * @return the guessed location
     */
    private int getEstimatedLocation(int index, String word){

        // gets the clamped location
        return this.clamp(word.hashCode() +
                ((index + (index * index)) / 2));
    }

    /**
     * rehashes the table to the new size
     * @param newSize the new size of the new table
     */
    private void rehashTable(int newSize){

        this.capacity = newSize;
        String [] newArray = new String[this.capacity];

        // goes through all the values
        for (String value: this.currentList){

            // checks the value is supposed to go to the next value
            if (value != null && value != ClosedHashSet.DELETED_INDICATOR)
            {
                newArray[this.findLocationForNewValue(value, newArray)] = value;
            }

        }

        // sets that new array as our main array
        this.currentList = newArray;
    }

    /**
     * finds the words location
     * @param word our word
     * @return were the word would be
     */
    private int findWordsLocation(String word){

        // goes through every value amount in that array
        for(int i = 0; i < this.currentList.length; i++){

            // gets an estimate location
            int estimatedLocation = getEstimatedLocation(i, word);

            // checks if that location is null
            if(this.currentList[estimatedLocation] == null){
                return ClosedHashSet.BAD_LOCATION;
            }
            // checks if we found the good location
            else if(this.currentList[estimatedLocation].equals(word) &&
            this.currentList[estimatedLocation] != ClosedHashSet.DELETED_INDICATOR)
            {
                return estimatedLocation;
            }

        }

        // the word was not there
        return ClosedHashSet.BAD_LOCATION;
    }
}
