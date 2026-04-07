package com.rykova_e.kts_project.data.source.local.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["courseId", "authorId"],
)
data class CourseAuthorCrossRef(
    val courseId: Long,
    val authorId: Long
)

@Entity(
    primaryKeys = ["courseId", "reviewId"],
)
data class CourseReviewCrossRef(
    val courseId: Long,
    val reviewId: Long
)

data class CourseWithData(
    @Embedded
    val course: CourseEntity,

    @Relation(
        parentColumn = "course_id",
        entityColumn = "user_id",
        associateBy = Junction(
            CourseAuthorCrossRef::class,
            parentColumn = "courseId",
            entityColumn = "authorId"
        )
    )
    val authors: List<UserEntity>,

    @Relation(
        parentColumn = "course_id",
        entityColumn = "review_id",
        associateBy = Junction(
            CourseReviewCrossRef::class,
            parentColumn = "courseId",
            entityColumn = "reviewId"
        )
    )
    val review: ReviewEntity?
)

data class UserWithCourses(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "courseId",
        associateBy = Junction(CourseAuthorCrossRef::class)
    )
    val courses: List<CourseEntity>,
)


