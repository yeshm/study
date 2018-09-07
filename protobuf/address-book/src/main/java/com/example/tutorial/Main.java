package com.example.tutorial;

import java.io.*;

public class Main {

    // This function fills in a Person message based on user input.
    static AddressBookProtos.Person PromptForAddress(BufferedReader stdin,
                                                     PrintStream stdout) throws IOException {
        AddressBookProtos.Person.Builder person = AddressBookProtos.Person.newBuilder();

        stdout.print("Enter person ID: ");
        person.setId(Integer.valueOf(stdin.readLine()));

        stdout.print("Enter name: ");
        person.setName(stdin.readLine());

        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if (email.length() > 0) {
            person.setEmail(email);
        }

        while (true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if (number.length() == 0) {
                break;
            }

            AddressBookProtos.Person.PhoneNumber.Builder phoneNumber =
                    AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            if (type.equals("mobile")) {
                phoneNumber.setType(AddressBookProtos.Person.PhoneType.MOBILE);
            } else if (type.equals("home")) {
                phoneNumber.setType(AddressBookProtos.Person.PhoneType.HOME);
            } else if (type.equals("work")) {
                phoneNumber.setType(AddressBookProtos.Person.PhoneType.WORK);
            } else {
                stdout.println("Unknown phone type.  Using default.");
            }

            person.addPhones(phoneNumber);
        }

        return person.build();
    }

    // Main function:  Reads the entire address book from a file,
    //   adds one person based on user input, then writes it back out to the same
    //   file.
    public static void main(String[] args) throws Exception {
        String fileName = "address_book.txt";
        AddressBookProtos.AddressBook.Builder addressBook = AddressBookProtos.AddressBook.newBuilder();

        // Read the existing address book.
        try {
            addressBook.mergeFrom(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println(fileName + ": File not found.  Creating a new file.");
        }

        System.out.println("addressBook:");
        System.out.println(addressBook.toString());

        System.out.println("creating new people:");

        // Add an address.
        addressBook.addPeople(
                PromptForAddress(new BufferedReader(new InputStreamReader(System.in)),
                        System.out));

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(fileName);
        addressBook.build().writeTo(output);
        output.close();
    }
}
