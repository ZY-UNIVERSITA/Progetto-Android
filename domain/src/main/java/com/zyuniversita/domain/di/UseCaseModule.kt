package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.FetchLanguageUseCase
import com.zyuniversita.domain.usecase.FetchLanguageUseCaseImpl
import com.zyuniversita.domain.usecase.StartFetchLanguageUseCase
import com.zyuniversita.domain.usecase.StartFetchLanguageUseCaseImpl
import com.zyuniversita.domain.usecase.imagerecognition.UploadImageToRecognizeUseCase
import com.zyuniversita.domain.usecase.imagerecognition.UploadImageToRecognizeUseCaseImpl
import com.zyuniversita.domain.usecase.mapping.GroupWordsAndUserDataByLevelUseCase
import com.zyuniversita.domain.usecase.mapping.GroupWordsAndUserDataByLevelUseCaseImpl
import com.zyuniversita.domain.usecase.mapping.GroupWordsByLevelUseCase
import com.zyuniversita.domain.usecase.mapping.GroupWordsByLevelUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.FetchCurrentDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.preferences.FetchCurrentDatabaseVersionUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.FetchUsernameUseCase
import com.zyuniversita.domain.usecase.preferences.FetchUsernameUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.FetchWordRepetitionUseCase
import com.zyuniversita.domain.usecase.preferences.FetchWordRepetitionUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.SetCurrentDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.preferences.SetCurrentDatabaseVersionUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.SetUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.SetUserIdUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.SetUsernameUseCase
import com.zyuniversita.domain.usecase.preferences.SetUsernameUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.SetWordRepetitionUseCase
import com.zyuniversita.domain.usecase.preferences.SetWordRepetitionUseCaseImpl
import com.zyuniversita.domain.usecase.question.GenerateMultipleChoiceQuestionUseCase
import com.zyuniversita.domain.usecase.question.GenerateMultipleChoiceQuestionUseCaseImpl
import com.zyuniversita.domain.usecase.question.GenerateWritingQuestionUseCase
import com.zyuniversita.domain.usecase.question.GenerateWritingQuestionUseCaseImpl
import com.zyuniversita.domain.usecase.question.UpdateAnswerSheetUseCase
import com.zyuniversita.domain.usecase.question.UpdateAnswerSheetUseCaseImpl
import com.zyuniversita.domain.usecase.question.UpdateRemainingWordUseCase
import com.zyuniversita.domain.usecase.question.UpdateRemainingWordUseCaseImpl
import com.zyuniversita.domain.usecase.singleword.FetchSingleWordProgressUseCase
import com.zyuniversita.domain.usecase.singleword.FetchSingleWordProgressUseCaseImpl
import com.zyuniversita.domain.usecase.singleword.StartFetchSingleWordProgressUseCase
import com.zyuniversita.domain.usecase.singleword.StartFetchSingleWordProgressUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.FetchUserGeneralStatsUseCase
import com.zyuniversita.domain.usecase.userdata.FetchUserGeneralStatsUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.InsertNewUserUseCase
import com.zyuniversita.domain.usecase.userdata.InsertNewUserUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.StartFetchUserGeneralStatsUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchUserGeneralStatsUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.UpdateUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserDataUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.UpdateUserPerformanceUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserPerformanceUseCaseImpl
import com.zyuniversita.domain.usecase.worddatabase.FetchLatestDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.worddatabase.FetchLatestDatabaseVersionUseCaseImpl
import com.zyuniversita.domain.usecase.worddatabase.UpdateWordDatabaseUseCase
import com.zyuniversita.domain.usecase.worddatabase.UpdateWordDatabaseUseCaseImpl
import com.zyuniversita.domain.usecase.words.FetchWordsAndUserDataByLanguageUseCase
import com.zyuniversita.domain.usecase.words.FetchWordsAndUserDataByLanguageUseCaseImpl
import com.zyuniversita.domain.usecase.words.FetchWordsForQuizUseCase
import com.zyuniversita.domain.usecase.words.FetchWordsForQuizUseCaseImpl
import com.zyuniversita.domain.usecase.words.StartFetchWordsAndUserDataByLanguageUseCase
import com.zyuniversita.domain.usecase.words.StartFetchWordsAndUserDataByLanguageUseCaseImpl
import com.zyuniversita.domain.usecase.words.StartFetchWordsForQuizUseCase
import com.zyuniversita.domain.usecase.words.StartFetchWordsForQuizUseCaseImpl
import com.zyuniversita.domain.usecase.words.UpdateUserDataWordSelectionUseCase
import com.zyuniversita.domain.usecase.words.UpdateUserDataWordSelectionUseCaseImpl
import com.zyuniversita.domain.usecase.wordslist.FetchWordsByLanguageGeneralListUseCase
import com.zyuniversita.domain.usecase.wordslist.FetchWordsByLanguageGeneralListUseCaseImpl
import com.zyuniversita.domain.usecase.wordslist.StartFetchWordsByLanguageGeneralListUseCase
import com.zyuniversita.domain.usecase.wordslist.StartFetchWordsByLanguageGeneralListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindFetchLanguageUseCase(
        fetchLanguageUseCaseImpl: FetchLanguageUseCaseImpl,
    ): FetchLanguageUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchLanguageUseCase(
        startFetchLanguageUseCaseImpl: StartFetchLanguageUseCaseImpl,
    ): StartFetchLanguageUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchWordsAndUserDataByLanguageUseCase(
        startFetchWordsAndUserDataByLanguageUseCaseImpl: StartFetchWordsAndUserDataByLanguageUseCaseImpl,
    ): StartFetchWordsAndUserDataByLanguageUseCase

    @Binds
    @Singleton
    abstract fun bindGenerateMultipleChoiceUseCase(
        generateMultipleChoiceQuestionUseCaseImpl: GenerateMultipleChoiceQuestionUseCaseImpl,
    ): GenerateMultipleChoiceQuestionUseCase

    @Binds
    @Singleton
    abstract fun bindGenerateWritingQuestionUseCase(
        generateWritingQuestionUseCaseImpl: GenerateWritingQuestionUseCaseImpl,
    ): GenerateWritingQuestionUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchWordsByLanguageGeneralListUseCase(
        startFetchWordsByLanguageGeneralListUseCaseImpl: StartFetchWordsByLanguageGeneralListUseCaseImpl,
    ): StartFetchWordsByLanguageGeneralListUseCase

    @Binds
    @Singleton
    abstract fun bindFetchWordsByLanguageGeneralListUseCase(
        fetchWordsByLanguageGeneralListUseCaseImpl: FetchWordsByLanguageGeneralListUseCaseImpl,
    ): FetchWordsByLanguageGeneralListUseCase

    @Binds
    @Singleton
    abstract fun bindGroupWordsByLevelUseCase(
        groupWordsByLevelUseCaseImpl: GroupWordsByLevelUseCaseImpl,
    ): GroupWordsByLevelUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchSingleWordProgressUseCase(
        startFetchSingleWordProgressUseCaseImpl: StartFetchSingleWordProgressUseCaseImpl,
    ): StartFetchSingleWordProgressUseCase

    @Binds
    @Singleton
    abstract fun bindFetchSingleWordProgressUseCase(
        fetchSingleWordProgressUseCaseImpl: FetchSingleWordProgressUseCaseImpl,
    ): FetchSingleWordProgressUseCase

    @Binds
    @Singleton
    abstract fun bindUploadImageToRecognizeUseCase(
        uploadImageToRecognizeUseCaseImpl: UploadImageToRecognizeUseCaseImpl,
    ): UploadImageToRecognizeUseCase

    @Binds
    @Singleton
    abstract fun bindFetchUsernameUseCase(
        fetchUsernameUseCaseImpl: FetchUsernameUseCaseImpl,
    ): FetchUsernameUseCase

    @Binds
    @Singleton
    abstract fun bindSetUsernameUseCase(
        setUsernameUseCaseImpl: SetUsernameUseCaseImpl,
    ): SetUsernameUseCase

    @Binds
    @Singleton
    abstract fun bindFetchCurrentDatabaseVersionUseCase(
        fetchCurrentDatabaseVersionUseCaseImpl: FetchCurrentDatabaseVersionUseCaseImpl,
    ): FetchCurrentDatabaseVersionUseCase

    @Binds
    @Singleton
    abstract fun bindSetCurrentDatabaseVersionUseCase(
        setCurrentDatabaseVersionUseCaseImpl: SetCurrentDatabaseVersionUseCaseImpl,
    ): SetCurrentDatabaseVersionUseCase

    @Binds
    @Singleton
    abstract fun bindFetchLatestDatabaseVersionUseCase(
        fetchLatestDatabaseVersionUseCaseImpl: FetchLatestDatabaseVersionUseCaseImpl,
    ): FetchLatestDatabaseVersionUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateWordDatabaseUseCase(
        updateWordDatabaseUseCaseImpl: UpdateWordDatabaseUseCaseImpl,
    ): UpdateWordDatabaseUseCase

    @Binds
    @Singleton
    abstract fun bindFetchWordsAndUserDataByLanguageUseCase(
        fetchWordsAndUserDataByLanguageUseCaseImpl: FetchWordsAndUserDataByLanguageUseCaseImpl,
    ): FetchWordsAndUserDataByLanguageUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateUserDataWordSelectionUseCase(
        updateUserDataWordSelectionUseCaseImpl: UpdateUserDataWordSelectionUseCaseImpl,
    ): UpdateUserDataWordSelectionUseCase

    @Binds
    @Singleton
    abstract fun bindGroupWordsAndUserDataByLevelUseCase(
        groupWordsAndUserDataByLevelUseCaseImpl: GroupWordsAndUserDataByLevelUseCaseImpl,
    ): GroupWordsAndUserDataByLevelUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchWordsForQuizUseCase(
        startFetchWordsForQuizUseCaseImpl: StartFetchWordsForQuizUseCaseImpl,
    ): StartFetchWordsForQuizUseCase

    @Binds
    @Singleton
    abstract fun bindFetchWordsForQuizUseCase(
        fetchWordsForQuizUseCaseImpl: FetchWordsForQuizUseCaseImpl,
    ): FetchWordsForQuizUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateUserDataUseCase(
        updateUserDataUseCaseImpl: UpdateUserDataUseCaseImpl,
    ): UpdateUserDataUseCase

    @Binds
    @Singleton
    abstract fun bindFetchWordRepetitionUseCase(
        fetchWordRepetitionUseCaseImpl: FetchWordRepetitionUseCaseImpl,
    ): FetchWordRepetitionUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateAnswerSheetUseCase(
        updateAnswerSheetUseCaseImpl: UpdateAnswerSheetUseCaseImpl,
    ): UpdateAnswerSheetUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateRemainingWordUseCase(
        updateRemainingWordUseCaseImpl: UpdateRemainingWordUseCaseImpl,
    ): UpdateRemainingWordUseCase

    @Binds
    @Singleton
    abstract fun bindInsertNewUserUseCase(
        insertNewUserUseCaseImpl: InsertNewUserUseCaseImpl,
    ): InsertNewUserUseCase

    @Binds
    @Singleton
    abstract fun bindSetUserIdUseCase(
        setUserIdUseCaseImpl: SetUserIdUseCaseImpl,
    ): SetUserIdUseCase

    @Binds
    @Singleton
    abstract fun bindFetchUserIdUseCase(
        fetchUserIdUseCaseImpl: FetchUserIdUseCaseImpl,
    ): FetchUserIdUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchUserGeneralStatsUseCase(
        startFetchUserGeneralStatsUseCaseImpl: StartFetchUserGeneralStatsUseCaseImpl,
    ): StartFetchUserGeneralStatsUseCase

    @Binds
    @Singleton
    abstract fun bindFetchUserGeneralStatsUseCase(
        fetchUserGeneralStatsUseCaseImpl: FetchUserGeneralStatsUseCaseImpl
    ): FetchUserGeneralStatsUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateUserPerformanceUseCase(
        updateUserPerformanceUseCaseImpl: UpdateUserPerformanceUseCaseImpl
    ): UpdateUserPerformanceUseCase

    @Binds
    @Singleton
    abstract fun bindSetWordRepetitionUseCase(
        setWordRepetitionUseCaseImpl: SetWordRepetitionUseCaseImpl
    ): SetWordRepetitionUseCase
}
