import java.util.Scanner;

public class Civilization {
    private static int numAttacks;
    private static double gold;
    private static double resources;
    private static int happiness;
    private static int military;
    private static int tech;
    private static String civName;
    private static String leaderName;
    private static String[] cities = new String[5];
    private static int numCities = 1;
    private static String cityString = "";

    public static void main(String[] args) {
        System.out.println("\n* * * * * * * * * *"
                + "\nWelcome to the game!"
                + "\nPlease enter the name of the Civilization "
                + "you want to play as:"
                + "\n\nAmerican"
                + "\nZulu"
                + "\nEnglish"
                + "\nChinese");
        Scanner s = new Scanner(System.in);
        String civChoice = s.nextLine().toLowerCase();

        while (civChoice.compareTo("american") != 0
              && civChoice.compareTo("zulu") != 0
              && civChoice.compareTo("english") != 0
              && civChoice.compareTo("chinese") != 0) {
            System.out.println("Must be one of the choices."
                             + " Please enter a new name:");
            civChoice = s.nextLine().toLowerCase();
        }
        createCiv(civChoice);
        System.out.println("\nWhat will you name your first city?");
        cities[0] = s.nextLine();
        int turn = 0;
        while (stillPlaying()) {      //game loop
            for (int i = 0; i < 10; i++) {
                System.out.print("\n");
                for (int k = 0; k < 10; k++) {
                    if (i % 2 == 0) {
                        System.out.print("* ");
                    } else {
                        System.out.print(" *");
                    }
                }
            }
            turn++;
            System.out.println("\nTurn " + turn);
            if (turn != 1) {
                newTurn();
                System.out.println("Press Enter to continue.");
                s.nextLine();
            }
            info();
            System.out.println("\nPress Enter to continue.");
            s.nextLine();
            boolean turnCompleted = false;
            while (!turnCompleted) {
                System.out.println("Enter the number for the action you would"
                                      + " like to take:"
                                  + "\n1. Settle a city: -15.5 gold"
                                  + "\n2. Demolish a city: +1.5 resources"
                                  + "\n3. Build militia: -5 gold, -3 resources,"
                                      + " +1 military"
                                  + "\n4. Research technology: -50 gold,"
                                      + " -2 resources, +1 Technology"
                                  + "\n5. Attack enemy city: -6 military,"
                                      + " -3 happiness, +10 gold"
                                  + "\n6. End turn"
                                  + "\n0. Quit game");
                String actionChoice = s.nextLine();
                if (actionChoice.compareTo("1") == 0) {
                    turnCompleted = settle();       //if settle was successful
                } else if (actionChoice.compareTo("2") == 0) {   //turnCompleted
                    turnCompleted = demolish();                //is true
                } else if (actionChoice.compareTo("3") == 0) {
                    turnCompleted = buildMilitia();
                } else if (actionChoice.compareTo("4") == 0) {
                    turnCompleted = research();
                } else if (actionChoice.compareTo("5") == 0) {
                    turnCompleted = attack();
                } else if (actionChoice.compareTo("6") == 0) {
                    turnCompleted = true;
                } else if (actionChoice.compareTo("0") == 0) {
                    return;
                } else {
                    System.out.println("Not a valid number.");
                }
                if (!turnCompleted) {
                    System.out.println("Please make a valid choice.");
                }
            }
            System.out.println("Turn Completed. Press Enter to continue.");
            s.nextLine();
        }
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
            for (int k = 0; k < 10; k++) {
                if (i % 2 == 0) {
                    System.out.println("* ");
                } else {
                    System.out.println(" *");
                }
            }
        }
        System.out.println("\n\n\nCongrats! You Won!");
    }

    public static void createCiv(String civChoice) {
        numAttacks = 0;
        gold = 20.5;
        resources = 30;
        happiness = 10;
        military = 0;
        tech = 0;
        if (civChoice.compareTo("american") == 0) {
            civName = "American";
            leaderName = "George Washington";
        } else if (civChoice.compareTo("zulu") == 0) {
            civName = "Zulu";
            leaderName = "Shaka";
        } else if (civChoice.compareTo("english") == 0) {
            civName = "English";
            leaderName = "Queen Elizabeth I";
        } else {
            civName = "Chinese";
            leaderName = "Wu Zetian";
        }
    }

    public static void cityString() {
        cityString = "";
        for (int i = 0; i < numCities; i++) {
            cityString += ((i + 1) + ". " + cities[i]);
            if ((i < 4) && (numCities - 1 > i)) {
                cityString += ", ";
            }
        }
    }

    public static void info() {
        cityString();

        System.out.print("------------------------------"
                + "----------------------------------------"
                + "\nCivilization Name: " + civName
                + "\t\tLeader Name: " + leaderName
                + "\nCities: " + cityString
                + "\nGold: ");
        System.out.printf("%.2f", gold);
        System.out.print("\tResources: ");
        System.out.printf("%.2f", resources);
        System.out.println("\t\tHappiness: " +  happiness
                + "\nMilitary: " + military
                + "\tTechnology: " + tech
                + "\nNumber of Attacks: " + numAttacks
                + "\n------------------------------"
                + "----------------------------------------");
    }

    public static void newTurn() {
        if (happiness > 20) {
            resources += 5 * numCities;
            System.out.println("Happiness is over 20! +5 resources per city.");
        } else {
            resources += 1;
            System.out.println("Happiness is below 20. Only +1 resources.");
        }
        gold += 3 * numCities;
        System.out.println("+3 gold per city: +" + 3 * numCities + " gold.");
        if (resources % 2 == 0) {
            happiness += 1;
            System.out.println("Your resources are divisible by two: "
                    + "+1 happiness.");
        } else {
            happiness -= 3;
            System.out.println("Your resources are not divisible by two: "
                    + "-3 happiness.");
        }
    }

    public static boolean settle() {
        Scanner s = new Scanner(System.in);
        if ((numCities < 5) && (gold >= 15.5)) {
            numCities++;
            System.out.println("What would you like to name your new city?");
            cities[numCities - 1] = s.nextLine();
            gold -= 15.5;
            System.out.println("New city \"" + cities[numCities - 1]
                    + "\" created.");
            return true;     //boolean that the move was completed successfully
        } else if (numCities == 5) {
            System.out.println("You already have the maximum number "
                    + "of cities.");
        } else if (gold < 15.5) {
            System.out.println("Insufficient gold.");
        }
        return false;
    }

    public static boolean demolish() {
        if (numCities == 1) {
            System.out.println("You only have one city, "
                    + "and you can't have less than one.");
            return false;
        } else {
            boolean demolished = false;
            while (!demolished) {
                System.out.println("Please enter the name of the city "
                        + "you would like to demolish:");
                cityString();  //generate the cityString
                System.out.println(cityString);
                Scanner s = new Scanner(System.in);
                String cityChoice = s.nextLine().toLowerCase();
                if (cityChoice.compareTo(cities[0].toLowerCase()) == 0) {
                    for (int i = 0; i < numCities - 1; i++) {
                        cities[i] = cities[i + 1];
                    }
                    cities[numCities - 1] = null;
                    demolished = true;
                    numCities -= 1;
                    resources += 1.5;
                    System.out.println("Resources + 1.5");
                } else if (cityChoice.compareTo(cities[1].toLowerCase()) == 0) {
                    for (int i = 1; i < numCities - 1; i++) {
                        cities[i] = cities[i + 1];
                    }
                    cities[numCities - 1] = null;
                    demolished = true;
                    numCities -= 1;
                    resources += 1.5;
                    System.out.println("Resources + 1.5");
                } else if ((numCities > 2)
                        && (cityChoice.compareTo(cities[2].toLowerCase())
                        == 0)) {
                    for (int i = 2; i < numCities - 1; i++)  {
                        cities[i] = cities[i + 1];
                    }
                    cities[numCities - 1] = null;
                    demolished = true;
                    numCities -= 1;
                    resources += 1.5;
                    System.out.println("Resources + 1.5");
                } else if ((numCities > 3)
                        && (cityChoice.compareTo(cities[3].toLowerCase())
                        == 0)) {
                    for (int i = 3; i < numCities - 1; i++)  {
                        cities[i] = cities[i + 1];
                    }
                    cities[numCities - 1] = null;
                    demolished = true;
                    numCities -= 1;
                    resources += 1.5;
                    System.out.println("Resources + 1.5");
                } else if ((numCities > 4)
                        && (cityChoice.compareTo(cities[4].toLowerCase())
                        == 0)) {
                    for (int i = 4; i < numCities - 1; i++)  {
                        cities[i] = cities[i + 1];
                    }
                    cities[numCities - 1] = null;
                    demolished = true;
                    numCities -= 1;
                    resources += 1.5;
                    System.out.println("Resources + 1.5");
                } else {
                    System.out.println("Please enter a valid city name.");
                }
            }
            cityString();
            System.out.println("New list of cities: " + cityString);
            return true; //the action was completed.
        }
    }

    public static boolean buildMilitia() {
        if (gold < 5) {
            System.out.println("Insufficient gold");
        } else if (resources < 3) {
            System.out.println("Insufficient resources");
        } else {
            gold -= 5;
            resources -= 3;
            military += 1;
            System.out.println("Action Completed: +1 Military.");
            return true;
        }
        return false;
    }

    public static boolean research() {
        if (gold < 50) {
            System.out.println("Insufficient gold.");
        } else if (resources < 2) {
            System.out.println("Insufficient resources.");
        } else {
            gold -= 50;
            resources -= 2;
            tech += 1;
            return true;
        }
        return false;
    }

    public static boolean attack() {
        if (military < 6) {
            System.out.println("Insufficient military units.");
        } else {
            military -= 6;
            happiness -= 3;
            gold += 10;
            numAttacks++;
            return true;
        }
        return false;
    }

    public static boolean stillPlaying() {
        return ((tech < 20) && (numAttacks < 10));
    }
}
