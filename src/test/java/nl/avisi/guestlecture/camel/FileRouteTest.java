package nl.avisi.guestlecture.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FileRouteTest extends CamelTestSupport {

    @Override
    public String isMockEndpointsAndSkip() {
        return "file:outbox*";
    }

    @Test
    public void testOutboxContainsFile() throws Exception {
        getMockEndpoint("mock:file:outbox/").expectedMessageCount(1);
        getMockEndpoint("mock:file:outbox/").expectedBodiesReceived("Goodbye World");

        template.sendBody("direct:sendfile", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testOutboxKlaasContainsFile() throws Exception {
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
