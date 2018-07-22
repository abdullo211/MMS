package com.wd.mms.presentation.template

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.presentation.base.BasePresenter
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.TemplateBody
import com.wd.mms.toothpick.qualifier.TemplateTitle
import javax.inject.Inject

@InjectViewState
class TemplatePresenter @Inject constructor( @TemplateBody private val templateBody: PrimitiveWrapper<String?>)
    : BasePresenter<TemplateView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        templateBody.value?.let {
            viewState.setBody(it)
        }
    }
}