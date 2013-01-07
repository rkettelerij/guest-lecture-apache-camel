package nl.avisi.guestlecture.camel;

public class Main {

    public static void main(String[] args) throws Exception {
        org.apache.camel.main.Main main = new org.apache.camel.main.Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new FileRoute());
        main.run();
    }
}
