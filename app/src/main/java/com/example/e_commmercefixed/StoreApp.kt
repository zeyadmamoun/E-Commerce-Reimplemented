package com.example.e_commmercefixed

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.e_commmercefixed.fragments.main.bottomNavigation.BottomNavigationViewModel
import com.example.e_commmercefixed.fragments.main.login.LoginViewModel
import com.example.e_commmercefixed.fragments.main.signup.SignupViewModel
import com.example.e_commmercefixed.fragments.main.welcome.WelcomeViewModel
import com.example.e_commmercefixed.fragments.splash.SplashViewModel
import com.example.e_commmercefixed.fragments.sub.home.HomeViewModel
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import com.example.e_commmercefixed.repositories.authentication.AuthRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

private const val TOKEN_PREFERENCE_NAME = "token_preferences"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
    name = TOKEN_PREFERENCE_NAME
)

class StoreApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single {
                HttpClient(CIO){
                    install(ContentNegotiation){
                        json()
                    }
                }
            }

            single<AuthRepository>{
                AuthRepositoryImpl(get(),tokenDataStore)
            }

            viewModel { SplashViewModel(get()) }
            viewModel { LoginViewModel(get()) }
            viewModel { BottomNavigationViewModel(get()) }
            viewModel { SignupViewModel(get()) }
            viewModel { WelcomeViewModel(get()) }
            viewModel { HomeViewModel(get()) }
        }

        startKoin{
            androidContext(this@StoreApp)
            modules(appModule)
        }
    }
}