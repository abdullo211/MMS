package com.wd.mms.ui.payment


import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.entity.Subscription
import com.wd.mms.extensions.textChangeListener
import com.wd.mms.model.interactor.payment.PaymentInteractor
import com.wd.mms.model.repository.payment.PaymentRepository
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.payment.PaymentPresenter
import com.wd.mms.presentation.payment.PaymentView
import com.wd.mms.toothpick.DI
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.SubscriptionId
import com.wd.mms.ui.common.BaseActivity
import com.wd.mms.ui.common.SuccessDialog
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_payment.*
import toothpick.Toothpick
import toothpick.config.Module
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PaymentActivity : BaseActivity(),
        PaymentView,
        DialogInterface.OnDismissListener {


    @InjectPresenter
    lateinit var presenter: PaymentPresenter

    @Inject
    lateinit var resourceManager: ResourceManager

    val subscription: Long
        get() = intent.getLongExtra(Subscription::id.name, -1)

    @ProvidePresenter
    fun provide(): PaymentPresenter {

        return Toothpick.openScopes(DI.SERVER_SCOPE, DI.PAYMENT_SCOPE)
                .apply {
                    installModules(object : Module() {
                        init {
                            bind(PrimitiveWrapper::class.java)
                                    .withName(SubscriptionId::class.java)
                                    .toInstance(PrimitiveWrapper(subscription))

                            bind(PaymentRepository::class.java)
                            bind(PaymentInteractor::class.java)
                        }
                    })
                }.getInstance(PaymentPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        initCard()
        initListeners()
        initSpinners()
    }

    private fun initCard() {
        cardNumber.setErrorColor(ContextCompat.getColor(this, R.color.colorRed))
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        payButton.setOnClickListener {
            presenter.onPaymentClicked(
                    cardNumber.text.toString(),
                    monthSpinner.selectedItem.toString(),
                    yearSpinner.selectedItem.toString(),
                    cvcText.text.toString())
        }

        cardNumber.textChangeListener {
            if (cardNumberLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                cardNumberLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }
        cvcText.textChangeListener {
            if (cvcLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                cvcLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (yearSpinnerLayout.strokeColor == ContextCompat.getColor(this@PaymentActivity, R.color.colorRed)) {
                    yearSpinnerLayout.strokeColor = ContextCompat.getColor(this@PaymentActivity, R.color.colorGrayLight)
                }
            }
        }
    }

    private fun initSpinners() {
        val monthList = ArrayList<String>()
        for (i in 1..12)
            monthList.add(i.toString())
        monthSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthList)

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val yearList = ArrayList<String>()
        for (i in year..year + 10)
            yearList.add(i.toString())
        yearSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearList)
    }

    override fun showProgress(isLoading: Boolean) {
        showProgressDialog(isLoading)
    }

    override fun showError(s: String) {
        AlertDialog.Builder(this)
                .setTitle(R.string.title_error)
                .setMessage(s)
                .setPositiveButton(R.string.title_ok) { _, _ -> }
                .create()
                .show()
    }

    override fun showSuccessMessage() {
        var fragment = supportFragmentManager.findFragmentByTag("success_message")
        if (fragment != null) {
            supportFragmentManager.executePendingTransactions()
        } else {
            fragment = SuccessDialog()
            fragment.show(supportFragmentManager, "success_message")
        }

        val finish = Completable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe({
                    setResult(Activity.RESULT_OK)
                    finish()
                }, {})
    }


    override fun showCardNumberError() {
        cardNumberLayout.strokeColor = resourceManager.getColor(R.color.colorRed)
    }

    override fun showExpMonthError() {
        monthSpinnerLayout.strokeColor = resourceManager.getColor(R.color.colorRed)
    }

    override fun showExpYearError() {
        yearSpinnerLayout.strokeColor = resourceManager.getColor(R.color.colorRed)
    }

    override fun showCVCError() {
        cvcLayout.strokeColor = resourceManager.getColor(R.color.colorRed)
    }

    override fun onDismiss(p0: DialogInterface?) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing)
            Toothpick.closeScope(DI.PAYMENT_SCOPE)
    }

}
