package net.environmentz.access;

public interface PlayerEnvAccess {

    public void setHotEnvAffected(boolean affected);

    public void setColdEnvAffected(boolean affected);

    public void setPlayerTemperature(int temperature);

    public void setPlayerHeatProtectionAmount(int amount);

    public void setPlayerColdProtectionAmount(int amount);

    public void setPlayerWetIntensityValue(int amount);

    public void setPlayerHeatResistance(int amount);

    public void setPlayerColdResistance(int amount);

    public boolean isHotEnvAffected();

    public boolean isColdEnvAffected();

    public int getPlayerWetIntensityValue();

    public int getPlayerHeatProtectionAmount();

    public int getPlayerColdProtectionAmount();

    public int getPlayerTemperature();

    public int getPlayerHeatResistance();

    public int getPlayerColdResistance();

    public void compatSync();
}
