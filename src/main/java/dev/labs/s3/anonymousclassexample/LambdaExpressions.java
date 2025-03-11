// Source: https://docs.oracle.com/javase/tutorial/java/javaOO/examples/RosterTest.java

package dev.labs.s3.anonymousclassexample;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaExpressions {
    // Approach 1: Create Methods That Search for Members That Match One Characteristic (age)
    public static void printPersonsOlderThan(List<Person> roster, int age) { // A List is a collection. More on that later.
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

//    public static void main(String[] args) {
//        printPersonsOlderThan(Person.createRoster(), 20);
//    }
    // Issues of Approach 1: What if you want members below a certain age?
    // You can use a method that works with a range:

    // Approach 2: Create More Generalized Search Methods
    public static void printPersonsWithinAgeRange(List<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printPerson();
            }
        }
    }

//    public static void main(String[] args) {
//        printPersonsWithinAgeRange(Person.createRoster(), 14,30);
//    }
    // Issues of Approach 2: What if you need a criteria of Members based on their gender, location and relationship status?
    // You can use a Local Class that handles all your search criteria:

    // Approach 3: Specify Search Criteria Code in a Local Class
    public static void printPersons(List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

//    public static void main(String[] args) {
//        // Local Class
//        class CheckPersonEligibleForSelectiveService implements CheckPerson {
//            @Override
//            public boolean test(Person p) {
//                return p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25;
//            }
//        }
//        printPersons(Person.createRoster(), new CheckPersonEligibleForSelectiveService());
//    }

    // This is a functional Interface (An Interface with just one Abstract Method). More on Interfaces later.
    interface CheckPerson {
        boolean test(Person p);
    }
    // Issues of Approach 3: It needs an interface, and Local Class for each criterion.
    // Can you use an anonymous class to make it more concise? Yes:

    // Approach 4: Specify Search Criteria Code in an Anonymous Class
    public static void printPersonsAnon(List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

//    public static void main(String[] args) {
        // Anonymous Class 1
//        CheckPerson tester = new CheckPerson() {
//            @Override
//            public boolean test(Person p) {
//                return p.getGender() == Person.Sex.MALE
//                        && p.getAge() >= 18
//                        && p.getAge() <= 25;
//            }
//        };
//        printPersonsAnon(Person.createRoster(), tester);

        //  Anonymous Class 2 - More concise
//        printPersonsAnon(Person.createRoster(), new CheckPerson() {
//            @Override
//            public boolean test(Person p) {
//                return p.getGender() == Person.Sex.MALE
//                        && p.getAge() >= 18
//                        && p.getAge() <= 25;
//            }
//        });
//    }
    // Issues with Approach 4: An Anonymous Class is bulky for implementing an interface that contains one method.
    // To fix this, we use Lambda Expressions:

    // Approach 5: Specify Search Criteria Code with a Lambda Expression
    public static void printPersonsLambda(List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

//    public static void main(String[] args) {
//
//        printPersonsLambda(Person.createRoster(),
//                (Person p) -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25
//        );
//    }
    // A lambda expression implements a functional interface.
    // You do not always have to explicitly create a functional interface.
    // In some cases, you can use built-in standard functional interface. I.e. in place of the interface CheckPerson,
    // JDK has standard functional interfaces in java.util.function you can use.
    // E.g. Generic Interface Predicate:
    // interface Predicate<T> {
    //    boolean test(T t);
    // ...
    //}
    // Source: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
    // More on Generics later.
    // In this code, you parameterize Predicate to:
    // interface Predicate<Person> {
    //    boolean test(Person t);
    //}
    // This approach removes the need of defining CheckPerson interface and makes our code more compact:

    // Approach 6: Use Standard Functional interfaces with Lambda Expressions
    public static void printPersonsWithPredicate(List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }

    }

    public static void main(String[] args) {
        printPersonsWithPredicate(
                Person.createRoster(),
                p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25
        );
    }

    // Other ways to use lambda expressions:
    //interface Consumer<T> {
    //    void accept(T t);
    // ...
    //    }
    // Source: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
    //interface Consumer<Person>{
    // void accept (Person p);
//}

    // Approach 7: Use Lambda Expressions Throughout Your Application
    public static void printPersonsWithPredicateConsumer(List<Person> roster, Predicate<Person> tester, Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

//    public static void main(String[] args) {
//         printPersonsWithPredicateConsumer(
//                Person.createRoster(),
//                p -> p.getGender() == Person.Sex.MALE
//                        && p.getAge() >= 18
//                        && p.getAge() <= 25,
//                p -> p.printPerson()
//        );
//    }

    // Approach 7, second example: Using a functional interface that contains an abstract method that returns a value.
    //interface Function<T, R> {
    //    R apply(T t);
    //    ...
    //    }
    // Source: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
    // interface Function<Person, String> {
    //      String apply(Person p);
    //}
    //interface Consumer<String>{
    // void accept (String data);
//}

    public static void processPersonsWithFunction(List<Person> roster, Predicate<Person> tester, Function<Person, String> mapper, Consumer<String> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

//    public static void main(String[] args) {
//         processPersonsWithFunction(
//                Person.createRoster(),
//                p -> p.getGender() == Person.Sex.MALE
//                        && p.getAge() >= 18
//                        && p.getAge() <= 25,
//                p -> p.getEmailAddress(),
//                email -> System.out.println(email)
//        );
//    }

    // Approach 8: Use Generics More Extensively. More on Generics later.
    public static <X, Y> void processElements( Iterable<X> source, Predicate<X> tester, Function<X, Y> mapper, Consumer<Y> block) {
        for (X p : source) {
            if (tester.test(p)) {
                Y data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

//    public static void main(String[] args) {
//         processElements(
//                Person.createRoster(),
//                p -> p.getGender() == Person.Sex.MALE
//                        && p.getAge() >= 18
//                        && p.getAge() <= 25,
//                p -> p.getEmailAddress(),
//                email -> System.out.println(email)
//        );
//    }

    // You can replace Approach 8 with aggregate operations, which process elements from a stream, not directly from a collection.
    // More on aggregate operations later.

    // Approach 9: Use Bulk Data Operations That Accept Lambda Expressions as Parameters
//    public static void main(String[] args) {
//        Person.createRoster()
//                .stream()
//                .filter(
//                        p -> p.getGender() == Person.Sex.MALE
//                                && p.getAge() >= 18
//                                && p.getAge() <= 25)
//                .map(p -> p.getEmailAddress())
//                .forEach(email -> System.out.println(email));
//    }
}