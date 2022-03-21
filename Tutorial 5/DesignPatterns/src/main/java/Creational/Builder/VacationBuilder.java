package Creational.Builder;

public abstract class VacationBuilder {
    protected Vacation vacation = new Vacation();
    public abstract void buildDay();
    public abstract void addHotel(Hotel hotel);
    public abstract void addTickets(Tickets ticket);
    public abstract void addReservation(Reservation reservation);
    public abstract Vacation getVacation();
}
