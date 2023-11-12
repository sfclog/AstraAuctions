package ru.astrainteractive.astramarket.domain.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astramarket.data.di.SharedDataModule
import ru.astrainteractive.astramarket.domain.mapping.AuctionSortTranslationMapping
import ru.astrainteractive.astramarket.domain.mapping.AuctionSortTranslationMappingImpl
import ru.astrainteractive.astramarket.domain.usecase.AuctionBuyUseCase
import ru.astrainteractive.astramarket.domain.usecase.AuctionBuyUseCaseImpl
import ru.astrainteractive.astramarket.domain.usecase.CreateAuctionUseCase
import ru.astrainteractive.astramarket.domain.usecase.CreateAuctionUseCaseImpl
import ru.astrainteractive.astramarket.domain.usecase.ExpireAuctionUseCase
import ru.astrainteractive.astramarket.domain.usecase.ExpireAuctionUseCaseImpl
import ru.astrainteractive.astramarket.domain.usecase.RemoveAuctionUseCase
import ru.astrainteractive.astramarket.domain.usecase.RemoveAuctionUseCaseImpl
import ru.astrainteractive.astramarket.plugin.AuctionConfig
import ru.astrainteractive.astramarket.plugin.Translation
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface SharedDomainModule {
    val sharedDataModule: SharedDataModule
    val platformSharedDomainModule: PlatformSharedDomainModule

    // Mappers
    val auctionSortTranslationMapping: AuctionSortTranslationMapping

    // UseCases
    val auctionBuyUseCase: AuctionBuyUseCase
    val createAuctionUseCase: CreateAuctionUseCase
    val expireAuctionUseCase: ExpireAuctionUseCase
    val removeAuctionUseCase: RemoveAuctionUseCase

    class Default(
        translation: Translation,
        configuration: AuctionConfig,
        economyProvider: EconomyProvider,
        sharedDataModuleFactory: Factory<SharedDataModule>,
        platformSharedDomainModuleFactory: Factory<PlatformSharedDomainModule>
    ) : SharedDomainModule {
        override val sharedDataModule: SharedDataModule by Provider {
            sharedDataModuleFactory.create()
        }
        override val platformSharedDomainModule: PlatformSharedDomainModule by Provider {
            platformSharedDomainModuleFactory.create()
        }
        override val auctionSortTranslationMapping: AuctionSortTranslationMapping by Provider {
            AuctionSortTranslationMappingImpl(
                translation = translation
            )
        }
        override val auctionBuyUseCase: AuctionBuyUseCase by Provider {
            AuctionBuyUseCaseImpl(
                translation = translation,
                config = configuration,
                economyProvider = economyProvider,
                auctionsBridge = sharedDataModule.auctionBridge,
                playerInteractionBridge = sharedDataModule.playerInteractionBridge
            )
        }
        override val createAuctionUseCase: CreateAuctionUseCase by Provider {
            CreateAuctionUseCaseImpl(
                translation = translation,
                config = configuration,
                auctionsBridge = sharedDataModule.auctionBridge,
                playerInteractionBridge = sharedDataModule.playerInteractionBridge
            )
        }
        override val expireAuctionUseCase: ExpireAuctionUseCase by Provider {
            ExpireAuctionUseCaseImpl(
                translation = translation,
                auctionsBridge = sharedDataModule.auctionBridge,
                playerInteractionBridge = sharedDataModule.playerInteractionBridge
            )
        }
        override val removeAuctionUseCase: RemoveAuctionUseCase by Provider {
            RemoveAuctionUseCaseImpl(
                translation = translation,
                config = configuration,
                auctionsBridge = sharedDataModule.auctionBridge,
                playerInteractionBridge = sharedDataModule.playerInteractionBridge
            )
        }
    }
}
