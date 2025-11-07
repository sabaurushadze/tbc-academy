package com.example.academy_tbc.field

import androidx.lifecycle.ViewModel
import com.example.academy_tbc.box.FieldBox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class FieldsViewModel : ViewModel() {
    private val json = """
        [
            [
                {
                    "field_id": 1,
                    "hint": "UserName",
                    "field_type": "input",
                    "keyboard": "text",
                    "required": false,
                    "is_active": true,
                    "icon": "https://jemala.png/"
                },
                {
                    "field_id": 2,
                    "hint": "Email",
                    "field_type": "input",
                    "required": true,
                    "keyboard": "text",
                    "is_active": true,
                    "icon": "https://jemala.png/"
                }
            ],
            [
                {
                    "field_id": 3,
                    "hint": "Phone",
                    "field_type": "input",
                    "required": true,
                    "keyboard": "number",
                    "is_active": true,
                    "icon": "https://picsum.photos/id/237/200/300"
                }
            ],
            [
                {
                    "field_id": 4,
                    "hint": "FullName",
                    "field_type": "input",
                    "keyboard": "text",
                    "required": true,
                    "is_active": true,
                    "icon": "https://picsum.photos/id/237/200/300"
                },
                {
                    "field_id": 14,
                    "hint": "Jemali",
                    "field_type": "input",
                    "keyboard": "text",
                    "required": false,
                    "is_active": true,
                    "icon": "https://picsum.photos/id/237/200/300"
                },
                {
                    "field_id": 89,
                    "hint": "Birthday",
                    "field_type": "chooser",
                    "required": true,
                    "is_active": true,
                    "icon": "https://picsum.photos/id/237/200/300"
                },
                {
                    "field_id": 898,
                    "hint": "Gender",
                    "field_type": "chooser",
                    "required": true,
                    "is_active": true,
                    "icon": "https://picsum.photos/id/237/200/300"
                }
                
            ]
        ]
    """
    private val _fieldValues = MutableStateFlow<Map<Int, String?>>(emptyMap())
    val fieldValues: StateFlow<Map<Int, String?>> = _fieldValues.asStateFlow()

    private val _fieldContainers = MutableStateFlow(
        Json.decodeFromString<List<List<Field>>>(json).map { FieldBox(it) })
    val fieldContainers: StateFlow<List<FieldBox>> get() = _fieldContainers

    fun updateFieldValue(fieldId: Int, value: String?) {
        _fieldValues.value = _fieldValues.value.toMutableMap().apply {
            put(fieldId, value)
        }
    }


}