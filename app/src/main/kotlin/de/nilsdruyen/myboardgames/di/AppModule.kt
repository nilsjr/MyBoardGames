/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.di

import android.app.Application
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.nilsdruyen.myboardgames.data.database.AppDatabase
import de.nilsdruyen.myboardgames.data.database.daos.BoardGameDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule

@Module
@InstallIn(SingletonComponent::class)
object StaticAppModule {

  @Provides
  @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder().build()

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application, moshi: Moshi): AppDatabase =
    AppDatabase.getInstance(application, moshi)

  @Provides
  @Singleton
  fun providePosterDao(database: AppDatabase): BoardGameDao = database.boardGameDao()
}