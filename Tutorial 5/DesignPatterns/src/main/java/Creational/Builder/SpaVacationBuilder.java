package Creational.Builder;

public class SpaVacationBuilder extends VacationBuilder{

    @Override
    public void buildDay() {

    }


    @Override
    public void addHotel(Hotel hotel) {
        vacation.h= hotel;
    }

    @Override
    public void addTickets(Tickets ticket) {
        vacation.t= ticket;
    }

    @Override
    public void addReservation(Reservation reservation) {
        vacation.r = reservation;
    }

    @Override
    public Vacation getVacation() {
        return vacation;
    }
}
