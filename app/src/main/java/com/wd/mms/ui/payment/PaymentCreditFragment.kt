package com.wd.mms.ui.payment

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.extensions.textChangeListener
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.payment.PaymentPresenter
import com.wd.mms.presentation.payment.PaymentView
import com.wd.mms.toothpick.DI
import com.wd.mms.ui.common.BaseFragment
import com.wd.mms.ui.common.SuccessDialog
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_payment.*
import toothpick.Toothpick
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PaymentCreditFragment : BaseFragment(), PaymentView,
        DialogInterface.OnDismissListener {


    @InjectPresenter
    lateinit var presenter: PaymentPresenter

    @Inject
    lateinit var resourceManager: ResourceManager


    @ProvidePresenter
    fun provide(): PaymentPresenter {
        val scope = "creditPayment${hashCode()}"
        return Toothpick.openScopes(DI.PAYMENT_SCOPE, scope)
                .getInstance(PaymentPresenter::class.java)
                .apply {
                    Toothpick.closeScope(scope)
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCard()
        initListeners()
        initSpinners()
    }


    private fun initCard() {
        cardNumber.setErrorColor(ContextCompat.getColor(context!!, R.color.colorRed))
    }

    private fun initListeners() {

        payButton.setOnClickListener {
            presenter.onPaymentClicked(
                    cardNumber.text.toString(),
                    monthSpinner.selectedItem.toString(),
                    yearSpinner.selectedItem.toString(),
                    cvcText.text.toString())
        }

        cardNumber.textChangeListener {
            if (cardNumberLayout.strokeColor == ContextCompat.getColor(context!!, R.color.colorRed)) {
                cardNumberLayout.strokeColor = ContextCompat.getColor(context!!, R.color.colorGrayLight)
            }
        }
        cvcText.textChangeListener {
            if (cvcLayout.strokeColor == ContextCompat.getColor(context!!, R.color.colorRed)) {
                cvcLayout.strokeColor = ContextCompat.getColor(context!!, R.color.colorGrayLight)
            }
        }

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (yearSpinnerLayout.strokeColor == ContextCompat.getColor(context!!, R.color.colorRed)) {
                    yearSpinnerLayout.strokeColor = ContextCompat.getColor(context!!, R.color.colorGrayLight)
                }
            }
        }
    }

    private fun initSpinners() {
        val monthList = ArrayList<String>()
        for (i in 1..12)
            monthList.add(i.toString())
        monthSpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, monthList)

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val yearList = ArrayList<String>()
        for (i in year..year + 10)
            yearList.add(i.toString())
        yearSpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, yearList)
    }

    override fun showProgress(isLoading: Boolean) {
        showProgressDialog(isLoading)
    }

    override fun showError(s: String) {
        AlertDialog.Builder(context!!)
                .setTitle(R.string.title_error)
                .setMessage(s)
                .setPositiveButton(R.string.title_ok) { _, _ -> }
                .create()
                .show()
    }

    override fun showSuccessMessage() {
        var fragment = childFragmentManager.findFragmentByTag("success_message")
        if (fragment != null) {
            childFragmentManager.executePendingTransactions()
        } else {
            fragment = SuccessDialog()
            fragment.show(childFragmentManager, "success_message")
        }

        val finish = Completable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe({
                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
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
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }


}
