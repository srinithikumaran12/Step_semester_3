package constructor.assigment_problems;

/**
 * A3: final Method - Parking Overstay Fine Calculator
 * Scenario: Campus parking issues a fine for every vehicle that overstays
 * its allotted free time; the fine formula must behave identically for
 * every ticket, so both methods are final.
 */
public class ParkingTicket {

    String vehicleNo;
    double ratePerMinute;

    public ParkingTicket(String vehicleNo, double ratePerMinute) {
        this.vehicleNo = vehicleNo;
        this.ratePerMinute = ratePerMinute;
    }

    final double calculateFine(int overstayMinutes) {
        return overstayMinutes * ratePerMinute;
    }

    final void printReceipt(int overstayMinutes) {
        double fine = calculateFine(overstayMinutes);
        System.out.println(vehicleNo + " - Fine: Rs " + fine);
    }

    public static void main(String[] args) {
        String[] vehicleNos = {"TN09AB1234", "TN22CD5678", "TN09EF9012", "TN10GH3456"};
        double[] ratePerMinute = {2, 2, 3, 2};
        int[] overstayMinutes = {15, 0, -5, 8};

        for (int i = 0; i < vehicleNos.length; i++) {
            ParkingTicket ticket = new ParkingTicket(vehicleNos[i], ratePerMinute[i]);

            if (overstayMinutes[i] > 0) {
                ticket.printReceipt(overstayMinutes[i]);
            } else {
                System.out.println(vehicleNos[i] + " - No fine, within allotted time");
            }
        }
        // TN09AB1234 - Fine: Rs 30.0
        // TN22CD5678 - No fine, within allotted time
        // TN09EF9012 - No fine, within allotted time
        // TN10GH3456 - Fine: Rs 16.0
    }
}