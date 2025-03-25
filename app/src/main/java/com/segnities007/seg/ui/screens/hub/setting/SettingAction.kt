package com.segnities007.seg.ui.screens.hub.setting

data class SettingAction(
    val onLogout: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDatePickerOpen: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
)
