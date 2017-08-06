package ru.inno.earthquakes.di.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.inno.earthquakes.model.PermissionsManager;
import ru.inno.earthquakes.model.settings.SettingsRepository;

/**
 * @author Artur Badretdinov (Gaket)
 *         21.07.17.
 */
@Module
@Singleton
public class AppModule {

    public static final String APP_PREFS = "AppPrefs";
    private Context context;

    public AppModule(Context aContext) {
        context = aContext.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(SharedPreferences sharedPreferences) {
        return new SettingsRepository(sharedPreferences);
    }

    @Provides
    @Singleton
    PermissionsManager providePermissionsManager(RxPermissions rxPermissions) {
        return new PermissionsManager(rxPermissions);
    }

    @Provides
    @Singleton
    RxPermissions provideRxPermissions(Context context) {
        return RxPermissions.getInstance(context);
    }
}
