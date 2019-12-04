package com.example.android.myapplication.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.android.myapplication.R
import com.example.android.myapplication.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private var buttons = arrayOfNulls<Button>(9)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        for (i in 0 until 3){
            val linearLayout = LinearLayout(activity)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
            for (j in 0 until 3) {
                val button = Button(activity)
                buttons[i*3+j] = button
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )

                button.textSize = 60f

                button.setOnClickListener {
                    place(i*3+j)
                    gameOver = checkWin()
                    if (gameOver)
                        Toast.makeText(activity, "You win, $current!", Toast.LENGTH_SHORT).show()
                    if (!gameOver)
                    swap()
                }
                linearLayout.addView(button)
            }
            binding.board.addView(linearLayout)
        }
        binding.restart.setOnClickListener {
            restart()
        }
        return binding.root
    }


    private var current = "O"
    private var gameOver = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }



    private fun place(loc : Int) {
        if (buttons[loc]!!.text == "" && !gameOver)
            buttons[loc]!!.text = current
    }

    private fun restart() {
        for (i in buttons.indices){
            buttons[i]!!.text = ""
        }
        gameOver = false
        Toast.makeText(activity, "Restarted!", Toast.LENGTH_SHORT).show()
    }
    private fun swap(){
        current = if (current == "O")
            "X"
        else
            "O"
    }
    private fun checkWin(): Boolean {
        var buttonsText = arrayOfNulls<String>(9)
        for (i in buttons.indices){
            buttonsText[i] = buttons[i]!!.text.toString()
        }

        for (i in 0 until 3) {
            if (buttonsText[i] == "")
                continue
            else if (buttonsText[i] == buttonsText[i + 3] &&
                buttonsText[i + 3] == buttonsText[i + 6]
            )
                return true
        }
        for (i in 0 until 8 step 3){
            if (buttonsText[i] == "")
                continue
            else if (buttonsText[i] == buttonsText[i+1] &&
                buttonsText[i+1] == buttonsText[i+2])
                return true
        }
        if (buttonsText[0] == buttonsText[4] && buttonsText[4] == buttonsText[8] && buttonsText[0] != "")
            return true
        if (buttonsText[2] == buttonsText[4] && buttonsText[4] == buttonsText[6] && buttonsText[4] != "")
            return true
        return false
    }
}
