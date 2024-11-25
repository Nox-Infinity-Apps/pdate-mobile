package com.noxinfinity.pdate.ui.view_models.home

import com.noxinfinity.pdate.ui.view_models.base.IViewEvent


sealed class HomeEvent() {
    data object LoadMoreProfile : HomeEvent()
    data object LikeProfile : HomeEvent()
    data object DislikeProfile : HomeEvent()
    data object ThinkProfile : HomeEvent()
}