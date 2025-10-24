package com.example.academy_tbc.screen.saved_addresses

enum class AddressType(val label: String) {
    HOME("Home"),
    OFFICE("Office");

    override fun toString(): String {
        return label
    }
}