package astral_mekanism.mixin.emextras.mekmm;

import java.util.EnumSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.jerry.mekaf.common.content.blocktype.AdvancedFactoryType;

import astral_mekanism.enums.AMEUpgrade;
import io.github.masyumero.emextras.common.integration.mekaf.regisrty.EMExtraAdvancedFactoryBlockTypes;
import io.github.masyumero.emextras.common.tier.EMExtraFactoryTier;
import io.github.masyumero.emextras.common.util.EMExtraEnumUtils;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.content.blocktype.BlockTypeTile;

@Mixin(value = EMExtraAdvancedFactoryBlockTypes.class, remap = false)
public class EMExtraAdvancedFactoryBlockTypesMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void astral_mekanism$clinitInject(CallbackInfo ci) {
        for (EMExtraFactoryTier tier : EMExtraEnumUtils.EMEXTRA_FACTORY_TIERS) {
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.DISSOLVING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.OXIDIZING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.WASHING),
                    AMEUpgrade.WATER_SUPPLY.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.LIQUIFYING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.PRESSURISED_REACTING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue(),
                    AMEUpgrade.WATER_SUPPLY.getValue(),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.CENTRIFUGING),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.CRYSTALLIZING),
                    AMEUpgrade.RADIOACTIVE_SEALING.getValue(),
                    AMEUpgrade.AIR_INTAKE.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.PIGMENT_EXTRACTING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
            astral_mekanism$addSupportedUpgrade(
                    EMExtraAdvancedFactoryBlockTypes.getEMExtraAdvancedFactory(tier, AdvancedFactoryType.PAINTING),
                    AMEUpgrade.COBBLESTONE_SUPPLY.getValue());
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
