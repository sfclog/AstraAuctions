package com.astrainteractive.astramarket.di.impl

import com.astrainteractive.astramarket.AstraMarket
import com.astrainteractive.astramarket.command.di.CommandsModule
import com.astrainteractive.astramarket.di.GuiModule
import com.astrainteractive.astramarket.di.RootModule
import com.astrainteractive.astramarket.di.UseCasesModule
import com.astrainteractive.astramarket.plugin.AuctionConfig
import com.astrainteractive.astramarket.plugin.Translation
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class CommandsModuleImpl(rootModule: RootModule) : CommandsModule {
    override val translation: Translation by rootModule.translation
    override val configuration: AuctionConfig by rootModule.configuration
    override val plugin: AstraMarket by rootModule.plugin
    override val scope: AsyncComponent by rootModule.scope
    override val dispatchers: BukkitDispatchers by rootModule.dispatchers
    override val guiModule: GuiModule by Provider {
        rootModule.guiModule
    }
    override val useCasesModule: UseCasesModule by Provider {
        rootModule.useCasesModule
    }
}
