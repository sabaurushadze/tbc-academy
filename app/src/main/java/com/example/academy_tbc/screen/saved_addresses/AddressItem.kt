package com.example.academy_tbc.screen.saved_addresses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class AddressItem(
    val id: UUID,
    val addressType: String,
    val address: String,
    val icon: AddressIcon = AddressIcon.HOME
) : Parcelable