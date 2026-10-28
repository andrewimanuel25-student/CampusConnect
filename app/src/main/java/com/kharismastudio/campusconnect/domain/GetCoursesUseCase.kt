package com.kharismastudio.campusconnect.domain

import com.kharisma.campusconnect.shared.CourseRepository
import com.kharismastudio.campusconnect.shared.MataKuliah

class GetCoursesUseCase(private val repository: CourseRepository) {
    // Operator invoke membuat UseCase ini bisa dipanggil seperti fungsi biasa: getCoursesUseCase()
    operator fun invoke(): List<MataKuliah> = repository.getAllCourses()
}
