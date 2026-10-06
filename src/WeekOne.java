/*
* Violet Gonzalez, October 4, 2026
* Assignment 1
* Professor Cristina Videira Lopes
*/

// The purpose of this program is to iterate through the book Pide and prejudice to find the top 25 words used.

// I know the java.util.* will import just about every interface/class but I want to showcase the exact ones being used
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;



public class WeekOne {
    public static void main(String[] args) throws IOException {
        // Verify if a file was given lets, provide feedback for wrong input and then exits the program
        if (args.length == 0) {
            System.out.println("Needed: java WeekOne.java pride-and-prejudice.txt ...");
            System.err.println("Unable to proceed");

            System.exit(1);
        }

        // Path to appropriate file
        Path path = Path.of(args[0]);

        // Allow for the path to actually be read by the program
        String book = Files.readString(path);

        // Read the Stop file
        Path stopPath = Path.of("../stop_words.txt");

        String stop = Files.readString(stopPath);

        // Splitting "," between the stop words to reflect individual words
        String[] splitStop =  stop.split(",");

        // Creating an empty set to collect the stop words
        Set<String> stopSet = new HashSet<>();

        // use a for loop to trim whitespace from the words
        for (String word : splitStop) {
           stopSet.add(word.trim());
        }


        // Focusing on the book now:

        // Turn the book into lowercase
        String lower = book.toLowerCase();

        // Creating a regex that allows for us to accept only these characters
        String[] bookWords = lower.split("[^a-z0-9]+");


        // Creating a hashMap to create key:value pairs, reflecting as word:count
        Map<String, Integer> wordCount = new HashMap<>();

        // Skip over any lingering words or anything defined within our stop
        for (String wordValue : bookWords) {
            if (wordValue.length() < 2 || stopSet.contains(wordValue)) {
                continue;
            }

            // use getOrDefault method to get words from book and provide a 0 if nothing is there
            Integer counting = wordCount.getOrDefault(wordValue, 0);

            /*
            * put(() method helps place new key-value pairs in the map, and the + 1 helps add
            * to the value of the same key
            */
            wordCount.put(wordValue, counting + 1);
        }

        // Sort the Maps entries by count:

        // Start with an ArrayList to hold the entries
        List<Map.Entry<String, Integer>> wordInBook = new ArrayList<>(wordCount.entrySet());

        // sort the entries to be in reverse order so the largest entries are presented first
        wordInBook.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));


        // Creating a for loop that counts the first 25 words appearing the most in the book
        for (int i = 0; i < 25; i++) {
            Map.Entry<String, Integer> sortedEntry = wordInBook.get(i);
            String key = sortedEntry.getKey();
            Integer value = sortedEntry.getValue();

            System.out.println(key + "  -  " + value);
        }
    }
}
