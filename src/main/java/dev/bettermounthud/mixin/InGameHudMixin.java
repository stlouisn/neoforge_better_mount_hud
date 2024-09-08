package dev.bettermounthud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("DataFlowIssue")
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

  @Shadow
  @Final
  private MinecraftClient client;

  @Redirect(method = "renderFoodLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
  private int bettermounthud$alwaysRenderFood(InGameHud inGameHud, LivingEntity entity) {
    return 0;
  }

  @Redirect(method = "maybeRenderJumpMeter", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getJumpingMount()Lnet/minecraft/entity/JumpingMount;"))
  private JumpingMount bettermounthud$switchBar(ClientPlayerEntity player) {
    var jumpingMount = player.getJumpingMount();
    if (client.options.jumpKey.isPressed()) {
      return jumpingMount;
    }
    return null;
  }

  @Redirect(method = "maybeRenderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getJumpingMount()Lnet/minecraft/entity/JumpingMount;"))
  private JumpingMount bettermounthud$getJumpingMount(ClientPlayerEntity player) {
    var jumpingMount = player.getJumpingMount();
    if (client.options.jumpKey.isPressed()) {
      return jumpingMount;
    }
    return null;
  }

  @Redirect(method = "maybeRenderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;shouldRenderExperience()Z"))
  private boolean bettermounthud$renderExperienceBar(InGameHud instance) {
    return this.client.interactionManager.hasExperienceBar();
  }

  @Redirect(method = "renderExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;shouldRenderExperience()Z"))
  private boolean bettermounthud$renderExperienceLevel(InGameHud instance) {
    return this.client.interactionManager.hasExperienceBar();
  }
}
