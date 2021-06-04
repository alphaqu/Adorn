package juuxel.adorn.platform

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

interface TagBridge {
    fun block(id: Identifier): Tag<Block>
    fun item(id: Identifier): Tag<Item>
}
