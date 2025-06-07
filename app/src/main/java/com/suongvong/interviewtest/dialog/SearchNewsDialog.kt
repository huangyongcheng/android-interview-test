package com.suongvong.interviewtest.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.enums.SearchType
import com.suongvong.interviewtest.extentions.setOnTextChangedListener
import com.suongvong.interviewtest.extentions.showIf
import com.suongvong.interviewtest.extentions.showKeyboard
import com.suongvong.interviewtest.interfaces.OnTextChangedListener
import com.suongvong.interviewtest.model.SearchParams
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchNewsDialog(
    context: Context,

    private val onSearch: (SearchParams) -> Unit
) : Dialog(context), View.OnClickListener, OnTextChangedListener {

    private var fromDate: String? = null
    private var toDate: String? = null
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private lateinit var sortOptions: List<String>
    private lateinit var availableCategories: List<String>
    private lateinit var searchIns: List<String>
    private lateinit var etKeyword: EditText
    private lateinit var btnFromDate: TextView
    private lateinit var btnToDate: TextView
    private lateinit var chipGroupCategory: ChipGroup
    private lateinit var chipGroupSearchIn: ChipGroup
    private lateinit var spinnerSort: Spinner
    private lateinit var btnSearch: TextView
    private lateinit var btnClose: TextView
    private lateinit var rgSearchType: RadioGroup
    private lateinit var llSearchIn: LinearLayout
    private lateinit var llCategory: LinearLayout
    private lateinit var llSearchDate: LinearLayout
    private lateinit var llSortBy: LinearLayout
    private lateinit var tvErrorText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search_news)

        etKeyword = findViewById(R.id.etKeyword)
        btnFromDate = findViewById(R.id.btnFromDate)
        btnToDate = findViewById(R.id.btnToDate)
        chipGroupCategory = findViewById(R.id.chipGroupCategories)
        chipGroupSearchIn = findViewById(R.id.chipGroupSearchIns)
        spinnerSort = findViewById(R.id.spinnerSort)
        btnSearch = findViewById(R.id.btnSearch)
        btnClose = findViewById(R.id.btnClose)
        rgSearchType = findViewById(R.id.rgSearchType)
        llSearchIn = findViewById(R.id.llSearchIn)
        llCategory = findViewById(R.id.llCategory)
        llSearchDate = findViewById(R.id.llSearchDate)
        llSortBy = findViewById(R.id.llSortBy)
        tvErrorText = findViewById(R.id.tvErrorText)

        btnFromDate.setOnClickListener(this)
        btnToDate.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
        btnClose.setOnClickListener(this)
        etKeyword.setOnTextChangedListener(this)
        etKeyword.showKeyboard()

        availableCategories = context.resources.getStringArray(R.array.news_categories).toList()
        sortOptions = context.resources.getStringArray(R.array.sort_options).toList()
        searchIns = context.resources.getStringArray(R.array.news_search_in).toList()

        rgSearchType.setOnCheckedChangeListener { _, checkedId ->
            val isEverything = checkedId == R.id.rbEverything
            llCategory.showIf(!isEverything)
            llSearchDate.showIf(isEverything)
            llSearchIn.showIf(isEverything)
            llSortBy.showIf(isEverything)

            if (isEverything && TextUtils.isEmpty(etKeyword.text.toString())) {
                tvErrorText.visibility = View.VISIBLE
            } else {
                tvErrorText.visibility = View.GONE
            }

        }

        chipGroupCategory.removeAllViews()

        availableCategories.forEach { item ->
            val chip = Chip(context).apply {
                text = item.capitalize(Locale.current)
                isCheckable = true
            }
            chipGroupCategory.addView(chip)
        }


        searchIns.forEach { item ->
            val chip = Chip(context).apply {
                text = item.capitalize(Locale.current)
                isCheckable = true
            }
            chipGroupSearchIn.addView(chip)
        }

        spinnerSort.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, sortOptions).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        setCancelable(true)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val today = LocalDate.now()
        val dialog = DatePickerDialog(context, R.style.CustomDatePickerDialogTheme, { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        }, today.year, today.monthValue - 1, today.dayOfMonth)
        dialog.show()

        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(context, R.color.warm_grey))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFromDate -> {
                showDatePicker { date ->
                    fromDate = date.format(dateFormatter)
                    btnFromDate.text = fromDate
                }
            }

            R.id.btnToDate -> {
                showDatePicker { date ->
                    toDate = date.format(dateFormatter)
                    btnToDate.text = toDate
                }
            }

            R.id.btnSearch -> {
                val keyword: String? = if (TextUtils.isEmpty(etKeyword.text)) {
                    null
                } else {
                    etKeyword.text.toString()
                }
                val selectedCategory = chipGroupCategory.findViewById<Chip>(chipGroupCategory.checkedChipId)?.text?.toString()
                val selectedSearchIns = chipGroupSearchIn.checkedChipIds.mapNotNull { id ->
                    chipGroupSearchIn.findViewById<Chip>(id)?.text?.toString()
                }
                val sortBy = spinnerSort.selectedItem?.toString() ?: sortOptions[0]

                if (keyword?.isNotBlank() == true || rgSearchType.checkedRadioButtonId == R.id.rbTopHeadlines) {
                    val params = SearchParams(
                        searchType = if (rgSearchType.checkedRadioButtonId == R.id.rbEverything) SearchType.EVERYTHING else SearchType.TOP_HEADLINE,
                        keyword = keyword,
                        fromDate = fromDate,
                        toDate = toDate,
                        category = selectedCategory,
                        searchIns = selectedSearchIns,
                        sortBy = sortBy
                    )
                    onSearch(params)
                    dismiss()
                } else {
                    tvErrorText.visibility = View.VISIBLE
                    etKeyword.background = ContextCompat.getDrawable(context, R.drawable.bg_border_error)
                }
            }

            R.id.btnClose -> {
                dismiss()
            }
        }
    }

    override fun onTextChanged(text: String) {
        tvErrorText.showIf(TextUtils.isEmpty(text))

    }
}
