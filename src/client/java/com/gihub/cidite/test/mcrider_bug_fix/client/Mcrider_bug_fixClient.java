package com.gihub.cidite.test.mcrider_bug_fix.client;


import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mcrider_bug_fixClient implements ClientModInitializer {
    public static final String MOD_ID = "mcrider_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int tick_count = 0;
    public static boolean Riding = false;
    public static boolean isRiding = false;
    public static byte displayEntityModification = 0;
    public static byte gameAcceleration = 3;


    @Override
    public void onInitializeClient() {
    }


//
//    public float getGameTPS() {
//        gameTps = (byte) (20 * getGameAcceleration());
//    }

    public byte getGameAcceleration() {
        return gameAcceleration;
    }

    public byte setGameAcceleration(byte i) {
        gameAcceleration = i;
        return i;
    }

}
