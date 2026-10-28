package com.kharismastudio.campusconnect.di

import com.kharismastudio.campusconnect.domain.GetCoursesUseCase
import com.kharisma.campusconnect.shared.CourseRepository
import com.kharisma.campusconnect.shared.CourseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Memberi tahu Hilt cara menyediakan CourseRepository
    @Provides
    fun provideCourseRepository(): CourseRepository = CourseRepositoryImpl()

    // Memberi tahu Hilt cara menyediakan GetCoursesUseCase dengan menyuntikkan repo di atas
    @Provides
    fun provideGetCoursesUseCase(repo: CourseRepository): GetCoursesUseCase =
        GetCoursesUseCase(repo)
}
