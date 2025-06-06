package com.gihub.cidite.test.mcrider_bug_fix.client;


import com.gihub.cidite.test.mcrider_bug_fix.client.config.SimpleConfig;
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
    public static SimpleConfig CONFIG = SimpleConfig.of("mcrider_bugfix").provider(Mcrider_bug_fixClient::provider).request();
    //public static final int gameAcceleration = CONFIG.getOrDefault( "game_acceleration", 3 );
    public static final int gameAcceleration = 1;


    @Override
    public void onInitializeClient() {
    }

    private static String provider(String filename) {
        return "#게임 배속. 1~6의 숫자만 허용됩니다. \ngame_acceleration=3";
    }

}
