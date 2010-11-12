package org.mwc.asset.comms.restlet.host;

import java.io.File;

import org.mwc.asset.comms.restlet.data.DemandedStatusServerResource;
import org.mwc.asset.comms.restlet.data.ParticipantServerResource;
import org.mwc.asset.comms.restlet.data.ScenarioServerResource;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class HostServer extends Application {

    /**
     * When launched as a standalone application.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getClients().add(Protocol.FILE);
        component.getServers().add(Protocol.HTTP, 8080);
        component.getDefaultHost().attach(new HostServer());
        component.start();
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        getConnectorService().getClientProtocols().add(Protocol.FILE);

        // Serve the files generated by the GWT compilation step.
        File warDir = new File("");
        if (!"war".equals(warDir.getName())) {
            warDir = new File(warDir, "war/");
        }

        Directory dir = new Directory(getContext(), LocalReference
                .createFileReference(warDir));
        router.attachDefault(dir);
        router.attach("/scenario", ScenariosHandler.class);
        router.attach("/scenario/{scenario}", ScenarioServerResource.class);
        router.attach("/scenario/{scenario}/participant", ParticipantsHandler.class);
        router.attach("/scenario/{scenario}/participant/{participant}", ParticipantServerResource.class);
        router.attach("/scenario/{scenario}/participant/{participant}/state", StatusHandler.class);
        return router;
    }

}
