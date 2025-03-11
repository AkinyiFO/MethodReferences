package dev.labs.s3.anonymousclassexample;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.Set;
import java.util.HashSet;
import java.time.chrono.IsoChronology;

public class MethodReferencesTest {

    // The method transferElements copies elements from one collection to
    // another

    public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
    DEST transferElements(
            SOURCE sourceCollection,
            Supplier<DEST> collectionFactory) {

        DEST result = collectionFactory.get();
        for (T t : sourceCollection) {
            result.add(t);
        }
        return result;
    }

    public static void main(String... args) {

        List<Person> roster = Person.createRoster();

        for (Person p : roster) {
            p.printPerson();
        }


        Person[] rosterAsArray =
                roster.toArray(new Person[roster.size()]);

        class PersonAgeComparator implements Comparator<Person> {
            @Override
            public int compare(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }

        // static <T> void sort(T[] a, Comparator<? super T> c)
        // 1. Without method reference
        Arrays.sort(rosterAsArray, new PersonAgeComparator());

        // 2. With lambda expression - Example 1
        Arrays.sort(rosterAsArray, (Person a, Person b) -> {
                    return a.getBirthday().compareTo(b.getBirthday());
                }
        );

        // 2. With lambda expression - Example 2
        // This lambda function does nothing but call an existing method
        Arrays.sort(rosterAsArray, (a, b) -> Person.compareByAge(a,b));

        // 3. With method reference
        // a. Reference to a static method
        Arrays.sort(rosterAsArray, Person::compareByAge);

        // b. Reference to an instance method of a particular object
        class ComparisonProvider {
            public int compareByName(Person a,
                                     Person b) {
                return a.getName().compareTo(b.getName());
            }

            public int compareByAge(Person a,
                                    Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }
        ComparisonProvider myComparisonProvider = new ComparisonProvider();
        Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);

        // c. Reference to an instance method of an arbitrary object of a particular type

        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        // d. Reference to a constructor
        // ClassName::new
        Set<Person> rosterSetLambda =
                transferElements(roster, () -> { return new HashSet<>(); });

        Set<Person> rosterSet = transferElements(
                roster, HashSet::new);
        System.out.println("Printing rosterSet:");
        rosterSet.stream().forEach(p -> p.printPerson());


        // More examples: https://docs.oracle.com/javase/tutorial/java/javaOO/examples/MethodReferencesExamples.java

        // When to use: https://docs.oracle.com/javase/tutorial/java/javaOO/whentouse.html
        // 1. Nested Classes
        // 2. Local Classes
        // 3. Anonymous Classes
        // 4. Lambda Expressions
    }
}
