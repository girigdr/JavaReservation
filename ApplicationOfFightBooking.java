import java.util.* ;

public class ApplicationOfFightBooking {
    public static void main(String[] args) {
        new ClassMain() ;
        PassengersVtickets T = new PassengersVtickets() ;
        int ii = 0 ;
        while( true ) {
            try {
                Scanner sc = new Scanner( System.in ) ;
                System.out.println("Enter 1 ----- > book Tickets ");
                System.out.println("Enter 2 ----- > cancel Tickets ");
                System.out.println("Enter 3 ----- > booked Passenger details ");
                System.out.println("Enter 4 ----- > waiting List Passeneger details ");
                int i = sc.nextInt() ;
                if( i == 1 ) {
                    System.out.println(" Enter name ");
                    String name = sc.next() ;
                    System.out.println(" enter age ");
                    int age = sc.nextInt() ;
                    int passangerId = ++ii;
                    Pasanger passenger = new Pasanger( name , age , passangerId  ) ;
                    PassengersVtickets.getTicket( passenger ) ;
                }
                else if( i == 2 ) {
                    System.out.println(" enter id to cancel ");
                    int passengerId = sc.nextInt();
                    new Cancellation( passengerId ).cancel() ;
                }
                else if( i == 3 ) {
                    System.out.println(" <-------------------------- Passengers Details ------------------------------> ");
                    T.travellerDetails()  ;
                }
                else if( i == 4 ) {
                    System.out.println(" <-------------------------- waiting List Passengers Details ------------------------------> ");
                    T.waitingListPassengerDetails()  ;
                }
                else System.out.println( " Invalid option -------- > TRY AGAIN ");
            }
            catch ( Exception e ) {
                break;
            }
        }
    }
}
class Cancellation {
    int passengerId ;
    Cancellation( int passengerId ) {
        this.passengerId = passengerId ;
    }
    public void cancel() {
        Map < Pasanger , Tickets > map = PassengersVtickets.PassengerAndTickets ;
        boolean b = false ;
        for( Pasanger i : map.keySet() ) {
            if( i.getId() == passengerId ) {
                Tickets t = map.get( i ) ;
                map.remove( i ) ;
                ClassMain.tickets.addFirst( t );
                b = true ;
                if( PassengersVtickets.waitingPassengerAndTickets.isEmpty() )  break ;
                moveWaitingToTravel() ;
                return ;
            }
        }
        if( !b ) System.out.println(" ----- > INVALID DATA ");
    }
    private void moveWaitingToTravel() {
        Map < Pasanger , Tickets > map = PassengersVtickets.waitingPassengerAndTickets ;
        int z = Integer.MAX_VALUE ;
        Pasanger p = null ;
        for( Pasanger i : map.keySet() ) {
            int x = i.getId() ;
            if( x < z ) {
                p = i ;
                z = x ;
            }
        }
        map.remove( p ) ;
        PassengersVtickets.getTicket( p ) ;
    }
}
class PassengersVtickets {
    static Map < Pasanger , Tickets > PassengerAndTickets ;
    static Map < Pasanger , Tickets > waitingPassengerAndTickets ;
    PassengersVtickets() {
        PassengerAndTickets = new HashMap<>() ;
        waitingPassengerAndTickets = new HashMap<>() ;
    }
    public static void getTicket( Pasanger P ) {
        System.out.println();
        List < Tickets > l = ClassMain.getList( ) ;
        if( !l.isEmpty() ) PassengerAndTickets.put( P , provideTickets( P ) ) ;
        else waitingPassengerAndTickets.put( P ,moveWaitingList() ) ;
    }
    private static Tickets provideTickets( Pasanger P) {
        Tickets p =  ClassMain.giveTickets() ;
        System.out.print(  P.getId()  +" gets a ticket\n " + p.toString() );
        return p ;

    }
    private static Tickets moveWaitingList() {
        Tickets t = ClassMain.moveWaitingList() ;
        System.out.println( " moved to waiting list if any cancellation we will call you and provide an ticket and your waitingList id is " + t.id );
        return t ;
    }
    public void travellerDetails() {
        for ( Pasanger i : PassengerAndTickets.keySet() ) {
            System.out.println( i.toString() + " ------- " + PassengerAndTickets.get( i ).toString() );
        }
    }
    public void waitingListPassengerDetails() {
        for ( Pasanger i : waitingPassengerAndTickets.keySet() ) {
            System.out.println( i.toString() + " ------- " + waitingPassengerAndTickets.get( i ).toString() );
        }
    }
}
class Tickets {
    int id ;
    boolean booked ;
    char seats ;
    int capartment ;
    @Override
    public String toString() {
        return "Tickets{" +
                "id=" + id +
                ", booked=" + booked +
                ", seats=" + seats +
                ", capartment=" + capartment +
                '}';
    }
    Tickets(int id , boolean booked , char seats , int capartment ) {
        this.id = id ;
        this.booked = booked ;
        this.seats = seats ;
        this.capartment = capartment ;
    }
    Tickets( int id ) {
        this.id = id ;
    }
    public int getId() {
        return id;
    }
    public boolean isBooked() {
        return booked;
    }
    public char getSeats() {
        return seats;
    }
    public int getCapartment() {
        return capartment;
    }
}
class TotalTickets {
    int totalNumberOfTickets ;
    int waitingTickets ;
    TotalTickets( int totalNumberOfTickets , int waitingTickets ) {
        this.totalNumberOfTickets = totalNumberOfTickets ;
        this.waitingTickets = waitingTickets ;
    }
    public List < Tickets > TicketsList () {
        List < Tickets > Tickets = new ArrayList<>() ;
        int seatSelection = 0 ;
        int capartment = 0 ;
        for( int i = 0 ; i < totalNumberOfTickets ; i++ ) {
            char seat ;
            if( seatSelection % 3 == 0 ) seat = 'U' ;
            else if( seatSelection % 2 == 0 ) seat = 'M' ;
            else seat = 'L' ;
            seatSelection++ ;
            if( capartment % 6 == 0 ) capartment++ ;
            Tickets.add( new Tickets( seatSelection , false , seat , capartment + 1 ) ) ;
        }
        return Tickets ;
    }
    public List < Tickets > waitingList () {
        List < Tickets > waitingTicket = new ArrayList<>() ;
        for( int i = 0 ; i < waitingTickets ; i++ ) {
            waitingTicket.add( new Tickets( i + 1 ) ) ;
        }
        return waitingTicket ;
    }
}
class Pasanger {
    String name ;
    int age ;
    int id ;
    public Pasanger(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }
    public int getId() {
        return id ;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    @Override
    public String toString() {
        return "Pasanger{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
class ClassMain {
    static int max = 4 ;
    static int maxy = 2 ;
    static TotalTickets totalTickets = new TotalTickets ( max , maxy ) ;
    static List < Tickets > tickets = totalTickets.TicketsList() ;
    static List < Tickets > waitingTickets = totalTickets.waitingList() ;
    public static Tickets giveTickets() {
        return tickets.removeFirst() ;
    }
    public static Tickets moveWaitingList() {
        return waitingTickets.removeFirst() ;

    }
    ClassMain() {
    }
    public static List<Tickets> getList() {
        return tickets ;
    }
}