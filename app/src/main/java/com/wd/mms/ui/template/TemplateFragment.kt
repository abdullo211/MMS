package com.wd.mms.ui.template

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.entity.Template
import com.wd.mms.presentation.template.TemplatePresenter
import com.wd.mms.presentation.template.TemplateView
import com.wd.mms.toothpick.DI
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.TemplateBody
import com.wd.mms.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_template.*
import toothpick.Toothpick
import toothpick.config.Module

class TemplateFragment : BaseFragment(), TemplateView {

    @InjectPresenter
    lateinit var presenter: TemplatePresenter

    private val template: String? get() = arguments?.getString(Template::body.name)

    @ProvidePresenter
    fun provide(): TemplatePresenter {
        val scope = "template${hashCode()}"
        return Toothpick.openScopes(DI.SERVER_SCOPE, scope)
                .apply {
                    installModules(object : Module() {
                        init {
                            bind(PrimitiveWrapper::class.java)
                                    .withName(TemplateBody::class.java)
                                    .toInstance(PrimitiveWrapper(template))
                        }
                    })
                }
                .getInstance(TemplatePresenter::class.java)
                .apply {
                    Toothpick.closeScope(scope)
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_template, container, false)
    }

    override fun setBody(htmlBody: String) {
        webView.settings.javaScriptEnabled = true

        val mime = "text/html"
        val encoding = "utf-8"
        webView.loadDataWithBaseURL(null, htmlBody, mime, encoding, null)
    }


}