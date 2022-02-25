/*
 * Created by Nils Druyen on 12-29-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

package de.nilsdruyen.myboardgames.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import de.nilsdruyen.myboardgames.data.BoardGameRepository
import de.nilsdruyen.myboardgames.data.BoardGameRepositoryImpl
import de.nilsdruyen.myboardgames.data.database.BoardGameDbDatasource
import de.nilsdruyen.myboardgames.data.database.BoardGameDbDatasourceImpl
import de.nilsdruyen.myboardgames.data.database.transformers.BoardGameTransformer
import de.nilsdruyen.myboardgames.data.database.transformers.BoardGameTransformerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  @Singleton
  abstract fun bindsBoardGameRepository(repositoryImpl: BoardGameRepositoryImpl):
          BoardGameRepository

  @Binds
  @Singleton
  abstract fun bindsBoardGameDbDatasource(datasource: BoardGameDbDatasourceImpl):
          BoardGameDbDatasource

  @Binds
  @Reusable
  abstract fun bindsBoardGameTransformer(transformer: BoardGameTransformerImpl):
          BoardGameTransformer
}