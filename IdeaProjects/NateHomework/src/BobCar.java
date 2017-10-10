import java.util.Scanner;

public class BobCar {

    public static void main(String[] args) {
        int flatRate = 0;
        int userCarChoice = 0;
        int rentalDays = 0;
        int clubMember = 0;
        int platPkg = 0;
        int clubMemDiscount = 0;
        int baseTotal = 0;
        double platPkgMarkup = 0.0;
        double grandTotal = 0.0;
        String userChoice = " ";

        Scanner in = new Scanner(System.in);

        System.out.println("Available cars: 1 for Economy, 2 for Compact, 3 for Standard");
        System.out.print("Please choose the rental car: ");
        userCarChoice = in.nextInt();
        System.out.print("Please enter the number of rental days: ");
        rentalDays = in.nextInt();
        System.out.print("Club member? 1 for yes, 0 for no: ");
        clubMember = in.nextInt();

        if (clubMember == 1) {
            System.out.print("Platinum Executive Package? 1 for yes, 0 for no: ");
            platPkg = in.nextInt();
        }
        switch (userCarChoice) {
            case 1:
                userChoice = "Economy";
                flatRate = 35;
                break;
            case 2:
                userChoice = "Compact";
                flatRate = 45;
                break;
            case 3:
                userChoice = "Standard";
                flatRate = 95;
                break;
            default:
                break;
        }

        baseTotal = rentalDays * flatRate;
        System.out.print("\nBase: " + rentalDays + " days for a " + userChoice + " @ $" + flatRate + " per day:\t$ ");
        System.out.println(baseTotal);


        if (clubMember == 1) {
            clubMemDiscount = (int)((rentalDays / 7) * flatRate);
            grandTotal = baseTotal - clubMemDiscount;
            System.out.print("Club Member Discount:\t\t\t      - $ ");
            System.out.println(clubMemDiscount);
            if (platPkg == 1) {
                platPkgMarkup = baseTotal * 0.15;
                grandTotal = grandTotal + platPkgMarkup;
                System.out.print("Platinum Executive Package:\t\t      + $ ");
                if (platPkgMarkup - (int)(platPkgMarkup) > 0)
                    System.out.println(platPkgMarkup);
                else
                    System.out.println((int)(platPkgMarkup));

            }
        }
        else
            grandTotal = baseTotal;
        System.out.print("\nTotal Estimate for Rental:\t\t\t$ ");
        if (grandTotal - (int)(grandTotal) > 0)
            System.out.println(grandTotal);
        else
            System.out.println((int)(grandTotal));

    }
}