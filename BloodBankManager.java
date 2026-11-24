
import java.util.*;

class Donor {
    int id;
    String name;
    String bloodGroup;
    String phone;
    Donor next;

    Donor(int id, String name, String bloodGroup, String phone) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.next = null;
    }
}

class BloodBankManager {

    // Linked List
    static Donor head = null;

    // Hash Table
    static HashMap<String, Integer> inventory = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    // Add Donor
    static void addDonor() {

        System.out.print("Enter Donor ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Blood Group: ");
        String group = sc.nextLine().toUpperCase();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        String[] groups = {
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
        };

        if (!Arrays.asList(groups).contains(group)) {
            System.out.println("Invalid Blood Group!");
            return;
        }

        Donor newDonor = new Donor(id, name, group, phone);

        if (head == null) {
            head = newDonor;
        } else {
            Donor temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newDonor;
        }

        // Update Hash Table
        inventory.put(group, inventory.getOrDefault(group, 0) + 1);

        System.out.println("Donor Added Successfully!");
    }

    // Display Donors
    static void displayDonors() {

        if (head == null) {
            System.out.println("No Donors Available!");
            return;
        }

        Donor temp = head;

        System.out.println("\n----- DONOR LIST -----");

        while (temp != null) {
            System.out.println(
                "ID: " + temp.id +
                " | Name: " + temp.name +
                " | Blood Group: " + temp.bloodGroup +
                " | Phone: " + temp.phone
            );

            temp = temp.next;
        }
    }

    // Sorting
    static void sortDonors() {

        if (head == null) {
            System.out.println("No Donors Available!");
            return;
        }

        ArrayList<Donor> list = new ArrayList<>();

        Donor temp = head;

        while (temp != null) {
            list.add(temp);
            temp = temp.next;
        }

        list.sort((a, b) ->
            a.name.compareToIgnoreCase(b.name)
        );

        head = null;

        for (Donor d : list) {
            d.next = null;

            if (head == null) {
                head = d;
            } else {
                Donor current = head;

                while (current.next != null) {
                    current = current.next;
                }

                current.next = d;
            }
        }

        System.out.println("Donors Sorted Alphabetically!");
    }

    // Binary Search
    static void searchDonor() {

        if (head == null) {
            System.out.println("No Donors Available!");
            return;
        }

        // Convert Linked List into Array
        ArrayList<Donor> list = new ArrayList<>();

        Donor temp = head;

        while (temp != null) {
            list.add(temp);
            temp = temp.next;
        }

        // Sort before Binary Search
        list.sort((a, b) ->
            a.name.compareToIgnoreCase(b.name)
        );

        System.out.print("Enter Donor Name to Search: ");
        String key = sc.nextLine();

        int low = 0;
        int high = list.size() - 1;
        boolean found = false;

        while (low <= high) {

            int mid = (low + high) / 2;

            int result = list.get(mid).name.compareToIgnoreCase(key);

            if (result == 0) {

                Donor d = list.get(mid);

                System.out.println("\nDonor Found!");
                System.out.println("ID: " + d.id);
                System.out.println("Name: " + d.name);
                System.out.println("Blood Group: " + d.bloodGroup);
                System.out.println("Phone: " + d.phone);

                found = true;
                break;

            } else if (result < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (!found) {
            System.out.println("Donor Not Found!");
        }
    }

    // Blood Inventory
    static void displayInventory() {

        System.out.println("\n----- BLOOD INVENTORY -----");

        String[] groups = {
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
        };

        for (String group : groups) {
            System.out.println(
                group + " : " +
                inventory.getOrDefault(group, 0) + " Units"
            );
        }
    }

    // Request Blood
    static void requestBlood() {

        System.out.print("Enter Required Blood Group: ");
        String group = sc.nextLine().toUpperCase();

        int available = inventory.getOrDefault(group, 0);

        if (available > 0) {

            inventory.put(group, available - 1);

            System.out.println("Blood Request Successful!");
            System.out.println("Remaining Units: " + (available - 1));

        } else {
            System.out.println("Sorry! Blood Not Available.");
        }
    }

    // Main Method
    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println("      BLOOD BANK MANAGER");
            System.out.println("==============================");

            System.out.println("1. Add Donor");
            System.out.println("2. Display Donors");
            System.out.println("3. Sort Donors");
            System.out.println("4. Search Donor (Binary Search)");
            System.out.println("5. Display Blood Inventory");
            System.out.println("6. Request Blood");
            System.out.println("7. Exit");

            System.out.print("\nEnter Your Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addDonor();
                    break;

                case 2:
                    displayDonors();
                    break;

                case 3:
                    sortDonors();
                    break;

                case 4:
                    searchDonor();
                    break;

                case 5:
                    displayInventory();
                    break;

                case 6:
                    requestBlood();
                    break;

                case 7:
                    System.out.println("Thank You for Using Blood Bank Manager!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}

