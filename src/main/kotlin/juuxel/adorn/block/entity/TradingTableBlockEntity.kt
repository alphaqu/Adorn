package juuxel.adorn.block.entity

import juuxel.adorn.block.TradingTableBlock
import juuxel.adorn.util.getTextComponent
import juuxel.adorn.util.putTextComponent
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.text.StringTextComponent
import net.minecraft.text.TextComponent
import java.util.UUID

class TradingTableBlockEntity : BlockEntity(TradingTableBlock.BLOCK_ENTITY_TYPE), BlockEntityClientSerializable {
    var owner: UUID? = null
    var ownerName: TextComponent? = StringTextComponent("???")

    fun setOwner(player: PlayerEntity) {
        owner = player.gameProfile.id
        ownerName = StringTextComponent(player.gameProfile.name)
        markDirty()
    }

    // NBT

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        owner = tag.getUuid(NBT_TRADING_OWNER)
        ownerName = tag.getTextComponent(NBT_TRADING_OWNER_NAME) ?: StringTextComponent("??")
    }

    override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
        if (owner != null) {
            tag.putUuid(NBT_TRADING_OWNER, owner)
        }

        ownerName?.let { name ->
            tag.putTextComponent(NBT_TRADING_OWNER_NAME, name)
        }
    }

    // Client NBT

    override fun toClientTag(tag: CompoundTag) = tag.apply {
        putTextComponent(NBT_TRADING_OWNER_NAME, ownerName ?: return@apply)
    }

    override fun fromClientTag(tag: CompoundTag) {
        if (ownerName != null) {
            ownerName = tag.getTextComponent(NBT_TRADING_OWNER_NAME)
        }
    }

    companion object {
        const val NBT_TRADING_OWNER = "TradingOwner"
        const val NBT_TRADING_OWNER_NAME = "TradingOwnerName"
    }
}