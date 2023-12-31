package ru.astrainteractive.astramarket.command.auction.di

import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astramarket.core.Translation
import ru.astrainteractive.astramarket.di.RootModule
import ru.astrainteractive.astramarket.domain.usecase.CreateAuctionUseCase
import ru.astrainteractive.astramarket.presentation.router.GuiRouter
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface AuctionCommandDependencies {
    val plugin: JavaPlugin
    val translationContext: BukkitTranslationContext
    val translation: Translation
    val router: GuiRouter
    val encoder: Encoder
    val scope: CoroutineScope
    val dispatchers: KotlinDispatchers
    val createAuctionUseCase: CreateAuctionUseCase

    class Default(rootModule: RootModule) : AuctionCommandDependencies {
        override val plugin: JavaPlugin by rootModule.bukkitCoreModule.plugin
        override val translationContext: BukkitTranslationContext = rootModule.bukkitCoreModule.translationContext
        override val translation: Translation by rootModule.coreModule.translation
        override val router: GuiRouter by Provider {
            rootModule.auctionGuiModule.router
        }
        override val encoder: Encoder by Provider {
            rootModule.bukkitCoreModule.encoder.value
        }
        override val scope: CoroutineScope by rootModule.coreModule.scope
        override val dispatchers: KotlinDispatchers = rootModule.coreModule.dispatchers
        override val createAuctionUseCase: CreateAuctionUseCase by Provider {
            rootModule.sharedDomainModule.createAuctionUseCase
        }
    }
}
