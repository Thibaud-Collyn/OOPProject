package be.ugent.flash.beheersinterface;

import be.ugent.flash.viewer.viewer_factories.*;

import java.util.Map;

public class PreviewPopUp {
    private final Map<String, ControllerFactory> typeFactories = Map.of("mcs", new MCSControllerFactory(),
                                                                        "mcc", new MCCControllerFactory(),
                                                                        "mci", new MCIControllerFactory(),
                                                                        "mr", new MRControllerFactory(),
                                                                        "open", new OpenControllerFactory(),
                                                                        "openi", new OpenIControllerFactory());
}
