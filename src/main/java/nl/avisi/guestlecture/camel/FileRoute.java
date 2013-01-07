package nl.avisi.guestlecture.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://inbox/?move=../processed").from("direct:sendfile")
            .log(LoggingLevel.INFO, "Processing file ${headers.CamelFileName}")
            .transform(body().regexReplaceAll("V", "W"))
            .to("file://outbox/");
    }
}