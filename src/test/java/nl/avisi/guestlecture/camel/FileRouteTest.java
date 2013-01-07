package nl.avisi.guestlecture.camel;

import nl.avisi.guestlecture.camel.FileRoute;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FileRouteTest extends CamelTestSupport {

    @Test
    public void testConfigure() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                mockEndpointsAndSkip("file:outbox/");
            }
        });

        getMockEndpoint("mock:file:outbox/").expectedMessageCount(1);
        getMockEndpoint("mock:file:outbox/").expectedBodiesReceived("Hello Wis");

        template.sendBody("direct:sendfile", "Hello Vis");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileRoute();
    }
}
