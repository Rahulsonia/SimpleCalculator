package com.example.simplecalculator

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.simplecalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {



    var lengthOfNumber = 0
    var lastValueEntered: String = ""
    var errorOccurred = false
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var operatorEntered = false
        var dotEntered = false


        binding.apply {
            textView.showSoftInputOnFocus = false

            seven.setOnClickListener {
                submitNumber("7")
            }
            eight.setOnClickListener {
                submitNumber("8")
            }
            nine.setOnClickListener {
                submitNumber("9")
            }
            four.setOnClickListener {
                submitNumber("4")
            }
            five.setOnClickListener {
                submitNumber("5")
            }
            six.setOnClickListener {
                submitNumber("6")
            }
            one.setOnClickListener {
                submitNumber("1")
            }
            two.setOnClickListener {
                submitNumber("2")
            }
            three.setOnClickListener {
                submitNumber("3")
            }
            dot.setOnClickListener {
                if(!dotEntered && !errorOccurred){
                    lastValueEntered = "."
                    dotEntered = true
                    textView.append(".")
                }

            }
            zero.setOnClickListener {
                submitNumber("0")
            }
            divide.setOnClickListener {
                if(!errorOccurred && lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                    lastValueEntered = "/"
                    operatorEntered = true
                    dotEntered = false
                    lengthOfNumber = 0
                    textView.append("/")
                }
            }
            multiply.setOnClickListener {
                if(!errorOccurred &&lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                    lastValueEntered = "*"
                    operatorEntered = true
                    dotEntered = false
                    lengthOfNumber = 0
                    textView.append("*")
                }
            }
            subtract.setOnClickListener {
                if(!errorOccurred &&lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                    lastValueEntered = "-"
                    operatorEntered = true
                    dotEntered = false
                    lengthOfNumber = 0
                    textView.append("-")
                }
            }
            add.setOnClickListener {
                if(!errorOccurred &&lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                    lastValueEntered = "+"
                    operatorEntered = true
                    dotEntered = false
                    lengthOfNumber = 0
                    textView.append("+")
                }
            }

            deleteAll.setOnClickListener {
                operatorEntered = false
                dotEntered = false
                lengthOfNumber = 0
                textView.setText("")
                answerView.text = ""
            }

            equals.setOnClickListener {
                if(!errorOccurred && operatorEntered && lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                    try {
                        val expressionBuilder = ExpressionBuilder(textView.text.toString()).build()
                        val answer = expressionBuilder.evaluate()
                        lastValueEntered = answer.toString()
                        operatorEntered = false
                        textView.setText(answer.toString())
                        answerView.text = ""
                    }catch (e: Exception){
                        operatorEntered = false
                        errorOccurred = true
                        textView.setText("Error")
                    }
                }
            }

            backspace.setOnClickListener {
                val str = textView.text
                if(str.length <= 1){
                    lastValueEntered = ""
                    operatorEntered = false
                    dotEntered = false
                    textView.setText("")
                }else{
                    if(str.substring(str.length - 1) == "+" || str.substring(str.length - 1) == "-" || str.substring(str.length - 1) == "/" || str.substring(str.length - 1) == "*"){
                        Log.i("TAG", "onCreate: sure")
                        lastValueEntered = str.substring(str.length - 2, str.length - 1)
                        operatorEntered = false
                        textView.setText(str.substring(0, str.length - 1))
                    }
                    else if(str.substring(str.length-2 , str.length-1) == "+" || str.substring(str.length-2 , str.length-1) == "-" || str.substring(str.length-2 , str.length-1) == "/" || str.substring(str.length-2 , str.length-1) == "*"){
                        lastValueEntered = str.substring(str.length-2 , str.length-1)
                        operatorEntered = true
                        textView.setText(str.substring(0, str.length - 1))
                    }else if(str.substring(str.length - 1) == ".") {
                        dotEntered = false
                        lastValueEntered = str.substring(str.length-2, str.length-1)
                        textView.setText(str.substring(0, str.length - 1))
                    }else{
                        lastValueEntered = str.substring(str.length-2, str.length-1)
                        textView.setText(str.substring(0, str.length - 1))
                    }
                }

            }

            textView.addTextChangedListener {
                if(!errorOccurred && operatorEntered && lastValueEntered != "" && lastValueEntered != "." && lastValueEntered != "/" && lastValueEntered != "*" && lastValueEntered != "-" && lastValueEntered != "+"){
                   val expressionBuilder = ExpressionBuilder(it.toString()).build()
                   val answer = expressionBuilder.evaluate()
                   answerView.text = answer.toString()
                }
            }
        }
    }
    private fun submitNumber(num: String){
        if(lengthOfNumber <= 15 && !errorOccurred){
            lastValueEntered = num
            lengthOfNumber++
            val textView = findViewById<EditText>(R.id.textView)
            textView.append(num)
        }

    }

}