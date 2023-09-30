package com.astrainteractive.astramarket.gui.di

import com.astrainteractive.astramarket.api.market.AuctionsAPI
import com.astrainteractive.astramarket.gui.di.factory.AuctionGuiFactory
import com.astrainteractive.astramarket.gui.di.factory.AuctionViewModelFactory
import com.astrainteractive.astramarket.gui.domain.di.GuiDomainModule
import com.astrainteractive.astramarket.gui.domain.mapping.AuctionSortTranslationMapping
import com.astrainteractive.astramarket.plugin.AuctionConfig
import com.astrainteractive.astramarket.plugin.Translation
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface AuctionGuiModule : Module {
    // Factories
    val auctionGuiFactory: AuctionGuiFactory
    val auctionViewModelFactory: AuctionViewModelFactory

    // modules
    val guiDomainModule: GuiDomainModule

    class Default(
        private val economyProvider: EconomyProvider,
        private val config: AuctionConfig,
        private val translation: Translation,
        private val serializer: Serializer,
        private val auctionApi: AuctionsAPI,
        private val dispatchers: BukkitDispatchers,
        private val auctionSortTranslationMapping: AuctionSortTranslationMapping
    ) : AuctionGuiModule {
        override val guiDomainModule: GuiDomainModule by Provider {
            GuiDomainModule.Default(
                translation = translation,
                configuration = config,
                economyProvider = economyProvider,
                auctionsAPI = auctionApi,
                serializer = serializer
            )
        }

        override val auctionViewModelFactory: AuctionViewModelFactory by Provider {
            AuctionViewModelFactory(
                dispatchers = dispatchers,
                auctionsAPI = auctionApi,
                serializer = serializer,
                guiDomainModule = guiDomainModule
            )
        }

        override val auctionGuiFactory: AuctionGuiFactory by Provider {
            AuctionGuiFactory(
                auctionViewModelFactory = auctionViewModelFactory,
                config = config,
                translation = translation,
                dispatchers = dispatchers,
                auctionSortTranslationMapping = auctionSortTranslationMapping,
                serializer = serializer
            )
        }
    }
}
