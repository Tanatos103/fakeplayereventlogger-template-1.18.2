package com.fakeplayereventlogger;

import carpet.CarpetExtension;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EventsFakePlayerExtension implements CarpetExtension, ModInitializer {

    private static final String MOD_ID = "eventsfakeplayers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_ID);
    }

}