package astral_mekanism.mixin.emextras.mekmm;

import java.util.EnumSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.jerry.mekmm.common.content.blocktype.MoreMachineFactoryType;

import astral_mekanism.enums.AMEUpgrade;
import io.github.masyumero.emextras.common.integration.mekmm.registry.EMExtraMoreMachineBlockTypes;
import io.github.masyumero.emextras.common.tier.EMExtraFactoryTier;
import io.github.masyumero.emextras.common.util.EMExtraEnumUtils;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.content.blocktype.BlockTypeTile;

@Mixin(value = EMExtraMoreMachineBlockTypes.class, remap = false)
public class EMExtraMoreMachineBlockTypesMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void astral_mekanism$clinitInject(CallbackInfo ci) {
        for (EMExtraFactoryTier tier : EMExtraEnumUtils.EMEXTRA_FACTORY_TIERS) {
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.CNC_LATHING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.CNC_ROLLING_MILL),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.CNC_STAMPING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.PLANTING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.RECYCLING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraMoreMachineBlockTypes.getEMExtraMoreMachineFactory(tier, MoreMachineFactoryType.REPLICATING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue());
        }
    }

    @Unique
    private static void astral_mekanism$addSupportedUpgrade(BlockTypeTile<?> tile, Upgrade... additionalUpgrades) {
        if (tile == null) {
            return;
        }
        boolean has = tile.has(AttributeUpgradeSupport.class);
        Set<Upgrade> upgrades = has
                ? EnumSet.copyOf(tile.get(AttributeUpgradeSupport.class).supportedUpgrades())
                : EnumSet.noneOf(Upgrade.class);
        for (Upgrade upgrade : additionalUpgrades) {
            upgrades.add(upgrade);
        }
        if (has) {
            tile.remove(AttributeUpgradeSupport.class);
        }
        tile.add(new AttributeUpgradeSupport(upgrades));
    }
}
