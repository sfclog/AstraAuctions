package ru.astrainteractive.astramarket.data

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astralibs.string.StringDesc
import java.util.UUID

class BukkitPlayerInteractionBridge(
    private val stringSerializer: KyoriComponentSerializer
) : PlayerInteractionBridge {
    override fun sendTranslationMessage(uuid: UUID, message: StringDesc.Raw) {
        val component = stringSerializer.toComponent(message)
        Bukkit.getPlayer(uuid)?.sendMessage(component)
    }

    override fun sendTranslationMessage(uuid: UUID, message: () -> StringDesc.Raw) {
        sendTranslationMessage(uuid, message.invoke())
    }

    override fun broadcast(string: StringDesc.Raw) {
        val component = stringSerializer.toComponent(string)
        Bukkit.broadcast(component)
    }

    override fun playSound(uuid: UUID, sound: String) {
        Bukkit.getPlayer(uuid)?.let { player ->
            player.playSound(player.location, sound, 1f, 1f)
        }
    }

    override fun playSound(uuid: UUID, sound: () -> String) {
        playSound(uuid, sound.invoke())
    }
}
