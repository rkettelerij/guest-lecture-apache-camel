package nl.avisi.guestlecture.camel;

import nl.avisi.guestlecture.camel.FileRoute;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FileRouteTest extends CamelTestSupport {

    @Test
    public void testOutboxContainsFile() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                mockEndpointsAndSkip("file:outbox/");
            }
        });

        getMockEndpoint("mock:file:outbox/").expectedMessageCount(1);
        getMockEndpoint("mock:file:outbox/").expectedBodiesReceived("Goodbye World");

        template.sendBody("direct:sendfile", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testOutboxKlaasContainsFile() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                mockEndpointsAndSkip("file:outbox/", "file:outbox-klaas/");
            }
        });

        getMockEndpoint("mock:file:outbox/").expectedMessageCount(2); // aap + noot
        getMockEndpoint("mock:file:outbox-klaas/").expectedMessageCount(1); // klaas

        template.sendBody("direct:sendfile", "Aap\nKlaas\nNoot\n");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileRoute();
    }
}
