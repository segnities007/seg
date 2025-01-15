package com.segnities007.seg.navigation.login


import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.Routes
import kotlinx.serialization.Serializable

@Serializable
object NavigationLoginRoute: Routes {

    @Serializable
    object SignIn: Route
    @Serializable
    object SignUp: Route
    @Serializable
    object ConfirmEmail: Route
    @Serializable
    object CreateAccount: Route

    override val routeList = listOf(
        SignIn,
        SignUp,
        ConfirmEmail,
        CreateAccount,
    )

}
