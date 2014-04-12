package model.handlers;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundHandler {
    private static String baseFolder = "audio/";
    private static String effectsFolder = baseFolder + "effects/";
    private static String backgroundFolder = baseFolder + "background/";

    private Audio backgroundMusic;

    public static final int SHOT1 = 0;
    public static final int SHOT2 = 1;
    public static final int SHOT3 = 2;
    public static final int SHOT4 = 3;
    public static final int ENEMY_DIE1 = 4;
    public static final int ENEMY_DIE2 = 5;
    public static final int ENEMY_HIT1 = 6;
    public static final int ENEMY_HIT2 = 7;
    public static final int CONFIRM_CLICK = 8;

    private Map<Integer, Audio> effects;

    public SoundHandler() {
        loadAudio();
    }

    private void loadAudio() {
        try {
            backgroundMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(backgroundFolder + "Cut and Run.wav"));

            effects = new HashMap<Integer, Audio>();

            effects.put(SHOT1, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "shoot1.wav")));
            effects.put(SHOT2, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "shoot2.wav")));
            effects.put(SHOT3, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "shoot3.wav")));
            effects.put(SHOT4, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "shoot4.wav")));

            effects.put(ENEMY_DIE1, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "powerDown.wav")));
            effects.put(ENEMY_DIE2, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "whoop.wav")));

            effects.put(ENEMY_HIT1, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "enemyHit.wav")));
            effects.put(ENEMY_HIT2, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "enemyHit2.wav")));

            effects.put(CONFIRM_CLICK, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(effectsFolder + "confirmClick.wav")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Audio getBackgroundAudio() {
        return backgroundMusic;
    }

    public Audio getSoundEffect(int effect) {
        return effects.get(effect);
    }

    // Need to close audio streams
}