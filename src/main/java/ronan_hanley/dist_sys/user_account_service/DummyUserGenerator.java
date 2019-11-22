package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Generates users with random user details
 */
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

    private static final String[] EMAIL_DOMAINS = {
            "gmail.com",
            "yahoo.com",
            "mail.com",
            "hotmail.com",
            "gmit.ie"
    };

    private static final String[] BASE_PASSWORDS = {
            "password",
            "qwerty",
            "abcde",
            "letmein",
            "monkey",
            "dragon",
            "admin"
    };

    private static final String[] PASSWORD_EXTENTIONS = {
            "",
            "1",
            "123",
            "12345",
            "666",
            "x"
    };

    private Random random;
    private Map<String, Integer> usernameFreq;

    public DummyUserGenerator(int seed) {
        // seeded random (so you get the same dummy users every time you run the service)
        random = new Random(seed);
        usernameFreq = new HashMap<>();
    }

    private <T> T randomElement(T[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    public NewUser[] generateDummyUsers(int numDummyUsers) {
        NewUser[] dummyUsers = new NewUser[numDummyUsers];

        for (int i = 0; i < numDummyUsers; i++) {
            dummyUsers[i] = generateDummyUser(i);
        }

        return dummyUsers;
    }

    public NewUser generateDummyUser(int id) {
        // pick random user details
        String firstName = randomElement(FIRST_NAMES);
        String lastName = randomElement(SURNAMES);
        String emailDomain = randomElement(EMAIL_DOMAINS);
        String basePassword = randomElement(BASE_PASSWORDS);
        String passwordExt = randomElement(PASSWORD_EXTENTIONS);

        String userName = firstName + lastName;

        // ensure username is unique (by adding a number if it isn't)
        Integer freq = usernameFreq.get(userName);

        if (freq != null) {
            usernameFreq.put(userName, freq + 1);
            userName += "_" + freq;
        }
        else {
            usernameFreq.put(userName, 1);
        }

        // form email and password from random fields
        String email = String.format("%s.%s@%s",
                firstName.toLowerCase(),
                lastName.toLowerCase(),
                emailDomain);
        String password = String.format("%s%s",
                random.nextInt(5) == 0 ? basePassword.toUpperCase() : basePassword.toLowerCase(),
                passwordExt);

        // create NewUser object
        return new NewUser(
                new UserDetails(id, userName, email),
                password
        );
    }

}
