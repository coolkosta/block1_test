package com.coolkosta.simbirsofttestapp.util

import androidx.annotation.DrawableRes
import com.coolkosta.core.R

enum class ImageResource(@DrawableRes val resourceId: Int) {
    IMAGE_CHILD(R.drawable.img_child),
    IMAGE_KID(R.drawable.image_kid),
    IMAGE_ANIMAL(R.drawable.image_animal),
    IMAGE_OLD(R.drawable.image_old),
    IMAGE_WORLD(R.drawable.image_world),
    IMAGE_RUNNER(R.drawable.image_runner),
    DEFAULT_IMAGE(R.drawable.image_charity);

    companion object {
        fun from(name: String): ImageResource {
            return when (name) {
                "image_child" -> IMAGE_CHILD
                "image_kid" -> IMAGE_KID
                "image_animal" -> IMAGE_ANIMAL
                "image_old" -> IMAGE_OLD
                "image_world" -> IMAGE_WORLD
                "image_runner" -> IMAGE_RUNNER
                else -> DEFAULT_IMAGE
            }
        }
    }
}