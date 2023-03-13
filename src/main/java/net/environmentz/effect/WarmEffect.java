package net.environmentz.effect;

import net.environmentz.access.TemperatureManagerAccess;
import net.environmentz.init.ConfigInit;
import net.environmentz.temperature.TemperatureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

@SuppressWarnings("unused")
public class WarmEffect extends StatusEffect {

    public WarmEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    // @Override
    // public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    //     if (entity instanceof PlayerEntity) {
    //         TemperatureManager temperatureManager = ((TemperatureManagerAccess) (PlayerEntity) entity).getTemperatureManager();

    //         int coldProtectionAmount = temperatureManager.getPlayerColdProtectionAmount();
    //         if (coldProtectionAmount < ConfigInit.CONFIG.max_cold_protection_amount)
    //             temperatureManager.setPlayerColdProtectionAmount(coldProtectionAmount + ConfigInit.CONFIG.cold_protection_amount_addition);
    //         int playerTemperature = temperatureManager.getPlayerTemperature();
    //         if (playerTemperature < -100)
    //             temperatureManager.setPlayerTemperature(playerTemperature + ConfigInit.CONFIG.cold_protection_amount_addition);
    //     }
    // }

    // @Override
    // public boolean canApplyUpdateEffect(int duration, int amplifier) {
    //     int i = 50 >> amplifier;
    //     if (i > 0) {
    //         return duration % i == 0;
    //     }
    //     return true;
    // }

}
