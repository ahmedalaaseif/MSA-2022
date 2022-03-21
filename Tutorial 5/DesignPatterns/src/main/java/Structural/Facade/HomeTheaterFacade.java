package Structural.Facade;

public class HomeTheaterFacade {
    Amplifier amplifier;
    Tuner tuner;
    DVDPlayer dvdPlayer;
    ProjectorScreen projectorScreen;
    Lights lights;


    public HomeTheaterFacade() {
        this.amplifier = new Amplifier();
        this.tuner = new Tuner();
        this.dvdPlayer = new DVDPlayer();
        this.projectorScreen = new ProjectorScreen();
        this.lights = new Lights();
    }

    public HomeTheaterFacade(Amplifier amplifier, Tuner tuner, DVDPlayer dvdPlayer, ProjectorScreen projectorScreen, Lights lights) {
        this.amplifier = amplifier;
        this.tuner = tuner;
        this.dvdPlayer = dvdPlayer;
        this.projectorScreen = projectorScreen;
        this.lights = lights;
    }


    public void watchMovie(String movie) {
        System.out.println("Watching " + movie);
        System.out.println("Initializing Theater");
        amplifier.on();
        lights.on();
        dvdPlayer.on();
        projectorScreen.on();
        tuner.on();
    }

    public void endMovie() {
        System.out.println("Shutting Down Theater");
        amplifier.off();
        lights.off();
        dvdPlayer.off();
        projectorScreen.off();
        tuner.off();
    }




}
