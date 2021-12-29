package de.nilsdruyen.myboardgames.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import de.nilsdruyen.myboardgames.model.BoardGameRepository
import de.nilsdruyen.myboardgames.model.BoardGameRepositoryImpl
import de.nilsdruyen.myboardgames.model.database.BoardGameDbDatasource
import de.nilsdruyen.myboardgames.model.database.BoardGameDbDatasourceImpl
import de.nilsdruyen.myboardgames.model.database.transformers.BoardGameTransformer
import de.nilsdruyen.myboardgames.model.database.transformers.BoardGameTransformerImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

  @Binds
  abstract fun bindsBoardGameRepository(repositoryImpl: BoardGameRepositoryImpl):
          BoardGameRepository

  @Binds
  abstract fun bindsBoardGameDbDatasource(datasource: BoardGameDbDatasourceImpl):
          BoardGameDbDatasource
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class TransformerModule {

  @Binds
  abstract fun bindsBoardGameTransformer(transformer: BoardGameTransformerImpl):
          BoardGameTransformer
}