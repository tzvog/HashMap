import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * a class to analyze data from the list with
 * @author Timothy Vogel
 */
public class SimpleSetPerformanceAnalyzer {

    private final static String DATA_SET_ONE = "/cs/usr/tzvog/IdeaProjects/ex4-tzvog/src/data1.txt";
    private final static String DATA_SET_TWO = "/cs/usr/tzvog/IdeaProjects/ex4-tzvog/src/data2.txt";
    private final static String TEST_WORD = "hi";
    private final static String TEST_NEGATIVE_WORD = "-13170890158";
    private final static String TEST_NON_EXISTING_WORD = "23";
    private final static String [] DATA_SET_TYPE_NAME = {"OpenHashSet", "ClosedHashSet", "TreeSet",
            "LinkedList", "HashSet"};

    public static void main(String[] args) {
        choiceReader(args);
    }

    /**
     *  goes through all the choice the user has put in the command line when running the program
     * @param testerInput the input the tester wants to try
     */
    private static void choiceReader(String[] testerInput) {

        SimpleSet [] firstSets = createSimpleSets();
        SimpleSet [] secondSets = createSimpleSets();

        // goes through all the choices
        for(String argument:testerInput){

            int testNum;

            // makes sure the argument is actually a number
            try {
                testNum = Integer.parseInt(argument);
            }
            catch(NumberFormatException e){
                continue;
            }

            // checks what test we wanted to run
            switch(testNum){

                // a test case of add data set one
                case 1:

                    // if test was ran before it cleans it
                    firstSets = createSimpleSets();

                    System.out.println("Analyzing add Data1 Test");

                    // runs the full tests
                    fullAddTest(SimpleSetPerformanceAnalyzer.DATA_SET_ONE, firstSets);

                    break;
                // a test case of add data set two
                case 2:

                    // if test was ran before it cleans it
                    secondSets = createSimpleSets();

                    System.out.println("Analyzing add Data2 Test");

                    // runs the full tests
                    fullAddTest(SimpleSetPerformanceAnalyzer.DATA_SET_TWO, secondSets);

                    break;
                // a test case of checking if data the data structures contains hi with data file one
                case 3:

                    System.out.println("data file 1");

                    // assumes the data is full and wil run tests
                    fullContainsTest(SimpleSetPerformanceAnalyzer.TEST_WORD, firstSets);
                    break;
                // a test case of checking if data the data structures contains negative number with
                // data file one
                case 4:

                    System.out.println("data file 1");

                    // assumes the data is full and wil run tests
                    fullContainsTest(SimpleSetPerformanceAnalyzer.TEST_NEGATIVE_WORD, firstSets);
                    break;
                // a test case of checking if data the data structures contains 23 with data file two
                case 5:

                    System.out.println("data file 2");

                    // assumes the data is full and wil run tests
                    fullContainsTest(SimpleSetPerformanceAnalyzer.TEST_NON_EXISTING_WORD, secondSets);
                    break;
                // a test case of checking if data the data structures contains hi with data file two
                case 6:

                    System.out.println("data file 2");

                    // assumes the data is full and wil run tests
                    fullContainsTest(SimpleSetPerformanceAnalyzer.TEST_WORD, secondSets);
                    break;
                default:
            }

            System.out.println("--------------------------------------------------------");
        }
    }

    /**
     * initializes the set we want to test
     * @return an array with initialized sets we wanna test
     */
    private static SimpleSet[] createSimpleSets() {

        SimpleSet [] setHolder = new SimpleSet[5];

        // open hash sets
        setHolder[0] = new OpenHashSet();

        // closed hash sets
        setHolder[1] = new ClosedHashSet();

        // tree sets
        setHolder[2] = new CollectionFacadeSet(new TreeSet<String>());

        // linked list
        setHolder[3] = new CollectionFacadeSet(new LinkedList<String>());

        // java built in Hash Set
        setHolder[4] = new CollectionFacadeSet(new HashSet<String>());

        return setHolder;
    }

    /**
     * calculates how long each one of our data set does the task of add in
     * @param fileName the name of the file we are getting the data from
     */
    private static void fullAddTest(String fileName, SimpleSet[] setHolder){

        for(int i = 0; i < setHolder.length; i++){
            // prints results for every test
            System.out.println(
                    "it takes " + add(fileName, setHolder[i]) / 1000000 +
                            " millisecond to add all the words to set " +
                            SimpleSetPerformanceAnalyzer.DATA_SET_TYPE_NAME[i]);
        }
    }

    /**
     * figures our for each data type how long add will take for all the data from a file
     * @param fileName the file to get the data from
     * @param set our current set
     * @return how long did it take
     */
    private static long add(String fileName, SimpleSet set){

        // gets all the file data
        String[] fileData = Ex4Utils.file2array(fileName);

        long timeBefore = System.nanoTime();

        // goes through all the data and adds it to the data structure
        for(String data: fileData){
            set.add(data);
        }

        long difference = System.nanoTime() - timeBefore;

        return difference;
    }

    /**
     * prints how long it takes a data structure to find a certain word
     * @param word word the word we are searching for
     * @param setHolder the set we wanna go over
     */
    private static void fullContainsTest(String word, SimpleSet[] setHolder){

        System.out.println("Analyzing how long it takes each data structure to check if it " +
                "contains the word " + word);

        for(int i = 0; i < setHolder.length; i++){

            // user message on what is going on
            System.out.println("it takes  " + contains(word, setHolder[i], i) + "  nanoseconds to find "
                    + word + " on data structure " + SimpleSetPerformanceAnalyzer.DATA_SET_TYPE_NAME[i]);

            contains(word, setHolder[i], i);
        }


    }

    /**
     * prints how long it takes a certain data structure to find a word
     * @param word the word we wanna find
     * @param ourSet the set we are looking through
     * @param type the type to make sure that it is not a linked list
     */
    private static long contains(String word, SimpleSet ourSet, int type){

        int linkedListTime = 7000;
        int notLinkedListTime = 70000;

        long timeBefore, difference;

        // if we are dealing with a linked list treat it differently
        if(SimpleSetPerformanceAnalyzer.DATA_SET_TYPE_NAME[type].equals("LinkedList")){

            timeBefore = System.nanoTime();

            //
            for(int i = 0; i < linkedListTime; i++){

                // try to find the word
                ourSet.contains(word);
            }

            difference = System.nanoTime() - timeBefore;

            return (difference / linkedListTime);

        }
        else {

            // warms up the JVM
            for (int i = 0; i < notLinkedListTime; i++){
                // try to find the word
                ourSet.contains(word);
            }

            // starts measuring time
            timeBefore = System.nanoTime();

            // looks for that word
            for (int i = 0; i < notLinkedListTime; i++){
                // try to find the word
                ourSet.contains(word);
            }

            difference = System.nanoTime() - timeBefore;

            return (difference / notLinkedListTime);
        }

    }
}