package com.example.loremperpus.ui.Lending

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.view.isVisible
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.LandingRequest
import com.example.loremperpus.databinding.ActivityFinalLendingFormBinding
import com.example.loremperpus.ui.MenuActivity
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinalLendingFormActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFinalLendingFormBinding
    private val viewModel: LandingBookViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinalLendingFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}