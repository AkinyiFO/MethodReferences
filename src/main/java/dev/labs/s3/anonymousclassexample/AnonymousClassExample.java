package dev.labs.s3.anonymousclassexample;

// Source: https://docs.oracle.com/javase/tutorial/java/javaOO/examples/LocalClassExample.java
public class AnonymousClassExample {

    static String regularExpression = "[^0-9]";

    // Same rules of a static method/ class method applies.
    public static void validatePhoneNumber(String phoneNumber1, String phoneNumber2) {

        //  JDK 7 and below:
        //  However, a local class can only access local variables that are declared final.
//        final int numberLength = 10;

        //  Valid in JDK 8 and later: Can be final or effectively final.
        int numberLength = 10;

        class PhoneNumber {

            String formattedPhoneNumber = null;

            PhoneNumber(String phoneNumber) {
//                  numberLength = 7; // Test for effective final
                String currentNumber = phoneNumber.replaceAll(regularExpression, "");
                if (currentNumber.length() == numberLength) // Captured variable numberLength
                    formattedPhoneNumber = currentNumber;
                else
                    formattedPhoneNumber = null;
            }

            public String getNumber() {
                return formattedPhoneNumber;
            }

            //  Valid in JDK 8 and later:
            //  If you declare the local class in a method, it can access the method's parameters.

            public void printOriginalNumbers() {
                System.out.println("Original numbers are " + phoneNumber1 +
                        " and " + phoneNumber2); // Captures phoneNumber1 and phoneNumber2
            }
        }

        PhoneNumber myNumber1 = new PhoneNumber(phoneNumber1);
        PhoneNumber myNumber2 = new PhoneNumber(phoneNumber2);

        // Anonymous class extending PhoneNumber
        PhoneNumber anonClass = new PhoneNumber(phoneNumber1) {
            @Override
            public String getNumber() {
                return "+254" + super.getNumber();
            }
        };

        //  Valid in JDK 8 and later:

        myNumber1.printOriginalNumbers();

        if (myNumber1.getNumber() == null)
            System.out.println("First number is invalid");
        else
            System.out.println("First number is " + myNumber1.getNumber());
        if (myNumber2.getNumber() == null)
            System.out.println("Second number is invalid");
        else
            System.out.println("Second number is " + myNumber2.getNumber());

        System.out.println(anonClass.getNumber());
    }

    public static void main(String... args) {
        validatePhoneNumber("123-456-7890", "456-7890");
    }

}