package com.coolkosta.simbirsofttestapp.entity

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class HelpItem(
    @DrawableRes val imageId: Int,
    @StringRes val title: Int,
) : Parcelable
