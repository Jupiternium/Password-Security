import java.util.Scanner;

public class PasswordSecurity {
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_STRENGTH = 4;
    public static final int MIN_NUMBER_OF_LETTERS = 2;
    public static void main(String[] args) {

        System.out.print("Please enter a password: ");
        Scanner passwordReader = new Scanner(System.in);
        String password = passwordReader.next();

        if (password.length() < MIN_PASSWORD_LENGTH) {
            System.out.println("Password is too short");
            return;
        }

        if (password.isBlank()) {
            System.out.println("Password is too short");
            return;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;
        int numOfLetters = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                numOfLetters++;
            }
            else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
                numOfLetters++;
            }
            else if (Character.isDigit(c)) {
                hasNumber = true;
            }
            else {
                hasSymbol = true;
            }
        }

        int passwordStrength = 0;
        if (hasUpperCase) {
            passwordStrength++;
        }
        if (hasLowerCase) {
            passwordStrength++;
        }
        if (hasNumber) {
            passwordStrength++;
        }
        if (hasSymbol) {
            passwordStrength++;
        }

        if (passwordStrength == 1) {
            System.out.println("Password strength: very weak");
        }
        else if (passwordStrength == 2) {
            System.out.println("Password strength: weak");
        }
        else if (passwordStrength == 3) {
            System.out.println("Password strength: medium");
        }
        else {
            System.out.println("Password strength: strong");
        }

        int originalPasswordLength = password.length();
        int k = originalPasswordLength % 10;
        if (passwordStrength < MAX_PASSWORD_STRENGTH) {
            StringBuilder suggestedPassword = new StringBuilder(password);
            boolean changedToLowerCase = false;
            boolean prependedCse = false;

            if (numOfLetters < MIN_NUMBER_OF_LETTERS) {
                suggestedPassword.insert(0, "Cse");
                prependedCse = true;
            }

            if (!hasLowerCase && !prependedCse) {
                for (int i = 0; i < password.length(); i++) {
                    char c = suggestedPassword.charAt(i);
                    if (Character.isUpperCase(c)) {
                        char lowercaseChar = Character.toLowerCase(c);
                        suggestedPassword.setCharAt(i, lowercaseChar);
                        changedToLowerCase = true;
                    }
                    break;
                }
            }

            if (!hasUpperCase && !changedToLowerCase && !prependedCse) {
                int highestValueLowerCase = 0;
                int lastLowerCaseIndex = -1;
                for (int i = 0; i < password.length(); i++) {
                    int currentChar = password.charAt(i);
                    if (Character.isLowerCase(currentChar)) {
                        if (currentChar > highestValueLowerCase) {
                            highestValueLowerCase = currentChar;
                            lastLowerCaseIndex = i;
                        }
                    }
                }

                if (lastLowerCaseIndex >= 0) {
                    char lastLowerCase = suggestedPassword.charAt(lastLowerCaseIndex);
                    char lowerToUpperCase = Character.toUpperCase(lastLowerCase);
                    suggestedPassword.setCharAt(lastLowerCaseIndex, lowerToUpperCase);
                }
            }

            if (!hasNumber) {
                int suggestedPasswordLength = suggestedPassword.length();
                for (int i = 4; i < suggestedPasswordLength + (suggestedPasswordLength / 4); i += 5) {
                    suggestedPassword.insert(i, k);
                } 
            }

            if (!hasSymbol) {
                suggestedPassword.append("@!");
            }

            System.out.println("Here is a suggested stronger password: " + suggestedPassword);
        }
    }
}