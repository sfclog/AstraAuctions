package ru.astrainteractive.astramarket.di.impl

import ru.astrainteractive.astralibs.encoding.encoder.BukkitObjectEncoder
import ru.astrainteractive.astralibs.encoding.encoder.ObjectEncoder
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.logging.JUtilFileLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astramarket.AstraMarket
import ru.astrainteractive.astramarket.di.BukkitCoreModule
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import java.io.File

class BukkitCoreModuleImpl : BukkitCoreModule {

    override val plugin: Lateinit<AstraMarket> = Lateinit<AstraMarket>()

    override val encoder: Single<ObjectEncoder> = Single {
        BukkitObjectEncoder()
    }

    override val inventoryClickEventListener: Single<EventListener> = Single {
        DefaultInventoryClickEvent()
    }

    override val kyoriComponentSerializer: Reloadable<KyoriComponentSerializer> = Reloadable {
        KyoriComponentSerializer.Legacy
    }

    override val logger: Single<Logger> = Single {
        JUtilFileLogger(
            tag = "AstraMarket",
            folder = File(plugin.value.dataFolder, "logs"),
            logger = plugin.value.logger
        )
    }

    override val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onEnable = {
                inventoryClickEventListener.value.onEnable(plugin.value)
            },
            onDisable = {
                inventoryClickEventListener.value.onEnable(plugin.value)
            }
        )
    }
}
