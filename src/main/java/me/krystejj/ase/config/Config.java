package me.krystejj.ase.config;

public class Config {
    public int range;
    public boolean centerPitchOnTp, allowCarpetsOnElevator, playUseSound, elevatorNotFoundMsg;

    public Config() {
        range = 20;
        centerPitchOnTp = true;
        allowCarpetsOnElevator = true;
        playUseSound = true;
        elevatorNotFoundMsg = false;
    }

    public String serialize() {
        return ConfigManager.GSON.toJson(this, Config.class);
    }
}
