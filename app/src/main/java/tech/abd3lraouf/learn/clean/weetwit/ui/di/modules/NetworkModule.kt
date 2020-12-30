package tech.abd3lraouf.learn.clean.weetwit.ui.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.abd3lraouf.learn.clean.weetwit.BuildConfig
import tech.abd3lraouf.learn.clean.weetwit.data.Repository
import tech.abd3lraouf.learn.clean.weetwit.data.api.TwitterService
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        @Provides
        @Singleton
        fun provideService(httpClient: OkHttpClient): TwitterService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .build()
                .create(TwitterService::class.java)
        }
    }

    @Binds
    abstract fun bindData(impl: Repository): IRepository
}