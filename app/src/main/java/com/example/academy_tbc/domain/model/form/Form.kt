package com.example.academy_tbc.domain.model.form


data class Form(
    val form: List<List<Field>>
) {
    data class Field(
        val fieldId: Int,
        val hint: HintType,
        val fieldType: FieldType,
        val keyboard: String?,
        val required: Boolean,
        val isActive: Boolean,
        val icon: String
    )

}

enum class FieldType {
    INPUT,
    CHOOSER;

    companion object {
        fun fromString(value: String): FieldType {
            return when(value) {
                "input" -> INPUT
                "chooser" -> CHOOSER
                else -> INPUT
            }
        }
    }
}

enum class HintType {
    USERNAME,
    EMAIL,
    PHONE,
    FULLNAME,
    BIRTHDAY,
    GENDER,
    JEMALI;

    companion object {
        fun fromString(value: String): HintType {
            return when (value) {
                "UserName" -> USERNAME
                "Email" -> EMAIL
                "phone" -> PHONE
                "FullName" -> FULLNAME
                "Birthday" -> BIRTHDAY
                "Gender" -> GENDER
                else -> JEMALI
            }
        }
    }



}