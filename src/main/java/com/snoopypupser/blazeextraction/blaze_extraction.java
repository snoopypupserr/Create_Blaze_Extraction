package com.snoopypupser.blazeextraction;

import com.snoopypupser.blazeextraction.handler.handler_capability;
import com.snoopypupser.blazeextraction.handler.handler_spawner_interaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(blaze_extraction.MOD_ID)
public class blaze_extraction {
    public static final String MOD_ID = "blaze_extraction";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public blaze_extraction(IEventBus modEventBus) {
        modEventBus.addListener(handler_capability::registerCapabilities);
        NeoForge.EVENT_BUS.register(handler_spawner_interaction.class);
        LOGGER.info("Create: Blaze Extraction initialized");
    }
}
