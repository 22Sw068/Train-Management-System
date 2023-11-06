import java.util.ArrayList;
import java.util.Scanner;

abstract class Vehicle {
    public abstract void boardPassenger();

    public abstract void depart();

    public abstract void displayDetails();
}

class TicketTrain {
    private String passengerName;
    private int seatNumber;

    public TicketTrain(String passengerName, int seatNumber) {
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public static void Ticket_Menu() {
        System.out.println(" |>--------------- Ticket Menu --------------------<|");
        System.out.println(" |          1: Buy Ticket                           |");
        System.out.println(" |          2: Ticket Purchased Details             |");
        System.out.println(" |          3: Remaining Seats                      |");
        System.out.println(" |          4: Back to Train Menu                   |");
        System.out.println("|>------------------------------------------------<|");
    }
}

class Train extends Vehicle {
    private String trainName;
    private String trainNumber;
    private String origin;
    private String destination;
    private int totalNumberOfSeats;
    private int currentPassengers;
    private ArrayList<TicketTrain> ticketList = new ArrayList<>();

    public Train(String trainName, String trainNumber, String origin, String destination, int totalNumberOfSeats) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.origin = origin;
        this.destination = destination;
        this.totalNumberOfSeats = totalNumberOfSeats;
        this.currentPassengers = 0;
    }

    public void displayDetails() {
        String trainInfo = " Train Name   => " + getTrainName() + " \n" +
                " Train Number => " + getTrainNumber() + "\n" +
                " Travel from  => " + getOrigin() + "\t to => " + getDestination() + "\n" +
                " Total  Seats => " + getTotalNumberOfSeats();
        System.out.println(trainInfo);
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalNumberOfSeats() {
        return totalNumberOfSeats;
    }

    public void showAvailableSeats() {
        System.out.println("Available Seats: " + (totalNumberOfSeats - currentPassengers));
    }

    public static void Train_Menu() {
        System.out.println("||>---------------TRAIN MENU  ---------------<||");
        System.out.println("|------------- 1: Add New Train ---------------|");
        System.out.println("|------------- 2: Remove New Train ------------|");
        System.out.println("|------------- 3: All Train's Details ---------|");
        System.out.println("|------------- *: Back to Main ----------------|");
        System.out.println("||>------------------------------------------<||");
    }

    public void buyTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of tickets you want to buy: ");
        int numberOfTickets = scanner.nextInt();

        for (int i = 0; i < numberOfTickets; i++) {
            if (currentPassengers < totalNumberOfSeats) {
                showAvailableSeats();
                System.out.print("Enter the seat number for ticket " + (i + 1) + ": ");
                int seatNumber = scanner.nextInt();
                if (seatNumber > 0 && seatNumber <= totalNumberOfSeats && !isSeatOccupied(seatNumber)) {
                    scanner.nextLine();
                    System.out.print("Enter passenger name for ticket " + (i + 1) + ": ");
                    String passengerName = scanner.nextLine();

                    TicketTrain ticket = new TicketTrain(passengerName, seatNumber);
                    ticketList.add(ticket);
                    currentPassengers++;

                    System.out.println("Ticket " + (i + 1) + " purchased successfully!");
                } else {
                    System.out.println("Invalid seat number or seat already occupied. Please try again.");
                    i--;
                }
            } else {
                System.out.println("Train is full. No more tickets available.");
                break;
            }
        }
    }

    private boolean isSeatOccupied(int seatNumber) {
        for (TicketTrain ticket : ticketList) {
            if (ticket.getSeatNumber() == seatNumber) {
                return true;
            }
        }
        return false;
    }

    public void displayTickets() {
        System.out.println("Passenger List:");
        for (TicketTrain ticket : ticketList) {
            System.out.println("Seat " + ticket.getSeatNumber() + ": " + ticket.getPassengerName());
        }
    }

    @Override
    public void boardPassenger() {
        if (currentPassengers < totalNumberOfSeats) {
            currentPassengers++;
            System.out.println("Passenger Boarded. \t  =>  Number Of Current Passengers: " + currentPassengers);
        } else
            System.out.println("Train is full!!!");
    }

    @Override
    public void depart() {
        System.out.println("Train departing from " + origin + " to " + destination + " with " + currentPassengers + " passengers.");
    }

    public void displayPurchasedTickets() {
        if (ticketList.isEmpty()) {
            System.out.println("No tickets purchased yet.");
        } else {
            System.out.println("Purchased Tickets:");
            for (TicketTrain ticket : ticketList) {
                System.out.println("Train: " + getTrainName() +
                        "\tSeat Number: " + ticket.getSeatNumber() +
                        "\tPassenger Name: " + ticket.getPassengerName());
            }
        }
    }

    public void addNewTrain(ArrayList<Train> trainList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(" Enter Train Name :\t");
        String trainName = scanner.nextLine();
        System.out.print(" Enter Train Number :\t");
        String trainNumber = scanner.next();
        scanner.nextLine();
        System.out.print(" Enter Train Origin :\t");
        String trainOrigin = scanner.nextLine();
        System.out.print(" Enter Train Destination : \t");
        String trainDest = scanner.nextLine();
        System.out.print(" Enter Total Seats (Train) :\t ");
        int numberOfSeats = scanner.nextInt();
        Train newTrain = new Train(trainName, trainNumber, trainOrigin, trainDest, numberOfSeats);
        trainList.add(newTrain);
        System.out.println("Train added successfully!");
    }

    public void removeTrain(ArrayList<Train> trainList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Train Name:\t");
        String removeName = scanner.nextLine();
        System.out.print("Enter Train Number:\t");
        String removeNumber = scanner.next();
        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < trainList.size(); i++) {
            Train train = trainList.get(i);
            if (train.getTrainName().equalsIgnoreCase(removeName) &&
                    train.getTrainNumber().equals(removeNumber)) {
                trainList.remove(i);
                found = true;
                System.out.println("Train removed successfully!");
                break;
            }
        }

        if (!found) {
            System.out.println("Train not found!");
        }
    }


    public void showRemainingSeats() {
        System.out.println("Remaining Available Seats for " + getTrainName() + ": " + (totalNumberOfSeats - currentPassengers));
    }
}

public class Train_Management_System {

    public static void home() {
        System.out.println("|-------|> Home <|-------|");
        System.out.println("|        1: Train        |");
        System.out.println("|        2: Ticket       |");
        System.out.println("|        3: Exit         |");
        System.out.println("|------------------------|");
        System.out.print(" Enter Any Choice : ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Train> trainList = new ArrayList<>();

        Train selectedTrain = null;

        System.out.println(" |      Created by   Muhammad Khan   => 22SW068     |");

        Train train1 = new Train("IQBAL TRAIN", "12TR450", "Karachi", "Lahore", 50);
        Train train2 = new Train("Akbar Express", "23UP/24DN", "Quetta Railway Station", "Lahore Junction", 100);
        Train train3 = new Train("Bahauddin Zakaria Express", "25UP/26DN", "Karachi City railway station", "Multan Cantonment railway station", 120);
        Train train4 = new Train("Green Line Express", "5UP/6DN", "Karachi Cantonment railway station", "Margalla railway station", 150);
        Train train5 = new Train("Koh i Noor Express", "330UP/345KH", "Lahore Junction railway station", "Narowal Junction railway station", 200);
        Train train6 = new Train("Mohenjo-daro Express", "213UP/214DN", "Rohri Junction railway station", "Kotri Junction railway station", 170);

        trainList.add(train1);
        trainList.add(train2);
        trainList.add(train3);
        trainList.add(train4);
        trainList.add(train5);
        trainList.add(train6);

        int counter = 0;

        do {
            home();
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Train.Train_Menu();
                    System.out.println("|>------------------------------------------------<|");
                    System.out.print(" Enter Your Choice : ");
                    int anyChoice = sc.nextInt();
                    switch (anyChoice) {
                        case 1:
                            System.out.print(" Do You Want to Add a New Train (1 => yes/0 => no): ");
                            int ch = sc.nextInt();
                            if (ch == 1) {
                                train1.addNewTrain(trainList);
                                System.out.println("|>------------------------------------------------<|");
                            } else {
                                System.out.println(" Train not Added!!");
                                System.out.println("|>------------------------------------------------<|");
                            }
                            break;
                        case 2:
                            train1.removeTrain(trainList);
                            System.out.println("|>------------------------------------------------<|");
                            break;
                        case 3:
                            System.out.println("|>--------------All Train Details:-------------------<|");
                            for (Train train : trainList) {
                                train.displayDetails();
                                System.out.println("|>------------------------------------------------<|");
                            }
                            break;

                        default:
                            System.out.println("Returning to main menu.");
                            break;
                    }
                    break;
                case 2:
                    TicketTrain.Ticket_Menu();
                    System.out.println(" Enter your choice (1-4)");
                    int chzc = sc.nextInt();
                    switch (chzc) {
                        case 1:
                            System.out.print(" Do you want to Buy a ticket (1/0)  :   ");
                            int buyTicket = sc.nextInt();
                            sc.nextLine();

                            if (buyTicket == 1) {
                                System.out.println("Available Trains:");
                                for (Train train : trainList) {
                                    System.out.println("=|>  " + train.getTrainName() +
                                            "   _   " + train.getOrigin() + "   to  " + train.getDestination());
                                }
                                System.out.print("Enter the name of the train to buy a ticket: ");
                                String selectedTrainName = sc.nextLine();

                                selectedTrain = null;
                                for (Train train : trainList) {
                                    if (train.getTrainName().equalsIgnoreCase(selectedTrainName)) {
                                        selectedTrain = train;
                                        break;
                                    }
                                }

                                if (selectedTrain != null) {
                                    selectedTrain.buyTicket();
                                } else {
                                    System.out.println("Invalid train name. Please try again.");
                                }
                            }
                            System.out.println("|>------------------------------------------------<|");
                            break;
                        case 2:
                            for (Train train : trainList) {
                                train.displayPurchasedTickets();
                                System.out.println("|>------------------------------------------------<|");
                            }
                            break;
                        case 3:
                            System.out.println("Enter the name of the train to check remaining seats: ");
                            String selectedTrainName = sc.next();

                            selectedTrain = null;
                            for (Train train : trainList) {
                                if (train.getTrainName().equalsIgnoreCase(selectedTrainName)) {
                                    selectedTrain = train;
                                    break;
                                }
                            }

                            if (selectedTrain != null) {
                                selectedTrain.showRemainingSeats();
                            } else {
                                System.out.println("Invalid train name. Please try again.");
                            }
                            break;

                        case 4:
                            Train.Train_Menu();
                            System.out.println("|>------------------------------------------------<|");
                            System.out.print(" Enter Your Choice : ");
                            int trainMenuChoice = sc.nextInt();
                            switch (trainMenuChoice) {
                                case 1:
                                    System.out.print(" Do You Want to Add a New Train (1 => yes/0 => no): ");
                                    int ch = sc.nextInt();
                                    if (ch == 1) {
                                        train1.addNewTrain(trainList);
                                        System.out.println("|>------------------------------------------------<|");
                                    } else {
                                        System.out.println(" Train not Added!!");
                                        System.out.println("|>------------------------------------------------<|");
                                    }
                                    break;
                                case 2:
                                    train1.removeTrain(trainList);
                                    System.out.println(" Train Removed Successfully.");
                                    System.out.println("|>------------------------------------------------<|");
                                    break;
                                case 3:
                                    System.out.println("All Train Details:");
                                    for (Train train : trainList) {
                                        train.displayDetails();
                                        System.out.println("|>------------------------------------------------<|");
                                    }
                                    break;
                                case 4:

                                    System.out.println("|>------------------------------------------------<|");
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice. Returning to main menu.");
                            home();
                            System.out.println("|>------------------------------------------------<|");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Returning to main menu.");
                    home();
                    System.out.println("|>------------------------------------------------<|");
                    break;
            }
        } while (counter != 3);

        if (selectedTrain != null) {
            selectedTrain.displayTickets();
            selectedTrain.boardPassenger();
            selectedTrain.depart();
        }
    }
}