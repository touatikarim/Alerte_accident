package com.example.alertaccident.ui.firstaids

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.alertaccident.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cardiac_arrest.*
import moe.feng.common.stepperview.VerticalStepperItemView

class CardiacArrest : Fragment() {


    private val mSteppers = arrayOfNulls<VerticalStepperItemView>(4)
    private lateinit var mNextBtn0: Button
    private lateinit var mNextBtn1: Button
    private lateinit var mPrevBtn1: Button
    private lateinit var mPrevBtn2: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cardiac_arrest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSteppers[0] = view.findViewById(R.id.stepper_0)
        mSteppers[1] = view.findViewById(R.id.stepper_1)
        mSteppers[2] = view.findViewById(R.id.stepper_2)
        mSteppers[3]=view.findViewById(R.id.stepper_3)

        VerticalStepperItemView.bindSteppers(*mSteppers)

        mNextBtn0 = view.findViewById(R.id.button_next_0)
        mNextBtn0.setOnClickListener { mSteppers[0]?.nextStep() }


        mPrevBtn1 = view.findViewById(R.id.button_prev_1)
        mPrevBtn1.setOnClickListener { mSteppers[1]?.prevStep() }

        mNextBtn1 = view.findViewById(R.id.button_next_1)
        mNextBtn1.setOnClickListener { mSteppers[1]?.nextStep() }

        mPrevBtn2 = view.findViewById(R.id.button_prev_2)
        mPrevBtn2.setOnClickListener { mSteppers[2]?.prevStep() }

        button_next_2.setOnClickListener {
            mSteppers[2]?.nextStep()
        }



        button_next_3.setOnClickListener {
            Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show()
        }
        button_prev_3.setOnClickListener {
            mSteppers[3]?.prevStep()
        }


    }
}

