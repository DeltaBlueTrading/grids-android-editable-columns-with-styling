package com.shinobicontrols.grids.sample.styled_editable_grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a random list of Person objects
 */
public class PersonGenerator {
    private static final String[] FORENAMES = new String[]{"Brian", "Fred", "Carol", "Philip", "Ian", "James", "Archie", "Marlon",
            "Josh", "Sacha", "Laurent", "Robert", "Sarra", "Jessica", "Lauren",
            "Mike", "John", "Josh", "Peter", "Lucy", "Anna", "Hannah", "Lilly",
            "Digby", "Elizabeth", "Jonathan", "Andrew", "Alexander", "Christopher",
            "Nicholas", "Addison", "Madison", "Samantha", "Abigail", "Mia", "Ava",
            "Taylor"};
    private static final String[] SURNAMES = new String[]{"Smith", "Blessed", "Barber", "Jones", "Newsome", "Price", "Page", "Pounder",
            "Scott", "Martin", "Wildsmith", "Wilder", "King", "Ferguson", "Batchelor", "Peter",
            "Elliot", "Fritz", "Gregg", "Harper", "Karl", "Lees", "Ousbourne", "Quincey", "Rogers",
            "Rea", "Richards", "Digby", "Daniels", "Phillips", "Eberhardt", "Taylor",
            "Brown", "Johnson", "Walker", "Hill", "Moore", "Baker", "Patel", "Morgan"};

    public static List<Person> generatePeople(int numberOfPeople) {
        Random random = new Random();
        List<Person> people = new ArrayList<Person>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < numberOfPeople; i++) {
            stringBuilder.setLength(0);
            stringBuilder.append(FORENAMES[random.nextInt(FORENAMES.length)]);
            stringBuilder.append(" ");
            stringBuilder.append(SURNAMES[random.nextInt(SURNAMES.length)]);
            int age = random.nextInt(75) % 30 + 10;
            people.add(new Person(stringBuilder.toString(), age));
        }

        return people;
    }
}
