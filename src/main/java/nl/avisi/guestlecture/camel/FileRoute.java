package nl.avisi.guestlecture.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://inbox/?move=../processed").from("direct:sendfile").from("jetty:http://0.0.0.0:9000/file")
            .log(LoggingLevel.INFO, "Processing file ${headers.CamelFileName}")
            .transform(body().regexReplaceAll("Hello", "Goodbye"))
            .split(body(String.class).tokenize("\n"))
            .log(LoggingLevel.INFO, "Creating separate file ${body}.txt")
            .choice()
                .when(body().contains("Mies"))
                    .to("file://outbox-mies/")
                .when(body().contains("Klaas"))
                    .to("file://outbox-klaas/")
                .otherwise()
                    .to("file://outbox/")
            .end();
    }
}
