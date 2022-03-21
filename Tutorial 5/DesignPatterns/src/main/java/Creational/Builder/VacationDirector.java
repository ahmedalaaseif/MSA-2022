package Creational.Builder;

public class VacationDirector {
    public Vacation createVacation(VacationBuilder builder){
        builder.buildDay();
        builder.addHotel(new Hotel());
        builder.addReservation(new Reservation());
        builder.addTickets(new Tickets());
        return builder.getVacation();
    }
}
