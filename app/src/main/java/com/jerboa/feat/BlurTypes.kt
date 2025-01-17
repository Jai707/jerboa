package com.jerboa.feat

import androidx.annotation.StringRes
import com.jerboa.R
import com.jerboa.toEnum
import it.vercruysse.lemmyapi.v0x19.datatypes.PostView

enum class BlurTypes(
    @StringRes val resId: Int,
) {
    Nothing(R.string.app_settings_nothing),
    NSFW(R.string.app_settings_blur_nsfw),
    NsfwExceptFromNsfwCommunities(R.string.app_settings_blur_nsfw_except_from_nsfw_communities),
    ;

    companion object {
        fun changeBlurTypeInsideCommunity(blurTypes: Int): Int =
            if (blurTypes.toEnum<BlurTypes>() == NsfwExceptFromNsfwCommunities) {
                Nothing.ordinal
            } else {
                blurTypes
            }
    }
}

fun BlurTypes.needBlur(postView: PostView) = this.needBlur(postView.community.nsfw, postView.post.nsfw)

fun BlurTypes.needBlur(
    isCommunityNsfw: Boolean,
    isPostNsfw: Boolean = isCommunityNsfw,
): Boolean {
    return when (this) {
        BlurTypes.Nothing -> false
        BlurTypes.NSFW, BlurTypes.NsfwExceptFromNsfwCommunities -> isPostNsfw
    }
}
