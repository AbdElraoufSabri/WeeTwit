package tech.abd3lraouf.learn.clean.weetwit.ui.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import tech.abd3lraouf.learn.clean.weetwit.domain.features.GetTweetsUseCase
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main)
    }

    @Provides
    @Singleton
    fun provideUseCase(scope: CoroutineScope, repository: IRepository): GetTweetsUseCase {
        return GetTweetsUseCase(scope, repository)
    }
}