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
import kotlinx.android.synthetic.main.fragment_bleeding.*
import kotlinx.android.synthetic.main.fragment_cardiac_arrest.*
import moe.feng.common.stepperview.VerticalStepperItemView


class Bleeding : Fragment() {

    private val mSteppers = arrayOfNulls<VerticalStepperItemView>(6)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bleeding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSteppers[0] = view.findViewById(R.id.stepper_0)
        mSteppers[1] = view.findViewById(R.id.stepper_1)
        mSteppers[2] = view.findViewById(R.id.stepper_2)
        mSteppers[3]=view.findViewById(R.id.stepper_3)
        mSteppers[4]=view.findViewById(R.id.stepper_4)
        mSteppers[5]=view.findViewById(R.id.stepper_5)



        VerticalStepperItemView.bindSteppers(*mSteppers)

        button_next_0_bleeding.setOnClickListener { mSteppers[0]?.nextStep() }
        button_prev_1_bleeding.setOnClickListener { mSteppers[1]?.prevStep() }
        button_next_1_bleeding.setOnClickListener { mSteppers[1]?.nextStep() }
        button_prev_2_bleeding.setOnClickListener { mSteppers[2]?.prevStep() }
        button_next_2_bleeding.setOnClickListener { mSteppers[2]?.nextStep() }
        button_next_3_bleeding.setOnClickListener { mSteppers[3]?.nextStep() }
        button_prev_3_bleeding.setOnClickListener { mSteppers[3]?.prevStep() }
        button_next_4_bleeding.setOnClickListener { mSteppers[4]?.nextStep() }
        button_prev_4_bleeding.setOnClickListener { mSteppers[4]?.prevStep() }


        button_next_5_bleeding.setOnClickListener {
            Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show()
        }
        button_prev_5_bleeding.setOnClickListener {
            mSteppers[5]?.prevStep()
        }


    }
    }

