package Creational.Builder;

public class Main {
    public static void main(String[] args) {
        VacationDirector director = new VacationDirector();
        VacationBuilder spaBuilder = new SpaVacationBuilder();
        VacationBuilder sceneryBuilder = new SceneryVacationBuilder();

        Vacation vacation  = director.createVacation(spaBuilder);

    }
}
