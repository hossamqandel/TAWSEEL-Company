package dev.hossam.tawseelcompany.feature_profile.presentation.profile

import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile

sealed interface ProfileEvent {
    data class SaveProfileChanges(val updateProfile: UpdateProfile) : ProfileEvent
    object ChangePassword : ProfileEvent
}