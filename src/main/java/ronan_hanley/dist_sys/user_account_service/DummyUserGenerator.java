package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.UserDetails;

import java.util.Random;

public class DummyUserGenerator {
    private static final String[] FIRST_NAMES = {
            "Matthew",
            "Mark",
            "Luke",
            "John",
            "Connor",
            "Edward",
            "Sean",
            "Micheal",
            "Oisin",
            "Frank",

            "Sarah",
            "Rachel",
            "Emma",
            "Sophie",
            "Orla",
            "Mary",
            "Ciara",
            "Laura",
            "Catherine",
            "Ellen"
    };

    private static final String[] SURNAMES = {
            "OHara",
            "Doherty",
            "OBrien",
            "Burns",
            "Henderson",
            "McNeill",
            "Anderson",
            "Cunningham",
            "May",
            "Daly"
    };

    public static final String[] EMAIL_DOMAINS = {
            "gmail.com",
            "yahoo.com",
            "mail.com",
            "hotmail.com",
            "gmit.ie"
    };

    public static final String[] BASE_PASSWORDS = {
            "password",
            "qwerty",
            "abcde",
            "letmein",
            "monkey"
    };

    public static final String[] PASSWORD_EXTENTIONS = {
            "",
            "1",
            "123",
            "12345",
            "666",
            "x"
    };

    private static Random random = new Random();

    private static <T> T randomElement(T[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    public static NewUser[] generateDummyUsers(int numDummyUsers) {
        NewUser[] dummyUsers = new NewUser[numDummyUsers];

        for (int i = 0; i < numDummyUsers; i++) {
            dummyUsers[i] = generateDummyUser(i);
        }

        return dummyUsers;
    }

    public static NewUser generateDummyUser(int id) {
        String firstName = randomElement(FIRST_NAMES);
        String lastName = randomElement(SURNAMES);
        String emailDomain = randomElement(EMAIL_DOMAINS);
        String basePassword = randomElement(BASE_PASSWORDS);
        String passwordExt = randomElement(PASSWORD_EXTENTIONS);

        String userName = firstName + lastName;
        String email = String.format("%s.%s@%s",
                firstName.toLowerCase(),
                lastName.toLowerCase(),
                emailDomain);
        String password = String.format("%s%s",
                random.nextInt(5) == 0 ? basePassword.toUpperCase() : basePassword.toLowerCase(),
                passwordExt);

        return new NewUser(
                new UserDetails(id, userName, email),
                password
        );
    }

}
